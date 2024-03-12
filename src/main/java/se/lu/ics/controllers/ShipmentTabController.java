package se.lu.ics.controllers;

import se.lu.ics.models.DataManager;
import se.lu.ics.models.DataService;
import se.lu.ics.models.Direction;
import se.lu.ics.models.InspectionLog;
import se.lu.ics.models.Location;
import se.lu.ics.models.Shipment;
import se.lu.ics.models.ShipmentLog;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ShipmentTabController {

    private DataManager dataManager;

    private DataService dataService;

    

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
    void handleButtonCreateInspectionLog(ActionEvent event) {
        try {
            LocalDate date = datePickerCreateInspectionLog.getValue();
            Warehouse warehouse = comboBoxCreateInspectionLogWarehouse.getValue();
            Shipment shipment = comboBoxSelectShipment.getValue();
            String inspector = "Inspector";
            String result = "Result";

            if (date != null && warehouse != null && shipment != null) {
                InspectionLog inspectionLog = dataManager.createInspectionLog(shipment, warehouse, date, inspector, result);
                textSystemStatus.setText("Inspection log created successfully");
                Shipment selectedShipment = comboBoxSelectShipment.getValue();
                if (selectedShipment != null) {
                    ObservableList<InspectionLog> inspectionLogs = dataService.getInspectionsLogsForShipment(selectedShipment);
                    ObservableList<ShipmentLog> shipmentLogs = dataService.getShipmentLogsForShipment(selectedShipment);
            
                    // Initialize or update the TableViews
                    tableViewInspectionLogs.setItems(inspectionLogs);
                    tableViewShipmentLogs.setItems(shipmentLogs);
                } 
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

        if (date != null && direction != null && warehouse != null && shipment != null) {
            ShipmentLog shipmentLog = dataManager.createShipmentLog(date, direction, warehouse, shipment);
            textSystemStatus.setText("Shipment log created successfully");
            Shipment selectedShipment = comboBoxSelectShipment.getValue();
            if (selectedShipment != null) {
                ObservableList<InspectionLog> inspectionLogs = dataService.getInspectionsLogsForShipment(selectedShipment);
                ObservableList<ShipmentLog> shipmentLogs = dataService.getShipmentLogsForShipment(selectedShipment);
        
                // Initialize or update the TableViews
                tableViewInspectionLogs.setItems(inspectionLogs);
                tableViewShipmentLogs.setItems(shipmentLogs);
            } 
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

    }

    @FXML
    void handleButtonDeleteShipmentLog(ActionEvent event) {

    }

    @FXML
    void handleComboBoxShipment(ActionEvent event) {
             Shipment selectedShipment = comboBoxSelectShipment.getValue();
    if (selectedShipment != null) {
        ObservableList<InspectionLog> inspectionLogs = dataService.getInspectionsLogsForShipment(selectedShipment);
        ObservableList<ShipmentLog> shipmentLogs = dataService.getShipmentLogsForShipment(selectedShipment);

        // Initialize or update the TableViews
        tableViewInspectionLogs.setItems(inspectionLogs);
        tableViewShipmentLogs.setItems(shipmentLogs);
    }
}
    


    public void initialize()throws Exception{

        dataManager = DataManager.getInstance();
        dataService = DataService.getInstance();
        dataService.updateAll();

        tableColumnShipmentLogsDate.setCellValueFactory(new PropertyValueFactory<ShipmentLog, LocalDate>("date"));
        tableColumnShipmentLogsDirection.setCellValueFactory(new PropertyValueFactory<ShipmentLog, Direction>("direction"));
        tableColumnShipmentLogsShipmentID.setCellValueFactory(new PropertyValueFactory<ShipmentLog, String>("shipmentId"));
        tableColumnShipmentLogsWarehouse.setCellValueFactory(new PropertyValueFactory<ShipmentLog, Warehouse>("warehouse"));

        tableColumnInspectionLogsInspector.setCellValueFactory(new PropertyValueFactory<InspectionLog, String>("inspector"));
        tableColumnInspectionLogsResult.setCellValueFactory(new PropertyValueFactory<InspectionLog, String>("result"));
        tableColumnInspectionLogsShipmentID.setCellValueFactory(new PropertyValueFactory<InspectionLog, Shipment>("shipment"));
        tableColumnInspectionLogsWarehouse.setCellValueFactory(new PropertyValueFactory<InspectionLog, Warehouse>("warehouse"));

        comboBoxCreateShipmentLogWarehouse.setItems(dataManager.readWarehouses());
        comboBoxCreateShipmentLogDirection.setItems(FXCollections.observableArrayList(Direction.values()));
        comboBoxSelectShipment.setItems(dataManager.readShipments());

        

        // Set up buttons
        buttonCreateInspectionLog.setOnAction(this::handleButtonCreateInspectionLog);
        buttonCreateShipmentLog.setOnAction(arg0 -> {
            try {
                handleButtonCreateShipmentLog(arg0);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        buttonDeleteInspectionLog.setOnAction(this::handleButtonDeleteInspectionLog);
        buttonDeleteShipmentLog.setOnAction(this::handleButtonDeleteShipmentLog);

        // Set up ComboBoxes
        // You need to replace `YourType` with the actual type of the items in the ComboBoxes
        comboBoxCreateInspectionLogWarehouse.setItems(dataManager.readWarehouses());
        comboBoxCreateShipmentLogDirection.setItems(FXCollections.observableArrayList(Direction.values()));        comboBoxSelectShipment.setItems(dataManager.readShipments());

        // Set up TableViews
        // You need to replace `YourType` with the actual type of the items in the TableViews
        

        // Set up DatePicker default values
        datePickerCreateInspectionLog.setValue(LocalDate.now());
        datePickerCreateShipmentLog.setValue(LocalDate.now());
    }
}
