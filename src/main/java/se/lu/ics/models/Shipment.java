package se.lu.ics.models;
// import java.lang.reflect.Array;
import java.util.ArrayList;

public class Shipment {

    private String shipmentId;
    private String label; // sets a label if the shipment is at warehouse > 14 days
    private Warehouse currentWarehouse;
    private int totalNumberOfWarehouses;
    private static ArrayList<String> generatedIds = new ArrayList<>();
    private static String idCounter = "001";

    public Shipment() {
        this.shipmentId = generateShipmentId();
        this.label = null;
        this.currentWarehouse = null;
        this.totalNumberOfWarehouses = 0;
    }

    public String generateShipmentId() {
        String generatedId = "S" + idCounter;
        int counter = Integer.parseInt(idCounter);
        counter++;
        idCounter = String.format("%03d", counter);
        return generatedId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String newShipmentId) {
    
        generatedIds.remove(shipmentId);
        this.shipmentId = newShipmentId;
        generatedIds.add(newShipmentId);
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    static public void resetGeneratedIds() {
        generatedIds.clear();
    }

    public Warehouse getCurrentWarehouse() {
        return currentWarehouse;
    }

    public void setCurrentWarehouse(Warehouse currentWarehouse) {
        this.currentWarehouse = currentWarehouse;
    }

    public int getTotalNumberOfWarehouses() {
        return totalNumberOfWarehouses;
    }

    public void setTotalNumberOfWarehouses(int totalNumberOfWarehouses) {
        this.totalNumberOfWarehouses = totalNumberOfWarehouses;
    }


    // för att kunna använda i combobox - t.ex i combobox i tablecolumn för inspectiones för shipment
    @Override
     public String toString() {
         return shipmentId;
    }

}
