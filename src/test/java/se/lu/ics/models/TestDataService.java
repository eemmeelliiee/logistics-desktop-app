package se.lu.ics.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Period;

import javafx.collections.ObservableList;

public class TestDataService {
            DataManager dataManager;
    

    @BeforeEach
    public void setUp() {
        dataManager = DataManager.getInstance();
    }

    @AfterEach
    public void tearDown() {
        dataManager.clearData(); // Assuming warehousehandler has a clearData method

    }


    @Test
public void testUpdateWarehouseShipmentInformation() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment()    ;
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse, shipment);
    // Assuming DataManager and DataService are properly set up

    // dataService.updateWarehouseShipmentInformation(warehouse);
    double totalShipmentVolume = warehouse.getCurrentStockLevel();

    assertEquals(1, totalShipmentVolume);
    // Add assertions based on specific scenarios and data
}

@Test
public void testUpdateAverageTimeShipmentSpendsAtWarehouse() throws  Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.of(2021,8,9), Direction.INCOMING, warehouse, shipment);
   ShipmentLog log2 = dataManager.createShipmentLog(LocalDate.of(2021, 8, 10), Direction.OUTGOING, warehouse, shipment);
    // Assuming DataManager and DataService are properly set up

    Period averageTime = warehouse.getAverageTimeShipmentSpendsAtWarehouse();

    assertEquals(Period.ofDays(1), averageTime);
    // Add assertions based on specific scenarios and data
}

@Test
public void testUpdateShipmentInformation() throws Exception {
    Shipment shipment = dataManager.createShipment();
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse, shipment);
    // Assuming DataManager and DataService are properly set up

    // dataService.updateShipmentInformation(shipment);
    Warehouse currentWarehouse = shipment.getCurrentWarehouse();
    int totalNumberOfWarehouses = shipment.getTotalNumberOfWarehouses();

    assertEquals("Test Warehouse", currentWarehouse.getName());
    assertEquals(1,totalNumberOfWarehouses);
    // Add assertions based on specific scenarios and data


}

@Test
public void testFindBusiestWarehouse() throws Exception {
    Warehouse warehouse1 = dataManager.createWarehouse("Test Warehouse 1", Location.NORTH, "Test Address", 1000);
    Warehouse warehouse2 = dataManager.createWarehouse("Test Warehouse 2", Location.NORTH, "Test Address", 1000);
    Shipment shipment1 = dataManager.createShipment();
    Shipment shipment2 = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog log2 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse2, shipment2);
    // Assuming DataManager and DataService are properly set up

    String busiestWarehouse = DataService.getInstance().findBusiestWarehouse();

    assertEquals("Busiest warehouses are: Test Warehouse 1, Test Warehouse 2, using 0.1% of capacity", busiestWarehouse);
    // Add assertions based on specific scenarios and data

}

@Test

public void testFindBusiestWarehouseWhenNoShipments() throws Exception {
    Warehouse warehouse1 = dataManager.createWarehouse("Test Warehouse 1", Location.NORTH, "Test Address", 1000);
    Warehouse warehouse2 = dataManager.createWarehouse("Test Warehouse 2", Location.NORTH, "Test Address", 1000);
    // Assuming DataManager and DataService are properly set up

    String busiestWarehouse = DataService.getInstance().findBusiestWarehouse();

    assertEquals("No incoming shipmentlogs exist for any warehouse", busiestWarehouse);
    // Add assertions based on specific scenarios and data

}

@Test
public void testFindBusiestWarehouseWhenNoIncomingShipments() throws Exception {
    Warehouse warehouse1 = dataManager.createWarehouse("Test Warehouse 1", Location.NORTH, "Test Address", 1000);
    Warehouse warehouse2 = dataManager.createWarehouse("Test Warehouse 2", Location.NORTH, "Test Address", 1000);
    Shipment shipment1 = dataManager.createShipment();
    Shipment shipment2 = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse1, shipment1);
    ShipmentLog log2 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse2, shipment2);
    // Assuming DataManager and DataService are properly set up

    String busiestWarehouse = DataService.getInstance().findBusiestWarehouse();

    assertEquals("No incoming shipmentlogs exist for any warehouse", busiestWarehouse);
    // Add assertions based on specific scenarios and data

}

@Test
public void testFindBusiestWarehouseWhenNoOutgoingShipments() throws Exception {
    Warehouse warehouse1 = dataManager.createWarehouse("Test Warehouse 1", Location.NORTH, "Test Address", 1000);
    Warehouse warehouse2 = dataManager.createWarehouse("Test Warehouse 2", Location.NORTH, "Test Address", 1000);
    Shipment shipment1 = dataManager.createShipment();
    Shipment shipment2 = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog log2 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse2, shipment2);
    // Assuming DataManager and DataService are properly set up

    String busiestWarehouse = DataService.getInstance().findBusiestWarehouse();

    assertEquals("Busiest warehouses are: Test Warehouse 1, Test Warehouse 2, using 0.1% of capacity", busiestWarehouse);
    // Add assertions based on specific scenarios and data

}

@Test
public void testFindBusiestWarehouseWhenMultipleWarehouses() throws Exception {
    Warehouse warehouse1 = dataManager.createWarehouse("Test Warehouse 1", Location.NORTH, "Test Address", 1000);
    Warehouse warehouse2 = dataManager.createWarehouse("Test Warehouse 2", Location.NORTH, "Test Address", 1000);
    Shipment shipment1 = dataManager.createShipment();
    Shipment shipment2 = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog log2 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment2);
    // Assuming DataManager and DataService are properly set up

    String busiestWarehouse = DataService.getInstance().findBusiestWarehouse();

    assertEquals("Busiest warehouse is: Test Warehouse 1, using 0.2% of capacity", busiestWarehouse);
    // Add assertions based on specific scenarios and data

}

@Test
public void testFindBusiestWarehouseWhenMultipleWarehousesAndMultipleShipments() throws Exception {
    Warehouse warehouse1 = dataManager.createWarehouse("Test Warehouse 1", Location.NORTH, "Test Address", 1000);
    Warehouse warehouse2 = dataManager.createWarehouse("Test Warehouse 2", Location.NORTH, "Test Address", 1000);
    Shipment shipment1 = dataManager.createShipment();
    Shipment shipment2 = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog log2 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse2, shipment2);
    ShipmentLog log3 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse1, shipment1);
    ShipmentLog log4 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse2, shipment2);
    // Assuming DataManager and DataService are properly set up

    String busiestWarehouse = DataService.getInstance().findBusiestWarehouse();

    assertEquals("No incoming shipmentlogs exist for any warehouse", busiestWarehouse);
    // Add assertions based on specific scenarios and data

}

@Test
public void testFindBusiestWarehouseWhenMultipleWarehousesAndMultipleShipmentsAndMultipleLogs() throws Exception {
    Warehouse warehouse1 = dataManager.createWarehouse("Test Warehouse 1", Location.NORTH, "Test Address", 1000);
    Warehouse warehouse2 = dataManager.createWarehouse("Test Warehouse 2", Location.NORTH, "Test Address", 1000);
    Shipment shipment1 = dataManager.createShipment();
    Shipment shipment2 = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog log2 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse2, shipment2);
    ShipmentLog log3 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse1, shipment1);
    ShipmentLog log4 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse2, shipment2);
    ShipmentLog log5 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog log6 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse2, shipment2);
    ShipmentLog log7 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse1, shipment1);
    ShipmentLog log8 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse2, shipment2);
    ShipmentLog log9 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
    // Assuming DataManager and DataService are properly set up

    String busiestWarehouse = DataService.getInstance().findBusiestWarehouse();

    assertEquals("Busiest warehouse is: Test Warehouse 1, using 0.1% of capacity", busiestWarehouse);
    // Add assertions based on specific scenarios and data


}

@Test
public void testFindBusiestWarehouseWhenMultipleWarehousesAndMultipleShipmentsAndMultipleLogsAndMultipleOutgoingLogs() throws Exception {
    Warehouse warehouse1 = dataManager.createWarehouse("Test Warehouse 1", Location.NORTH, "Test Address", 1000);
    Warehouse warehouse2 = dataManager.createWarehouse("Test Warehouse 2", Location.NORTH, "Test Address", 1000);
    Shipment shipment1 = dataManager.createShipment();
    Shipment shipment2 = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog log2 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse2, shipment2);
    ShipmentLog log3 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse1, shipment1);
    ShipmentLog log4 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse2, shipment2);
    ShipmentLog log5 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog log6 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse2, shipment2);
    ShipmentLog log7 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse1, shipment1);
    ShipmentLog log8 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse2, shipment2);
    ShipmentLog log9 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog log10 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse2, shipment2);
    ShipmentLog log11 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse1, shipment1);
    ShipmentLog log12 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse2, shipment2);
    // Assuming DataManager and DataService are properly set up

    String busiestWarehouse = DataService.getInstance().findBusiestWarehouse();

    assertEquals("No incoming shipmentlogs exist for any warehouse", busiestWarehouse);
    // Add assertions based on specific scenarios and data
}

@Test
public void testFindBusiestWarehouseWhenMultipleWarehousesAndMultipleShipmentsAndMultipleLogsAndMultipleOutgoingLogsAndMultipleIncomingLogs() throws Exception {
    Warehouse warehouse1 = dataManager.createWarehouse("Test Warehouse 1", Location.NORTH, "Test Address", 1000);
    Warehouse warehouse2 = dataManager.createWarehouse("Test Warehouse 2", Location.NORTH, "Test Address", 1000);
    Shipment shipment1 = dataManager.createShipment();
    Shipment shipment2 = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog log2 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse2, shipment2);
    ShipmentLog log3 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse1, shipment1);
    ShipmentLog log4 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse2, shipment2);
    ShipmentLog log5 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog log6 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse2, shipment2);
    ShipmentLog log7 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse1, shipment1);
    ShipmentLog log8 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse2, shipment2);
    ShipmentLog log9 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog log10 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse2, shipment2);
    ShipmentLog log11 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse1, shipment1);
    ShipmentLog log12 = dataManager.createShipmentLog(LocalDate.now(), Direction.OUTGOING, warehouse2, shipment2);
    ShipmentLog log13 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog log14 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse2, shipment2);

    // Assuming DataManager and DataService are properly set up

    String busiestWarehouse = DataService.getInstance().findBusiestWarehouse();

    assertEquals("Busiest warehouses are: Test Warehouse 1, Test Warehouse 2, using 0.1% of capacity", busiestWarehouse);
}
}