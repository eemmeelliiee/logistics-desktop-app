package se.lu.ics.controllers;

import se.lu.ics.models.DataManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ShipmentTabController {

    private DataManager dataManager;

    @FXML
    private Button buttonCreateInspectionLog;

    @FXML
    private Button buttonCreateShipmentLog;

    @FXML
    private Button buttonDeleteInspectionLog;

    @FXML
    private Button buttonDeleteShipmentLog;

    @FXML
    private ComboBox<?> comboBoxCreateInspectionLogWarehouse;

    @FXML
    private ComboBox<?> comboBoxCreateShipmentLogDirection;

    @FXML
    private ComboBox<?> comboBoxCreateShipmentLogWarehouse;

    @FXML
    private ComboBox<?> comboBoxSelectShipment;

    @FXML
    private DatePicker datePickerCreateInspectionLog;

    @FXML
    private DatePicker datePickerCreateShipmentLog;

    @FXML
    private AnchorPane shipmentTabAnchorPane;

    @FXML
    private TableColumn<?, ?> tableColumnInspectionLogsInspector;

    @FXML
    private TableColumn<?, ?> tableColumnInspectionLogsResult;

    @FXML
    private TableColumn<?, ?> tableColumnInspectionLogsShipmentID;

    @FXML
    private TableColumn<?, ?> tableColumnInspectionLogsWarehouse;

    @FXML
    private TableColumn<?, ?> tableColumnShipmentLogsDate;

    @FXML
    private TableColumn<?, ?> tableColumnShipmentLogsDirection;

    @FXML
    private TableColumn<?, ?> tableColumnShipmentLogsShipmentID;

    @FXML
    private TableColumn<?, ?> tableColumnShipmentLogsWarehouse;

    @FXML
    private TableView<?> tableViewInspectionLogs;

    @FXML
    private TableView<?> tableViewShipmentLogs;

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
    void handleButtonCreateInspectionLog(ActionEvent event) {

    }

    @FXML
    void handleButtonCreateShipmentLog(ActionEvent event) {

    }

    @FXML
    void handleButtonDeleteInspectionLog(ActionEvent event) {

    }

    @FXML
    void handleButtonDeleteShipmentLog(ActionEvent event) {

    }

}
