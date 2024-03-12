package se.lu.ics.models;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WarehouseHandler {

    private ObservableList<Warehouse> warehouses;
    private static WarehouseHandler instance;

    private static final String ALREADY_EXISTS_WAREHOUSE_WITH_NAME = "Error: A warehouse with given name already exists";

    private WarehouseHandler() {
        warehouses = FXCollections.observableList(new ArrayList<>());
    }

    public static WarehouseHandler getInstance() {
        if (instance == null) {
            instance = new WarehouseHandler();
        }
        return instance;
    }

    // <<-----Create----->>

    public Warehouse createWarehouse(String name, Location location, String address, double capacity) throws Exception {
        if (doesAWarehouseExistWithName(name)) {
            throw new Exception(ALREADY_EXISTS_WAREHOUSE_WITH_NAME);
        }
        Warehouse warehouse = new Warehouse(name, location, address, capacity);
        warehouses.add(warehouse);
        return warehouse;

    }

    // <<-----Read----->>

    public ObservableList<Warehouse> getWarehouses() {
        return warehouses;
    }

    // <<-----Update----->>

    public void updateWarehouse(Warehouse warehouse, UpdateFieldWarehouse field, Object newValue) throws Exception {
        if (newValue == null || newValue.equals("")) {
            throw new Exception(Constants.CANNOT_BE_EMPTY);
        }
        switch (field) {
            case NAME:
                updateWarehouseName(warehouse, (String) newValue);
                break;
            case LOCATION:
                updateWarehouseLocation(warehouse, (Location) newValue);
                break;
            case ADDRESS:
                updateWarehouseAddress(warehouse, (String) newValue);
                break;
            case CAPACITY:
                updateWarehouseCapacity(warehouse, (Double) newValue);
                break;
        }
    }

    public void updateWarehouseName(Warehouse warehouse, String newWarehouseName) throws Exception {
        if (doesAWarehouseExistWithName(newWarehouseName)) {
            throw new Exception(ALREADY_EXISTS_WAREHOUSE_WITH_NAME);
        } else {
            warehouse.setName(newWarehouseName);
            forceUpdateOfObservableList();
            for (ShipmentLog log : ShipmentLogHandler.getInstance().getShipmentLogs()) {
                if (log.getWarehouse().equals(warehouse)) {

                }

            }
            return;
        }
    }

    public void updateWarehouseLocation(Warehouse warehouse, Location newLocation) {
        // if (warehouse != null){
        warehouse.setLocation(newLocation);
        forceUpdateOfObservableList();
        return;
        // }
    }

    public void updateWarehouseAddress(Warehouse warehouse, String newAddress) {
        // if (warehouse != null){
        warehouse.setAddress(newAddress);
        forceUpdateOfObservableList();
        return;

    }

    public void updateWarehouseCapacity(Warehouse warehouse, double newCapacity) throws Exception {
        // if (warehouse != null){
        warehouse.setCapacity(newCapacity);
        forceUpdateOfObservableList();
        return;

    }

    // <<-----Delete----->>

    public void deleteWarehouse(Warehouse warehouse) {
        warehouses.remove(warehouse);
    }

    // Needed to update ComboBoxes in GUI
    public void forceUpdateOfObservableList() {
        warehouses.add(0, null);
        warehouses.remove(0);
    }

    // <<----- Used in updateWarehouseName to validate uniqueness of name ----->>
    boolean doesAWarehouseExistWithName(String warehouseName) throws Exception {
        if (warehouseName == null) {
            throw new Exception(Constants.CANNOT_BE_EMPTY);
        }
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getName().equals(warehouseName)) {
                return true;
            }
        }
        return false;

    }

    // For testing purposes only
    public void clearData() {
        warehouses.clear();
    }

}
