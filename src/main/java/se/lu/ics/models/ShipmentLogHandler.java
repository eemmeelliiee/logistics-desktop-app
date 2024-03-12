package se.lu.ics.models;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import se.lu.ics.controllers.ShipmentTabController;

public class ShipmentLogHandler {
    // Set these in Constants if desired
    public static final String NEEDS_ATTENTION = "Needs attention!";
    public static final String WAREHOUSE_IS_FULL = "Error: Warehouse is at capacity";
    public static final String SHIPMENT_IS_AT_OTHER_WAREHOUSE = "Error: Shipment is currently incoming at another warehouse";
    public static final String SHIPMENT_ALREADY_INCOMING_ON_THIS_WAREHOUSE = "Error: Shipment is already incoming at this warehouse";
    public static final String SAME_SHIPMENT = "Error: ShipmentLog already has this shipment";
    public static final String SAME_WAREHOUSE = "Error: ShipmentLog already has this warehouse";
    public static final String SAME_DIRECTION = "Error: ShipmentLog already has this direction";
    public static final String SAME_DATE = "Error: ShipmentLog already has this date";

    private ObservableList<ShipmentLog> shipmentLogs;
    private static ShipmentLogHandler instance;

    public static ShipmentLogHandler getInstance() {
        if (instance == null) {
            instance = new ShipmentLogHandler();
        }
        return instance;
    }

    private ShipmentLogHandler() {
        shipmentLogs = FXCollections.observableArrayList();

    }

    // <<------Create------>>

    public ShipmentLog createShipmentLog(LocalDate date, Direction direction, Warehouse warehouse, Shipment shipment)
            throws Exception {
        if (warehouse.getCurrentAvailableCapacity() < 1 && direction == Direction.INCOMING) {
            throw new Exception(WAREHOUSE_IS_FULL);
        }
        validateDate(date, direction, shipment, warehouse);
        validateShipmentLog(shipment, warehouse, direction, null);
        ShipmentLog newShipmentLog = new ShipmentLog(date, direction, warehouse, shipment);
        shipmentLogs.add(newShipmentLog);
        return newShipmentLog;

    }

    // <<------Read------>>

    public ObservableList<ShipmentLog> getShipmentLogs() {
        return shipmentLogs;
    }

    // <<------Update------>>

    public void warehouseUpdate() {

    }

    public void updateShipmentLog(ShipmentLog shipmentLog, UpdateFieldShipmentLog field, Object newValue)
            throws Exception {
        if (newValue == null || newValue.equals("")) {
            throw new Exception(Constants.CANNOT_BE_EMPTY);
        }
        switch (field) {
            case SHIPMENT:
                Shipment oldShipment = shipmentLog.getShipment();
                Shipment newShipment = (Shipment) newValue;
                if (oldShipment.equals(newShipment)) {
                    throw new Exception(SAME_SHIPMENT);
                }
                validateShipmentLog(newShipment, shipmentLog.getWarehouse(), shipmentLog.getDirection(), shipmentLog);
                validateDate(shipmentLog.getDate(), shipmentLog.getDirection(), newShipment,
                        shipmentLog.getWarehouse());
                shipmentLog.setShipment(newShipment);
                break;
            case WAREHOUSE:
                Warehouse oldWarehouse = shipmentLog.getWarehouse();
                Warehouse newWarehouse = (Warehouse) newValue;
                Direction direction = shipmentLog.getDirection();
                if (newWarehouse.getCurrentAvailableCapacity() < 1 && direction == Direction.INCOMING) {
                    throw new Exception(WAREHOUSE_IS_FULL);
                }
                if (oldWarehouse.equals(newWarehouse)) {
                    throw new Exception(SAME_WAREHOUSE);
                }
                validateShipmentLog(shipmentLog.getShipment(), newWarehouse, shipmentLog.getDirection(), shipmentLog);
                validateDate(shipmentLog.getDate(), shipmentLog.getDirection(), shipmentLog.getShipment(),
                        newWarehouse);
                shipmentLog.setWarehouse(newWarehouse);
                break;
            case DIRECTION:
                Direction oldDirection = shipmentLog.getDirection();
                Direction newDirection = (Direction) newValue;
                if (oldDirection.equals(newDirection)) {
                    throw new Exception(SAME_DIRECTION);
                }
                validateShipmentLog(shipmentLog.getShipment(), shipmentLog.getWarehouse(), newDirection, shipmentLog);
                validateDate(shipmentLog.getDate(), newDirection, shipmentLog.getShipment(),
                        shipmentLog.getWarehouse());
                shipmentLog.setDirection(newDirection);
                break;
            case DATE:
                LocalDate oldDate = shipmentLog.getDate();
                LocalDate newDate = (LocalDate) newValue;
                if (oldDate.equals(newDate)) {
                    throw new Exception(SAME_DATE);
                }
                validateShipmentLog(shipmentLog.getShipment(), shipmentLog.getWarehouse(), shipmentLog.getDirection(),
                        shipmentLog);
                validateDate(newDate, shipmentLog.getDirection(), shipmentLog.getShipment(),
                        shipmentLog.getWarehouse());
                shipmentLog.setDate(newDate);
                break;
            // default:
            // throw new Exception(Constants.INVALID_FIELD);
        }
        updateAttentionStatus();
    }

    // <<------Delete------>>

    public void deleteShipmentLog(ShipmentLog shipmentLog) {
        shipmentLogs.remove(shipmentLog);
    }

    private void validateDate(LocalDate date, Direction direction, Shipment shipment, Warehouse warehouse)
            throws Exception {
        int incomingCount = countLogs(shipment, warehouse, Direction.INCOMING);
        int outgoingCount = countLogs(shipment, warehouse, Direction.OUTGOING);

        // Allows the date to be set without validation if no shipment logs exist for
        // the shipment and warehouse
        if (incomingCount == 0 && outgoingCount == 0) {
            return;
        }

        // Only looks at most recent shipment log - allows for an outgoing log to be
        // created after an incoming log and vice versa if corresponding logs exist
        if (incomingCount != outgoingCount) {
            ShipmentLog mostRecentLog = findMostRecentLog(shipment, warehouse);

            if (direction == Direction.OUTGOING && mostRecentLog != null
                    && mostRecentLog.getDirection() == Direction.INCOMING && date.isBefore(mostRecentLog.getDate())) {
                throw new Exception(
                        "Outgoing date " + date + " cannot be before incoming date " + mostRecentLog.getDate());
            }

            if (direction == Direction.INCOMING && mostRecentLog != null
                    && mostRecentLog.getDirection() == Direction.OUTGOING && date.isAfter(mostRecentLog.getDate())) {
                throw new Exception(
                        "Incoming date " + date + " cannot be after outgoing date " + mostRecentLog.getDate());
            }
        }
    }

    // <<------ Help method in validateDate, finds most recent shipment log------>>
    private ShipmentLog findMostRecentLog(Shipment shipment, Warehouse warehouse) {
        ShipmentLog mostRecentLog = null;
        for (ShipmentLog log : shipmentLogs) {
            if (log.getShipment().equals(shipment) && log.getWarehouse().equals(warehouse)) {
                if (mostRecentLog == null || log.getDate().isAfter(mostRecentLog.getDate())) {
                    mostRecentLog = log;
                }
            }
        }
        return mostRecentLog;
    }

    private Direction getCurrentDirection(Shipment shipment, Warehouse warehouse) {
        ShipmentLog mostRecentLog = findMostRecentLog(shipment, warehouse);

        // If there's no log, return null
        if (mostRecentLog == null) {
            return null;
        }

        // Return the direction of the most recent log
        return mostRecentLog.getDirection();
    }

    // Updates the label of the shipment if shipment has spent more than 14 days at
    // a warehouse
    public void updateAttentionStatus() {
        for (ShipmentLog shipmentLog : shipmentLogs) {
            if (shipmentLog.needsAttention()) {
                shipmentLog.getShipment().setLabel(NEEDS_ATTENTION);
            } else {
                shipmentLog.getShipment().setLabel("");
            }
        }
    }

    // << ------ Validates the shipment log data before creating or updating it ------ >>
    private void validateShipmentLog(Shipment shipment, Warehouse warehouse, Direction direction,
            ShipmentLog logToUpdate) throws Exception {

        int sameDirectionCount = countLogs(shipment, warehouse, direction);
        int oppositeDirectionCount = countLogs(shipment, warehouse, direction.opposite());

        Direction currentDirection = getCurrentDirection(shipment, warehouse);

        // Exempt the log being updated from direction validation
        if (logToUpdate != null && shipmentLogs.contains(logToUpdate)) {
            if (logToUpdate.getShipment().equals(shipment) && logToUpdate.getWarehouse().equals(warehouse)) {
                sameDirectionCount--;
            }
        }
        //
        if (sameDirectionCount > oppositeDirectionCount && direction != currentDirection) {
            throw new Exception(SHIPMENT_ALREADY_INCOMING_ON_THIS_WAREHOUSE);
        }

        if (direction == Direction.INCOMING) {
            for (ShipmentLog log : shipmentLogs) {
                if (log.getShipment().equals(shipment) && log.getDirection() == Direction.INCOMING
                        && !log.getWarehouse().equals(warehouse)) {
                    int outgoingCount = countLogs(shipment, log.getWarehouse(), Direction.OUTGOING);
                    int incomingCount = countLogs(shipment, log.getWarehouse(), Direction.INCOMING);
                    if (outgoingCount < incomingCount) {
                        throw new Exception(SHIPMENT_IS_AT_OTHER_WAREHOUSE);
                    }
                }
            }
        }

        if (sameDirectionCount > oppositeDirectionCount) {
            throw new Exception(SHIPMENT_ALREADY_INCOMING_ON_THIS_WAREHOUSE);
        }

        if (sameDirectionCount == oppositeDirectionCount && sameDirectionCount > 0) {
            // Make sure warning is printed in the console and in GUI
            ShipmentTabController.warningMessage = "Warning: Transportation loop detected";
        }

    }

    // <<------ Used in validateShipmentLog to count the number of logs with specific shipment, warehouse and direction ------ >>
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

    // For testing purposes only
    public void clearData() {
        shipmentLogs.clear();
    }
}
