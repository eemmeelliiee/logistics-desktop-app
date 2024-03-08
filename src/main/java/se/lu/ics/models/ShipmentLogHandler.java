package se.lu.ics.models;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ShipmentLogHandler {
    private static final String NEEDS_ATTENTION = "Needs attention";
    private ObservableList<ShipmentLog> shipmentLogs;

    public ShipmentLogHandler() {
        shipmentLogs = FXCollections.observableArrayList();
    }

    public ObservableList<ShipmentLog> getShipmentLogs() {
        return shipmentLogs;
    }

    public ShipmentLog createShipmentLog(LocalDate date, Direction direction, Warehouse warehouse, Shipment shipment)
            throws Exception {
        validateShipmentLog(shipment, warehouse, direction);
        ShipmentLog newShipmentLog = new ShipmentLog(date, direction, warehouse, shipment);
        shipmentLogs.add(newShipmentLog);
        return newShipmentLog;
    }

    public void updateShipmentLog(ShipmentLog shipmentLog, UpdateFieldShipmentLog field, Object newValue) throws Exception {
        LocalDate date = shipmentLog.getDate();
        Direction direction = shipmentLog.getDirection();
        Warehouse warehouse = shipmentLog.getWarehouse();
        Shipment shipment = shipmentLog.getShipment();

        switch (field) {
            case SHIPMENT:
                shipment = (Shipment) newValue;
                break;
            case WAREHOUSE:
                warehouse = (Warehouse) newValue;
                break;
            case DIRECTION:
                direction = (Direction) newValue;
                break;
            case DATE:
                date = (LocalDate) newValue;
                break;
        }

        updateLog(shipmentLog, date, direction, warehouse, shipment);
    }

    public void deleteShipmentLog(ShipmentLog shipmentLog) {
        shipmentLogs.remove(shipmentLog);
    }

    private void updateLog(ShipmentLog shipmentLog, LocalDate newDate, Direction newDirection, Warehouse newWarehouse,
            Shipment newShipment) throws Exception {
        validateDate(newDate, newDirection, newShipment, newWarehouse);
        shipmentLog.setShipment(newShipment);
        shipmentLog.setWarehouse(newWarehouse);
        shipmentLog.setDirection(newDirection);
        shipmentLog.setDate(newDate);
        updateAttentionStatus();
    }

    private void validateDate(LocalDate date, Direction direction, Shipment shipment, Warehouse warehouse)
            throws Exception {
        ShipmentLog incomingLog = findShipmentLog(shipment, warehouse, Direction.INCOMING);
        ShipmentLog outgoingLog = findShipmentLog(shipment, warehouse, Direction.OUTGOING);

        if (direction == Direction.OUTGOING && incomingLog != null && date.isBefore(incomingLog.getDate())) {
            throw new Exception("Outgoing date " + date + " cannot be before incoming date");
        }
        if (direction == Direction.INCOMING && outgoingLog != null && date.isAfter(outgoingLog.getDate())) {
            throw new Exception("Incoming date " + date + " cannot be after outgoing date");
        }
    }

    private void updateAttentionStatus() {
        for (ShipmentLog shipmentLog : shipmentLogs) {
            if (shipmentLog.needsAttention(shipmentLog)) {
                shipmentLog.getShipment().setLabel(NEEDS_ATTENTION);
            } else {
                shipmentLog.getShipment().setLabel("");
            }
        }
    }

    private ShipmentLog findShipmentLog(Shipment shipment, Warehouse warehouse, Direction direction) {
        for (ShipmentLog log : shipmentLogs) {
            if (log.getShipment().equals(shipment) && log.getWarehouse().equals(warehouse)
                    && log.getDirection() == direction) {
                return log;
            }
        }
        return null;
    }

    private void validateShipmentLog(Shipment shipment, Warehouse warehouse, Direction direction) throws Exception {
        int sameDirectionCount = countLogs(shipment, warehouse, direction);
        int oppositeDirectionCount = countLogs(shipment, warehouse, direction.opposite());

        if (sameDirectionCount == oppositeDirectionCount && sameDirectionCount > 0) {
            System.err.println(
                    "Warning: A shipment log with the same shipment, warehouse and direction already exists with an equal amount of corresponding logs with the opposite direction.");
        }

        if (sameDirectionCount > 0 && oppositeDirectionCount == 0) {
            throw new Exception(
                    "Warning: A shipment log with the same shipment, warehouse and direction already exists without a corresponding log with the opposite direction.");
        }
    }

    private int countLogs(Shipment shipment, Warehouse warehouse, Direction direction) {
        int count = 0;
        for (ShipmentLog log : shipmentLogs) {
            if (log.getShipment().equals(shipment) && log.getWarehouse().equals(warehouse)
                    && log.getDirection() == direction) {
                count++;
            }
        }
        return count;
    }
}
