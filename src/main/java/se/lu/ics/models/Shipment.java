package se.lu.ics.models;
// import java.lang.reflect.Array;
import java.util.ArrayList;

public class Shipment {

    private String shipmentId;
    private Boolean inspected;
    private String label; // sets a label if the shipment is at warehouse > 14 days
    private ArrayList <ShipmentLog> shipmentLogs;
    private ArrayList <InspectionLog> inspectionsMade;

    public Shipment() {
    }

    public Shipment(Boolean inspected, String label) {
        this.shipmentId = generateRandomID();
        this.inspected = inspected;
        this.label = label;
        this.inspectionsMade = new ArrayList<>();
        this.shipmentLogs = new ArrayList<>();
    }

    public String generateRandomID(){
        String randomID = "";
        for (int i = 0; i < 3; i++) {
            int random = (int) (Math.random() * 10);
            randomID += random;
        }
        this.shipmentId = randomID;
        return randomID;
    }

    public String getShipmentId() {
        return shipmentId;
    }


    public Boolean getInspected() {
        return inspected;
    }

    public void setInspected(Boolean inspected) {
        this.inspected = inspected;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getShipmentLogs() {
        return shipmentLogs.toString();
    }

    public double getAmountOfShipmentLogs() {
        if (shipmentLogs == null) {
            return 0;
        }
        return shipmentLogs.size();
    }

    public void setShipmentLogs(ArrayList<ShipmentLog> shipmentLogs) {
        this.shipmentLogs = shipmentLogs;
    }

    public ArrayList<InspectionLog> getInspectionsMade() {
        return inspectionsMade;
    }

    public double getInspectionCount(){
        return inspectionsMade.size();
    }

    public void setInspecLogs(ArrayList<InspectionLog> inspections) {
        this.inspectionsMade = inspections;
    }
    public void addShipmentLog(ShipmentLog shipmentLog) {
        shipmentLogs.add(shipmentLog);
        System.out.println("Shipmentlog added to shipment " + shipmentLog.getShipment().getShipmentId());
    }
// error handling if its not in the list!
    public void removeShipmentLog(ShipmentLog shipmentLog) {
        shipmentLogs.remove(shipmentLog);
        System.out.println("Shipmentlog removed from shipment " + shipmentLog.getShipment().getShipmentId());
    }

    public void addInspection(InspectionLog inspectionLog) {
        inspectionsMade.add(inspectionLog);
        inspectionLog.getShipment().setInspected(true);
        System.out.println("Inspection added to shipment " + inspectionLog.getShipment().getShipmentId());
    }
    public void removeInspection(InspectionLog inspection) {
        if (inspection.getShipment().getInspected() == true) {
            inspectionsMade.remove(inspection);
            System.out.println("Inspection removed from shipment " + inspection.getShipment().getShipmentId());
        }
        System.out.println("Shipment has not been inspected yet");
        
    }


}
