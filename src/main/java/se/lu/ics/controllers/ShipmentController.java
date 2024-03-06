package se.lu.ics.controllers;
import se.lu.ics.models.DataManager;
import se.lu.ics.models.Shipment;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

 
public class ShipmentController {
    private DataManager dataManager;
   /* ----------------------------EXAMPLE OF HOW TO USE THE SHIPMENT CONTROLLER------------------- 
 @FXML
    private TableView<Shipment> tableView;
    @FXML
    private TableColumn<Shipment, String> shipmentIdColumn;
    @FXML
    private TableColumn<Shipment, String> shipmentDetailsColumn;
    // Add more columns as needed

    public void setShipments(List<Shipment> shipments) {
        shipmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("shipmentId"));
        shipmentDetailsColumn.setCellValueFactory(new PropertyValueFactory<>("shipmentDetails"));
        // Set up more columns as needed

        tableView.getItems().setAll(shipments);
    }

----------------AND TO MAKE SURE DATA IS SAVED::::::::-------------------------------- 
     @FXML
    public void initialize() {
        comboBox.getItems().addAll("Option 1", "Option 2", "Option 3");

        saveButton.setOnAction(e -> {
            String comboBoxInput = comboBox.getValue();
            String userInput = textField.getText();
            DataManager.getInstance().setData("comboBoxKey", comboBoxInput);
            DataManager.getInstance().setData("shipment", userInput);
            DataManager.getInstance().saveData();
        });

        deleteButton.setOnAction(e -> {
            DataManager.getInstance().removeData("comboBoxKey");
            DataManager.getInstance().removeData("shipment");
            DataManager.getInstance().saveData();
        });

        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
                String key = newTab.getId();
                String newData = newTab.getText();
                DataManager.getInstance().setData(key, newData);
                DataManager.getInstance().saveData();
            }
        });
    }*/

}
