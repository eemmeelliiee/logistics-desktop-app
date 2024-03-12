package se.lu.ics.controllers;

import java.time.LocalDate;
import java.util.Arrays;

import javafx.util.Callback;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Pair;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.StringConverter;
import se.lu.ics.models.DataManager;
import se.lu.ics.models.DataService;
import se.lu.ics.models.Location;
import se.lu.ics.models.LocationStringConverter;
import se.lu.ics.models.Shipment;
import se.lu.ics.models.UpdateFieldWarehouse;
import se.lu.ics.models.Warehouse;
import se.lu.ics.models.Constants;

public class MainViewController {

    private DataManager dataManager;
    private DataService dataService;

    @FXML
    private Button buttonAddShipment;

    @FXML
    private Button buttonAddWarehouse;

    @FXML
    private Button buttonDeleteShipment;

    @FXML
    private Button buttonDeleteWarehouse;

    @FXML
    private ComboBox<Location> comboBoxAddWarehouseLocation;

    @FXML
    private AnchorPane mainViewAnchorPane;

    @FXML
    private Label labelSystemStatus;

    @FXML
    private Tab mainViewTab;

    @FXML
    private Tab shipmentTab;

    @FXML
    private TableColumn<Pair<Location, Double>, Double> tableColumnRegionsCurrentCapacity;

    @FXML
    private TableColumn<Pair<Location, Double>, Location> tableColumnRegionsLocation;

    @FXML
    private TableColumn<Shipment, String> tableColumnShipmentsCurrentWarehouse;

    @FXML
    private TableColumn<Shipment, String> tableColumnShipmentsID;

    @FXML
    private TableColumn<Shipment, String> tableColumnShipmentsStatus;

    @FXML
    private TableColumn<Warehouse, String> tableColumnWarehousesAddress;

    @FXML
    private TableColumn<Warehouse, Double> tableColumnWarehousesCapacity;

    @FXML
    private TableColumn<Warehouse, Double> tableColumnWarehousesCurrentCapacity;

    @FXML
    private TableColumn<Warehouse, LocalDate> tableColumnWarehousesInspec;

    @FXML
    private TableColumn<Warehouse, Location> tableColumnWarehousesLocation;

    @FXML
    private TableColumn<Warehouse, String> tableColumnWarehousesName;

    @FXML
    private TableView<Pair<Location, Double>> tableViewRegions;

    @FXML
    private TableView<Shipment> tableViewShipments;

    @FXML
    private TableView<Warehouse> tableViewWarehouses;

    @FXML
    private Text textAddShipment;

    @FXML
    private Text textAddWarehouse;

    @FXML
    private Text textAllRegions;

    @FXML
    private Text textAllShipments;

    @FXML
    private Text textAllWarehouses;

    @FXML
    private Text textBusiestWarehouse;

    @FXML
    private TextField textFieldAddWarehouseAddress;

    @FXML
    private TextField textFieldAddWarehouseCapacity;

    @FXML
    private TextField textFieldAddWarehouseName;

    @FXML
    private Text textIDAddShipment;

    @FXML
    private Text textInfoEdit;

    @FXML
    private Text textMainVIew;

    @FXML
    private Tab warehouseTab;

    @FXML
    void handleButtonAddShipment(ActionEvent event) {
        // Code to execute when the button is pressed
        dataManager.createShipment();
        labelSystemStatus.setText("Shipment created successgully!");

    }

    @FXML
void handleButtonAddWarehouse(ActionEvent event) {

    String name = textFieldAddWarehouseName.getText();
    String address = textFieldAddWarehouseAddress.getText();
    String capacity = textFieldAddWarehouseCapacity.getText();
    Location location = (Location) comboBoxAddWarehouseLocation.getValue();

    try {
        if (name.isEmpty() || address.isEmpty() || capacity.isEmpty() || location == null) {
            throw new Exception("Please fill in all fields");
        }

        // Check if capacity is a valid number
        double capacityValue;
        try {
            capacityValue = Double.parseDouble(capacity);
        } catch (NumberFormatException e) {
            throw new Exception("Capacity must be a number.");
        }

        dataManager.createWarehouse(name, location, address, capacityValue);
        labelSystemStatus.setText("Warehouse created successfully!");
        textFieldAddWarehouseAddress.clear();
        textFieldAddWarehouseCapacity.clear();
        textFieldAddWarehouseName.clear();
        comboBoxAddWarehouseLocation.getSelectionModel().clearSelection();
        comboBoxAddWarehouseLocation.setPromptText("Location");
        dataService.updateAll();
        textBusiestWarehouse.setText(dataService.getBusiestWarehouse());
        tableViewRegions.setItems(dataService.getCurrentAvailableCapacityForLocations());
        tableViewRegions.refresh(); // Refresh tableViewRegions
    } catch (Exception e) {
        // Create an alert dialog for error
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error Dialog");
        errorAlert.setHeaderText("Error creating warehouse");
        errorAlert.setContentText(e.getMessage());

        // Show the dialog
        errorAlert.showAndWait();
    }
}

@FXML
void handleButtonDeleteShipment(ActionEvent event) {
    Shipment selectedShipment = tableViewShipments.getSelectionModel().getSelectedItem();
    if (selectedShipment != null) {
        // Remove the selected shipment from the data source
        dataManager.deleteShipment(selectedShipment);
        // Refresh the table view
        tableViewShipments.getItems().remove(selectedShipment);
        labelSystemStatus.setText("Shipment deleted successfully!");
    } else {
        // Display an error message
        labelSystemStatus.setText(Constants.NO_ROW_SELECTED);

        // Create an alert dialog for error
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error Dialog");
        errorAlert.setHeaderText("Error deleting shipment");
        errorAlert.setContentText(Constants.NO_ROW_SELECTED);

        // Show the dialog
        errorAlert.showAndWait();
    }
}

@FXML
void handleButtonDeleteWarehouse(ActionEvent event) throws Exception {
    Warehouse selectedWarehouse = tableViewWarehouses.getSelectionModel().getSelectedItem();
    if (selectedWarehouse != null) {
        // Remove the selected warehouse from the data source
        dataManager.deleteWarehouse(selectedWarehouse);
        dataService.updateAll();
        // Refresh the table view
        tableViewWarehouses.getItems().remove(selectedWarehouse);
        labelSystemStatus.setText("Warehouse deleted successfully!");
        tableViewShipments.setItems(dataManager.readShipments());
        tableViewShipments.refresh(); // Refresh tableViewShipments
        textBusiestWarehouse.setText(dataService.getBusiestWarehouse());
        tableViewRegions.setItems(dataService.getCurrentAvailableCapacityForLocations());
        tableViewRegions.refresh(); // Refresh tableViewRegions
    } else {
        // Display an error message
        labelSystemStatus.setText(Constants.NO_ROW_SELECTED);

        // Create an alert dialog for error
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error Dialog");
        errorAlert.setHeaderText("Error deleting warehouse");
        errorAlert.setContentText(Constants.NO_ROW_SELECTED);

        // Show the dialog
        errorAlert.showAndWait();
    }
}

    @FXML
    public void handleTabSelection(Event event) {
        if (mainViewTab.isSelected()) {
            System.out.println("Tab selected");
            try {
                System.out.println("Updating all data");
                dataService.updateAll();
                refreshMainView();
            } catch (Exception e) {
                System.out.println("Exception caught");
                e.printStackTrace();
            }
            System.out.println("Populating table view");
            try {
                populateTableView();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    public void initialize() throws Exception {

        mainViewTab.setOnSelectionChanged(this::handleTabSelection);

        dataManager = DataManager.getInstance();
        dataService = DataService.getInstance();
        dataService.updateAll();

        initTableColumns();
        populateTableView();
        setEditable();

    }

    public void populateTableView() throws Exception {
        dataService.updateAll();

        tableViewRegions.setItems(dataService.getCurrentAvailableCapacityForLocations());
        tableViewShipments.setItems(dataManager.readShipments());
        tableViewWarehouses.setItems(dataManager.readWarehouses());
        textBusiestWarehouse.setText(dataService.getBusiestWarehouse());

    }

    private void refreshMainView() {
        tableViewRegions.refresh();
        tableViewShipments.refresh();
        tableViewWarehouses.refresh();
        textBusiestWarehouse.setText(dataService.getBusiestWarehouse());

    }

    private void setEditable() {
        tableViewShipments.setEditable(true);
        tableViewWarehouses.setEditable(true);
        tableColumnWarehousesName.setEditable(true);
        tableColumnWarehousesLocation.setEditable(true);
        tableColumnWarehousesAddress.setEditable(true);
        tableColumnWarehousesCapacity.setEditable(true);
        tableColumnShipmentsID.setEditable(true);
    }

    private void initTableColumns() {
        initWarehousesNameColumn();
        initRegionsColumns();
        initWarehousesLocationColumn();
        initWarehousesAddressColumn();
        initWarehousesCapacityColumns();
        initWarehousesCurrentCapacityColumn();
        initWarehousesInspectionDateColumn();
        initShipmentsColumns();
    }

    private void initWarehousesNameColumn() {
        tableColumnWarehousesName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnWarehousesName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnWarehousesName.setOnEditCommit(event -> {
            Warehouse warehouse = event.getRowValue();
            String newValue = event.getNewValue();
            UpdateFieldWarehouse field = UpdateFieldWarehouse.NAME; // Replace with the actual field you want to update
            try {
                dataManager.updateWarehouse(warehouse, field, newValue);
                labelSystemStatus.setText("Warehouse name updated successfully!");
            } catch (Exception e) {
                event.getOldValue(); // VIKTIGT reset to this value in table!!!
    
                // Create an alert dialog for error
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setHeaderText("Error updating warehouse");
                errorAlert.setContentText(e.getMessage());
    
                // Show the dialog
                errorAlert.showAndWait();
            }
        });
    }

    private void initRegionsColumns() {
        ComboBox<Location> comboBox = comboBoxAddWarehouseLocation;
        comboBox.getItems().addAll(Location.values());

        tableColumnRegionsLocation.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getKey()));
        tableColumnRegionsCurrentCapacity
                .setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValue()).asObject());
    }

    private void initWarehousesLocationColumn() {
        tableColumnWarehousesLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        tableColumnWarehousesLocation
                .setCellFactory(new Callback<TableColumn<Warehouse, Location>, TableCell<Warehouse, Location>>() {
                    @Override
                    public TableCell<Warehouse, Location> call(TableColumn<Warehouse, Location> param) {
                        return new TableCell<Warehouse, Location>() {
                            @Override
                            protected void updateItem(Location item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item == null || empty) {
                                    setText(null);
                                } else {
                                    setText(item.toString());
                                }
                            }
                        };
                    }
                });

                tableColumnWarehousesLocation.setOnEditCommit(event -> {
                    Warehouse warehouse = event.getRowValue();
                    Location newValue = event.getNewValue();
                    UpdateFieldWarehouse field = UpdateFieldWarehouse.LOCATION; // Replace with the actual field you want to update
                    try {
                        dataManager.updateWarehouse(warehouse, field, newValue);
                        labelSystemStatus.setText("Warehouse location updated successfully!");
                        dataService.updateAll();
                        tableViewRegions.getItems().clear();
                        tableViewShipments.getItems().clear();
                        tableViewShipments.setItems(dataManager.readShipments());
                        tableViewRegions.setItems(dataService.getCurrentAvailableCapacityForLocations());
                        textBusiestWarehouse.setText(dataService.getBusiestWarehouse());
                    } catch (Exception e) {
                        event.getOldValue(); // VIKTIGT reset to this value in table!!!
                
                        // Create an alert dialog for error
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error Dialog");
                        errorAlert.setHeaderText("Error updating warehouse location");
                        errorAlert.setContentText(e.getMessage());
                
                        // Show the dialog
                        errorAlert.showAndWait();
                    }
                });
    }

    private void initWarehousesAddressColumn() {
        tableColumnWarehousesAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tableColumnWarehousesAddress.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnWarehousesAddress.setOnEditCommit(event -> {
            Warehouse warehouse = event.getRowValue();
            String newValue = event.getNewValue();
            UpdateFieldWarehouse field = UpdateFieldWarehouse.ADDRESS; // Replace with the actual field you want to update
            try {
                dataManager.updateWarehouse(warehouse, field, newValue);
                labelSystemStatus.setText("Warehouse address updated successfully!");
            } catch (Exception e) {
                event.getOldValue(); // VIKTIGT reset to this value in table!!!
        
                // Create an alert dialog for error
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setHeaderText("Error updating warehouse address");
                errorAlert.setContentText(e.getMessage());
        
                // Show the dialog
                errorAlert.showAndWait();
            }
        });
    }

    private void initWarehousesCapacityColumns() {
        tableColumnWarehousesCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        tableColumnWarehousesCapacity.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Double>() {
            @Override
            public String toString(Double value) {
                return value != null ? value.toString() : "";
            }

            @Override
            public Double fromString(String string) {
                return Double.valueOf(string);
            }
        }));
        tableColumnWarehousesCapacity.setOnEditCommit(event -> {
            Warehouse warehouse = event.getRowValue();
            Double newValue = event.getNewValue();
            UpdateFieldWarehouse field = UpdateFieldWarehouse.CAPACITY; // Replace with the actual field you want to update
            try {
                dataManager.updateWarehouse(warehouse, field, newValue);
                labelSystemStatus.setText("Warehouse capacity updated successfully!");
                dataService.updateAll();
                tableViewRegions.setItems(dataService.getCurrentAvailableCapacityForLocations());
                tableViewWarehouses.setItems(dataManager.readWarehouses());
                tableViewShipments.setItems(dataManager.readShipments());
                textBusiestWarehouse.setText(dataService.getBusiestWarehouse());
        
            } catch (Exception e) {
                try {
                    event.getTableView().getItems().get(event.getTablePosition().getRow())
                            .setCapacity(event.getOldValue());
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
        
                // Create an alert dialog for error
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setHeaderText("Error updating warehouse capacity");
                errorAlert.setContentText(e.getMessage());
        
                // Show the dialog
                errorAlert.showAndWait();
            }
        });
    }

    private void initWarehousesCurrentCapacityColumn() {
        tableColumnWarehousesCurrentCapacity
                .setCellValueFactory(new PropertyValueFactory<>("currentAvailableCapacity"));
        tableColumnWarehousesCurrentCapacity.setCellFactory(new DoubleTableCellFactory());
    }

    private void initWarehousesInspectionDateColumn() {
        tableColumnWarehousesInspec.setCellValueFactory(new PropertyValueFactory<>("mostRecentInspectionDate"));
        tableColumnWarehousesInspec.setCellFactory(new LocalDateTableCellFactory());
    }

    private void initShipmentsColumns() {
        tableColumnShipmentsID
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShipmentId()));
        tableColumnShipmentsID.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnShipmentsID.setOnEditCommit(event -> {
            Shipment shipment = event.getRowValue();
        
            String newValue = event.getNewValue();
            String newValueAsStringProperty = newValue;
        
            try {
                dataManager.updateShipmentId(shipment, newValueAsStringProperty);
                labelSystemStatus.setText("Shipment ID updated successfully!");
            } catch (Exception e) {
                event.getOldValue(); // VIKTIGT reset to this value in table!!!
                labelSystemStatus.setText(e.getMessage());
        
                // Create an alert dialog for error
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error Dialog");
                errorAlert.setHeaderText("Error updating shipment ID");
                errorAlert.setContentText(e.getMessage());
        
                // Show the dialog
                errorAlert.showAndWait();
            }
        });

        tableColumnShipmentsStatus
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLabel()));
        tableColumnShipmentsStatus.setCellFactory(TextFieldTableCell.forTableColumn());

        tableColumnShipmentsCurrentWarehouse
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCurrentWarehouse()));
        tableColumnWarehousesLocation.setCellFactory(new LocationTableCellFactory());
    }

    // Define custom cell factories
    private class LocationTableCellFactory
            implements Callback<TableColumn<Warehouse, Location>, TableCell<Warehouse, Location>> {
        @Override
        public TableCell<Warehouse, Location> call(TableColumn<Warehouse, Location> param) {
            return new TableCell<Warehouse, Location>() {
                @Override
                protected void updateItem(Location item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                }
            };
        }
    }

    private class DoubleTableCellFactory
            implements Callback<TableColumn<Warehouse, Double>, TableCell<Warehouse, Double>> {
        @Override
        public TableCell<Warehouse, Double> call(TableColumn<Warehouse, Double> param) {
            return new TableCell<Warehouse, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.toString());
                }
            };
        }
    }

    private class LocalDateTableCellFactory
            implements Callback<TableColumn<Warehouse, LocalDate>, TableCell<Warehouse, LocalDate>> {
        @Override
        public TableCell<Warehouse, LocalDate> call(TableColumn<Warehouse, LocalDate> param) {
            return new TableCell<Warehouse, LocalDate>() {
                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setText((empty || item == null) ? null : item.toString());
                }
            };
        }
    }

}
