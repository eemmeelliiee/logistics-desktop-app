package se.lu.ics.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.binding.ObjectBinding;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Period;


public class TestDataService {
            DataManager dataManager;
            DataService dataService;
    

    @BeforeEach
    public void setUp() {
        dataManager = DataManager.getInstance();
        dataService = DataService.getInstance();    
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

    String averageTime = warehouse.getAverageTimeShipmentSpendsAtWarehouse();

    // assertEquals(Period.ofDays(1), averageTime);
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

    String busiestWarehouse = dataService.getBusiestWarehouse();

    assertEquals("Busiest warehouses are: Test Warehouse 1, Test Warehouse 2, using 0,10% of capacity", busiestWarehouse);
    // Add assertions based on specific scenarios and data

}

@Test

public void testFindBusiestWarehouseWhenNoShipments() throws Exception {
    Warehouse warehouse1 = dataManager.createWarehouse("Test Warehouse 1", Location.NORTH, "Test Address", 1000);
    Warehouse warehouse2 = dataManager.createWarehouse("Test Warehouse 2", Location.NORTH, "Test Address", 1000);
    // Assuming DataManager and DataService are properly set up

    String busiestWarehouse = dataService.getBusiestWarehouse();

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

    String busiestWarehouse = dataService.getBusiestWarehouse();

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

    String busiestWarehouse = dataService.getBusiestWarehouse();

    assertEquals("Busiest warehouses are: Test Warehouse 1, Test Warehouse 2, using 0,10% of capacity", busiestWarehouse);
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

    String busiestWarehouse = dataService.getBusiestWarehouse();

    assertEquals("Busiest warehouse is: Test Warehouse 1, using 0,20% of capacity", busiestWarehouse);
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

    String busiestWarehouse = dataService.getBusiestWarehouse();

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

    String busiestWarehouse = dataService.getBusiestWarehouse();

    assertEquals("Busiest warehouse is: Test Warehouse 1, using 0,10% of capacity", busiestWarehouse);
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

    String busiestWarehouse = dataService.getBusiestWarehouse();

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

    String busiestWarehouse = dataService.getBusiestWarehouse();

    assertEquals("Busiest warehouses are: Test Warehouse 1, Test Warehouse 2, using 0,10% of capacity", busiestWarehouse);
}

@Test
public void testUpdateMostRecentInspectionDateForWarehouse()throws Exception{
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    // Assuming DataManager and DataService are properly set up

    InspectionLog log1 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2021,9,8), "Test Inspector", "Test Result");
    InspectionLog log2 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2019,3,2), "Test Inspector", "Test Result");

    // dataService.updateMostRecentInspectionDateForWarehouse(warehouse);
    LocalDate mostRecentInspectionDate = warehouse.getMostRecentInspectionDate();

    assertEquals(LocalDate.of(2021,9,8), mostRecentInspectionDate);
    // Add assertions based on specific scenarios and data
}

@Test
public void testUpdateMostRecentInspectionDateForWarehouseWhenNoInspectionLogs()throws Exception{
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    // Assuming DataManager and DataService are properly set up

    // dataService.updateMostRecentInspectionDateForWarehouse(warehouse);
    LocalDate mostRecentInspectionDate = warehouse.getMostRecentInspectionDate();

    assertEquals(null, mostRecentInspectionDate);
    // Add assertions based on specific scenarios and data

}

@Test
public void testUpdateMostRecentInspectionDateForWarehouseWhenMultipleInspectionLogs()throws Exception{
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    // Assuming DataManager and DataService are properly set up

    InspectionLog log1 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2021,9,8), "Test Inspector", "Test Result");
    InspectionLog log2 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2019,3,2), "Test Inspector", "Test Result");
    InspectionLog log3 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2020,5,6), "Test Inspector", "Test Result");
    // dataService.updateMostRecentInspectionDateForWarehouse(warehouse);
    LocalDate mostRecentInspectionDate = warehouse.getMostRecentInspectionDate();

    assertEquals(LocalDate.of(2021,9,8), mostRecentInspectionDate);
    // Add assertions based on specific scenarios and data

}
@Test
public void testUpdateMostRecentInspectionDateForWarehouseWhenMultipleInspectionLogsDeleteOneLog()throws Exception{
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    // Assuming DataManager and DataService are properly set up

    InspectionLog log1 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2021,9,8), "Test Inspector", "Test Result");
    InspectionLog log2 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2019,3,2), "Test Inspector", "Test Result");
    InspectionLog log3 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2020,5,6), "Test Inspector", "Test Result");
    // dataService.updateMostRecentInspectionDateForWarehouse(warehouse);
    dataManager.deleteInspectionLog(log1);
    LocalDate mostRecentInspectionDate = warehouse.getMostRecentInspectionDate();

    assertEquals(LocalDate.of(2020,5,6), mostRecentInspectionDate);
    // Add assertions based on specific scenarios and data

}

@Test 
public void testGetCurrentStockLevel() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse, shipment);
    // Assuming DataManager and DataService are properly set up

    double currentStockLevel = warehouse.getCurrentStockLevel();

    assertEquals(1, currentStockLevel);
    // Add assertions based on specific scenarios and data

}

@Test
public void testGetCurrentStockLevelWhenNoShipments() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    // Assuming DataManager and DataService are properly set up

    double currentStockLevel = warehouse.getCurrentStockLevel();

    assertEquals(0, currentStockLevel);
    // Add assertions based on specific scenarios and data

}

@Test
public void testGetCurrentStockLevelWhenMultipleShipments() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment1 = dataManager.createShipment();
    Shipment shipment2 = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse, shipment1);
    ShipmentLog log2 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse, shipment2);
    // Assuming DataManager and DataService are properly set up

    double currentStockLevel = warehouse.getCurrentStockLevel();

    assertEquals(2, currentStockLevel);
    // Add assertions based on specific scenarios and data

}

@Test
public void testGetCurrentAvailableCapacity() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse, shipment);
    // Assuming DataManager and DataService are properly set up

    double currentAvailableCapacity = warehouse.getCurrentAvailableCapacity();

    assertEquals(999, currentAvailableCapacity);
    // Add assertions based on specific scenarios and data

}

@Test
public void testGetCurrentAvailableCapacityWhenNoShipments() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    // Assuming DataManager and DataService are properly set up

    double currentAvailableCapacity = warehouse.getCurrentAvailableCapacity();

    assertEquals(1000, currentAvailableCapacity);
    // Add assertions based on specific scenarios and data

}

@Test
public void testGetCurrentAvailableCapacityWhenMultipleShipments() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment1 = dataManager.createShipment();
    Shipment shipment2 = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse, shipment1);
    ShipmentLog log2 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse, shipment2);
    // Assuming DataManager and DataService are properly set up

    double currentAvailableCapacity = warehouse.getCurrentAvailableCapacity();

    assertEquals(998, currentAvailableCapacity);
    // Add assertions based on specific scenarios and data

}

@Test
public void testGetAverageTimeShipmentSpendsAtWarehouse() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.of(2021,8,9), Direction.INCOMING, warehouse, shipment);
    ShipmentLog log2 = dataManager.createShipmentLog(LocalDate.of(2021, 8, 10), Direction.OUTGOING, warehouse, shipment);
    ShipmentLog log3 = dataManager.createShipmentLog(LocalDate.of(2021, 8, 10), Direction.INCOMING, warehouse, shipment);
    // Assuming DataManager and DataService are properly set up

    String averageTime = warehouse.getAverageTimeShipmentSpendsAtWarehouse();

    // assertEquals(Period.ofDays(1), averageTime);
    // Add assertions based on specific scenarios and data

}

@Test
public void testGetAverageTimeShipmentSpendsAtWarehouseWhenNoShipments() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    // Assuming DataManager and DataService are properly set up

    String averageTime = warehouse.getAverageTimeShipmentSpendsAtWarehouse();

    assertEquals(null, averageTime);
    // Add assertions based on specific scenarios and data

}

@Test
public void testGetAverageTimeShipmentSpendsAtWarehouseWhenMultipleShipments() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment1 = dataManager.createShipment();
    Shipment shipment2 = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.of(2021,8, 9), Direction.INCOMING, warehouse, shipment1);
    ShipmentLog log2 = dataManager.createShipmentLog(LocalDate.of(2021, 8, 10), Direction.OUTGOING, warehouse, shipment1);
    ShipmentLog log3 = dataManager.createShipmentLog(LocalDate.of(2021, 8, 10), Direction.INCOMING, warehouse, shipment2);
    ShipmentLog log4 = dataManager.createShipmentLog(LocalDate.of(2021, 8, 11), Direction.OUTGOING, warehouse, shipment2);
    ShipmentLog log5 = dataManager.createShipmentLog(LocalDate.of(2021, 8, 11), Direction.INCOMING, warehouse, shipment1);
    // Assuming DataManager and DataService are properly set up

    String averageTime = warehouse.getAverageTimeShipmentSpendsAtWarehouse();
    assertThrows(Exception.class, () -> dataManager.createShipmentLog(LocalDate.of(2021, 6, 31), Direction.OUTGOING, warehouse, shipment1));
    // assertEquals(Period.ofDays(1), averageTime);

    // Add assertions based on specific scenarios and data
}

@Test
public void testGetMostRecentInspectionDate() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    InspectionLog log1 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2021,9,8), "Test Inspector", "Test Result");
    // Assuming DataManager and DataService are properly set up

    LocalDate mostRecentInspectionDate = warehouse.getMostRecentInspectionDate();

    assertEquals(LocalDate.of(2021,9,8), mostRecentInspectionDate);
    // Add assertions based on specific scenarios and data

}

@Test
public void testGetMostRecentInspectionDateWhenNoInspectionLogs() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    // Assuming DataManager and DataService are properly set up

    LocalDate mostRecentInspectionDate = warehouse.getMostRecentInspectionDate();

    assertEquals(null, mostRecentInspectionDate);
    // Add assertions based on specific scenarios and data

}

@Test
public void testGetMostRecentInspectionDateWhenMultipleInspectionLogs() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    InspectionLog log1 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2021,9,8), "Test Inspector", "Test Result");
    InspectionLog log2 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2019,3,2), "Test Inspector", "Test Result");
    InspectionLog log3 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2020,5,6), "Test Inspector", "Test Result");
    // Assuming DataManager and DataService are properly set up

    LocalDate mostRecentInspectionDate = warehouse.getMostRecentInspectionDate();

    assertEquals(LocalDate.of(2021,9,8), mostRecentInspectionDate);
    // Add assertions based on specific scenarios and data
}

@Test
public void testGetMostRecentInspectionDateWhenMultipleInspectionLogsDeleteOneLog() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    InspectionLog log1 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2021,9,8), "Test Inspector", "Test Result");
    InspectionLog log2 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2019,3,2), "Test Inspector", "Test Result");
    InspectionLog log3 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2020,5,6), "Test Inspector", "Test Result");
    // Assuming DataManager and DataService are properly set up

    dataManager.deleteInspectionLog(log1);
    LocalDate mostRecentInspectionDate = warehouse.getMostRecentInspectionDate();

    assertEquals(LocalDate.of(2020,5,6), mostRecentInspectionDate);
    // Add assertions based on specific scenarios and data

}

@Test
public void testGetMostRecentInspectionDateWhenMultipleInspectionLogsDeleteAllLogs() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    InspectionLog log1 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2021,9,8), "Test Inspector", "Test Result");
    InspectionLog log2 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2019,3,2), "Test Inspector", "Test Result");
    InspectionLog log3 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2020,5,6), "Test Inspector", "Test Result");
    // Assuming DataManager and DataService are properly set up

    dataManager.deleteInspectionLog(log1);
    dataManager.deleteInspectionLog(log2);
    dataManager.deleteInspectionLog(log3);
    LocalDate mostRecentInspectionDate = warehouse.getMostRecentInspectionDate();

    assertEquals(null, mostRecentInspectionDate);
    // Add assertions based on specific scenarios and data

}

@Test
public void testGetMostRecentInspectionDateWhenMultipleInspectionLogsDeleteAllLogsAndAddNewLog() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    InspectionLog log1 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2021,9,8), "Test Inspector", "Test Result");
    InspectionLog log2 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2019,3,2), "Test Inspector", "Test Result");
    InspectionLog log3 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2020,5,6), "Test Inspector", "Test Result");
    // Assuming DataManager and DataService are properly set up

    dataManager.deleteInspectionLog(log1);
    dataManager.deleteInspectionLog(log2);
    dataManager.deleteInspectionLog(log3);
    InspectionLog log4 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2021,9,8), "Test Inspector", "Test Result");
    LocalDate mostRecentInspectionDate = warehouse.getMostRecentInspectionDate();

    assertEquals(LocalDate.of(2021,9,8), mostRecentInspectionDate);
    // Add assertions based on specific scenarios and data

}


@Test
public void testGetMostRecentInspectionDateWhenMultipleInspectionLogsDeleteAllLogsAndAddNewLogAndDeleteNewLogAndAddNewLog() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    InspectionLog log1 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2021,9,8), "Test Inspector", "Test Result");
    InspectionLog log2 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2019,3,2), "Test Inspector", "Test Result");
    InspectionLog log3 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2020,5,6), "Test Inspector", "Test Result");
    // Assuming DataManager and DataService are properly set up

    dataManager.deleteInspectionLog(log1);
    dataManager.deleteInspectionLog(log2);
    dataManager.deleteInspectionLog(log3);
    InspectionLog log4 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2021,9,8), "Test Inspector", "Test Result");
    dataManager.deleteInspectionLog(log4);
    InspectionLog log5 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2021,9,8), "Test Inspector", "Test Result");
    LocalDate mostRecentInspectionDate = warehouse.getMostRecentInspectionDate();

    assertEquals(LocalDate.of(2021,9,8), mostRecentInspectionDate);
    // Add assertions based on specific scenarios and data

}

@Test
public void testGetRemainingCapacityInPercent() throws Exception{
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse, shipment);
    // Assuming DataManager and DataService are properly set up

    String remainingCapacityInPercent = warehouse.getRemainingCapacityInPercent();

    assertEquals("99.9%", remainingCapacityInPercent);
    // Add assertions based on specific scenarios and data}
}

@Test
public void testGetRemainingCapacityInPercentWhenNoShipments() throws Exception{
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    // Assuming DataManager and DataService are properly set up

    String remainingCapacityInPercent = warehouse.getRemainingCapacityInPercent();

    assertEquals("100.0%", remainingCapacityInPercent);
    // Add assertions based on specific scenarios and data

}

@Test
public void testGetRemainingCapacityInPercentWhenMultipleShipments() throws Exception{
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment1 = dataManager.createShipment();
    Shipment shipment2 = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse, shipment1);
    ShipmentLog log2 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse, shipment2);
    // Assuming DataManager and DataService are properly set up

    String remainingCapacityInPercent = warehouse.getRemainingCapacityInPercent();

    assertEquals("99.8%", remainingCapacityInPercent);
    // Add assertions based on specific scenarios and data
}

@Test
public void testGetTotalNumberOfWarehouses() throws Exception{
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Warehouse warehouse2 = dataManager.createWarehouse("Test Warehouse 2", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.of(2021,8,8), Direction.INCOMING, warehouse, shipment);
    ShipmentLog log2 = dataManager.createShipmentLog(LocalDate.of(2021,8,9), Direction.OUTGOING, warehouse, shipment);
    ShipmentLog log3 = dataManager.createShipmentLog(LocalDate.of(2021,9,9), Direction.INCOMING, warehouse2, shipment);
    assertThrows(Exception.class, () -> dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse2, shipment));
    // Assuming DataManager and DataService are properly set up

    int totalNumberOfWarehouses = shipment.getTotalNumberOfWarehouses();

    assertEquals(2, totalNumberOfWarehouses);
    // Add assertions based on specific scenarios and data
}

@Test
public void testGetCurrentWarehouse() throws Exception{
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse, shipment);
    // Assuming DataManager and DataService are properly set up

    Warehouse currentWarehouse = shipment.getCurrentWarehouse();

    assertEquals("Test Warehouse", currentWarehouse.getName());
    // Add assertions based on specific scenarios and data
}

@Test
public void testGetCurrentWarehouseWhenManyShipmentLogs() throws Exception{
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse 1", Location.NORTH, "Test Address", 1000);
    Warehouse warehouse2 = dataManager.createWarehouse("Test Warehouse 2", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse, shipment);
    // Assuming DataManager and DataService are properly set up

    Warehouse currentWarehouse = shipment.getCurrentWarehouse();
    assertThrows(Exception.class, () -> dataManager.createShipmentLog(LocalDate.of(2021, 6, 31), Direction.INCOMING, warehouse, shipment));
    assertThrows(Exception.class, () -> dataManager.createShipmentLog(LocalDate.of(2021, 5, 31), Direction.OUTGOING, warehouse, shipment));
    assertThrows(Exception.class, () -> dataManager.createShipmentLog(LocalDate.of(2021, 6, 31), Direction.INCOMING, warehouse2, shipment));

    assertEquals("Test Warehouse 1", currentWarehouse.getName());
    // Add assertions based on specific scenarios and data}
}

@Test
public void testGetTotalNumberOfWarehousesWhenNoShipments() throws Exception{
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    // Assuming DataManager and DataService are properly set up

    int totalNumberOfWarehouses = shipment.getTotalNumberOfWarehouses();

    assertEquals(0, totalNumberOfWarehouses);
    // Add assertions based on specific scenarios and data

}
@Test
    public void testUpdateShipmentLog() throws Exception {
        // Create initial data
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        // Create shipment log
        ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);

        // Update fields
        LocalDate newDate = LocalDate.now().plusDays(1);
        Direction newDirection = Direction.OUTGOING;
        Shipment newShipment = dataManager.createShipment();
        Warehouse newWarehouse = dataManager.createWarehouse("New Test Warehouse", Location.MIDDLE, "Test Address", 1000);

        // Update shipment log
        dataManager.updateShipmentLog(log, UpdateFieldShipmentLog.DATE, newDate);
        dataManager.updateShipmentLog(log, UpdateFieldShipmentLog.DIRECTION, newDirection);
        dataManager.updateShipmentLog(log, UpdateFieldShipmentLog.WAREHOUSE, newWarehouse);
        dataManager.updateShipmentLog(log, UpdateFieldShipmentLog.SHIPMENT, newShipment);

        // Assertions
        assertEquals(newDate, log.getDate());
        assertEquals(newDirection, log.getDirection());
        assertEquals(newWarehouse, log.getWarehouse());
        assertEquals(newShipment, log.getShipment());
    }

    @Test
    public void testValidateShipmentLog() throws Exception {
        // Create initial data
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;

        // Create shipment log
        dataManager.createShipmentLog(date, direction, warehouse, shipment);

        // Try to create another shipment log with the same parameters, which should throw an exception
        assertThrows(Exception.class, () -> {
            dataManager.createShipmentLog(date, direction, warehouse, shipment);
        });

        // Additional tests for other scenarios can be added here
    }


    @Test
    public void testDeleteShipmentLog() throws Exception {
        // Create initial data
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        Direction direction = Direction.INCOMING;
    
        // Create shipment log
        ShipmentLog log = dataManager.createShipmentLog(date, direction, warehouse, shipment);
    
        // Delete shipment log
        dataManager.deleteShipmentLog(log);
    
        // Check that the shipment log is no longer in the dataManager
        assertFalse(dataManager.readShipmentLogs().contains(log));
    }

    @Test
public void testGetCurrentAvailableCapacityForLocations() throws Exception {
    // Set up some warehouses
    Warehouse warehouse1 = dataManager.createWarehouse("Test Warehouse 1", Location.NORTH, "Test Address", 1000);
    Warehouse warehouse2 = dataManager.createWarehouse("Test Warehouse 2", Location.NORTH, "Test Address", 500);
    Warehouse warehouse3 = dataManager.createWarehouse("Test Warehouse 3", Location.SOUTH, "Test Address", 2000);


    // Set up some shipments
    Shipment shipment1 = dataManager.createShipment();
    Shipment shipment2 = dataManager.createShipment();
    Shipment shipment3 = dataManager.createShipment();

    // Set up some shipment logs
    ShipmentLog log1 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog log2 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse2, shipment2);
    ShipmentLog log3 = dataManager.createShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse3, shipment3);

    // Call the method
    ObservableList<Pair<Location, Double>> capacityForLocations = dataService.getCurrentAvailableCapacityForLocations();

    // Check that the list contains the correct pairs
    assertTrue(capacityForLocations.contains(new Pair<>(Location.NORTH, 1498.0)));
    assertTrue(capacityForLocations.contains(new Pair<>(Location.SOUTH, 1999.0)));
}

@Test
public void testGetInspectorsForWarehouse() throws Exception {
    Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.NORTH, "Test Address", 1000);
    Shipment shipment = dataManager.createShipment();
    InspectionLog log1 = dataManager.createInspectionLog(shipment, warehouse, LocalDate.of(2021,9,8), "Test Inspector", "Test Result");

    Shipment shipment2 = dataManager.createShipment();
    InspectionLog log2 = dataManager.createInspectionLog(shipment2, warehouse, LocalDate.of(2021,9,8), "Test Inspector 2", "Test Result");

    ObservableList<String> inspectors = dataService.getInspectorsForWarehouse(warehouse);

    assertEquals(2, inspectors.size());

}

@Test
public void test() throws Exception{
    Warehouse warehouse1 = dataManager.createWarehouse("Odins Nest", Location.MIDDLE, "Storgatan 1", 100);
    Warehouse warehouse2 = dataManager.createWarehouse("Thors House",Location.NORTH, "Storgatan 2", 200);
    Warehouse warehouse3 = dataManager.createWarehouse("Adams Warehouse",Location.SOUTH, "Storgatan 3", 300);
    Warehouse warehouse4 = dataManager.createWarehouse("Eves Warehouse",Location.MIDDLE, "Storgatan 4", 400);
    Warehouse warehouse5 = dataManager.createWarehouse("Liliths Warehouse",Location.SOUTH, "Storgatan 5", 500);
    Warehouse warehouse6 = dataManager.createWarehouse("Lucifers Warehouse",Location.NORTH, "Storgatan 6", 600);
    Warehouse warehouse7 = dataManager.createWarehouse("Gabriels Warehouse",Location.MIDDLE, "Storgatan 7", 700);
    Warehouse warehouse8 = dataManager.createWarehouse("Raphaels Warehouse",Location.SOUTH, "Storgatan 8", 800);
    Warehouse warehouse9 = dataManager.createWarehouse("Uriels Warehouse",Location.NORTH, "Storgatan 9", 900);
    Warehouse warehouse10 = dataManager.createWarehouse("Azraels Warehouse",Location.MIDDLE, "Storgatan 10", 1000);

    Shipment shipment1 = dataManager.createShipment();
    Shipment shipment2 = dataManager.createShipment();
    Shipment shipment3 = dataManager.createShipment();
    Shipment shipment4 = dataManager.createShipment();
    Shipment shipment5 = dataManager.createShipment();
    Shipment shipment6 = dataManager.createShipment();
    Shipment shipment7 = dataManager.createShipment();
    Shipment shipment8 = dataManager.createShipment();

    ShipmentLog shipmentLog = dataManager.createShipmentLog(LocalDate.of(2020,8,9), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog shipmentLog2 = dataManager.createShipmentLog(LocalDate.of(2020,8,10), Direction.OUTGOING, warehouse1, shipment1);

    ShipmentLog shipmentLog3 = dataManager.createShipmentLog(LocalDate.of(2020,8,11), Direction.INCOMING, warehouse2, shipment2);
    ShipmentLog shipmentLog4 = dataManager.createShipmentLog(LocalDate.of(2020,8,12), Direction.OUTGOING, warehouse2, shipment2);

    ShipmentLog shipmentLog5 = dataManager.createShipmentLog(LocalDate.of(2020,8,10), Direction.INCOMING, warehouse3, shipment3);
    ShipmentLog shipmentLog6 = dataManager.createShipmentLog(LocalDate.of(2020,8,15), Direction.OUTGOING, warehouse3, shipment3);

    ShipmentLog shipmentLog7 = dataManager.createShipmentLog(LocalDate.of(2020,8,15), Direction.INCOMING, warehouse3, shipment3);
    ShipmentLog shipmentLog9 = dataManager.createShipmentLog(LocalDate.of(2020,8,15), Direction.OUTGOING, warehouse3, shipment3);
    ShipmentLog shipmentLog8 = dataManager.createShipmentLog(LocalDate.of(2020,7,15), Direction.INCOMING, warehouse3, shipment3);
    // ShipmentLog shipmentLog10 = dataManager.createShipmentLog(LocalDate.of(2020,7,15), Direction.OUTGOING, warehouse3, shipment3);

    InspectionLog inspectionLog = dataManager.createInspectionLog(shipment1, warehouse1, LocalDate.of(2020,8,9), "Olle", "Test Result");
    InspectionLog inspectionLog2 = dataManager.createInspectionLog(shipment1, warehouse1, LocalDate.of(2020,8,10), "Alle", "Test Result");



    // assertThrows(Exception.class, () -> dataManager.createShipmentLog(LocalDate.of(2020,7,15), Direction.INCOMING, warehouse4, shipment3));
    // dataManager.updateShipmentLog(shipmentLog, UpdateFieldShipmentLog.DATE, LocalDate.of(2020,8,9));

    System.out.println(warehouse1.getAddress());
    System.out.println(warehouse1.getName());
    System.out.println(warehouse1.getCapacity());
    System.out.println(warehouse3.getCurrentAvailableCapacity());
    System.out.println(warehouse1.getCurrentStockLevel());
    System.out.println(warehouse1.getRemainingCapacityInPercent());
    System.out.println(warehouse1.getAverageTimeShipmentSpendsAtWarehouse());
    System.out.println(warehouse1.getLocation());
    System.out.println(warehouse1.getMostRecentInspectionDate());

    System.out.println(dataService.getBusiestWarehouse());
    System.out.println(dataService.getCurrentAvailableCapacityForLocations());
    System.out.println(dataService.getInspectionLogsForWarehouse(warehouse1));
    System.out.println(dataService.getShipmentLogsForWarehouse(warehouse1));
    System.out.println(dataService.getInspectorsForWarehouse(warehouse1));

    System.out.println(dataService.getInspectionsLogsForShipment(shipment1));
    System.out.println(dataService.getShipmentLogsForShipment(shipment3));











    



}
}
