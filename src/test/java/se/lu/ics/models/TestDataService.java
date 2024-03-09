package se.lu.ics.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Period;

import javafx.collections.ObservableList;

public class TestDataService {
            DataManager dataManager;
    private DataService dataService;
    private Warehouse warehouse;
    private Shipment shipment;

    @BeforeEach
    public void setUp() throws Exception{
        DataManager.getInstance();
        dataService = new DataService();
        warehouse = new Warehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
        shipment = new Shipment();
        ShipmentLog shipmentLog = new ShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse, shipment);
        InspectionLog inspectionLog = new InspectionLog(shipment, warehouse, LocalDate.now(), "Inspector", "Result");

    }

    @Test
    public void testGetShipmentLogsForWarehouse() {
        // Assuming some shipment logs exist for the warehouse
        ObservableList<ShipmentLog> shipmentLogsForWarehouse = DataService.getShipmentLogsForWarehouse(warehouse);

        assertNotNull(shipmentLogsForWarehouse);
        assertEquals(0, shipmentLogsForWarehouse.size()); // Assuming no shipment logs exist for the warehouse initially
    }

    @Test
    public void testGetShipmentLogsForShipment() {
        // Assuming some shipment logs exist for the shipment
        ObservableList<ShipmentLog> shipmentLogsForShipment = DataService.getShipmentLogsForShipment(shipment);

        assertNotNull(shipmentLogsForShipment);
        assertEquals(0, shipmentLogsForShipment.size()); // Assuming no shipment logs exist for the shipment initially
    }

    @Test
    public void testUpdateMostRecentInspectionDateForWarehouse() {
        // Assuming some inspection logs exist for the warehouse
        dataService.updateMostRecentInspectionDateForWarehouse(warehouse);
        LocalDate mostRecentInspectionDate = warehouse.getMostRecentInspectionDate();

        assertNotNull(mostRecentInspectionDate);
        // Add assertions based on specific scenarios and data
    }

    @Test
    public void testGetInspectionsLogsForShipment() {
        // Assuming some inspection logs exist for the shipment
        ObservableList<InspectionLog> inspectionLogsForShipment = DataService.getInspectionsLogsForShipment(shipment);

        assertNotNull(inspectionLogsForShipment);
        assertEquals(0, inspectionLogsForShipment.size()); // Assuming no inspection logs exist for the shipment initially
    }

    @Test
    public void testGetInspectionLogsForWarehouse() {
        // Assuming some inspection logs exist for the warehouse
        ObservableList<InspectionLog> inspectionLogsForWarehouse = DataService.getInspectionLogsForWarehouse(warehouse);

        assertNotNull(inspectionLogsForWarehouse);
        assertEquals(0, inspectionLogsForWarehouse.size()); // Assuming no inspection logs exist for the warehouse initially
    }

    @Test
public void testUpdateWarehouseShipmentInformation() throws Exception {
    Warehouse warehouse = new Warehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = new Shipment();
    ShipmentLog shipmentLog = new ShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse, shipment);
    // Assuming DataManager and DataService are properly set up
    DataManager dataManager = DataManager.getInstance();
    DataService dataService = new DataService();

    dataService.updateWarehouseShipmentInformation(warehouse);
    double totalShipmentVolume = warehouse.getCurrentStockLevel();

    assertNotNull(totalShipmentVolume);
    // Add assertions based on specific scenarios and data
}

@Test
public void testUpdateAverageTimeShipmentSpendsAtWarehouse() throws  Exception {
    Warehouse warehouse = new Warehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = new Shipment();
    ShipmentLog shipmentLog = new ShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse, shipment);
    // Assuming DataManager and DataService are properly set up
    DataManager dataManager = DataManager.getInstance();
    DataService dataService = new DataService();

    DataService.updateAverageTimeShipmentSpendsAtWarehouse(warehouse);
    Period averageTime = warehouse.getAverageTimeShipmentSpendsAtWarehouse();

    assertNotNull(averageTime);
    // Add assertions based on specific scenarios and data
}



   


}