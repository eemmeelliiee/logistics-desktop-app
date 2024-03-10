package se.lu.ics.models;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;

public class TestShipmentLogHandler {

    private DataManager dataManager;
 
    @BeforeEach
    public void setUp() throws Exception{
        dataManager = DataManager.getInstance();
    }

    @AfterEach
    public void tearDown() {
        dataManager.clearData(); // Assuming WarehouseHandler has a clearData method
    }

    @Test
    public void testCreateShipmentLog() throws Exception {
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

        assertEquals(date, log.getDate());
        assertEquals(direction, log.getDirection());
        assertEquals(warehouse, log.getWarehouse());
        assertEquals(shipment, log.getShipment());
    }

    @Test
    public void testUpdateShipmentLog() throws Exception {
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

        LocalDate newDate = LocalDate.now().plusDays(1);
        Direction newDirection = Direction.OUTGOING;
        Shipment newShipment = dataManager.createShipment();
        Warehouse newWarehouse = dataManager.createWarehouse("New Test Warehouse", Location.MIDDLE, "Test Address", 1000);

        dataManager.updateShipmentLog(log, UpdateFieldShipmentLog.DATE, newDate);
        dataManager.updateShipmentLog(log, UpdateFieldShipmentLog.DIRECTION, newDirection);
        dataManager.updateShipmentLog(log, UpdateFieldShipmentLog.WAREHOUSE, newWarehouse);
        dataManager.updateShipmentLog(log, UpdateFieldShipmentLog.SHIPMENT, newShipment);

        assertEquals(newDate, log.getDate());
        assertEquals(newDirection, log.getDirection());
        assertEquals(newWarehouse, log.getWarehouse());
        assertEquals(newShipment, log.getShipment());
    }

    @Test
    public void testDeleteShipmentLog() throws Exception {
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

        dataManager.deleteShipmentLog(log);

        assertFalse(dataManager.readShipmentLogs().contains(log));
    }
    @Test
    public void testNeedsAttention() throws Exception{
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;
    
        ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);
    
        LocalDate newDate = LocalDate.now().plusDays(15);
        Direction newDirection = Direction.OUTGOING;
        Warehouse newWarehouse = new Warehouse("New Test Warehouse", Location.SOUTH, "New Test Address", 3000);
        Shipment newShipment = new Shipment();
    
        ShipmentLog other = dataManager.createShipmentLog(newDate, newDirection, newWarehouse, newShipment);
    
        // Create a corresponding log with the opposite direction
        dataManager.createShipmentLog(newDate, newDirection.opposite(), newWarehouse, newShipment);
    }

    @Test
    public void testValidateShipmentLog() throws Exception{
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

        LocalDate newDate = LocalDate.now().plusDays(15);
        Direction newDirection = Direction.OUTGOING;
        ShipmentLog other = dataManager.createShipmentLog(newDate, newDirection, warehouse, shipment);

        // Create a corresponding log with the opposite direction
        dataManager.createShipmentLog(newDate, newDirection.opposite(), warehouse, shipment);
        assertTrue(log.needsAttention(other));
    }

    @Test
    public void testGetShipmentLogs() throws Exception{
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

        assertTrue(dataManager.readShipmentLogs().contains(log));
    }


    @Test
    public void testGetShipmentLogHandler() throws Exception{
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

        assertTrue(dataManager.readShipmentLogs().contains(log));
    }

    @Test
    public void testSetShipmentLogHandler() throws Exception{
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

        assertTrue(dataManager.readShipmentLogs().contains(log));
    }

    @Test
    public void testSetShipmentLogs() throws Exception{
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

        assertTrue(dataManager.readShipmentLogs().contains(log));
    }

    @Test
public void testCreateShipmentLogWithExistingLog() throws Exception {
    Shipment shipment = dataManager.createShipment();
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
    LocalDate date = LocalDate.now();
    Direction direction = Direction.INCOMING;

    // Create a shipment log
    dataManager.createShipmentLog(date, direction, warehouse, shipment);

    // Try to create another shipment log with the same parameters
    assertThrows(Exception.class, () -> {
        dataManager.createShipmentLog(date, direction, warehouse, shipment);
    }, "Warning: A shipment log with the same shipment, warehouse and direction already exists without a corresponding log with the opposite direction.");
}



@Test
public void testUpdateShipmentLogWithExistingLog() throws Exception {
    Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
    LocalDate date = LocalDate.now();
    Direction direction = Direction.INCOMING;

    // Create a shipment log
    ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

    // Try to update the shipment log with the same parameters
    assertThrows(Exception.class, () -> {
        dataManager.updateShipmentLog(log, UpdateFieldShipmentLog.SHIPMENT, shipment);
        dataManager.updateShipmentLog(log, UpdateFieldShipmentLog.WAREHOUSE, warehouse);
        dataManager.updateShipmentLog(log, UpdateFieldShipmentLog.DIRECTION, direction);
    }, "Warning: A shipment log with the same shipment, warehouse and direction already exists without a corresponding log with the opposite direction.");
}


@Test
public void testUpdateShipmentLogWithInvalidDirection() throws Exception {
    Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
    LocalDate date = LocalDate.now();
    Direction direction = Direction.INCOMING;

    // Create a shipment log
    ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

    // Try to update the shipment log with a direction that is the same as the existing direction
    assertThrows(Exception.class, () -> {
        dataManager.updateShipmentLog(log, UpdateFieldShipmentLog.DIRECTION, direction);
    });
}

@Test
public void testUpdateShipmentLogWithInvalidWarehouse() throws Exception {
    Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
    LocalDate date = LocalDate.now();
    Direction direction = Direction.INCOMING;

    // Create a shipment log
    ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

    // Try to update the shipment log with a warehouse that is the same as the existing warehouse
    assertThrows(Exception.class, () -> {
        dataManager.updateShipmentLog(log, UpdateFieldShipmentLog.WAREHOUSE, warehouse);
    });
}

@Test
public void testUpdateShipmentLogWithInvalidShipment() throws Exception {
    Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
    LocalDate date = LocalDate.now();
    Direction direction = Direction.INCOMING;

    // Create a shipment log
    ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

    // Try to update the shipment log with a shipment that is the same as the existing shipment
    assertThrows(Exception.class, () -> {
        dataManager.updateShipmentLog(log, UpdateFieldShipmentLog.SHIPMENT, shipment);
    });
}
// not needed since shipmentlog will be selected in tableview
// @Test
// public void testDeleteShipmentLogWithExistingLog() throws Exception {
//     Warehouse warehouse = new Warehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
//     Shipment shipment = new Shipment();
//     LocalDate date = LocalDate.now();
//     Direction direction = Direction.INCOMING;

//     // Create a shipment log
//     ShipmentLog log = shipmentLogHandler.createShipmentLog(date, direction, warehouse, shipment);

//     // Try to delete the shipment log
//     shipmentLogHandler.deleteShipmentLog(log);

//     // Try to delete the shipment log again
//     assertThrows(Exception.class, () -> {
//         shipmentLogHandler.deleteShipmentLog(log);
//     });
// }

    @Test
    public void testValidateDate() throws Exception {
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        // Create a shipment log
        ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

        // Try to create another shipment log with the same parameters
        assertThrows(Exception.class, () -> {
            dataManager.createShipmentLog(date, direction, warehouse, shipment);
        }, "Warning: A shipment log with the same shipment, warehouse and direction already exists without a corresponding log with the opposite direction.");
    }

    @Test
    public void testUpdateAttentionStatus() throws Exception {
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        // Create a shipment log
        ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

        // Try to create another shipment log with the same parameters
        assertThrows(Exception.class, () -> {
            dataManager.createShipmentLog(date, direction, warehouse, shipment);
        }, "Warning: A shipment log with the same shipment, warehouse and direction already exists without a corresponding log with the opposite direction.");
    }

    @Test
    public void testFindShipmentLog() throws Exception {
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        // Create a shipment log
        ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

        // Try to create another shipment log with the same parameters
        assertThrows(Exception.class, () -> {
            dataManager.createShipmentLog(date, direction, warehouse, shipment);
        }, "Warning: A shipment log with the same shipment, warehouse and direction already exists without a corresponding log with the opposite direction.");
    }

    @Test
    public void testCountLogs() throws Exception {
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        // Create a shipment log
        ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

        // Try to create another shipment log with the same parameters
        assertThrows(Exception.class, () -> {
            dataManager.createShipmentLog(date, direction, warehouse, shipment);
        }, "Warning: A shipment log with the same shipment, warehouse and direction already exists without a corresponding log with the opposite direction.");
    }

    @Test
    public void testValidateShipmentLogWithExistingLog() throws Exception {
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        // Create a shipment log
        ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

        // Try to create another shipment log with the same parameters
        assertThrows(Exception.class, () -> {
            dataManager.createShipmentLog(date, direction, warehouse, shipment);
        }, "Warning: A shipment log with the same shipment, warehouse and direction already exists without a corresponding log with the opposite direction.");
    }

   


}