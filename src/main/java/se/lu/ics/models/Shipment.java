package se.lu.ics.models;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Shipment {

    private String shipmentId;
    private Boolean inspected;
    private String label; // sets a label if the shipment is at warehouse > 14 days
    private ArrayList <ShipmentLog> shipmentLogs;
    private ArrayList <Inspection> inspectionsMade;

    public Shipment() {
    }

    public Shipment(String shipmentId, Boolean inspected, String label) {
        this.shipmentId = shipmentId;
        this.inspected = inspected;
        this.label = label;
        this.inspectionsMade = new ArrayList<>();
        this.shipmentLogs = new ArrayList<>();
    }

    public void addShipmentLog(ShipmentLog shipmentLog) {
        this.shipmentLogs.add(shipmentLog);
    }

    public void addInspection(Inspection inspection) {
        this.inspectionsMade.add(inspection);
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
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

    public ArrayList<ShipmentLog> getShipmentLogs() {
        return shipmentLogs;
    }

    public void setShipmentLogs(ArrayList<ShipmentLog> shipmentLogs) {
        this.shipmentLogs = shipmentLogs;
    }

    public ArrayList<Inspection> getInspections() {
        return inspectionsMade;
    }

    public void setInspecLogs(ArrayList<Inspection> inspections) {
        this.inspectionsMade = inspections;
    }
    




}
