package se.lu.ics.models;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;

public class TestWarehouseHandler {

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
    public void testCreateWarehouse() throws Exception{
        String name = "Odens Lager";
        Location location = Location.SOUTH; // Assuming NORTH is a valid Location
        String address = "Testgatan 1";
        double capacity = 120;

        Warehouse result = dataManager.createWarehouse(name, location, address, capacity);

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(location, result.getLocation());
        assertEquals(address, result.getAddress());
        assertEquals(capacity, result.getCapacity(), 0.001);
    }

    @Test
    public void testReadWarehouses() {
        assertNotNull(dataManager.readWarehouses());
    }

    @Test
    public void testUpdateWarehouse() throws Exception {
        Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100.0);

        String newName = "New Name";
        dataManager.updateWarehouse(warehouse, UpdateFieldWarehouse.NAME, newName);
        assertEquals(newName, warehouse.getName());

        Location newLocation = Location.SOUTH; // Assuming SOUTH is a valid Location
        dataManager.updateWarehouse(warehouse, UpdateFieldWarehouse.LOCATION, newLocation);
        assertEquals(newLocation, warehouse.getLocation());

        String newAddress = "New Address";
        dataManager.updateWarehouse(warehouse, UpdateFieldWarehouse.ADDRESS, newAddress);
        assertEquals(newAddress, warehouse.getAddress());

        double newCapacity = 200.0;
        dataManager.updateWarehouse(warehouse, UpdateFieldWarehouse.CAPACITY, newCapacity);
        assertEquals(newCapacity, warehouse.getCapacity(), 0.001);
    }

    @Test
    public void testDeleteWarehouse() throws Exception {
        Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100.0);
        dataManager.deleteWarehouse(warehouse);
        assertFalse(dataManager.readWarehouses().contains(warehouse));
    }

    @Test
    public void testDeleteWarehouseIncorrectly() throws Exception{
        Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 200);
        Warehouse warehouse2 = dataManager.createWarehouse("TestWarehouse2", Location.NORTH, "TestAddress2", 200.0);
        dataManager.deleteWarehouse(warehouse);
        assertTrue(dataManager.readWarehouses().contains(warehouse2));
    }

    @Test
    public void testForceUpdateOfObservableList() throws Exception{
        dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100.0);
        dataManager.createWarehouse("TestWarehouse2", Location.NORTH, "TestAddress2", 200.0);
        dataManager.createWarehouse("TestWarehouse3", Location.NORTH, "TestAddress3", 300.0);

        assertEquals(3, dataManager.readWarehouses().size());

        WarehouseHandler.getInstance().forceUpdateOfObservableList();
        assertEquals(3, dataManager.readWarehouses().size());
    }

    @Test
    public void testUpdateWarehouseNameIncorrectly() throws Exception{
        Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100.0);
        String newName = "";
        assertThrows(Exception.class, () -> dataManager.updateWarehouse(warehouse, UpdateFieldWarehouse.NAME, newName));
    }

    @Test
    public void testUpdateWarehouseCapacityIncorrectly() throws Exception{
        Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100.0);
        double newCapacity = 0.0;

        assertThrows(Exception.class, () -> dataManager.updateWarehouse(warehouse, UpdateFieldWarehouse.CAPACITY, newCapacity));
    }

    @Test
    public void testUpdateWarehouseLocationIncorrectly() throws Exception{
        Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100.0);
        Location newLocation = null;
        assertThrows(Exception.class, () -> dataManager.updateWarehouse(warehouse, UpdateFieldWarehouse.LOCATION, newLocation));

    }

    @Test
    public void testUpdateWarehouseAddressIncorrectly() throws Exception{
        Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100.0);
        String newAddress = null;
        assertThrows(Exception.class, () -> dataManager.updateWarehouse(warehouse, UpdateFieldWarehouse.ADDRESS, newAddress));
    }

    // @Test
    // public void testDoesAWarehouseExistWithName() throws Exception  {
    //     Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100.0);
    //     assertTrue(WarehouseHandler.doesAWarehouseExistWithName("TestWarehouse"));
    //     assertFalse(WarehouseHandler.doesAWarehouseExistWithName("TestWarehouse2"));
    // }

    // @Test
    // public void testDoesAWarehouseExistWithNameIncorrectly() throws Exception{
    //     Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100.0);
    //     assertTrue(WarehouseHandler.doesAWarehouseExistWithName("TestWarehouse"));
    // }
    
}

// package se.lu.ics.models;
// import java.time.LocalDate;
// import java.time.temporal.ChronoUnit;
// import java.util.ArrayList;
// //import java.util.Collections;
// //import java.util.Comparator;
// import se.lu.ics.models.Location;

// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import javafx.collections.ObservableList;
// import javafx.scene.chart.PieChart.Data;

// import java.util.ArrayList;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.DynamicTest.stream;


// import javafx.scene.chart.PieChart.Data;

// public class TestWarehouseHandler {

//     DataManager dataManager;
//     WarehouseHandler warehouseHandler;

//     @BeforeEach
//     public void setUp() {
//         dataManager = DataManager.getInstance();
//         warehouseHandler = dataManager.getWarehouseHandler();
//     }

//      @AfterEach
//     public void tearDown() {
//         warehouseHandler.getWarehouses().clear();
//     }

//     @Test
//     public void testCreateWarehouse() {
        
//         dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
//         dataManager.createWarehouse("TestWarehouse2", Location.MIDDLE, "TestAddress2", 200);
//         dataManager.createWarehouse("TestWarehouse3", Location.SOUTH, "TestAddress3", 300);

//         ObservableList<Warehouse> warehouses = dataManager.readWarehouses();
//         assertEquals(3, warehouses.size());
//     }

//     @Test
//     public void testReadWarehouses() {
//         ObservableList<Warehouse> warehouses = dataManager.readWarehouses();
//         assertNotNull(warehouses);
//     }

    
// }

    
//     }

//     @Test
//     public void testUpdateName() throws Exception {
//         Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
        
//         String oldName = warehouse.getName();
//         String newName = oldName + "2";

//         dataManager.updateWarehouseName(warehouse, newName);
//         assertEquals(warehouse.getName(), newName);
//     }

//     @Test
//     public void testUpdateIncorrectly() throws Exception {
//         Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
        
//         String oldName = warehouse.getName();
//         String newName = oldName + "2";

//         dataManager.updateWarehouseName(warehouse, newName);
//         assertEquals(warehouse.getName(), newName);

//         assertThrows(Exception.class, () -> dataManager.updateWarehouseName(warehouse, newName));
//     }

//     @Test
//     public void testUpdateLocation() {
//         Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
        
//         Location oldLocation = warehouse.getLocation();
//         Location newLocation = Location.MIDDLE;

//         dataManager.updateWarehouseLocation(warehouse, newLocation);
//         assertEquals(warehouse.getLocation(), newLocation);
//     }

//     @Test
//     public void testUpdateAddress() {
//         Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
        
//         String oldAddress = warehouse.getAddress();
//         String newAddress = oldAddress + "2";

//         dataManager.updateWarehouseAddress(warehouse, newAddress);
//         assertEquals(warehouse.getAddress(), newAddress);
//     }

//     @Test
//     public void testUpdateCapacity() throws Exception {
//         Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
        
//         double oldCapacity = warehouse.getCapacity();
//         double newCapacity = oldCapacity + 100;

//         dataManager.updateWarehouseCapacity(warehouse, newCapacity);
//         assertEquals(warehouse.getCapacity(), newCapacity);
//     }

//     @Test
//     public void testUpdateCapacityIncorrectly() throws Exception {
//         Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
        
//         double oldCapacity = warehouse.getCapacity();
//         double newCapacity = oldCapacity - 100;

//         assertThrows(Exception.class, () -> dataManager.updateWarehouseCapacity(warehouse, newCapacity));
//     }

//     @Test
//     public void testDeleteWarehouse() throws Exception {
//         Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
//         dataManager.deleteWarehouse(warehouse);
//         ObservableList<Warehouse> warehouses = dataManager.readWarehouses();
//         assertEquals(0, warehouses.size());
//     }
    
//     @Test
//     public void testDeleteWarehouseIncorrectly() throws Exception {
//         Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
//         Warehouse warehouse2 = dataManager.createWarehouse("TestWarehouse2", Location.NORTH, "TestAddress2", 200);
//         dataManager.deleteWarehouse(warehouse);
//         ObservableList<Warehouse> warehouses = dataManager.readWarehouses();
//         assertEquals(1, warehouses.size());    }

//     // @Test
//     // public void testForceUpdateOfObservableList() {
//     //     dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
//     //     dataManager.createWarehouse("TestWarehouse2", Location.MIDDLE, "TestAddress2", 200);
//     //     dataManager.createWarehouse("TestWarehouse3", Location.SOUTH, "TestAddress3", 300);

//     //     ObservableList<Warehouse> warehouses = dataManager.readWarehouses();
//     //     assertEquals(3, warehouses.size());

//     //     dataManager.forceUpdateOfObservableList(); <-------- blir error
//     //     assertEquals(3, warehouses.size());
//     // }


    




// }
