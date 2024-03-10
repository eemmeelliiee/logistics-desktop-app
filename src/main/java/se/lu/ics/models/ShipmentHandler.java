package se.lu.ics.models;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class ShipmentHandler {

    private ObservableList<Shipment> shipments;
    private static ShipmentHandler instance;

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
    // public ObservableList<Shipment> readShipments() {
    //     return shipments;
    // }

    public void updateShipmentId(Shipment shipment, String newShipmentId) throws Exception {

    // inte nödvändigt om vi inte ska skriva in något i textfältet ?
      //  if (!doesAShipmentExistWithId(shipmentIdToBeUpdated)) {
      //      throw new Exception(Constants.NO_SHIPMENT_EXISTS_WITH_THAT_ID);
      //  }

        if (doesAShipmentExistWithId(newShipmentId)) {
           throw new Exception(Constants.ALREADY_EXISTS_SHIPMENT_WITH_ID);
        } 

        if (newShipmentId == null || newShipmentId.equals("")) {
            throw new Exception(Constants.CANNOT_BE_EMPTY);
        }

        if (shipment != null) {
            shipment.setShipmentId(newShipmentId);
            forceUpdateOfObservableList(); // please manually update the observable list in the controller instead
        }


        // for (Shipment shipment : shipments) {
        //     if (shipment.getShipmentId().equals(shipmentIdToBeUpdated)) {
        //         shipment.setShipmentId(newShipmentId);
        //         forceUpdateOfObservableList();
        //         return;
        //         //forceUpdateOfObservableList(); //because we changed something to a shipment object
        //         // maybe this should be a part of all public methods in Shipment that can change its state )
        //     }
        // }
    }


    public void deleteShipment(Shipment shipment){
        // if (!doesAShipmentExistWithId(shipmentId)) {
        //     throw new Exception(Constants.NO_SHIPMENT_EXISTS_WITH_THAT_ID);
        // }
        //     for (Shipment shipment : shipments){
        //         if (shipment.getShipmentId().get().equals(shipmentId.get())) {
        //             shipments.remove(shipment);
        //             return;
        //         }
        //     }
        // if (shipment != null) {
            shipments.remove(shipment);
        // }
    }

    // INTE REKOMMENDERAT, manually edit the observable list in the controller instead !!!
    // only needed to updated ComboBoxes!
    public void forceUpdateOfObservableList() {
        //shipments = FXCollections.observableList(shipments);
        
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





    /*
     * 
     * Om jag vill göra så att ShipmentHandler fungerar som DataManager,
     * dvs. att den är en Singleton, så kan jag göra så här:
     * 
     * OBS: konstruktorn måste då sättas private
     * 
     * private static ShipmentHandler instance;
     * 
     * public static ShipmentHandler getInstance() {
     * if (instance == null) {
     * instance = new ShipmentHandler();
     * }
     * return instance;
     * }
     * 
     */


}
