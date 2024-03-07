package se.lu.ics.models;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import se.lu.ics.models.Location;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart.Data;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.stream;


import javafx.scene.chart.PieChart.Data;

public class TestWarehouseHandler {

    DataManager dataManager;

    @BeforeEach
    public void setUp() {
        dataManager = DataManager.getInstance();
        dataManager.clearData();
    }

    @Test
    public void testCreateWarehouse() {
        
        dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
        dataManager.createWarehouse("TestWarehouse2", Location.MIDDLE, "TestAddress2", 200);
        dataManager.createWarehouse("TestWarehouse3", Location.SOUTH, "TestAddress3", 300);

        ObservableList<Warehouse> warehouses = dataManager.readWarehouses();
        assertEquals(3, warehouses.size());
    }

    @Test
    public void testReadWarehouses() {
        ObservableList<Warehouse> warehouses = dataManager.readWarehouses();
        assertNotNull(warehouses);
    }

    @Test
    public void testUpdateName() throws Exception {
        Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
        
        String oldName = warehouse.getName();
        String newName = oldName + "2";

        dataManager.updateWarehouseName(warehouse, newName);
        assertEquals(warehouse.getName(), newName);
    }

    @Test
    public void testUpdateIncorrectly() throws Exception {
        Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
        
        String oldName = warehouse.getName();
        String newName = oldName + "2";

        dataManager.updateWarehouseName(warehouse, newName);
        assertEquals(warehouse.getName(), newName);

        assertThrows(Exception.class, () -> dataManager.updateWarehouseName(warehouse, newName));
    }

    @Test
    public void testUpdateLocation() {
        Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
        
        Location oldLocation = warehouse.getLocation();
        Location newLocation = Location.MIDDLE;

        dataManager.updateWarehouseLocation(warehouse, newLocation);
        assertEquals(warehouse.getLocation(), newLocation);
    }

    @Test
    public void testUpdateAddress() {
        Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
        
        String oldAddress = warehouse.getAddress();
        String newAddress = oldAddress + "2";

        dataManager.updateWarehouseAddress(warehouse, newAddress);
        assertEquals(warehouse.getAddress(), newAddress);
    }

    @Test
    public void testUpdateCapacity() throws Exception {
        Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
        
        double oldCapacity = warehouse.getCapacity();
        double newCapacity = oldCapacity + 100;

        dataManager.updateWarehouseCapacity(warehouse, newCapacity);
        assertEquals(warehouse.getCapacity(), newCapacity);
    }

    @Test
    public void testUpdateCapacityIncorrectly() throws Exception {
        Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
        
        double oldCapacity = warehouse.getCapacity();
        double newCapacity = oldCapacity - 100;

        assertThrows(Exception.class, () -> dataManager.updateWarehouseCapacity(warehouse, newCapacity));
    }

    @Test
    public void testDeleteWarehouse() throws Exception {
        Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
        dataManager.deleteWarehouse(warehouse);
        ObservableList<Warehouse> warehouses = dataManager.readWarehouses();
        assertEquals(0, warehouses.size());
    }
    
    @Test
    public void testDeleteWarehouseIncorrectly() throws Exception {
        Warehouse warehouse = dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
        dataManager.deleteWarehouse(warehouse);
        ObservableList<Warehouse> warehouses = dataManager.readWarehouses();
        assertEquals(0, warehouses.size());
        assertThrows(Exception.class, () -> dataManager.deleteWarehouse(warehouse));
    }

    // @Test
    // public void testForceUpdateOfObservableList() {
    //     dataManager.createWarehouse("TestWarehouse", Location.NORTH, "TestAddress", 100);
    //     dataManager.createWarehouse("TestWarehouse2", Location.MIDDLE, "TestAddress2", 200);
    //     dataManager.createWarehouse("TestWarehouse3", Location.SOUTH, "TestAddress3", 300);

    //     ObservableList<Warehouse> warehouses = dataManager.readWarehouses();
    //     assertEquals(3, warehouses.size());

    //     dataManager.forceUpdateOfObservableList(); <-------- blir error
    //     assertEquals(3, warehouses.size());
    // }


    




}
