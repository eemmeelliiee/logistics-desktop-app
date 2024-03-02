package se.lu.ics.models;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Warehouse {
    private String name;
    private Location location;
    private String address;
    private double capacity;
    private double currentStockLevel;
    private ArrayList <ShipmentLog> shipments;
    private ArrayList <Inspection> inspections;

    private double currentCapacity = capacity - currentStockLevel;
    private double usedCapacity = currentStockLevel/capacity;

    public double getCurrentCapacity(double capacity, double currentStockLevel) {
        return capacity - currentStockLevel;
    }

    public void setCurrentCapacity(double currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public Warehouse() {
    }

    public Warehouse(String name, Location location, String address, double capacity) {
        this.name = name;
        this.location = location;
        this.address = address;
        this.capacity = capacity;
        this.shipments = new ArrayList<>();
        this.inspections = new ArrayList<>();
    }

    public void addShipment(ShipmentLog shipmentLog) {
        if (shipmentLog.getDirection() == Direction.INCOMING) {
            this.currentStockLevel += 1;
        } else {
            this.currentStockLevel -= 1;
        } if (this.getCurrentCapacity(capacity, currentStockLevel) < 1) {
            //INSERT ERROR HANDLING FOR CAPACITY
            System.out.println("Warehouse is full");
        } else {
            this.shipments.add(shipmentLog);
        }
    }

    public void addInspection(Inspection inspection) {

        this.inspections.add(inspection);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getCapacity() {
        return capacity;
    }
    
    //INSERT ERROR HANDLING FOR CAPACITY
    public void setCapacity(double capacity) {
        if (capacity < 1) {
            System.out.println("Capacity must be greater than 0");
        } else {
            this.capacity = capacity;
        }
    }

    public double getCurrentStockLevel() {
        return currentStockLevel;
    }

    public void setCurrentStockLevel(double currentStockLevel) {
        this.currentStockLevel = currentStockLevel;
    }

    public double getUsedCapacity() {
        return usedCapacity;
    }

    public void setUsedCapacity(double usedCapacity) {
        this.usedCapacity = usedCapacity;
    }

    public ArrayList<ShipmentLog> getShipments() {
        return shipments;
    }

    public void setShipments(ArrayList<ShipmentLog> shipments) {
        this.shipments = shipments;
    }

    public ArrayList<Inspection> getInspections() {
        return inspections;
    }

    public void setInspections(ArrayList<Inspection> inspections) {
        this.inspections = inspections;
    }
// COMPARING TWO DATES:
    // // Assuming inspections is your List of Inspection objects
// Collections.sort(inspections, new Comparator<Inspection>() {
  //   public int compare(Inspection i1, Inspection i2) {
 //        return i2.getDate().compareTo(i1.getDate());
 ///   }
//});

// // Now the first item in the list is the most recent inspection
// Inspection lastInspection = inspections.get(0);
    
}
