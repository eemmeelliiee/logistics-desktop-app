package se.lu.ics.models;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    public void testUpdateId() throws Exception {
        Shipment shipment = dataManager.createShipment();
        
        StringProperty oldId = shipment.getShipmentId();
        StringProperty newId = new SimpleStringProperty(String.valueOf(Integer.parseInt(oldId.get()) + 1));

        dataManager.updateShipmentId(oldId, newId);
        assertEquals(shipment.getShipmentId(), newId);
    }


    @Test
    public void testUpdateIncorrectly() {
        Shipment shipment = dataManager.createShipment();
        
        StringProperty oldId = shipment.getShipmentId();

        try {
            dataManager.updateShipmentId(oldId, oldId);
        } catch (Exception e) {
            assertEquals(Constants.ALREADY_EXISTS_SHIPMENT_WITH_ID, e.getMessage());
        }
    }


}
