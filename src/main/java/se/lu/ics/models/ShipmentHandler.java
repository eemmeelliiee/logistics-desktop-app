package se.lu.ics.models;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ShipmentHandler {

    private ObservableList<Shipment> shipments;
    private static ShipmentHandler instance;

    public static final String ALREADY_EXISTS_SHIPMENT_WITH_ID = "Error: A shipment with given ID already exists";

    private ShipmentHandler() {
        shipments = FXCollections.observableList(new ArrayList<>());
    }

    public static ShipmentHandler getInstance() {
        if (instance == null) {
            instance = new ShipmentHandler();
        }
        return instance;
    }

    public ObservableList<Shipment> getShipments() {
        return shipments;
    }

    public Shipment createShipment() {
        Shipment shipment = new Shipment();
        shipments.add(shipment);
        return shipment;
    }

    public void updateShipmentId(Shipment shipment, String newShipmentId) throws Exception {

        if (doesAShipmentExistWithId(newShipmentId)) {
            throw new Exception(ALREADY_EXISTS_SHIPMENT_WITH_ID);
        }

        if (newShipmentId == null || newShipmentId.equals("")) {
            throw new Exception(Constants.CANNOT_BE_EMPTY);
        }

        if (shipment != null) {
            shipment.setShipmentId(newShipmentId);
            forceUpdateOfObservableList(); // please manually update the observable list in the controller instead
        }

    }

    public void deleteShipment(Shipment shipment) {
        shipments.remove(shipment);
    }

    // INTE REKOMMENDERAT, manually edit the observable list in the controller
    // instead !!!
    // only needed to updated ComboBoxes!
    public void forceUpdateOfObservableList() {
        // shipments = FXCollections.observableList(shipments);

        shipments.add(0, null);
        shipments.remove(0);
    }

    private boolean doesAShipmentExistWithId(String shipmentId) {
        for (Shipment shipment : shipments) {
            if (shipment.getShipmentId().equals(shipmentId)) {
                return true;
            }
        }
        return false;
    }

    // these are only for tests, move to a test class !? how?
    public void clearData() {
        shipments.clear();
    }

}
