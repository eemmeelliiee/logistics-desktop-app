package se.lu.ics.models;
import java.time.LocalDate;
import javafx.collections.ObservableList;


public class DataManager {

    // private ShipmentHandler shipmentHandler;
    // private WarehouseHandler warehouseHandler;
    // private ShipmentLogHandler shipmentLogHandler;
    // private InspectionLogHandler inspectionLogHandler;
    // private static DataService dataService;
    
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

        // shipmentHandler = new ShipmentHandler();
        // warehouseHandler = new WarehouseHandler();
        // shipmentLogHandler = new ShipmentLogHandler();
        // inspectionLogHandler = new InspectionLogHandler();
        // dataService = new DataService();

        // kanske skapa stack för att undo removements
        //deletedShipments = new Stack<>();

    }

    // ShipmentHandler

    // public ShipmentHandler getShipmentHandler() {
    //     return shipmentHandler;
    // }

    public Shipment createShipment() {
        return ShipmentHandler.getInstance().createShipment();
    }

    public ObservableList<Shipment> readShipments() {
        return ShipmentHandler.getInstance().getShipments();
    }

    public void updateShipmentId(Shipment shipment, String newId) throws Exception {
        ShipmentHandler.getInstance().updateShipmentId(shipment, newId);
    
        // Updates the shipmentId for all shipmentlogs that has the shipment
        for (ShipmentLog log : ShipmentLogHandler.getInstance().getShipmentLogs()) {
            if (log.getShipment().equals(shipment)) {
                updateShipmentLog(log, UpdateFieldShipmentLog.SHIPMENT, shipment);
            }
        }
        // Updates the shipmentId for all inspectionlogs that has the shipment
        for (InspectionLog log : InspectionLogHandler.getInstance().getInspectionLogs()) {
            if (log.getShipment().equals(shipment)) {
                updateInspectionLog(log, UpdateFieldInspectionLog.SHIPMENT, shipment);
            }
        }
    }

    public void deleteShipment(Shipment shipment){
        ShipmentHandler.getInstance().deleteShipment(shipment);

        // is this the way to go?
        for (ShipmentLog log : ShipmentLogHandler.getInstance().getShipmentLogs()) {
            if (log.getShipment().equals(shipment)) {
                deleteShipmentLog(log);
            }
        }
        //Deletes all inspectionlogs for the shipment
        for (InspectionLog log : InspectionLogHandler.getInstance().getInspectionLogs()) {
            if (log.getShipment().equals(shipment)) {
                deleteInspectionLog(log);
            } 
        }
    }


    // WarehouseHandler

    // public WarehouseHandler getWarehouseHandler() {
    //     return ;
    // }

    public Warehouse createWarehouse(String name, Location location, String address, double capacity) throws Exception {
        return WarehouseHandler.getInstance().createWarehouse(name, location, address, capacity);
    }

    public ObservableList<Warehouse> readWarehouses() {
        return WarehouseHandler.getInstance().getWarehouses();
    }

    public void updateWarehouse(Warehouse warehouse, UpdateFieldWarehouse field, Object newValue) throws Exception {
        WarehouseHandler.getInstance().updateWarehouse(warehouse, field, newValue);
     
        // Updates the warehouse for all shipmentlogs that has the warehouse
        for (ShipmentLog log : ShipmentLogHandler.getInstance().getShipmentLogs()) {
            if (log.getWarehouse().equals(warehouse)) {
                updateShipmentLog(log, UpdateFieldShipmentLog.WAREHOUSE, warehouse);
            } DataService.getInstance().updateWarehouseShipmentInformation(warehouse);
              DataService.getInstance().updateShipmentInformation(log.getShipment());
        }

        // Updates the warehouse for all inspectionlogs that has the warehouse
        for (InspectionLog log : InspectionLogHandler.getInstance().getInspectionLogs()) {
            if (log.getWarehouse().equals(warehouse)) {
                updateInspectionLog(log, UpdateFieldInspectionLog.WAREHOUSE, warehouse);
            } DataService.getInstance().updateMostRecentInspectionDateForWarehouse(warehouse);
        }
    }

    public void deleteWarehouse(Warehouse warehouse){
        WarehouseHandler.getInstance().deleteWarehouse(warehouse);

        // Deletes all shipmentlogs for the warehouse
        for (ShipmentLog log : ShipmentLogHandler.getInstance().getShipmentLogs()) {
            if (log.getWarehouse().equals(warehouse)) {
                deleteShipmentLog(log);
            } DataService.getInstance().updateShipmentInformation(log.getShipment());
        }    
        //Deletes all inspectionlogs for the warehouse
        for (InspectionLog log : InspectionLogHandler.getInstance().getInspectionLogs()) {
            if (log.getWarehouse().equals(warehouse)) {
                deleteInspectionLog(log);
            }
        } 
    }


    // ShipmentLogHandler

    // public ShipmentLogHandler getShipmentLogHandler() {
    //     return shipmentLogHandler;
    // }

    public ShipmentLog createShipmentLog(LocalDate date, Direction direction, Warehouse warehouse, Shipment shipment) throws Exception{
        ShipmentLog shipmentLog = ShipmentLogHandler.getInstance().createShipmentLog(date, direction, warehouse, shipment);
        DataService.getInstance().updateWarehouseShipmentInformation(warehouse);
        DataService.getInstance().updateShipmentInformation(shipment);
        return shipmentLog;

    }

    public ObservableList<ShipmentLog> readShipmentLogs() {
        return ShipmentLogHandler.getInstance().getShipmentLogs();
    }

    public void updateShipmentLog(ShipmentLog shipmentLog, UpdateFieldShipmentLog field, Object newValue) throws Exception {
        Warehouse oldWarehouse = shipmentLog.getWarehouse();
        ShipmentLogHandler.getInstance().updateShipmentLog(shipmentLog, field, newValue);
        Warehouse newWarehouse = shipmentLog.getWarehouse();
        
        DataService.getInstance().updateWarehouseShipmentInformation(oldWarehouse);
        if (!oldWarehouse.equals(newWarehouse)) {
            DataService.getInstance().updateWarehouseShipmentInformation(newWarehouse);
        } 
        DataService.getInstance().updateShipmentInformation(shipmentLog.getShipment());
    }

    public void deleteShipmentLog(ShipmentLog shipmentLog) {
        ShipmentLogHandler.getInstance().deleteShipmentLog(shipmentLog);
        DataService.getInstance().updateWarehouseShipmentInformation(shipmentLog.getWarehouse());
    }

    // InspectionLogHandler

    // public InspectionLogHandler getInspectionLogHandler() {
    //     return inspectionLogHandler;
    // }

    public InspectionLog createInspectionLog(Shipment shipment, Warehouse warehouse, LocalDate date, String inspector, String result) {
        InspectionLog newLog = InspectionLogHandler.getInstance().createInspectionLog(shipment, warehouse, date, inspector, result);
        DataService.getInstance().updateMostRecentInspectionDateForWarehouse(warehouse);
        return newLog;
    }

    public ObservableList<InspectionLog> readInspectionLogs() {
        return InspectionLogHandler.getInstance().getInspectionLogs();
    }

    public void updateInspectionLog(InspectionLog inspectionLog, UpdateFieldInspectionLog field, Object newValue) throws Exception {
        InspectionLogHandler.getInstance().updateInspectionLog(inspectionLog, field, newValue);
        DataService.getInstance().updateMostRecentInspectionDateForWarehouse(inspectionLog.getWarehouse());
    }

    public void deleteInspectionLog(InspectionLog inspectionLog) {
        InspectionLogHandler.getInstance().deleteInspectionLog(inspectionLog);
        DataService.getInstance().updateMostRecentInspectionDateForWarehouse(inspectionLog.getWarehouse());
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

        ShipmentHandler.getInstance().clearData();
        WarehouseHandler.getInstance().clearData();
        InspectionLogHandler.getInstance().clearData();
        ShipmentLogHandler.getInstance().clearData();
        // saem for dataService???


        // Do this for all the other classes also
        // shipmentLogs = new ArrayList<>();
        // warehouses = new ArrayList<>();
        // inspectionLogs = new ArrayList<>();

        Shipment.resetGeneratedIds();
    }

}