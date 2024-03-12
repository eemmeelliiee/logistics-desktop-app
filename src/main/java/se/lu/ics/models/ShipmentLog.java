package se.lu.ics.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class ShipmentLog {

    private LocalDate date;
    private Direction direction;
    private Warehouse warehouse;
    private Shipment shipment;

    public ShipmentLog(LocalDate date, Direction direction, Warehouse warehouse, Shipment shipment) throws Exception {
        if (date == null || direction == null || warehouse == null || shipment == null) {
            throw new Exception(Constants.CANNOT_BE_EMPTY);
        }
        this.date = date;
        this.direction = direction;
        this.warehouse = warehouse;
        this.shipment = shipment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public String getShipmentId() { // Currently has no setter
        return shipment.getShipmentId();
    }

    public String getShipmentLabel(){
        return shipment.getLabel();
    }



    // Used in shipmentLogHandler. The ShipmentLog class is responsible for
    // determining if a ShipmentLog is similar to another and if it needs attention.
    // Follows the Single Responsibility Principle,
    // making the code cleaner and easier to maintain.
    public boolean needsAttention() {
        boolean isIncoming = this.getDirection() == Direction.INCOMING;
    
        boolean needsAttention = isIncoming
                && ChronoUnit.DAYS.between(this.getDate(), LocalDate.now()) > 14;
    
        return needsAttention;
    }
}
