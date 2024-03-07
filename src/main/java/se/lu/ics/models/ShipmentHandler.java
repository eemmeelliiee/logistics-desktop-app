package se.lu.ics.models;

import java.util.ArrayList;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ShipmentHandler {

    private ObservableList<Shipment> shipments;

    public ShipmentHandler() {
        shipments = FXCollections.observableList(new ArrayList<>());
    }

    public ObservableList<Shipment> getShipments() {
        return shipments;
    }

    // A setter should not be needed for this. Why give other classes access to that?
    /*
    public void setShipments(ArrayList<Shipment> shipments) {
        this.shipments = shipments;
    }
    */


    public Shipment createShipment() {
        Shipment shipment = new Shipment();
        shipments.add(shipment);
        return shipment;
    }

    // does the same thing as getShipments right now, should this return a list of Strings by doing .toString on each shipment?
    // or will that kind of thing be handled in the view?
    public ObservableList<Shipment> readShipments() {
        return shipments;
    }

    public void updateShipmentId(StringProperty shipmentIdToBeUpdated, StringProperty newShipmentId) throws Exception {

        if (!doesAShipmentExistWithId(shipmentIdToBeUpdated)) {
            throw new Exception(Constants.NO_SHIPMENT_EXISTS_WITH_THAT_ID);
        }

        if (doesAShipmentExistWithId(newShipmentId)) {
            throw new Exception(Constants.ALREADY_EXISTS_SHIPMENT_WITH_ID);
        }


        for (Shipment shipment : shipments) {
            if (shipment.getShipmentId().equals(shipmentIdToBeUpdated)) {
                shipment.setShipmentId(newShipmentId);
                forceUpdateOfObservableList();
                return;
                //forceUpdateOfObservableList(); //because we changed something to a shipment object
                // maybe this should be a part of all public methods in Shipment that can change its state
            }
        }
    }


    public void deleteShipment(StringProperty shipmentId) throws Exception{
        for (Shipment shipment : shipments) {
            if (shipment.getShipmentId().get().equals(shipmentId.get())) {
                shipments.remove(shipment);
                return;
            }
        }

        throw new Exception(Constants.NO_SHIPMENT_EXISTS_WITH_THAT_ID);
    }


    // only needed to updated ComboBoxes!
    public void forceUpdateOfObservableList() {
        //shipments = FXCollections.observableList(shipments);
        
        shipments.add(0, null);
        shipments.remove(0);
    }


    private boolean doesAShipmentExistWithId(StringProperty shipmentId) {
        for (Shipment shipment : shipments) {
            if (shipment.getShipmentId().get().equals(shipmentId.get())) {
                return true;
            }
        }
        return false;
    }


    // these are only for tests
    public void clearData() {
        shipments.clear();
    }





    /*

    Om jag vill göra så att ShipmentHandler fungerar som DataManager,
    dvs. att den är en Singleton, så kan jag göra så här:

    OBS: konstruktorn måste då sättas private

    private static ShipmentHandler instance;

    public static ShipmentHandler getInstance() {
        if (instance == null) {
            instance = new ShipmentHandler();
        }
        return instance;
    }

    */


}
