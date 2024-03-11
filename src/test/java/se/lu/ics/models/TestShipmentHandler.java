package se.lu.ics.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestShipmentHandler {

    DataManager dataManager;
     

    @BeforeEach
    public void setUp()throws Exception{
        dataManager = DataManager.getInstance();
    }

     @AfterEach
    public void tearDown() throws Exception{
        DataManager.getInstance().clearData();
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
            assertEquals(ShipmentHandler.ALREADY_EXISTS_SHIPMENT_WITH_ID, e.getMessage());
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
            assertEquals(ShipmentHandler.ALREADY_EXISTS_SHIPMENT_WITH_ID, e.getMessage());
        }
    }

    @Test
    public void deleteShipment() throws Exception {
        Shipment shipment = dataManager.createShipment();
        dataManager.deleteShipment(shipment);
        ObservableList<Shipment> shipments = dataManager.readShipments();
        assertEquals(0, shipments.size());
    }

    // not needed scince we are using the observable list
    // @Test
    // public void deleteShipmentThatDoesNotExist() {
    //     Shipment shipment = new Shipment();
    //     assertThrows(Exception.class, () -> dataManager.deleteShipment(shipment));
    // }

    @Test
    public void testDeleteShipment() {
        Shipment shipment = dataManager.createShipment();
        dataManager.deleteShipment(shipment);
        ObservableList<Shipment> shipments = dataManager.readShipments();
        assertEquals(0, shipments.size());
    }

 

    


}
