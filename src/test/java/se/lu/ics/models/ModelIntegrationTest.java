package se.lu.ics.models;
import java.lang.reflect.Array;
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

        // Assert that the current stock level is equal to the number of shipments added
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
    public void testAddShipment() {
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
        warehouse.removeShipment(shipmentLog);
        assertEquals(0, warehouse.getShipmentCount(), 0.01);
    }

    @Test
    public void testAddInspection() {
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        Shipment shipment = new Shipment(false, "OK");
        InspectionLog inspectionLog = new InspectionLog(shipment, warehouse, LocalDate.now(), "Test Inspector", "OK");
        assertEquals(1, warehouse.getInspectionCount(), 0.01);
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
    public void testAddShipmentLog() {
        Shipment shipment = new Shipment(false, "OK");
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        ShipmentLog shipmentLog = new ShipmentLog(LocalDate.of(2013,3,9), Direction.INCOMING, warehouse, shipment);
        assertTrue(shipment.getShipmentLogs().contains(shipmentLog.toString()));
    }

    @Test
    public void testRemoveShipmentLog() {
        Shipment shipment = new Shipment(true, "Test Label");
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 100);
        ShipmentLog shipmentLog = new ShipmentLog(LocalDate.of(2013,3,9), Direction.INCOMING, warehouse, shipment);
        shipment.removeShipmentLog(shipmentLog);
        assertFalse(shipment.getShipmentLogs().contains(shipmentLog.toString()));
    }

    @Test
    public void testAddInspectionToShipment() {
        Shipment shipment = new Shipment();
        InspectionLog inspectionLog = new InspectionLog();
        assertTrue(shipment.getInspectionsMade().contains(inspectionLog));
    }

    @Test
    public void testRemoveInspection() {
        Shipment shipment = new Shipment();
        InspectionLog inspectionLog = new InspectionLog();
        shipment.removeInspection(inspectionLog);
        assertFalse(shipment.getInspectionsMade().contains(inspectionLog));
    }
    @Test
    public void testGenerateRandomID() {
        Shipment shipment = new Shipment();
        assertNotNull(shipment.generateRandomID());
    }

    @Test
    public void testGetShipmentLogs() {
        Shipment shipment = new Shipment();
        assertNotNull(shipment.getShipmentLogs());
    }

    @Test
    public void testSetShipmentLogs() {
        Shipment shipment = new Shipment();
        ArrayList<ShipmentLog> shipmentLogs = new ArrayList<>();
        shipment.setShipmentLogs(shipmentLogs);
        assertEquals(shipmentLogs, shipment.getShipmentLogs());
    }

    @Test
    public void testGetInspectionsMade() {
        Shipment shipment = new Shipment();
        assertNotNull(shipment.getInspectionsMade());
    }

    @Test
    public void testSetInspecLogs() {
        Shipment shipment = new Shipment();
        ArrayList<InspectionLog> inspections = new ArrayList<>();
        shipment.setInspecLogs(inspections);
        assertEquals(inspections, shipment.getInspectionsMade());
    }
}
/* 
    public void test(){
        // Creating warehouse objects:
        Warehouse warehouse1 = new Warehouse("Odin's Vault", Location.NORTH, "Valhalla", 1000.0);
        Warehouse warehouse2 = new Warehouse("Asgard", Location.SOUTH, "Bifrost", 1000.0);
        Warehouse warehouse3 = new Warehouse("Midgard", Location.MIDDLE, "Earth", 1000.0);
  
          // Creating shipment objects:
        Shipment shipment1 = new Shipment(false, "OK");
        Shipment shipment2 = new Shipment(false, "OK");
        Shipment shipment3 = new Shipment(false, "OK");
  
      
  
          // Creating inspection objects:
          // inspections are added to the shipment and warehouse objects inside the constructor for InspectionLog (add-methods declared in Warehouse and Shipment)
    
  
          InspectionLog inspection1 = new InspectionLog(shipment1, warehouse1, LocalDate.now(), "Thor", "OK");
          InspectionLog inspection2 = new InspectionLog(shipment2, warehouse1, LocalDate.of(2021,02,06), "Loki", "OK");
          InspectionLog inspection3 = new InspectionLog(shipment3, warehouse1, LocalDate.of(2021,02,07), "Odin", "OK");
          InspectionLog inspection4 = new InspectionLog(shipment1, warehouse1, LocalDate.of(2021,02,21), "Frigg", "OK");
          InspectionLog inspection5 = new InspectionLog(shipment2, warehouse2, LocalDate.of(2021,02,22), "Heimdall", "OK");
  
          // Creating shipmentLog objects:
          // shipmentlogs are added to the shipment and warehouse objects inside the constructor for ShipmentLog (add-methods declared in Warehouse and Shipment))
        ShipmentLog shipmentLog1 = new ShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
        ShipmentLog shipmentLog2 = new ShipmentLog(LocalDate.of(2021,02,06), Direction.INCOMING, warehouse1, shipment2);
        ShipmentLog shipmentLog3 = new ShipmentLog(LocalDate.of(2021,02,07), Direction.INCOMING, warehouse1, shipment3);
        ShipmentLog shipmentLog4 = new ShipmentLog(LocalDate.of(2021,02,21), Direction.OUTGOING, warehouse1, shipment1);
          ShipmentLog shipmentLog5 = new ShipmentLog(LocalDate.of(2021,02,22), Direction.OUTGOING, warehouse1, shipment2);
  
  // System.out.println(ShipmentLog.shipmentHistory(shipment1));
  //System.out.println("warehouse list: " + warehouse1.getShipments());
  
    //  System.out.println("Current capacity of warehouse " + warehouse1.getName() + ": " + currentCapacityWarehouse1);
    //  System.out.println("Current stock level of warehouse " + warehouse1.getName() + ": " + warehouse1.getCurrentStockLevel());
     // System.out.println("Used capacity of warehouse " + warehouse1.getName() + ": " + warehouse1.getUsedCapacity());
    //  System.out.println("Capacity of warehouse " + warehouse1.getName() + ": " + warehouse1.getCapacity());
  
      System.out.println("\nShipments in warehouse " + warehouse1.getName() + ": " + warehouse1.getShipments());
      System.out.println("\nInspections in warehouse " + warehouse1.getName() + ": " + warehouse1.getInspections());
      System.out.println("\nInspections for shipment " + shipment1.getShipmentId() + ": " + shipment1.getInspectionsMade());
  
      System.out.println("Shipmentlogs of shipment1:" + shipment1.getShipmentLogs());
  
      System.out.println("get shipment id for shipment1:" + shipment1.getShipmentId());

      assertEquals(shipment1.getShipmentId(), shipment1.getShipmentId()); 
    

    }*/

