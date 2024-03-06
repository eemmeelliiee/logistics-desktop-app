package se.lu.ics.controllers;
import se.lu.ics.models.Warehouse;
import se.lu.ics.models.DataManager;    
import se.lu.ics.models.Warehouse;   

public class WarehouseController {
    private DataManager dataManager;

/*---------- EXEMPEL PÅ HUR MAN KAN GÖRA sin WarehouseController!!!!!!------------------


    public WarehouseController() {
        this.dataManager = DataManager.getInstance();
    }

    public void handleWarehouseSelection(Warehouse warehouse) {
        // Serialize the warehouse to a string
        String warehouseData = serializeWarehouse(warehouse);

        // Store the warehouse data in the DataManager
        dataManager.setData("currentWarehouse", warehouseData);
    }

    public void updateView() {
        // Get the warehouse data from the DataManager
        String warehouseData = dataManager.getData("currentWarehouse");

        // Deserialize the warehouse data to a Warehouse object
        Warehouse warehouse = deserializeWarehouse(warehouseData);

        // Update the view with the warehouse
        // ...
    }

    // Methods to serialize and deserialize a Warehouse object would go here
    // ...

    -------------------------- AND TO MAKE SURE DATA IS SAVED:::::-----------------------
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
    
    
    */
}