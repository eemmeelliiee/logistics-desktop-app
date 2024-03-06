package se.lu.ics.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import se.lu.ics.models.DataManager;

public class MainViewController {

    @FXML
    private ComboBox<String> myComboBox;

    @FXML
    private Button myButton;

    @FXML
    private Label myLabel;

    @FXML
    private void handleButtonMyButton(ActionEvent event) {
        // Code to execute when the button is pressed
        System.out.println("Button was pressed!");
    }   
    

    @FXML
    public void initialize() {
        // myComboBox.getItems().addAll("Option 1", "Option 2", "Option 3");

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Option 1");
        arrayList.add("Option 2");
        arrayList.add("Option 3");


        // items är en "wrapper" för vår arraylist
        ObservableList<String> items = FXCollections.observableList(arrayList);

        myComboBox.setItems(items);

        

        myComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            
            
            myLabel.setText(newValue);

            arrayList.add("Option 4");
            System.out.println(items.size());

            items.add("Option 5");


        }

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
        
        
        );

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
