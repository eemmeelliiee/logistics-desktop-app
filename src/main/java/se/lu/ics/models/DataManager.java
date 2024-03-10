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
    private DataService dataService;
    private ShipmentHandler shipmentHandler;
    private WarehouseHandler warehouseHandler;
    private ShipmentLogHandler shipmentLogHandler;
    private InspectionLogHandler inspectionLogHandler;


    // Method that make the DataManager "static"
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }


    private DataManager() {
        this.dataService = DataService.getInstance();
        this.shipmentHandler = ShipmentHandler.getInstance();
        this.warehouseHandler = WarehouseHandler.getInstance();
        this.shipmentLogHandler = ShipmentLogHandler.getInstance();
        this.inspectionLogHandler = InspectionLogHandler.getInstance();

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
        return shipmentHandler.createShipment();
    }

    public ObservableList<Shipment> readShipments() {
        return shipmentHandler.getShipments();
    }

    public void updateShipmentId(Shipment shipment, String newId) throws Exception {
        shipmentHandler.updateShipmentId(shipment, newId);
    
        // Updates the shipmentId for all shipmentlogs that has the shipment
        for (ShipmentLog log : shipmentLogHandler.getShipmentLogs()) {
            if (log.getShipment().equals(shipment)) {
                updateShipmentLog(log, UpdateFieldShipmentLog.SHIPMENT, shipment);
            }
        }
        // Updates the shipmentId for all inspectionlogs that has the shipment
        for (InspectionLog log : inspectionLogHandler.getInspectionLogs()) {
            if (log.getShipment().equals(shipment)) {
                updateInspectionLog(log, UpdateFieldInspectionLog.SHIPMENT, shipment);
            }
        }
    }

    public void deleteShipment(Shipment shipment){
        shipmentHandler.deleteShipment(shipment);

        // is this the way to go?
        for (ShipmentLog log : shipmentLogHandler.getShipmentLogs()) {
            if (log.getShipment().equals(shipment)) {
                deleteShipmentLog(log);
            }
        }
        //Deletes all inspectionlogs for the shipment
        for (InspectionLog log : inspectionLogHandler.getInspectionLogs()) {
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
        return warehouseHandler.createWarehouse(name, location, address, capacity);
    }

    public ObservableList<Warehouse> readWarehouses() {
        return warehouseHandler.getWarehouses();
    }

    public void updateWarehouse(Warehouse warehouse, UpdateFieldWarehouse field, Object newValue) throws Exception {
        warehouseHandler.updateWarehouse(warehouse, field, newValue);
     
        // Updates the warehouse for all shipmentlogs that has the warehouse
        for (ShipmentLog log : shipmentLogHandler.getShipmentLogs()) {
            if (log.getWarehouse().equals(warehouse)) {
                updateShipmentLog(log, UpdateFieldShipmentLog.WAREHOUSE, warehouse);
            } dataService.updateWarehouseShipmentInformation(warehouse);
              dataService.updateShipmentInformation(log.getShipment());
        }

        // Updates the warehouse for all inspectionlogs that has the warehouse
        for (InspectionLog log : inspectionLogHandler.getInspectionLogs()) {
            if (log.getWarehouse().equals(warehouse)) {
                updateInspectionLog(log, UpdateFieldInspectionLog.WAREHOUSE, warehouse);
            } dataService.updateMostRecentInspectionDateForWarehouse(warehouse);
        }
    }

    public void deleteWarehouse(Warehouse warehouse){
        warehouseHandler.deleteWarehouse(warehouse);

        // Deletes all shipmentlogs for the warehouse
        for (ShipmentLog log : shipmentLogHandler.getShipmentLogs()) {
            if (log.getWarehouse().equals(warehouse)) {
                deleteShipmentLog(log);
            } dataService.updateShipmentInformation(log.getShipment());
        }    
        //Deletes all inspectionlogs for the warehouse
        for (InspectionLog log : inspectionLogHandler.getInspectionLogs()) {
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
        ShipmentLog shipmentLog = shipmentLogHandler.createShipmentLog(date, direction, warehouse, shipment);
        dataService.updateWarehouseShipmentInformation(warehouse);
        dataService.updateAverageTimeShipmentSpendsAtWarehouse(warehouse);
        dataService.updateShipmentInformation(shipment);
        return shipmentLog;

    }

    public ObservableList<ShipmentLog> readShipmentLogs() {
        return shipmentLogHandler.getShipmentLogs();
    }

    public void updateShipmentLog(ShipmentLog shipmentLog, UpdateFieldShipmentLog field, Object newValue) throws Exception {
        Warehouse oldWarehouse = shipmentLog.getWarehouse();
        Shipment oldShipment = shipmentLog.getShipment();
        shipmentLogHandler.updateShipmentLog(shipmentLog, field, newValue);
        Warehouse newWarehouse = shipmentLog.getWarehouse();
        Shipment newShipment = shipmentLog.getShipment();
        
        dataService.updateWarehouseShipmentInformation(oldWarehouse);
        if (!oldWarehouse.equals(newWarehouse)) {
            dataService.updateWarehouseShipmentInformation(newWarehouse);
        } 
        dataService.updateShipmentInformation(oldShipment);
        if (!oldShipment.equals(newShipment)) {
            dataService.updateShipmentInformation(newShipment);
        }
    }

    public void deleteShipmentLog(ShipmentLog shipmentLog) {
        Shipment  shipment = shipmentLog.getShipment();
        Warehouse warehouse = shipmentLog.getWarehouse();
        shipmentLogHandler.deleteShipmentLog(shipmentLog);
        dataService.updateWarehouseShipmentInformation(warehouse);
        dataService.updateShipmentInformation(shipment);
    }

    // InspectionLogHandler

    // public InspectionLogHandler getInspectionLogHandler() {
    //     return inspectionLogHandler;
    // }

    public InspectionLog createInspectionLog(Shipment shipment, Warehouse warehouse, LocalDate date, String inspector, String result) {
        InspectionLog newLog = inspectionLogHandler.createInspectionLog(shipment, warehouse, date, inspector, result);
        dataService.updateMostRecentInspectionDateForWarehouse(warehouse);
        return newLog;
    }

    public ObservableList<InspectionLog> readInspectionLogs() {
        return inspectionLogHandler.getInspectionLogs();
    }

    public void updateInspectionLog(InspectionLog inspectionLog, UpdateFieldInspectionLog field, Object newValue) throws Exception {
        Warehouse oldWarehouse = inspectionLog.getWarehouse();
        Shipment oldShipment = inspectionLog.getShipment();
        inspectionLogHandler.updateInspectionLog(inspectionLog, field, newValue);
        Warehouse newWarehouse = inspectionLog.getWarehouse();
        Shipment newShipment = inspectionLog.getShipment();
        dataService.updateMostRecentInspectionDateForWarehouse(oldWarehouse);
        if (!oldWarehouse.equals(newWarehouse)) {
            dataService.updateMostRecentInspectionDateForWarehouse(newWarehouse);
        }
        dataService.updateShipmentInformation(oldShipment);
        if (!oldShipment.equals(newShipment)) {
            dataService.updateShipmentInformation(newShipment);
        }
    }

    public void deleteInspectionLog(InspectionLog inspectionLog) {
        Warehouse warehouse = inspectionLog.getWarehouse();
        inspectionLogHandler.deleteInspectionLog(inspectionLog);
        dataService.updateMostRecentInspectionDateForWarehouse(warehouse);
    }


private void addTestData() throws Exception{
    Warehouse warehouse1 = createWarehouse("Odins Nest", Location.MIDDLE, "Storgatan 1", 100);
    Warehouse warehouse2 = createWarehouse("Thors House",Location.NORTH, "Storgatan 2", 200);
    Warehouse warehouse3 = createWarehouse("Adams Warehouse",Location.SOUTH, "Storgatan 3", 300);
    Warehouse warehouse4 = createWarehouse("Eves Warehouse",Location.MIDDLE, "Storgatan 4", 400);
    Warehouse warehouse5 = createWarehouse("Liliths Warehouse",Location.SOUTH, "Storgatan 5", 500);
    Warehouse warehouse6 = createWarehouse("Lucifers Warehouse",Location.NORTH, "Storgatan 6", 600);
    Warehouse warehouse7 = createWarehouse("Gabriels Warehouse",Location.MIDDLE, "Storgatan 7", 700);
    Warehouse warehouse8 = createWarehouse("Raphaels Warehouse",Location.SOUTH, "Storgatan 8", 800);
    Warehouse warehouse9 = createWarehouse("Uriels Warehouse",Location.NORTH, "Storgatan 9", 900);
    Warehouse warehouse10 = createWarehouse("Azraels Warehouse",Location.MIDDLE, "Storgatan 10", 1000);

    Shipment shipment1 = createShipment();
    Shipment shipment2 = createShipment();
    Shipment shipment3 = createShipment();
    Shipment shipment4 = createShipment();
    Shipment shipment5 =  createShipment();
    Shipment shipment6 =  createShipment();
    Shipment shipment7 = createShipment();
    Shipment shipment8 = createShipment();

    ShipmentLog shipmentLog = createShipmentLog(LocalDate.of(2020,8,9), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog shipmentLog2 = createShipmentLog(LocalDate.of(2020,8,10), Direction.OUTGOING, warehouse1, shipment1);
    ShipmentLog shipmentLog3 = createShipmentLog(LocalDate.of(2020,8,11), Direction.INCOMING, warehouse2, shipment2);
    ShipmentLog shipmentLog4 = createShipmentLog(LocalDate.of(2020,8,12), Direction.OUTGOING, warehouse2, shipment2);
    ShipmentLog shipmentLog5 = createShipmentLog(LocalDate.of(2020,8,10), Direction.INCOMING, warehouse3, shipment3);
    ShipmentLog shipmentLog6 = createShipmentLog(LocalDate.of(2020,8,15), Direction.OUTGOING, warehouse3, shipment3);

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
        inspectionLogHandler.clearData();
        shipmentLogHandler.clearData();
        // saem for dataService???


        // Do this for all the other classes also
        // shipmentLogs = new ArrayList<>();
        // warehouses = new ArrayList<>();
        // inspectionLogs = new ArrayList<>();

        Shipment.resetGeneratedIds();
    }

}