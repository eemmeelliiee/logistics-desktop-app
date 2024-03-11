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


    // Used in shipmentloghandler. The ShipmentLog class is responsible for
    // determining if a ShipmentLog is similar to another and if it needs attention.
    // This follows the Single Responsibility Principle,
    // making my code cleaner and easier to maintain.
    public boolean needsAttention(ShipmentLog other) {
        boolean isPairAndNeedsAttention = this.getShipment().equals(other.getShipment()) &&
                this.getWarehouse().equals(other.getWarehouse()) &&
                this.getDirection() == Direction.INCOMING &&
                other.getDirection() == Direction.OUTGOING;

        boolean needsAttention = isPairAndNeedsAttention
                && ChronoUnit.DAYS.between(this.getDate(), other.getDate()) > 14;

        return needsAttention;
    }

    @Override
    public String toString(){
        return "\n\nShipmentID: " + shipment.getShipmentId()
        +       "\nWarehouse: " + warehouse.getName()
        +       "\nDate: " + date
        +       "\nDirection: " + direction;
        
    }

}
