package se.lu.ics.controllers;

import se.lu.ics.models.DataManager;
import se.lu.ics.models.DataService;
import se.lu.ics.models.Direction;
import se.lu.ics.models.InspectionLog;
import se.lu.ics.models.Shipment;
import se.lu.ics.models.ShipmentLog;
import se.lu.ics.models.UpdateFieldInspectionLog;
import se.lu.ics.models.UpdateFieldShipmentLog;
import se.lu.ics.models.Warehouse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ShipmentTabController {

    private DataManager dataManager;

    private DataService dataService;

    public static String warningMessage = null;

    public void updateTextSystemStatus() {
        if (warningMessage != null) {
            textSystemStatus.setText(warningMessage);
            warningMessage = null; // Clear the warning message after displaying it
        }
    }

    @FXML
    private Button buttonCreateInspectionLog;

    @FXML
    private Button buttonCreateShipmentLog;

    @FXML
    private Button buttonDeleteInspectionLog;

    @FXML
    private Button buttonDeleteShipmentLog;

    @FXML
    private ComboBox<Warehouse> comboBoxCreateInspectionLogWarehouse;

    @FXML
    private ComboBox<Direction> comboBoxCreateShipmentLogDirection;

    @FXML
    private ComboBox<Warehouse> comboBoxCreateShipmentLogWarehouse;

    @FXML
    private ComboBox<Shipment> comboBoxSelectShipment;

    @FXML
    private DatePicker datePickerCreateInspectionLog;

    @FXML
    private DatePicker datePickerCreateShipmentLog;

    @FXML
    private AnchorPane shipmentTabAnchorPane;

    @FXML
    private TableColumn<InspectionLog, LocalDate> tableColumnInspectionLogsDate;

    @FXML
    private TableColumn<InspectionLog, String> tableColumnInspectionLogsInspector;

    @FXML
    private TableColumn<InspectionLog, String> tableColumnInspectionLogsResult;

    @FXML
    private TableColumn<InspectionLog, Shipment> tableColumnInspectionLogsShipmentID;

    @FXML
    private TableColumn<InspectionLog, Warehouse> tableColumnInspectionLogsWarehouse;

    @FXML
    private TableColumn<ShipmentLog, LocalDate> tableColumnShipmentLogsDate;

    @FXML
    private TableColumn<ShipmentLog, Direction> tableColumnShipmentLogsDirection;

    @FXML
    private TableColumn<ShipmentLog, String> tableColumnShipmentLogsShipmentID;

    @FXML
    private TableColumn<ShipmentLog, Warehouse> tableColumnShipmentLogsWarehouse;

    @FXML
    private TableView<InspectionLog> tableViewInspectionLogs;

    @FXML
    private TableView<ShipmentLog> tableViewShipmentLogs;

    @FXML
    private TextField textFieldInspector;

    @FXML
    private TextField textFieldResult;

    @FXML
    private Text textAllInspectionLogs;

    @FXML
    private Text textAllShipmentLogs;

    @FXML
    private Text textCreateInspectionLog;

    @FXML
    private Text textCreateShipmentLog;

    @FXML
    private Text textEditInfo;

    @FXML
    private Text textShipmentManager;

    @FXML
    private Text textSystemStatus;

    @FXML
    private Text textTheActualAmount;

    @FXML
    private Text textTotalAmountOfWarehouses;

    @FXML
    private Button buttonUpdateInspection;

    @FXML
    private Button buttonUpdateShipment;

    @FXML
    void handleButtonUpdateShipment(ActionEvent event) {
        try {
            // Get the selected shipment log
            ShipmentLog selectedShipmentLog = tableViewShipmentLogs.getSelectionModel().getSelectedItem();

            if (selectedShipmentLog == null) {
                // Create an alert dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error updating shipment log");
                alert.setContentText("Please select a shipment log to update");

                // Show the dialog
                alert.showAndWait();
                return;
            }

            LocalDate date = datePickerCreateShipmentLog.getValue();
            Direction direction = comboBoxCreateShipmentLogDirection.getValue();
            Warehouse warehouse = comboBoxCreateShipmentLogWarehouse.getValue();

            // Check if all fields are null
            if (date == null && direction == null && warehouse == null) {
                // Create an alert dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error updating shipment log");
                alert.setContentText("No fields to update. Please fill in at least one field.");

                // Show the dialog
                alert.showAndWait();
                return;
            }

            // Update the selected shipment log
            if (date != null) {
                dataManager.updateShipmentLog(selectedShipmentLog, UpdateFieldShipmentLog.DATE, date);
            }
            if (direction != null) {
                dataManager.updateShipmentLog(selectedShipmentLog, UpdateFieldShipmentLog.DIRECTION, direction);
            }
            if (warehouse != null) {
                dataManager.updateShipmentLog(selectedShipmentLog, UpdateFieldShipmentLog.WAREHOUSE, warehouse);
            }

            textSystemStatus.setText("Shipment log updated successfully!");

            // Refresh the TableView
            tableViewShipmentLogs.setItems(dataService.getShipmentLogsForShipment(comboBoxSelectShipment.getValue()));
            textTheActualAmount.setText(String.valueOf(comboBoxSelectShipment.getValue().getTotalNumberOfWarehouses()));

            tableViewShipmentLogs.refresh();

        } catch (Exception e) {
            // Create an alert dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error updating shipment log");
            alert.setContentText(e.getMessage());

            // Show the dialog
            alert.showAndWait();
        }
    }

    @FXML
    void handleButtonUpdateInspection(ActionEvent event) {
        try {
            // Get the selected inspection log
            InspectionLog selectedInspectionLog = tableViewInspectionLogs.getSelectionModel().getSelectedItem();

            if (selectedInspectionLog == null) {
                // Create an alert dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error updating inspection log");
                alert.setContentText("Please select an inspection log to update");

                // Show the dialog
                alert.showAndWait();
                return;
            }

            LocalDate date = datePickerCreateInspectionLog.getValue();
            Warehouse warehouse = comboBoxCreateInspectionLogWarehouse.getValue();
            Shipment shipment = comboBoxSelectShipment.getValue();
            String inspector = textFieldInspector.getText();
            String result = textFieldResult.getText();

            // Check if all fields are null or empty
            if (date == null && warehouse == null && shipment == null && (inspector == null || inspector.isEmpty())
                    && (result == null || result.isEmpty())) {
                // Create an alert dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error updating inspection log");
                alert.setContentText("No fields to update. Please fill in at least one field.");

                // Show the dialog
                alert.showAndWait();
                return;
            }

            // Update the selected inspection log
            if (date != null) {
                dataManager.updateInspectionLog(selectedInspectionLog, UpdateFieldInspectionLog.DATE, date);
            }
            if (warehouse != null) {
                dataManager.updateInspectionLog(selectedInspectionLog, UpdateFieldInspectionLog.WAREHOUSE, warehouse);
            }
            if (shipment != null) {
                dataManager.updateInspectionLog(selectedInspectionLog, UpdateFieldInspectionLog.SHIPMENT, shipment);
            }
            if (inspector != null && !inspector.isEmpty()) {
                dataManager.updateInspectionLog(selectedInspectionLog, UpdateFieldInspectionLog.INSPECTOR, inspector);
            }
            if (result != null && !result.isEmpty()) {
                dataManager.updateInspectionLog(selectedInspectionLog, UpdateFieldInspectionLog.RESULT, result);
            }

            textSystemStatus.setText("Inspection log updated successfully!");

            // Refresh the TableView
            tableViewInspectionLogs.refresh();
            textTheActualAmount.setText(String.valueOf(comboBoxSelectShipment.getValue().getTotalNumberOfWarehouses()));
            tableViewInspectionLogs
                    .setItems(dataService.getInspectionsLogsForShipment(comboBoxSelectShipment.getValue()));
            tableViewShipmentLogs.setItems(dataService.getShipmentLogsForShipment(comboBoxSelectShipment.getValue()));

        } catch (Exception e) {
            // Create an alert dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error updating inspection log");
            alert.setContentText(e.getMessage());

            // Show the dialog
            alert.showAndWait();
        }
    }

    @FXML
    void handleButtonCreateInspectionLog(ActionEvent event) {
        try {
            LocalDate date = datePickerCreateInspectionLog.getValue();
            Warehouse warehouse = comboBoxCreateInspectionLogWarehouse.getValue();
            Shipment shipment = comboBoxSelectShipment.getValue();
            String inspector = textFieldInspector.getText();
            String result = textFieldResult.getText();

            if (shipment == null) {
                // Create an alert dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error creating inspection log");
                alert.setContentText("Please select a shipment");

                // Show the dialog
                alert.showAndWait();
                return;
            }

            if (date != null && warehouse != null && inspector != null && !inspector.isEmpty()
                    && result != null && !result.isEmpty()) {
                InspectionLog inspectionLog = dataManager.createInspectionLog(shipment, warehouse, date, inspector,
                        result);
                textSystemStatus.setText("Inspection log created successfully");
                Shipment selectedShipment = comboBoxSelectShipment.getValue();
                if (selectedShipment != null) {
                    ObservableList<InspectionLog> inspectionLogs = dataService
                            .getInspectionsLogsForShipment(selectedShipment);
                    ObservableList<ShipmentLog> shipmentLogs = dataService.getShipmentLogsForShipment(selectedShipment);

                    // Initialize or update the TableViews

                    tableViewInspectionLogs.setItems(inspectionLogs);
                    tableViewShipmentLogs.setItems(shipmentLogs);
                }
            } else {
                // Create an alert dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error creating inspection log");
                alert.setContentText("Please fill in all fields");

                // Show the dialog
                alert.showAndWait();
            }
        } catch (Exception e) {
            // Create an alert dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error creating inspection log");
            alert.setContentText(e.getMessage());

            // Show the dialog
            alert.showAndWait();
        }
    }

    @FXML
    void handleButtonCreateShipmentLog(ActionEvent event) {
        try {
            LocalDate date = datePickerCreateShipmentLog.getValue();
            Direction direction = comboBoxCreateShipmentLogDirection.getValue();
            Warehouse warehouse = comboBoxCreateShipmentLogWarehouse.getValue();
            Shipment shipment = comboBoxSelectShipment.getValue();

            if (shipment == null) {
                // Create an alert dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error creating shipment log");
                alert.setContentText("Please select a shipment");

                // Show the dialog
                alert.showAndWait();
                return;
            }

            if (date != null && direction != null && warehouse != null) {
                dataManager.createShipmentLog(date, direction, warehouse, shipment);
                if (ShipmentTabController.warningMessage != null) {
                    // Create an alert dialog with the warning message
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning Dialog");
                    alert.setHeaderText("Warning creating shipment log");
                    alert.setContentText(ShipmentTabController.warningMessage);

                    // Show the dialog
                    alert.showAndWait();

                    ShipmentTabController.warningMessage = null; // Clear the warning message after displaying it
                    textSystemStatus.setText("Shipment log created successfully!");
                } else {
                    textSystemStatus.setText("Shipment log created successfully!");
                }
                Shipment selectedShipment = comboBoxSelectShipment.getValue();
                if (selectedShipment != null) {
                    ObservableList<InspectionLog> inspectionLogs = dataService
                            .getInspectionsLogsForShipment(selectedShipment);
                    ObservableList<ShipmentLog> shipmentLogs = dataService.getShipmentLogsForShipment(selectedShipment);

                    // Initialize or update the TableViews
                    textTheActualAmount.setText(String.valueOf(selectedShipment.getTotalNumberOfWarehouses()));
                    tableViewInspectionLogs.setItems(inspectionLogs);
                    tableViewShipmentLogs.setItems(shipmentLogs);
                }
            } else {
                // Create an alert dialog
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Error creating shipment log");
                alert.setContentText("Please fill in all fields");

                // Show the dialog
                alert.showAndWait();
            }
        } catch (Exception e) {
            // Create an alert dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Error creating shipment log");
            alert.setContentText(e.getMessage());

            // Show the dialog
            alert.showAndWait();
        }
    }

    @FXML
    void handleButtonDeleteInspectionLog(ActionEvent event) {
        InspectionLog selectedInspectionLog = tableViewInspectionLogs.getSelectionModel().getSelectedItem();
        if (selectedInspectionLog != null) {
            dataManager.deleteInspectionLog(selectedInspectionLog);
            textSystemStatus.setText("Inspection log deleted successfully");
            Shipment selectedShipment = comboBoxSelectShipment.getValue();
            if (selectedShipment != null) {
                ObservableList<InspectionLog> inspectionLogs = dataService
                        .getInspectionsLogsForShipment(selectedShipment);
                ObservableList<ShipmentLog> shipmentLogs = dataService.getShipmentLogsForShipment(selectedShipment);

                // Initialize or update the TableViews
                tableViewInspectionLogs.setItems(inspectionLogs);
                tableViewShipmentLogs.setItems(shipmentLogs);
            }
        } else {
            // Create an alert dialog for error
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error Dialog");
            errorAlert.setHeaderText("Error deleting inspection log");
            errorAlert.setContentText("No row selected");

            // Show the dialog
            errorAlert.showAndWait();
        }
    }

    @FXML
    void handleButtonDeleteShipmentLog(ActionEvent event) {
        ShipmentLog selectedShipmentLog = tableViewShipmentLogs.getSelectionModel().getSelectedItem();
        if (selectedShipmentLog != null) {
            dataManager.deleteShipmentLog(selectedShipmentLog);
            textSystemStatus.setText("Shipment log deleted successfully!");
            Shipment selectedShipment = comboBoxSelectShipment.getValue();
            if (selectedShipment != null) {
                ObservableList<InspectionLog> inspectionLogs = dataService
                        .getInspectionsLogsForShipment(selectedShipment);
                ObservableList<ShipmentLog> shipmentLogs = dataService.getShipmentLogsForShipment(selectedShipment);

                // Initialize or update the TableViews
                textTheActualAmount.setText(String.valueOf(selectedShipment.getTotalNumberOfWarehouses()));
                tableViewInspectionLogs.setItems(inspectionLogs);
                tableViewShipmentLogs.setItems(shipmentLogs);
            }
        } else {
            // Create an alert dialog for error
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error Dialog");
            errorAlert.setHeaderText("Error deleting shipment log");
            errorAlert.setContentText("No row selected");

            // Show the dialog
            errorAlert.showAndWait();
        }
    }

    @FXML
    void handleComboBoxShipment(ActionEvent event) {
        textSystemStatus.setText("");
        Shipment selectedShipment = comboBoxSelectShipment.getValue();
        if (selectedShipment != null) {
            ObservableList<InspectionLog> inspectionLogs = dataService.getInspectionsLogsForShipment(selectedShipment);
            ObservableList<ShipmentLog> shipmentLogs = dataService.getShipmentLogsForShipment(selectedShipment);

            textTheActualAmount.setText(String.valueOf(selectedShipment.getTotalNumberOfWarehouses()));

            // Initialize or update the TableViews
            tableViewInspectionLogs.setItems(inspectionLogs);
            tableViewShipmentLogs.setItems(shipmentLogs);
        }
    }

    public void initialize() throws Exception {
        // Initialization code remains mostly the same as before, with slight
        // adjustments for readability
        dataManager = DataManager.getInstance();
        dataService = DataService.getInstance();
        dataService.updateAll();

        clearAll();

        setupTableColumns();
        setupComboBoxes();
        setupButtons();
        // makeEditable();
    }

    private void setupTableColumns() {
        // Initialize table columns using PropertyValueFactory
        tableColumnShipmentLogsDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Set the on edit commit event

        tableColumnShipmentLogsDirection.setCellValueFactory(new PropertyValueFactory<>("direction"));
        tableColumnShipmentLogsDirection.setOnEditCommit(e -> {
            ShipmentLog shipmentLog = e.getRowValue();
            Direction newValue = e.getNewValue();
            UpdateFieldShipmentLog field = UpdateFieldShipmentLog.DIRECTION; // Replace with the actual field you want
                                                                             // to
                                                                             // update
            try {
                dataManager.updateShipmentLog(shipmentLog, field, newValue);
            } catch (Exception ex) {
                // Handle exception
            }
        });
        tableColumnShipmentLogsShipmentID.setCellValueFactory(new PropertyValueFactory<>("shipmentId"));
        tableColumnShipmentLogsShipmentID.setOnEditCommit(e -> {
            ShipmentLog shipmentLog = e.getRowValue();
            String newValue = e.getNewValue();

            UpdateFieldShipmentLog field = UpdateFieldShipmentLog.SHIPMENT; // Replace
            // with the actual field you want to
            // update
            try {
                dataManager.updateShipmentLog(shipmentLog, field, newValue);
            } catch (Exception ex) {
                // Handle exception
            }
        });

        tableColumnShipmentLogsWarehouse.setCellValueFactory(new PropertyValueFactory<>("warehouse"));
        // tableColumnShipmentLogsWarehouse.setCellFactory(TextFieldTableCell.forTableColumn());

        tableColumnInspectionLogsDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableColumnInspectionLogsInspector.setCellValueFactory(new PropertyValueFactory<>("inspector"));
        tableColumnInspectionLogsInspector.setCellFactory(TextFieldTableCell.forTableColumn());

        tableColumnInspectionLogsResult.setCellValueFactory(new PropertyValueFactory<>("result"));
        tableColumnInspectionLogsResult.setCellFactory(TextFieldTableCell.forTableColumn());

        tableColumnInspectionLogsShipmentID.setCellValueFactory(new PropertyValueFactory<>("shipment"));
        tableColumnInspectionLogsWarehouse.setCellValueFactory(new PropertyValueFactory<>("warehouse"));
    }

    private void setupComboBoxes() {
        comboBoxCreateShipmentLogWarehouse.setItems(dataManager.readWarehouses());
        comboBoxCreateShipmentLogDirection.setItems(FXCollections.observableArrayList(Direction.values()));
        comboBoxSelectShipment.setItems(dataManager.readShipments());
        comboBoxCreateInspectionLogWarehouse.setItems(dataManager.readWarehouses());
        comboBoxCreateShipmentLogDirection.setItems(FXCollections.observableArrayList(Direction.values()));
        comboBoxSelectShipment.setItems(dataManager.readShipments());
    }

    private void setupButtons() {
        buttonCreateInspectionLog.setOnAction(this::handleButtonCreateInspectionLog);
        buttonCreateShipmentLog.setOnAction(this::handleButtonCreateShipmentLog);
        buttonDeleteInspectionLog.setOnAction(this::handleButtonDeleteInspectionLog);
        buttonDeleteShipmentLog.setOnAction(this::handleButtonDeleteShipmentLog);
    }

    public void setTextSystemStatus(String message) {
        textSystemStatus.setText(message);
    }

    public static void updateTextSystemStatus(ShipmentTabController controller, String message) {
        controller.setTextSystemStatus(message);
    }

    public void clearAll() {
        textSystemStatus.setText("");

        tableViewInspectionLogs.getItems().clear();
        tableViewShipmentLogs.getItems().clear();

    }

}
