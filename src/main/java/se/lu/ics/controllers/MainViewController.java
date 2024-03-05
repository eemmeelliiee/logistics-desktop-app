package se.lu.ics.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import se.lu.ics.models.DataManager;

public class MainViewController {
    @FXML
    private TabPane tabPane;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextField textField;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;

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
    }
}