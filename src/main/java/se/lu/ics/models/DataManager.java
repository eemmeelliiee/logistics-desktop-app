package se.lu.ics.models;

import java.io.*;
import java.util.*;
import java.util.function.DoublePredicate;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class DataManager {

    private ShipmentHandler shipmentHandler;
    private WarehouseHandler warehouseHandler;
    // samma för dessa v, det är dessa som har själva listorna av sig sjäva
    private ArrayList<InspectionLog> inspectionLogs;
    private ArrayList<ShipmentLog> shipmentLogs;
   // private Stack<Shipment> deletedShipments;

    private static DataManager instance;

    // Method that make the DataManager "static"
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }


    private DataManager() {

        shipmentHandler = new ShipmentHandler();
        warehouseHandler = new WarehouseHandler();

        // se till att alla dessa v blir handlers!
       // ShipmentLogHandler = new ShipmentLogHandler();
        inspectionLogs = new ArrayList<>();

        // kanske skapa stack för att undo removements
        //deletedShipments = new Stack<>();

    }

    // ShipmentHandler

    public ShipmentHandler getShipmentHandler() {
        return shipmentHandler;
    }

    public Shipment createShipment() {
        return shipmentHandler.createShipment();
    }

    public ObservableList<Shipment> readShipments() {
        return shipmentHandler.getShipments();
    }

    public void updateShipmentId(Shipment shipment, String newId) throws Exception {
        shipmentHandler.updateShipmentId(shipment, newId);
    }

    public void deleteShipment(Shipment shipment) throws Exception {
        shipmentHandler.deleteShipment(shipment);
    }

    // WarehouseHandler

    public WarehouseHandler getWarehouseHandler() {
        return warehouseHandler;
    }

    public Warehouse createWarehouse(String name, Location location, String address, double capacity) {
        return warehouseHandler.createWarehouse(name, location, address, capacity);
    }

    public ObservableList<Warehouse> readWarehouses() {
        return warehouseHandler.getWarehouses();
    }

    public void updateWarehouseName(Warehouse warehouse, String newWarehouseName) throws Exception {
        warehouseHandler.updateWarehouseName(warehouse, newWarehouseName);
    }

    public void updateWarehouseLocation(Warehouse warehouse, Location newLocation) {
        warehouseHandler.updateWarehouseLocation(warehouse, newLocation);
    }

    public void updateWarehouseAddress(Warehouse warehouse, String newAddress) {
        warehouseHandler.updateWarehouseAddress(warehouse, newAddress);
    }

    public void updateWarehouseCapacity(Warehouse warehouse, double newCapacity) throws Exception {
        warehouseHandler.updateWarehouseCapacity(warehouse, newCapacity);
    }

    public void deleteWarehouse(Warehouse warehouse) throws Exception {
        warehouseHandler.deleteWarehouse(warehouse);
    }









    // public ArrayList<Warehouse> getWarehouses() {
    //     return warehouses;
    // }

    // public void setWarehouses(ArrayList<Warehouse> warehouses) {
    //     this.warehouses = warehouses;
    // }

    // public ArrayList<InspectionLog> getInspectionLogs() {
    //     return inspectionLogs;
    // }

    // public void setInspectionLogs(ArrayList<InspectionLog> inspectionLogs) {
    //     this.inspectionLogs = inspectionLogs;
    // }

    // public ArrayList<ShipmentLog> getShipmentLogs() {
    //     return shipmentLogs;
    // }

    // public void setShipmentLogs(ArrayList<ShipmentLog> shipmentLogs) {
    //     this.shipmentLogs = shipmentLogs;
    // }

    // public void createWarehouse(String name, Location location, String address, double capacity) {
    //     Warehouse warehouse = new Warehouse(name, location, address, capacity);
    //     warehouses.add(warehouse);
    // }

    // public void createInspectionLog(Shipment shipment, Warehouse warehouse, LocalDate date, String inspector, String result) {
    //     InspectionLog inspectionLog = new InspectionLog(shipment, warehouse, date, inspector, result);
    //     inspectionLogs.add(inspectionLog);
    // }

    // public void createShipmentLog(LocalDate date, Direction direction, Warehouse warehouse, Shipment shipment) {
    //     ShipmentLog shipmentLog = new ShipmentLog(date, direction, warehouse, shipment);
    //     shipmentLogs.add(shipmentLog);
    // }

   // public Stack<Shipment> getDeletedShipments() {
    //    return deletedShipments;
    //}

   // public void addDeletedShipment(Shipment shipment) {
    //    deletedShipments.push(shipment);
   // }




// behövs bara för tester
    public void clearData() {

        shipmentHandler.clearData();
        warehouseHandler.clearData();


        // Do this for all the other classes also
        // shipmentLogs = new ArrayList<>();
        // warehouses = new ArrayList<>();
        // inspectionLogs = new ArrayList<>();

        Shipment.resetGeneratedIds();
    }

}