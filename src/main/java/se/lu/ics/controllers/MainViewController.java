package se.lu.ics.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import se.lu.ics.models.Constants;
import se.lu.ics.models.DataManager;
import se.lu.ics.models.Shipment;

public class MainViewController {

    //private Stack<Shipment> deletedShipments;

    @FXML
    private ComboBox<Shipment> myComboBox;

    @FXML
    private Button createButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label myLabel;

    @FXML
    private TextField newIdTextField;

    @FXML
    private Button updateIdButton;

    @FXML
    private Label errorLabel;
    
    @FXML
    private TableView<Shipment> myTableView;

    @FXML
    private TableColumn<Shipment, String> shipmentIdColumn;



    @FXML
    private void handleButtonCreateButton(ActionEvent event) {
        // Code to execute when the button is pressed
        System.out.println("create button was pressed!");
        DataManager.getInstance().createShipment();
        errorLabel.setText("Warehouse created!  ");
    }   

    @FXML
    private void handleButtonDeleteButton(ActionEvent event) throws Exception {
        Shipment selectedShipment = myTableView.getSelectionModel().getSelectedItem();
        if (selectedShipment != null) {
            // Remove the selected shipment from the data source
            DataManager.getInstance().deleteShipment(selectedShipment);
            // Refresh the table view
            myTableView.getItems().remove(selectedShipment);
        } else {
            // Display an error message
            errorLabel.setText(Constants.NO_ROW_SELECTED);
            System.out.println("No row selected");
        }
    }

//  @FXML
// private void handleButtonUndoButton(ActionEvent event) throws Exception {
//     if (!deletedShipments.isEmpty()) {
//         // Get the last deleted shipment
//         Shipment lastDeletedShipment = deletedShipments.pop();
//         // Add the shipment back to the data source
//         DataManager.getInstance().addDeletedShipment(lastDeletedShipment);
//         // Refresh the table view
//         myTableView.getItems().add(lastDeletedShipment);
//     } else {
//         // Display an error message
//         System.out.println("No shipment to undo delete");
//     }
// }
    
@FXML
private void handleButtonUpdateId(ActionEvent event) {
    // Code to execute when the button is pressed
    System.out.println("update button was pressed!");

    Shipment selectedShipment = myComboBox.getValue(); 
    String newId = newIdTextField.getText();
    ObservableValue<String> newIdObservable = new SimpleStringProperty(newId);

    myTableView.refresh();

    try {
        DataManager.getInstance().getShipmentHandler().updateShipmentId(selectedShipment, newId);
        errorLabel.setText("");
        newIdTextField.setText("");
    } catch (Exception e) {
        errorLabel.setText(e.getMessage());
    }
}


    @FXML
    public void initialize() {

        // hide error messages
        errorLabel.setText("");

        myComboBox.setItems(DataManager.getInstance().getShipmentHandler().getShipments());
        myComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        
            myLabel.setText(newValue.toString());

        });



            // Make the TableView editable
        myTableView.setEditable(true);

        // Initialize the shipmentId column
        //shipmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("shipmentId")); // VIKTIGT: KRÄVER EN GETTER I SHIPMENT, FRÅGA COPILOT
        shipmentIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShipmentId()));

        // Make the shipmentId column editable
        shipmentIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        shipmentIdColumn.setOnEditCommit(event -> {
            Shipment shipment = event.getRowValue();

            String newValue = event.getNewValue();
            String newValueAsStringProperty = newValue;

            try 
            {
                // maybe change updatedShipmentId to take a reference to Shipment instead of having to write shipment.getShipmentId()
                DataManager.getInstance().getShipmentHandler().updateShipmentId(shipment, newValueAsStringProperty);
                errorLabel.setText("Shipment ID updated!");
            } 
            catch (Exception e) 
            {
                errorLabel.setText(e.getMessage());
                event.getOldValue(); // VIKTIGT reset to this value in table!!!
                System.out.println("error: " + e.getMessage());
            }

            //needs to check if valid here
        });

        // Set the items of the table view
        myTableView.setItems(DataManager.getInstance().getShipmentHandler().getShipments());


    
        // myComboBox.getItems().addAll("Option 1", "Option 2", "Option 3");

        // ArrayList<String> arrayList = new ArrayList<String>();
        // arrayList.add("Option 1");
        // arrayList.add("Option 2");
        // arrayList.add("Option 3");


        // // items är en "wrapper" för vår arraylist
        // ObservableList<String> items = FXCollections.observableList(arrayList);

        // myComboBox.setItems(items);

        

        // myComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            
            
        //     myLabel.setText(newValue);

        //     arrayList.add("Option 4");
        //     System.out.println(items.size());

        //     items.add("Option 5");


        // }

        // (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
        //     myLabel.setText(newValue);
        //     arrayList.add("Option 4");
        //     System.out.println(items.size());
        // }


        

        // //här skapas en instans av en anonym klass (som också skapas här) som implementerar ChangeListener
        // new ChangeListener<String>() {
        //     @Override
        //     public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        //         myLabel.setText(newValue);
        //         arrayList.add("Option 4");
        //         System.out.println(items.size());
        //     }
        // }
        
        

    }

}
    
    // @FXML
    // private TabPane tabPane;
    // @FXML
    // private ComboBox<String> comboBox;
    // @FXML
    // private TextField textField;
    // @FXML
    // private Button saveButton;
    // @FXML
    // private Button deleteButton;

    // @FXML
    // public void initialize() {
       


        // comboBox.getItems().addAll("Option 1", "Option 2", "Option 3");

        // saveButton.setOnAction(e -> {
        //     String comboBoxInput = comboBox.getValue();
        //     String userInput = textField.getText();

        //     /*
        //     DataManager.getInstance().setData("comboBoxKey", comboBoxInput);
        //     DataManager.getInstance().setData("shipment", userInput);
        //     DataManager.getInstance().saveData();
        //     */

        //     String oldId = "100";
        //     String newId = "101";

        //     try {
        //         DataManager.getInstance().updateShipmentId(oldId, newId);

        //     } catch (Exception ex) {

        //         //errorMesssageRuta.setString(ex.getMessage());
        //         System.out.println(ex.getMessage());
        //     }
        // });

        // deleteButton.setOnAction(e -> {
        //     //DataManager.getInstance().removeData("comboBoxKey");
        //     //DataManager.getInstance().removeData("shipment");
        //     //DataManager.getInstance().saveData();
        // });

        // tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
        //     @Override
        //     public void changed(ObservableValue<? extends Tab> observable, Tab oldTab, Tab newTab) {
        //         String key = newTab.getId();
        //         String newData = newTab.getText();
        //         //DataManager.getInstance().setData(key, newData);
        //         //DataManager.getInstance().saveData();
        //     }
        // });
    //}
