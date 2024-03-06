package se.lu.ics.models;

import java.util.ArrayList;

public class ShipmentHandler {

    private ArrayList<Shipment> shipments;

    public ShipmentHandler() {
        shipments = new ArrayList<>();
    }


    public ArrayList<Shipment> getShipments() {
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
    public ArrayList<Shipment> readShipments() {
        return shipments;
    }

    public void updateShipmentId(String shipmentIdToBeUpdated, String newShipmentId) throws Exception {

        if (!doesAShipmentExistWithId(shipmentIdToBeUpdated)) {
            throw new Exception(Constants.NO_SHIPMENT_EXISTS_WITH_THAT_ID);
        }

        if (doesAShipmentExistWithId(newShipmentId)) {
            throw new Exception(Constants.ALREADY_EXISTS_SHIPMENT_WITH_ID);
        }


        for (Shipment shipment : shipments) {
            if (shipment.getShipmentId().equals(shipmentIdToBeUpdated)) {
                shipment.setShipmentId(newShipmentId);
            }
        }
    }


    public void deleteShipment(String shipmentId) throws Exception{
        for (Shipment shipment : shipments) {
            if (shipment.getShipmentId().equals(shipmentId)) {
                shipments.remove(shipment);
                return;
            }
        }

        throw new Exception(Constants.NO_SHIPMENT_EXISTS_WITH_THAT_ID);
    }



    private boolean doesAShipmentExistWithId(String shipmentId) {
        for (Shipment shipment : shipments) {
            if (shipment.getShipmentId().equals(shipmentId)) {
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
