package se.lu.ics.models;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart.Data;


public class DataManager {

    private ShipmentHandler shipmentHandler;
    private WarehouseHandler warehouseHandler;
    private ShipmentLogHandler shipmentLogHandler;
    private InspectionLogHandler inspectionLogHandler;
    
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
        shipmentLogHandler = new ShipmentLogHandler();
        inspectionLogHandler = new InspectionLogHandler();

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

        // is this the way to go?
        // for (ShipmentLog log : shipmentLogHandler.getShipmentLogs()) {
        //     if (log.getShipment().equals(shipment)) {
        //         shipmentLogHandler.updateShipmentLog(log, UpdateFieldShipmentLog.SHIPMENT, shipment);
        //     }
        // }
    }

    public void deleteShipment(Shipment shipment){
        shipmentHandler.deleteShipment(shipment);

        // is this the way to go?
        // for (ShipmentLog log : shipmentLogHandler.getShipmentLogs()) {
        //     if (log.getShipment().equals(shipment)) {
        //         shipmentLogHandler.deleteShipmentLog(log);
        //     }
        // }
    }


    // WarehouseHandler

    public WarehouseHandler getWarehouseHandler() {
        return warehouseHandler;
    }

    public Warehouse createWarehouse(String name, Location location, String address, double capacity) throws Exception {
        return warehouseHandler.createWarehouse(name, location, address, capacity);
    }

    public ObservableList<Warehouse> readWarehouses() {
        return warehouseHandler.getWarehouses();
    }

    public void updateWarehouse(Warehouse warehouse, UpdateFieldWarehouse field, Object newValue) throws Exception {
        warehouseHandler.updateWarehouse(warehouse, field, newValue);
        // is this the way to go?
        // for (ShipmentLog log : shipmentLogHandler.getShipmentLogs()) {
        //     if (log.getWarehouse().equals(warehouse)) {
        //         shipmentLogHandler.updateShipmentLog(log, UpdateFieldShipmentLog.WAREHOUSE, warehouse);
        //     }
        // }
    }

    public void deleteWarehouse(Warehouse warehouse){
        warehouseHandler.deleteWarehouse(warehouse);

        // // samma för alla CRUD-metoder som kan påverka andra
        // for (ShipmentLog log : shipmentLogHandler.getShipmentLogs()) {
        //     if (log.getWarehouse().equals(warehouse)) {
        //         shipmentLogHandler.deleteShipmentLog(log);
        //     }
        // }    
    }


    // ShipmentLogHandler

    public ShipmentLogHandler getShipmentLogHandler() {
        return shipmentLogHandler;
    }

    public ShipmentLog createShipmentLog(LocalDate date, Direction direction, Warehouse warehouse, Shipment shipment) throws Exception{
        ShipmentLog shipmentLog = shipmentLogHandler.createShipmentLog(date, direction, warehouse, shipment);
        DataService.updateWarehouseInformation(shipmentLog.getWarehouse());
        return shipmentLog;

    }

    public ObservableList<ShipmentLog> readShipmentLogs() {
        return shipmentLogHandler.getShipmentLogs();
    }

    public void updateShipmentLog(ShipmentLog shipmentLog, UpdateFieldShipmentLog field, Object newValue) throws Exception {
        Warehouse oldWarehouse = shipmentLog.getWarehouse();
        shipmentLogHandler.updateShipmentLog(shipmentLog, field, newValue);
        Warehouse newWarehouse = shipmentLog.getWarehouse();
        
        DataService.updateWarehouseInformation(oldWarehouse);
        if (!oldWarehouse.equals(newWarehouse)) {
            DataService.updateWarehouseInformation(newWarehouse);
        }
        // shipmentlog for shipment will be gotten through shipmentloghandler, so no need to update that here
    }

    public void deleteShipmentLog(ShipmentLog shipmentLog) {
        shipmentLogHandler.deleteShipmentLog(shipmentLog);
        // DataService.updateWarehouseInformation(shipmentLog.getWarehouse());
    }

    // InspectionLogHandler

    public InspectionLogHandler getInspectionLogHandler() {
        return inspectionLogHandler;
    }

    public InspectionLog createInspectionLog(Shipment shipment, Warehouse warehouse, LocalDate date, String inspector, String result) {
        return inspectionLogHandler.createInspectionLog(shipment, warehouse, date, inspector, result);
    }

    public ObservableList<InspectionLog> readInspectionLogs() {
        return inspectionLogHandler.getInspectionLogs();
    }

    public void updateInspectionLog(InspectionLog inspectionLog, UpdateFieldInspectionLog field, Object newValue) throws Exception {
        inspectionLogHandler.updateInspectionLog(inspectionLog, field, newValue);
    }

    public void deleteInspectionLog(InspectionLog inspectionLog) {
        inspectionLogHandler.deleteInspectionLog(inspectionLog);
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