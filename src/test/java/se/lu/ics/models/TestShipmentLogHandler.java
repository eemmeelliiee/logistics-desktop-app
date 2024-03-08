package se.lu.ics.models;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.chart.PieChart.Data;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;

public class TestShipmentLogHandler {

    private DataManager dataManager;
    private ShipmentLogHandler shipmentLogHandler;
 
    @BeforeEach
    public void setUp() {
        dataManager = DataManager.getInstance();
        shipmentLogHandler = dataManager.getShipmentLogHandler();
    }

    @AfterEach
    public void tearDown() {
        shipmentLogHandler.clearData(); // Assuming WarehouseHandler has a clearData method
    }

    @Test
    public void testCreateShipmentLog() throws Exception {
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
        Shipment shipment = new Shipment();
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        ShipmentLog log = shipmentLogHandler.createShipmentLog(date, direction, warehouse, shipment);

        assertEquals(date, log.getDate());
        assertEquals(direction, log.getDirection());
        assertEquals(warehouse, log.getWarehouse());
        assertEquals(shipment, log.getShipment());
    }

    @Test
    public void testUpdateShipmentLog() throws Exception {
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        Shipment shipment = new Shipment();
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        ShipmentLog log = shipmentLogHandler.createShipmentLog(date, direction, warehouse, shipment);

        LocalDate newDate = LocalDate.now().plusDays(1);
        Direction newDirection = Direction.OUTGOING;
        Warehouse newWarehouse = new Warehouse("New Test Warehouse", Location.SOUTH, "New Test Address", 3000);
        Shipment newShipment = new Shipment();

        shipmentLogHandler.updateShipmentLog(log, UpdateFieldShipmentLog.DATE, newDate);
        shipmentLogHandler.updateShipmentLog(log, UpdateFieldShipmentLog.DIRECTION, newDirection);
        shipmentLogHandler.updateShipmentLog(log, UpdateFieldShipmentLog.WAREHOUSE, newWarehouse);
        shipmentLogHandler.updateShipmentLog(log, UpdateFieldShipmentLog.SHIPMENT, newShipment);

        assertEquals(newDate, log.getDate());
        assertEquals(newDirection, log.getDirection());
        assertEquals(newWarehouse, log.getWarehouse());
        assertEquals(newShipment, log.getShipment());
    }

    @Test
    public void testDeleteShipmentLog() throws Exception {
        ShipmentLogHandler handler = new ShipmentLogHandler();
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        Shipment shipment = new Shipment();
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        ShipmentLog log = handler.createShipmentLog(date, direction, warehouse, shipment);

        handler.deleteShipmentLog(log);

        assertFalse(handler.getShipmentLogs().contains(log));
    }
    @Test
    public void testNeedsAttention() throws Exception{
        ShipmentLogHandler handler = new ShipmentLogHandler();
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        Shipment shipment = new Shipment();
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;
    
        ShipmentLog log = handler.createShipmentLog(date, direction, warehouse, shipment);
    
        LocalDate newDate = LocalDate.now().plusDays(15);
        Direction newDirection = Direction.OUTGOING;
        Warehouse newWarehouse = new Warehouse("New Test Warehouse", Location.SOUTH, "New Test Address", 3000);
        Shipment newShipment = new Shipment();
    
        ShipmentLog other = handler.createShipmentLog(newDate, newDirection, newWarehouse, newShipment);
    
        // Create a corresponding log with the opposite direction
        handler.createShipmentLog(newDate, newDirection.opposite(), newWarehouse, newShipment);
    }

    @Test
    public void testValidateShipmentLog() throws Exception{
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        Shipment shipment = DataManager.getInstance().createShipment();
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        ShipmentLog log = shipmentLogHandler.createShipmentLog(date, direction, warehouse, shipment);

        LocalDate newDate = LocalDate.now().plusDays(15);
        Direction newDirection = Direction.OUTGOING;
        ShipmentLog other = shipmentLogHandler.createShipmentLog(newDate, newDirection, warehouse, shipment);

        // Create a corresponding log with the opposite direction
        shipmentLogHandler.createShipmentLog(newDate, newDirection.opposite(), warehouse, shipment);
        assertTrue(log.needsAttention(other));
    }

    @Test
    public void testGetShipmentLogs() throws Exception{
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        Shipment shipment = new Shipment();
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        ShipmentLog log = DataManager.getInstance().createShipmentLog(date, direction, warehouse, shipment);

        assertTrue(shipmentLogHandler.getShipmentLogs().contains(log));
    }


    @Test
    public void testGetShipmentLogHandler() throws Exception{
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        Shipment shipment = new Shipment();
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        ShipmentLog log = DataManager.getInstance().createShipmentLog(date, direction, warehouse, shipment);

        assertTrue(shipmentLogHandler.getShipmentLogs().contains(log));
    }

    @Test
    public void testSetShipmentLogHandler() throws Exception{
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        Shipment shipment = new Shipment();
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        ShipmentLog log = DataManager.getInstance().createShipmentLog(date, direction, warehouse, shipment);

        assertTrue(shipmentLogHandler.getShipmentLogs().contains(log));
    }

    @Test
    public void testSetShipmentLogs() throws Exception{
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        Shipment shipment = new Shipment();
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        ShipmentLog log = DataManager.getInstance().createShipmentLog(date, direction, warehouse, shipment);

        assertTrue(shipmentLogHandler.getShipmentLogs().contains(log));
    }

    @Test
public void testCreateShipmentLogWithExistingLog() throws Exception {
    Warehouse warehouse = new Warehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = new Shipment();
    LocalDate date = LocalDate.now();
    Direction direction = Direction.INCOMING;

    // Create a shipment log
    shipmentLogHandler.createShipmentLog(date, direction, warehouse, shipment);

    // Try to create another shipment log with the same parameters
    assertThrows(Exception.class, () -> {
        shipmentLogHandler.createShipmentLog(date, direction, warehouse, shipment);
    }, "Warning: A shipment log with the same shipment, warehouse and direction already exists without a corresponding log with the opposite direction.");
}



@Test
public void testUpdateShipmentLogWithExistingLog() throws Exception {
    Warehouse warehouse = new Warehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = new Shipment();
    LocalDate date = LocalDate.now();
    Direction direction = Direction.INCOMING;

    // Create a shipment log
    ShipmentLog log = shipmentLogHandler.createShipmentLog(date, direction, warehouse, shipment);

    // Try to update the shipment log with the same parameters
    assertThrows(Exception.class, () -> {
        shipmentLogHandler.updateShipmentLog(log, UpdateFieldShipmentLog.SHIPMENT, shipment);
        shipmentLogHandler.updateShipmentLog(log, UpdateFieldShipmentLog.WAREHOUSE, warehouse);
        shipmentLogHandler.updateShipmentLog(log, UpdateFieldShipmentLog.DIRECTION, direction);
    }, "Warning: A shipment log with the same shipment, warehouse and direction already exists without a corresponding log with the opposite direction.");
}


@Test
public void testUpdateShipmentLogWithInvalidDirection() throws Exception {
    Warehouse warehouse = new Warehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = new Shipment();
    LocalDate date = LocalDate.now();
    Direction direction = Direction.INCOMING;

    // Create a shipment log
    ShipmentLog log = shipmentLogHandler.createShipmentLog(date, direction, warehouse, shipment);

    // Try to update the shipment log with a direction that is the same as the existing direction
    assertThrows(Exception.class, () -> {
        shipmentLogHandler.updateShipmentLog(log, UpdateFieldShipmentLog.DIRECTION, direction);
    });
}

@Test
public void testUpdateShipmentLogWithInvalidWarehouse() throws Exception {
    Warehouse warehouse = new Warehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = new Shipment();
    LocalDate date = LocalDate.now();
    Direction direction = Direction.INCOMING;

    // Create a shipment log
    ShipmentLog log = shipmentLogHandler.createShipmentLog(date, direction, warehouse, shipment);

    // Try to update the shipment log with a warehouse that is the same as the existing warehouse
    assertThrows(Exception.class, () -> {
        shipmentLogHandler.updateShipmentLog(log, UpdateFieldShipmentLog.WAREHOUSE, warehouse);
    });
}

@Test
public void testUpdateShipmentLogWithInvalidShipment() throws Exception {
    Warehouse warehouse = new Warehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = new Shipment();
    LocalDate date = LocalDate.now();
    Direction direction = Direction.INCOMING;

    // Create a shipment log
    ShipmentLog log = shipmentLogHandler.createShipmentLog(date, direction, warehouse, shipment);

    // Try to update the shipment log with a shipment that is the same as the existing shipment
    assertThrows(Exception.class, () -> {
        shipmentLogHandler.updateShipmentLog(log, UpdateFieldShipmentLog.SHIPMENT, shipment);
    });
}
}