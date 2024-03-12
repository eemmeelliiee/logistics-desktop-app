package se.lu.ics.controllers;

import se.lu.ics.models.DataManager;
import se.lu.ics.models.DataService;
import se.lu.ics.models.Direction;
import se.lu.ics.models.InspectionLog;
import se.lu.ics.models.Location;
import se.lu.ics.models.Shipment;
import se.lu.ics.models.ShipmentLog;
import se.lu.ics.models.UpdateFieldInspectionLog;
import se.lu.ics.models.UpdateFieldShipmentLog;
import se.lu.ics.models.Warehouse;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

        setupTableColumns();
        setupComboBoxes();
        setupButtons();
        setupDatePickers();
        makeEditable();
    }

    private void setupTableColumns() {
        // Initialize table columns using PropertyValueFactory
        tableColumnShipmentLogsDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableColumnShipmentLogsDate.setOnEditCommit(e -> {
            ShipmentLog shipmentLog = e.getRowValue();
            LocalDate newValue = e.getNewValue();
            UpdateFieldShipmentLog field = UpdateFieldShipmentLog.DATE; // Replace with the actual field you want to
                                                                        // update
            try {
                dataManager.updateShipmentLog(shipmentLog, field, newValue);
            } catch (Exception ex) {
                // Handle exception
            }
        });
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
        // tableColumnShipmentLogsShipmentID.setOnEditCommit(e -> {
        // ShipmentLog shipmentLog = e.getRowValue();
        // Shipment newValue = e.getNewValue();

        // UpdateFieldShipmentLog field = UpdateFieldShipmentLog.SHIPMENT; // Replace
        // with the actual field you want to
        // // update
        // try {
        // dataManager.updateShipmentLog(shipmentLog, field, newValue);
        // } catch (Exception ex) {
        // // Handle exception
        // }
        // });
        tableColumnShipmentLogsWarehouse.setCellValueFactory(new PropertyValueFactory<>("warehouse"));
        tableColumnShipmentLogsWarehouse.setOnEditCommit(e -> {
            ShipmentLog shipmentLog = e.getRowValue();
            Warehouse newValue = e.getNewValue();
            UpdateFieldShipmentLog field = UpdateFieldShipmentLog.WAREHOUSE; // Replace with the actual field you want
                                                                             // to
                                                                             // update
            try {
                dataManager.updateShipmentLog(shipmentLog, field, newValue);
            } catch (Exception ex) {
                // Handle exception
            }
        });

        tableColumnInspectionLogsDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableColumnInspectionLogsInspector.setCellValueFactory(new PropertyValueFactory<>("inspector"));
        tableColumnInspectionLogsResult.setCellValueFactory(new PropertyValueFactory<>("result"));
        tableColumnInspectionLogsShipmentID.setCellValueFactory(new PropertyValueFactory<>("shipment"));
        tableColumnInspectionLogsWarehouse.setCellValueFactory(new PropertyValueFactory<>("warehouse"));
    }

    private void makeEditable() {
        tableViewInspectionLogs.setEditable(true);
        tableViewShipmentLogs.setEditable(true);
        tableColumnInspectionLogsDate.setEditable(true);
        tableColumnInspectionLogsInspector.setEditable(true);
        tableColumnInspectionLogsResult.setEditable(true);
        tableColumnInspectionLogsShipmentID.setEditable(true);
        tableColumnInspectionLogsWarehouse.setEditable(true);
        tableColumnShipmentLogsDate.setEditable(true);
        tableColumnShipmentLogsDirection.setEditable(true);
        tableColumnShipmentLogsShipmentID.setEditable(true);
        tableColumnShipmentLogsWarehouse.setEditable(true);

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

    private void setupDatePickers() {
        datePickerCreateInspectionLog.setValue(LocalDate.now());
        datePickerCreateShipmentLog.setValue(LocalDate.now());
    }

    public void setTextSystemStatus(String message) {
        textSystemStatus.setText(message);
    }

    public static void updateTextSystemStatus(ShipmentTabController controller, String message) {
        controller.setTextSystemStatus(message);
    }

}
