package se.lu.ics.models;

import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class DataManager {

    private ShipmentHandler shipmentHandler;
    private ArrayList<Warehouse> warehouses;
    private ArrayList<InspectionLog> inspectionLogs;
    private ArrayList<ShipmentLog> shipmentLogs;

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

        // se till att dessa blir handlers!
        shipmentLogs = new ArrayList<>();
        warehouses = new ArrayList<>();
        inspectionLogs = new ArrayList<>();
    }


    public Shipment createShipment() {
        return shipmentHandler.createShipment();
    }

    public ArrayList<Shipment> readShipments() {
        return shipmentHandler.getShipments();
    }

    public void updateShipmentId(String shipmentIdToBeUpdated, String newShipmentId) throws Exception {
        shipmentHandler.updateShipmentId(shipmentIdToBeUpdated, newShipmentId);
    }

    public void deleteShipment(String shipmentId) throws Exception {
        shipmentHandler.deleteShipment(shipmentId);
    }



    public ArrayList<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(ArrayList<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    public ArrayList<InspectionLog> getInspectionLogs() {
        return inspectionLogs;
    }

    public void setInspectionLogs(ArrayList<InspectionLog> inspectionLogs) {
        this.inspectionLogs = inspectionLogs;
    }

    public ArrayList<ShipmentLog> getShipmentLogs() {
        return shipmentLogs;
    }

    public void setShipmentLogs(ArrayList<ShipmentLog> shipmentLogs) {
        this.shipmentLogs = shipmentLogs;
    }

    public void createWarehouse(String name, Location location, String address, double capacity) {
        Warehouse warehouse = new Warehouse(name, location, address, capacity);
        warehouses.add(warehouse);
    }

    public void createInspectionLog(Shipment shipment, Warehouse warehouse, LocalDate date, String inspector, String result) {
        InspectionLog inspectionLog = new InspectionLog(shipment, warehouse, date, inspector, result);
        inspectionLogs.add(inspectionLog);
    }

    public void createShipmentLog(LocalDate date, Direction direction, Warehouse warehouse, Shipment shipment) {
        ShipmentLog shipmentLog = new ShipmentLog(date, direction, warehouse, shipment);
        shipmentLogs.add(shipmentLog);
    }







    public void clearData() {

        shipmentHandler.clearData();

        // Do this for all the other classes also
        // shipmentLogs = new ArrayList<>();
        // warehouses = new ArrayList<>();
        // inspectionLogs = new ArrayList<>();

        Shipment.resetGeneratedIDs();
    }

}