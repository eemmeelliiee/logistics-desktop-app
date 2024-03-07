package se.lu.ics.models;
import java.time.LocalDate;
// import java.lang.reflect.Array;
import java.util.ArrayList;

import javafx.scene.control.TextField;

public class Shipment {

    private String shipmentId;
    private Boolean inspected;
    private String label; // sets a label if the shipment is at warehouse > 14 days
    private ArrayList <ShipmentLog> shipmentLogs;
    private ArrayList <InspectionLog> inspectionsMade;
    private static ArrayList<String> generatedIDs = new ArrayList<>();

    public Shipment() {
        this.shipmentId = generateRandomID();
        this.inspected = false;
        this.label = null;
        this.inspectionsMade = new ArrayList<>();
        this.shipmentLogs = new ArrayList<>(); // remove these, these are now in ShipmentLogHandler, that is accessed through DataManager
    }

    public String generateRandomID() {
        String randomID = "";
        do {
            randomID = "";
            for (int i = 0; i < 3; i++) {
                int random = (int) (Math.random() * 10);
                randomID += random;
            }
        } while (generatedIDs.contains(randomID)); // generates until we get a unique shipmentID
    
        generatedIDs.add(randomID);
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

    public ArrayList<ShipmentLog> getShipmentLogs() {
        return shipmentLogs;
    }

    public String getShipmentHistory(){
        StringBuilder stringBuilder = new StringBuilder();
        for (ShipmentLog log : shipmentLogs) {
            stringBuilder.append(log.toStringForShipment()).append("\n");
        }
        return stringBuilder.toString();
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

    public String getInspectionHistory() {
        StringBuilder stringBuilder = new StringBuilder();
        for (InspectionLog log : inspectionsMade) {
            stringBuilder.append(log.toStringForShipment()).append("\n");
        }
        return stringBuilder.toString();
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
        if (inspection.getShipment().getInspected()) {
            inspectionsMade.remove(inspection);
            System.out.println("Inspection removed from shipment " + inspection.getShipment().getShipmentId());
        }
        System.out.println("Shipment has not been inspected yet");
        
    }
//testing until here
public void updateShipmentLog(int userSelection, LocalDate newDate, Direction newDirection, Warehouse newWarehouse) {
    try {
        int index = userSelection - 1; // subtracts 1 to convert from 1-based to 0-based indexing
        ShipmentLog log = shipmentLogs.get(index);
        log.setDate(newDate);
        log.setDirection(newDirection);
        log.setWarehouse(newWarehouse);
    } catch (IndexOutOfBoundsException e) {
        System.out.println("Invalid selection");
        throw new IllegalArgumentException("Invalid selection");
    }
    }
// justera utefter gui:
    public void updateInspectionLog(TextField userSelectionField, LocalDate newDate, String newInspector, String newResult) {
    try {
        int userSelection = Integer.parseInt(userSelectionField.getText());
        int index = userSelection - 1; 
        InspectionLog log = inspectionsMade.get(index);
        log.setDate(newDate);
        log.setInspector(newInspector);
        log.setResult(newResult);
    } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter an integer.");
        userSelectionField.setText(""); // Clear the text field for the user to try again
    } catch (IndexOutOfBoundsException e) {
        System.out.println("Invalid selection");
        throw new IllegalArgumentException("Invalid selection");
    }
}

    public void setShipmentId(String newShipmentId) {
    
        generatedIDs.remove(shipmentId);
        this.shipmentId = newShipmentId;
        generatedIDs.add(newShipmentId);

        //DataManager.getInstance().getShipmentHandler().forceUpdateOfObservableList();

    }


    static public void resetGeneratedIDs() {
        generatedIDs.clear();
    }


    @Override
    public String toString() {
        return shipmentId;
    }

}
