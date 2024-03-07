package se.lu.ics.models;

import java.util.ArrayList;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.property.ObjectProperty;

public class WarehouseHandler {

    private ObservableList<Warehouse> warehouses;

    public WarehouseHandler() {
        warehouses = FXCollections.observableList(new ArrayList<>());
    }

    public ObservableList<Warehouse> getWarhouses() {
        return warehouses;
    }
    
    // A setter should not be needed for this. Why give other classes access to that?
    /*
    public void setWarehouses(ArrayList<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }
    */

    public Warehouse createWarehouse(){
        Warehouse warehouse = new Warehouse();
        warehouses.add(warehouse);
        return warehouse;

    }


// does the same thing as getWarehouses right now, should this return a list of Strings by doing .toString on each warehouse?
    // or will that kind of thing be handled in the view?
    public ObservableList<Warehouse> readWarehouses(){
        return warehouses;
    }

   public void updateWarehouseName(StringProperty warehouseNameToBeUpdated, StringProperty newWarehouseName) throws Exception {
        // inte nödvändigt om vi inte ska skriva in något i textfältet ?
        // if (!doesAWarehouseExistWithName(warehouseNameToBeUpdated)){
        //     throw new Exception(Constants.NO_WAREHOUSE_EXISTS_WITH_THAT_NAME);
        // }

        if (doesAWarehouseExistWithName(newWarehouseName)){
            throw new Exception(Constants.ALREADY_EXISTS_WAREHOUSE_WITH_NAME);
        }

        for (Warehouse warehouse : warehouses){
            if (warehouse.getName().equals(warehouseNameToBeUpdated)){
                warehouse.setName(newWarehouseName);
                forceUpdateOfObservableList(); // maybe this should be a part of all public methods that can change its state )

                return;
            }
        }

    }

    public void deleteWarehouse(StringProperty warehouseName) throws Exception {

        // inte nödvändigt om vi inte ska skriva in något i textfältet ?
        // if (!doesAWarehouseExistWithName(warehouseName)){
        //     throw new Exception(Constants.NO_WAREHOUSE_EXISTS_WITH_THAT_NAME);
        // }

        for (Warehouse warehouse : warehouses){
            if (warehouse.getName().get().equals(warehouseName.get())){
                warehouses.remove(warehouse);
                return;
            }
        }
    }

     // only needed to updated ComboBoxes!
     public void forceUpdateOfObservableList() {
        //shipments = FXCollections.observableList(shipments);
        warehouses.add(0, null);
        warehouses.remove(0);
    }

    public void updateWarehouseLocation(StringProperty warehouseName, ObjectProperty<Location> newLocation) {

        
       for (Warehouse warehouse : warehouses){
           if (warehouse.getName().get().equals(warehouseName.get())){
               warehouse.setLocation(newLocation);
               forceUpdateOfObservableList(); // maybe this should be a part of all public methods that can change its state )
               return;
           }
       }
    }

    updateWarehouseAddress(){

    }

    updateWarehouseCapacity(){}

    deleteWarehouse(){}

    boolean doesAWarehouseExistWithName(StringProperty warehouseName){
        for (Warehouse warehouse : warehouses){
            if (warehouse.getName().get().equals(warehouseName.get())){
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
