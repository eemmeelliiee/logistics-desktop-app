package se.lu.ics.controllers;

import se.lu.ics.models.DataManager;
import se.lu.ics.models.DataService;
import se.lu.ics.models.Direction;
import se.lu.ics.models.InspectionLog;
import se.lu.ics.models.Shipment;
import se.lu.ics.models.ShipmentLog;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.lu.ics.models.Warehouse;

public class WarehouseTabController {

    private DataManager dataManager;

    private DataService dataService;

    @FXML
    private ComboBox<Warehouse> comboBoxSelectWarehouse;

    @FXML
    private TableColumn<String, String> tableColumnAllInspectors;

    @FXML
    private TableColumn<InspectionLog, LocalDate> tableColumnInspectionLogsDate;

    @FXML
    private TableColumn<InspectionLog, String> tableColumnInspectionLogsInspector;

    @FXML
    private TableColumn<InspectionLog, String> tableColumnInspectionLogsResult;

    @FXML
    private TableColumn<InspectionLog, String> tableColumnInspectionLogsShipmentID;

    @FXML
    private TableColumn<ShipmentLog, LocalDate> tableColumnShipmentLogsDate;

    @FXML
    private TableColumn<ShipmentLog, Direction> tableColumnShipmentLogsDirection;

    @FXML
    private TableColumn<ShipmentLog, String> tableColumnShipmentLogsShipmentID;

    @FXML
    private TableColumn<ShipmentLog, String> tableColumnShipmentNeedsAttention;

    @FXML
    private TableView<String> tableViewAllInspectors;

    @FXML
    private TableView<InspectionLog> tableViewInspectionLogs;

    @FXML
    private TableView<ShipmentLog> tableViewShipmentLogs;

    @FXML
    private Text textActualAverageTime;

    @FXML
    private Text textActualRemainingCapacity;

    @FXML
    private Text textActualStockLevel;

    @FXML
    private Text textAllInspectionLogs;

    @FXML
    private Text textAllShipmentLogs;

    @FXML
    private Text textAverageTime;

    @FXML
    private Text textRemainingCapacity;

    @FXML
    private Text textStockLevel;

    @FXML
    private Text textWarehouseStatus;

    @FXML
    private AnchorPane warehouseTabAnchorPane;

    @FXML
    void handleComboBoxSelectWarehouse(ActionEvent event) {
        Warehouse selectedWarehouse = (Warehouse) comboBoxSelectWarehouse.getSelectionModel().getSelectedItem();
        textActualAverageTime.setText(selectedWarehouse.getAverageTimeShipmentSpendsAtWarehouse());
        textActualRemainingCapacity.setText((selectedWarehouse.getRemainingCapacityInPercent()));
        textActualStockLevel.setText(String.valueOf(selectedWarehouse.getCurrentStockLevel()));

        setupTableColumns();
        populateTableView();

        


    }

    public void initialize() throws Exception{

        dataManager = DataManager.getInstance();
        dataService = DataService.getInstance();

        clearAll();

        setupTableColumns();
        comboBoxSelectWarehouse.setItems(dataManager.readWarehouses());
        
    }

    private void setupTableColumns() {
                tableColumnShipmentLogsDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                tableColumnShipmentLogsDirection.setCellValueFactory(new PropertyValueFactory<>("direction"));
                tableColumnShipmentLogsShipmentID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShipmentId()));                
                tableColumnShipmentNeedsAttention.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShipmentLabel()));

                tableColumnAllInspectors.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

                tableColumnInspectionLogsDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                tableColumnInspectionLogsInspector.setCellValueFactory(new PropertyValueFactory<>("inspector"));
                tableColumnInspectionLogsResult.setCellValueFactory(new PropertyValueFactory<>("result"));
                tableColumnInspectionLogsShipmentID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShipment().getShipmentId()));
                
    }

    public void populateTableView() {
        Warehouse selectedWarehouse = comboBoxSelectWarehouse.getSelectionModel().getSelectedItem();
        tableViewShipmentLogs.setItems(dataService.getShipmentLogsForWarehouse(selectedWarehouse));
        tableViewInspectionLogs.setItems(dataService.getInspectionLogsForWarehouse(selectedWarehouse));
        tableViewAllInspectors.setItems(dataService.getInspectorsForWarehouse(selectedWarehouse));
}

public void clearAll() {
        tableViewShipmentLogs.getItems().clear();
        tableViewInspectionLogs.getItems().clear();
        tableViewAllInspectors.getItems().clear();
    }

}


