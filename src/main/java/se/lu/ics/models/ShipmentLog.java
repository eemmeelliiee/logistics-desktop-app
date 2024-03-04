package se.lu.ics.models;
import java.time.LocalDate;

public class ShipmentLog {

    private LocalDate date;
    private Direction direction;
    private Warehouse warehouse;
    private Shipment shipment;

    public ShipmentLog() {
    }

    public ShipmentLog(LocalDate date, Direction direction, Warehouse warehouse, Shipment shipment) {
        this.date = date;
        this.direction = direction;
        this.warehouse = warehouse;
        this.shipment = shipment;
        shipment.addShipmentLog(this);
        warehouse.addShipmentLog(this);
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

 @Override
    public String toString() {
        return  "\n\nShipmentID: " + shipment.getShipmentId() +
                "\nDate: " + date +
                "\nDirection: " + direction +
                "\nWarehouse: " + warehouse.getName() +
                "\n";
    }
    

}
