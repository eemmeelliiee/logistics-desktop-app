package se.lu.ics.models;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModelIntegrationTest {

    // Warehouse testing
  @Test
    public void testGetCurrentStockLevel() {
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        ShipmentLog shipmentLog = new ShipmentLog(LocalDate.of(2021,02,23), Direction.INCOMING, warehouse, new Shipment(false, "OK"));
        assertEquals(1, warehouse.getCurrentStockLevel(), 0); // The third argument is the delta for floating-point comparisons
    }

    @Test
    public void testGetName() {
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        assertEquals("Test Warehouse", warehouse.getName());
    }

    @Test
    public void testSetName() {
        Warehouse warehouse = new Warehouse();
        warehouse.setName("New Warehouse Name");
        assertEquals("New Warehouse Name", warehouse.getName());
    }
    @Test
    public void testGetLocation() {
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.SOUTH, "Test Address", 100);
        assertEquals(Location.SOUTH, warehouse.getLocation());
    }

    @Test
    public void testSetLocation() {
        Warehouse warehouse = new Warehouse();
        warehouse.setLocation(Location.NORTH);
        assertEquals(Location.NORTH, warehouse.getLocation());
    }

    @Test
    public void testGetAddress() {
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.NORTH, "Test Address", 100);
        assertEquals("Test Address", warehouse.getAddress());
    }

    @Test
    public void testSetAddress() {
        Warehouse warehouse = new Warehouse();
        warehouse.setAddress("New Warehouse Address");
        assertEquals("New Warehouse Address", warehouse.getAddress());
    }

    @Test
    public void testGetCapacity() {
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        assertEquals(100, warehouse.getCapacity(), 0.01);
    }

    @Test
    public void testSetCapacity() {
        Warehouse warehouse = new Warehouse();
        warehouse.setCapacity(200);
        assertEquals(200, warehouse.getCapacity(), 0.01);
    }

    @Test
    public void testGetUsedCapacity() {
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        assertEquals("0.0%", warehouse.getUsedCapacity());
    }

    @Test
public void testGetShipmentCount() {
    ArrayList<ShipmentLog> shipments = new ArrayList<ShipmentLog>();
    Warehouse warehouse = new Warehouse();
    ShipmentLog shipmentLog = new ShipmentLog(); // Create a shipment log
    shipments.add(shipmentLog); // Add the shipment log to the shipments list
    warehouse.setShipments(shipments); // Set the shipments list in the warehouse
    assertEquals(1, warehouse.getShipmentCount()); // Check if the count is 1
}

    @Test
    public void testGetInspectionCount() {
        ArrayList<InspectionLog> inspections = new ArrayList<InspectionLog>();
        Warehouse warehouse = new Warehouse();
        InspectionLog inspectionLog = new InspectionLog();
        inspections.add(inspectionLog);
        warehouse.setInspections(inspections);
        assertEquals(1, warehouse.getInspectionCount(), 0.01);
       
    }

    @Test
    public void testAddShipmentLog() {
        // Create a new warehouse
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        Shipment shipment = new Shipment(false, "OK");
        // Create a shipment log for testing
        ShipmentLog shipmentLog = new ShipmentLog(LocalDate.of(2021,02,23), Direction.INCOMING, warehouse, shipment);
        ShipmentLog shipmentLog2 = new ShipmentLog(LocalDate.of(202,02,24), Direction.INCOMING, warehouse, shipment);
        // Add the shipment log to the warehouse
    
        // Verify that the shipment was added by checking if the stock level increased
        assertEquals(2,  warehouse.getCurrentStockLevel(), 0);
        assertEquals(2, shipment.getAmountOfShipmentLogs(), 0);
    }

    @Test
    public void testRemoveShipment() {
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        Shipment shipment = new Shipment(false, "OK");
        ShipmentLog shipmentLog = new ShipmentLog(LocalDate.of(2021,02,23), Direction.INCOMING, warehouse, shipment);
        warehouse.removeShipmentLog(shipmentLog);
        assertEquals(0, warehouse.getShipmentCount(), 0.01);
    }

    @Test
    public void testAddInspectionToWarehouse() {
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        Shipment shipment = new Shipment(false, "OK");
        InspectionLog inspectionLog = new InspectionLog(shipment, warehouse, LocalDate.of(2021,2,3), "Test Inspector", "OK");
        assertEquals(1, warehouse.getInspectionCount(), 0.01);
        assertEquals(true, shipment.getInspected());
        assertEquals(1, shipment.getInspectionCount());
    }

    // Shipment testing
    @Test
    public void testGetShipmentId() {
        Shipment shipment = new Shipment(false, "OK");
        assertNotNull(shipment.getShipmentId());
    }

    @Test
    public void testGetInspected() {
        Shipment shipment = new Shipment(true, "Test Label");
        assertTrue(shipment.getInspected());
    }

    @Test
    public void testSetInspected() {
        Shipment shipment = new Shipment();
        shipment.setInspected(true);
        assertTrue(shipment.getInspected());
    }

    @Test
    public void testGetLabel() {
        Shipment shipment = new Shipment(true, "Test Label");
        assertEquals("Test Label", shipment.getLabel());
    }

    @Test
    public void testSetLabel() {
        Shipment shipment = new Shipment();
        shipment.setLabel("New Label");
        assertEquals("New Label", shipment.getLabel());
    }


    @Test
    public void testAddShipmentLogToShipment() {
        Shipment shipment = new Shipment(false, "OK");
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        ShipmentLog shipmentLog = new ShipmentLog(LocalDate.of(2013,3,9), Direction.INCOMING, warehouse, shipment);
        assertTrue(shipment.getShipmentLogs().contains(shipmentLog));
    }

    @Test
    public void testRemoveShipmentLog() {
        Shipment shipment = new Shipment(true, "Test Label");
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        ShipmentLog shipmentLog = new ShipmentLog(LocalDate.of(2013,3,9), Direction.INCOMING, warehouse, shipment);
        shipment.removeShipmentLog(shipmentLog);
        assertFalse(shipment.getShipmentLogs().contains(shipmentLog));
    }

    @Test
    public void testAddInspectionToShipment() {
        Shipment shipment = new Shipment(false, "OK");
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        InspectionLog inspectionLog = new InspectionLog(shipment, warehouse, LocalDate.of(2023,1,2), "Test Inspector", "OK");
        assertTrue(shipment.getInspectionsMade().contains(inspectionLog));
    }

    @Test
    public void testRemoveInspection() {
        Shipment shipment = new Shipment(true, "Test Label");
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        InspectionLog inspectionLog = new InspectionLog(shipment, warehouse, LocalDate.of(2023,1,2), "Test Inspector", "OK");
        shipment.removeInspection(inspectionLog);
        assertFalse(shipment.getInspectionsMade().contains(inspectionLog));
    }
    @Test
    public void testGenerateRandomID() {
        Shipment shipment = new Shipment(true, "Test Label" );
        assertNotNull(shipment.generateRandomID());
    }

    @Test
    public void testGetShipmentLogs() {
        Shipment shipment = new Shipment(false, "OK");
        assertNotNull(shipment.getShipmentLogs());
        assertEquals(0, shipment.getAmountOfShipmentLogs());
    }

    @Test
    public void testSetShipmentLogs() {
        Shipment shipment = new Shipment(false, "OK");
        Warehouse warehouse = new Warehouse("Test name", Location.MIDDLE, "TEST ADDRESS", 18.0);

        ShipmentLog shipmentLog = new ShipmentLog(LocalDate.of(2019,9,2), Direction.INCOMING, warehouse, shipment);
        ShipmentLog shipmentLog1 = new ShipmentLog(LocalDate.of(2017,9,2), Direction.INCOMING, warehouse, shipment);

        shipment.addShipmentLog(shipmentLog);
        shipment.addShipmentLog(shipmentLog1);

        // Get the already initialized shipment logs
        ArrayList<ShipmentLog> shipmentLogs = shipment.getShipmentLogs();
        // Set the modified shipment logs
        shipment.setShipmentLogs(shipmentLogs);
        assertEquals(shipmentLogs, shipment.getShipmentLogs());
    }

    @Test
    public void testGetInspectionsMade() {
        Shipment shipment = new Shipment(false, "OK");
        Warehouse warehouse = new Warehouse("Test", Location.MIDDLE, "TEST", 100.0);
        InspectionLog inspectionLog = new InspectionLog(shipment, warehouse, LocalDate.of(2021,9,8), "OLA", "OK");
        assertNotNull(shipment.getInspectionsMade());
    }

    @Test
    public void testSetInspecLogs() {
        Shipment shipment = new Shipment();
        ArrayList<InspectionLog> inspections = new ArrayList<>();
        shipment.setInspecLogs(inspections);
        assertEquals(inspections, shipment.getInspectionsMade());
    }

    // CREATING TESTS FOR INSPECTION LOG
    @Test
    public void testGetShipment() {
        Shipment shipment = new Shipment(false, "OK");
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        InspectionLog inspectionLog = new InspectionLog(shipment, warehouse, LocalDate.now(), "Inspector", "Pass");
        assertEquals(shipment, inspectionLog.getShipment());
    }

    @Test
    public void testGetDate() {
        Shipment shipment = new Shipment(false, "OK");
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        InspectionLog inspectionLog = new InspectionLog(shipment, warehouse, LocalDate.now(), "Inspector", "Pass");
        assertEquals(LocalDate.now(), inspectionLog.getDate());
    }

    @Test
    public void testGetInspector() {
        Shipment shipment = new Shipment(false, "OK");
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        InspectionLog inspectionLog = new InspectionLog(shipment, warehouse, LocalDate.now(), "Inspector", "Pass");
        assertEquals("Inspector", inspectionLog.getInspector());
    }

    @Test
    public void testGetResult() {
        Shipment shipment = new Shipment(false, "OK");
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        InspectionLog inspectionLog = new InspectionLog(shipment, warehouse, LocalDate.now(), "Inspector", "Pass");
        assertEquals("Pass", inspectionLog.getResult());
    }

    @Test
    public void testToStringInspectionLog() {
        Shipment shipment = new Shipment(false, "OK");
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        InspectionLog inspectionLog = new InspectionLog(shipment, warehouse, LocalDate.now(), "Inspector", "Pass");
        String expectedString = "\n\nShipmentID=" + shipment.getShipmentId() +
                "\nWarehouse=" + warehouse.getName() +
                "\nDate=" + LocalDate.now() +
                "\nInspector='Inspector" +
                "\nResult='Pass" +
                "\n";
        assertEquals(expectedString, inspectionLog.toString());
    }
    
    @Test
    public void testSetShipment() {
        Shipment shipment = new Shipment(false, "OK");
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        InspectionLog inspectionLog = new InspectionLog(shipment, warehouse, LocalDate.now(), "John Doe", "Pass");
        assertEquals(shipment, inspectionLog.getShipment());
    }

    @Test
    public void testSetDate() {
        LocalDate date = LocalDate.now();
        InspectionLog inspectionLog = new InspectionLog(new Shipment(false, "ok"), new Warehouse("Test name", Location.MIDDLE,"addess",499), date, "John Doe", "Pass");
        assertEquals(date, inspectionLog.getDate());
    }

    @Test
    public void testSetInspector() {
        String inspector = "John Doe";
        InspectionLog inspectionLog = new InspectionLog(new Shipment(false, "ok"), new Warehouse("Test name", Location.MIDDLE,"addess",499), LocalDate.now(), inspector, "Pass");
        assertEquals(inspector, inspectionLog.getInspector());
    }

    @Test
    public void testSetResult() {
        String result = "Pass";
        InspectionLog inspectionLog = new InspectionLog(new Shipment(false, "OK"), new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100), LocalDate.now(), "John Doe", result);
        assertEquals(result, inspectionLog.getResult());
    }

    @Test
    public void testInspectionHistory() {
        Shipment shipment = new Shipment(false, "OK");
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        LocalDate date = LocalDate.now();
        String inspector = "John Doe";
        String result = "Pass";
        InspectionLog inspectionLog = new InspectionLog(shipment, warehouse, date, inspector, result);
        String expectedHistory = "Inspection history for shipment with ID: " + shipment.getShipmentId() + "[" + inspectionLog.toString() + "]";
        assertEquals(expectedHistory, InspectionLog.inspectionHistory(shipment));
    }

    //Create shipmentlog tests:
    @Test
    public void testConstructorAndGetters() {
        LocalDate date = LocalDate.now();
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        Shipment shipment = new Shipment(false, "OK");
        Direction direction = Direction.INCOMING;

        ShipmentLog shipmentLog = new ShipmentLog(date, direction, warehouse, shipment);

        assertEquals(date, shipmentLog.getDate());
        assertEquals(direction, shipmentLog.getDirection());
        assertEquals(warehouse, shipmentLog.getWarehouse());
        assertEquals(shipment, shipmentLog.getShipment());
    }

    @Test
    public void testSetters() {
        LocalDate date = LocalDate.now();
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        Shipment shipment = new Shipment(false, "OK");
        Direction direction = Direction.INCOMING;

        ShipmentLog shipmentLog = new ShipmentLog();

        shipmentLog.setDate(date);
        shipmentLog.setDirection(direction);
        shipmentLog.setWarehouse(warehouse);
        shipmentLog.setShipment(shipment);

        assertEquals(date, shipmentLog.getDate());
        assertEquals(direction, shipmentLog.getDirection());
        assertEquals(warehouse, shipmentLog.getWarehouse());
        assertEquals(shipment, shipmentLog.getShipment());
    }

    @Test
    public void testToStringShipmentLog() {
        LocalDate date = LocalDate.now();
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        Shipment shipment = new Shipment(false, "OK");
        Direction direction = Direction.INCOMING;

        ShipmentLog shipmentLog = new ShipmentLog(date, direction, warehouse, shipment);

        String expectedString = "[\n\nShipmentID: " + shipment.getShipmentId() +
                                "\nDate: " + date +
                                "\nDirection: " + direction +
                                "\nWarehouse: " + warehouse.getName() + "\n]";

        assertEquals(expectedString, shipment.getShipmentLogs().toString());
    }

    @Test
    public void testAddToShipmentAndWarehouse() {
        LocalDate date = LocalDate.now();
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        Shipment shipment = new Shipment(false, "OK");
        Direction direction = Direction.INCOMING;

        ShipmentLog shipmentLog = new ShipmentLog(date, direction, warehouse, shipment);

        assertTrue(shipment.getShipmentLogs().contains(shipmentLog));
        assertTrue(warehouse.getShipments().contains(shipmentLog));
    }
}