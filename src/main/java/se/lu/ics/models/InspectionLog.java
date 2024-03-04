package se.lu.ics.models;

import java.time.LocalDate;

public class InspectionLog {

    private Shipment shipment;
    private Warehouse warehouse;
    private LocalDate date;
    private String inspector;
    private String result;

    public InspectionLog() {
    }

    public InspectionLog(Shipment shipment, Warehouse warehouse, LocalDate date, String inspector, String result) {
        this.shipment = shipment;
        this.warehouse = warehouse;
        this.date = date;
        this.inspector = inspector;
        this.result = result;
        shipment.addInspection(this);
        warehouse.addInspection(this);

    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static String inspectionHistory(Shipment shipment){
        return "Inspection history for shipment with ID: " + shipment.getShipmentId() + shipment.getInspectionsMade();
    }

    @Override
    public String toString() {
        return  "\n\nShipmentID=" + shipment.getShipmentId() +
                "\nWarehouse=" + warehouse.getName() +
                "\nDate=" + date +
                "\nInspector='" + inspector +
                "\nResult='" + result +
                "\n";
    }


}
