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
    private WarehouseHandler WarehouseHandler;
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
        WarehouseHandler = new WarehouseHandler();

        // se till att alla dessa blir handlers!
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

    public void updateShipmentId(Shipment shipmentToBeUpdated, String newShipmentId) throws Exception {
        shipmentHandler.updateShipmentId(shipmentToBeUpdated, newShipmentId);
    }

    public void deleteShipment(Shipment shipment) throws Exception {
        shipmentHandler.deleteShipment(shipment);
    }

    // WarehouseHandler

    public WarehouseHandler getWarehouseHandler() {
        return WarehouseHandler;
    }

    public void createWarehouse(String name, Location location, String address, double capacity) {
        WarehouseHandler.createWarehouse(name, location, address, capacity);
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


        // Do this for all the other classes also
        // shipmentLogs = new ArrayList<>();
        // warehouses = new ArrayList<>();
        // inspectionLogs = new ArrayList<>();

        Shipment.resetGeneratedIds();
    }

}