package se.lu.ics.models;
import java.time.LocalDate;

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

public class TestShipmentHandler {

    DataManager dataManager;

    @BeforeEach
    public void setUp() {
        dataManager = DataManager.getInstance();
        dataManager.clearData();
    }

    @Test
    public void testCreateShipment() {
        
        dataManager.createShipment();
        dataManager.createShipment();
        dataManager.createShipment();

        ObservableList<Shipment> shipments = dataManager.readShipments();
        assertEquals(3, shipments.size());
    }

    @Test
    public void testReadShipments() {
        ObservableList<Shipment> shipments = dataManager.readShipments();
        assertNotNull(shipments);
    }

    @Test
    public void testUpdateId() throws Exception {
        Shipment shipment = dataManager.createShipment();
        
        String oldId = shipment.getShipmentId();
        String newId = oldId + 1;

        dataManager.updateShipmentId(shipment, newId);
        assertEquals(shipment.getShipmentId(), newId);
    }


    @Test
    public void testUpdateIncorrectly() {
        Shipment shipment = dataManager.createShipment();
        
        String oldId = shipment.getShipmentId();

        try {
            dataManager.updateShipmentId(shipment, oldId);
        } catch (Exception e) {
            assertEquals(Constants.ALREADY_EXISTS_SHIPMENT_WITH_ID, e.getMessage());
        }
    }

    @Test
    public void testUpdateIdToExistingId() {
        Shipment shipment1 = dataManager.createShipment();
        Shipment shipment2 = dataManager.createShipment();
        
        String oldId = shipment1.getShipmentId();
        String newId = shipment2.getShipmentId();

        try {
            dataManager.updateShipmentId(shipment1, newId);
        } catch (Exception e) {
            assertEquals(Constants.ALREADY_EXISTS_SHIPMENT_WITH_ID, e.getMessage());
        }
    }

    @Test
    public void deleteShipment() throws Exception {
        Shipment shipment = dataManager.createShipment();
        dataManager.deleteShipment(shipment);
        ObservableList<Shipment> shipments = dataManager.readShipments();
        assertEquals(0, shipments.size());
    }



    


}
