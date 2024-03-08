package se.lu.ics.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ShipmentLogHandler {

    private ObservableList<ShipmentLog> shipmentLogs;

     public ShipmentLogHandler() {
        shipmentLogs = FXCollections.observableList(new ArrayList<>());
    }

    public ObservableList<ShipmentLog> getShipmentLogs() {
        return shipmentLogs;
    }

    // public void setShipmentLogs(ArrayList<ShipmentLog> shipmentLogs) {
    //     this.shipmentLogs = FXCollections.observableList(shipmentLogs);
    // }

    public ShipmentLog createShipmentLog(LocalDate date, Direction direction, Warehouse warehouse, Shipment shipment) {
        int sameDirectionCount = 0;
int oppositeDirectionCount = 0;

for (ShipmentLog log : shipmentLogs) {
    if (log != null && log.getShipment() != null && log.getWarehouse() != null) {
        if (log.getShipment().equals(shipment) && log.getWarehouse().equals(warehouse)) {
            if (log.getDirection() == direction) {
                sameDirectionCount++;
            } else {
                oppositeDirectionCount++;
            }
        }
    }
}

if (sameDirectionCount > 0 && oppositeDirectionCount == 0) {
    System.out.println("Error: A shipment log with the same shipment, warehouse and direction already exists without a corresponding log with the opposite direction.");
}

ShipmentLog newShipmentLog = new ShipmentLog(date, direction, warehouse, shipment);
shipmentLogs.add(newShipmentLog);
return newShipmentLog;
    }
    // does the same thing as getShipmentLogs right now, should this return a list of Strings by doing .toString on each shipmentLog?
    // public ObservableList<ShipmentLog> readShipmentLogs() {
    //     return shipmentLogs;
    // }

    public void updateShipmentForShipmentLog(ShipmentLog shipmentLog, Shipment newShipment) throws Exception{
        updateLog(shipmentLog, newShipment, shipmentLog.getWarehouse(), shipmentLog.getDirection(), shipmentLog.getDate());
    }
    
    public void updateWarehouseForShipmentLog(ShipmentLog shipmentLog, Warehouse newWarehouse)throws Exception {
        updateLog(shipmentLog, shipmentLog.getShipment(), newWarehouse, shipmentLog.getDirection(), shipmentLog.getDate());
    }
    
    public void updateDirectionForShipmentLog(ShipmentLog shipmentLog, Direction newDirection) throws Exception{
        updateLog(shipmentLog, shipmentLog.getShipment(), shipmentLog.getWarehouse(), newDirection, shipmentLog.getDate());
    }
    
    public void updateDateForShipmentLog(ShipmentLog shipmentLog, LocalDate newDate) throws Exception {
        updateLog(shipmentLog, shipmentLog.getShipment(), shipmentLog.getWarehouse(), shipmentLog.getDirection(), newDate);
    }
    
    private void updateLog(ShipmentLog shipmentLog, Shipment newShipment, Warehouse newWarehouse, Direction newDirection, LocalDate newDate) throws Exception {
        ShipmentLog incomingLog = findShipmentLog(newShipment, newWarehouse, Direction.INCOMING);
        ShipmentLog outgoingLog = findShipmentLog(newShipment, newWarehouse, Direction.OUTGOING);
    
        validateDate(newDate, newDirection, incomingLog, outgoingLog);
    
        shipmentLog.setShipment(newShipment);
        shipmentLog.setWarehouse(newWarehouse);
        shipmentLog.setDirection(newDirection);
        shipmentLog.setDate(newDate);
    
        updateAttentionStatus(shipmentLog);
    }

    public void deleteShipmentLog(ShipmentLog shipmentLog) {
        shipmentLogs.remove(shipmentLog);
}
// Better to manually update the observable list in the controller than to call this method every time a change is made
// public void forceUpdateOfObservableList() {
//     shipmentLogs = FXCollections.observableList(shipmentLogs);
// }
    
    private void validateDate(LocalDate date, Direction direction, ShipmentLog incomingLog, ShipmentLog outgoingLog) throws Exception{
        if (direction == Direction.OUTGOING && (incomingLog == null || date.isBefore(incomingLog.getDate()))) {
            throw new Exception("Outgoing date " + date + " cannot be before incoming date");
        }
        if (direction == Direction.INCOMING && (outgoingLog == null || date.isAfter(outgoingLog.getDate()))) {
            throw new Exception("Incoming date " + date + " cannot be after outgoing date");
        }
    }
    
    private void updateAttentionStatus(ShipmentLog newShipmentLog) {
        for (ShipmentLog shipmentLog : shipmentLogs) {
            if (shipmentLog.needsAttention(newShipmentLog)) {
                shipmentLog.getShipment().setLabel("Needs attention");
            } else {
                shipmentLog.getShipment().setLabel("");
            }
        }
    }


    private ShipmentLog findShipmentLog(Shipment shipment, Warehouse warehouse, Direction direction) {
        for (ShipmentLog log : shipmentLogs) {
            if (log.getShipment().equals(shipment) && log.getWarehouse().equals(warehouse) && log.getDirection() == direction) {
                return log;
            }
        }
        return null;
    }

   




}
