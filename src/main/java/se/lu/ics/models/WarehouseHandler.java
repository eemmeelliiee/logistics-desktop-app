package se.lu.ics.models;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WarehouseHandler {

    private ObservableList<Warehouse> warehouses;

    public WarehouseHandler() {
        warehouses = FXCollections.observableList(new ArrayList<>());
        //addTestData(); <--- metoden ska deklareras i Warehouse
        // bör finnas en i varje konstruktor för handlers
    }
// 
    public ObservableList<Warehouse> getWarehouses() {
        return warehouses;
    }
    
    // A setter should not be needed for this. Why give other classes access to that?
    /*
    public void setWarehouses(ArrayList<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }
    */

    // CREATE

    public Warehouse createWarehouse(String name, Location location, String address, double capacity) throws Exception {
        Warehouse warehouse = new Warehouse(name, location, address, capacity);
        warehouses.add(warehouse);
        return warehouse;

    }

    // READ

// does the same thing as getWarehouses right now, should this return a list of Strings by doing .toString on each warehouse?
    // or will that kind of thing be handled in the view?
    // public ObservableList<Warehouse> readWarehouses(){
    //     return warehouses;
    // }

    // UPDATE

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
            default:
                throw new IllegalArgumentException("Invalid field: " + field);
        }
    }

   public void updateWarehouseName(Warehouse warehouse, String newWarehouseName) throws Exception {
        if (doesAWarehouseExistWithName(newWarehouseName)){
            throw new Exception(Constants.ALREADY_EXISTS_WAREHOUSE_WITH_NAME);
        } else {
                warehouse.setName(newWarehouseName);
                forceUpdateOfObservableList(); // maybe this should be a part of all public methods that can change its state )
                return;
            }
        }

        public void updateWarehouseLocation(Warehouse warehouse, Location newLocation) {
            if (warehouse != null){
                warehouse.setLocation(newLocation);
                forceUpdateOfObservableList(); // maybe this should be a part of all public methods that can change its state )
                return;
            }
        }

        public void updateWarehouseAddress(Warehouse warehouse, String newAddress){
            if (warehouse != null){
                warehouse.setAddress(newAddress);
                forceUpdateOfObservableList(); // maybe this should be a part of all public methods that can change its state )
                return;
            }

        }
    
        public void updateWarehouseCapacity(Warehouse warehouse, double newCapacity) throws Exception {
            if (warehouse != null){
                warehouse.setCapacity(newCapacity);
                forceUpdateOfObservableList(); // maybe this should be a part of all public methods that can change its state )
                return;
            }

        }

    // DELETE

    public void deleteWarehouse(Warehouse warehouse) {
                 warehouses.remove(warehouse);
    }

     // only needed to updated ComboBoxes!
     public void forceUpdateOfObservableList() {
        warehouses.add(0, null);
        warehouses.remove(0);
    }


    boolean doesAWarehouseExistWithName(String warehouseName) throws Exception{
        if (warehouseName == null) {
            throw new Exception(Constants.CANNOT_BE_EMPTY);
        }
        for (Warehouse warehouse : warehouses){
            if (warehouse.getName().equals(warehouseName)){
                return true;
            }
        }
        return false;

    }

    // these are only for tests
    public void clearData() {
        warehouses.clear();
    }

}
