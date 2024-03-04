package se.lu.ics.models;
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;

public class Warehouse {
    private String name;
    private Location location;
    private String address;
    private double capacity;
    private double currentStockLevel;
    private ArrayList <ShipmentLog> shipments;
    private ArrayList <InspectionLog> inspections;

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
        if (shipments == null) {
            return 0;
        } else {     
        return shipments.size();
        }
    }

    public ArrayList<ShipmentLog> getShipments() {
        return shipments;
    }

    public void setShipments(ArrayList<ShipmentLog> shipments) {
        this.shipments = shipments;
    }

    public ArrayList<InspectionLog> getInspections() {
        return inspections;
    }

    public void setInspections(ArrayList<InspectionLog> inspections) {
        this.inspections = inspections;
    }

    // CRUD OPERATIONS:

    public void createWarehouse(String name, Location location, String address, double capacity){
        Warehouse warehouse = new Warehouse();
        warehouse.setName(name);
        warehouse.setLocation(location);
        warehouse.setAddress(address);
        warehouse.setCapacity(capacity);

    }

    public String readWarehouse() {
        if (this.getName() == null) {
        //INSERT ERROR HANDLING FOR NULL VALUES
            return "Warehouse does not exist";
        } else {
        return 
        "Warehouse: " + this.getName() + 
        "\nLocation: " + this.getLocation() + 
        "\nAddress: " + this.getAddress() + 
        "\nCapacity: " + this.getCapacity() + 
        "\nCurrent stock level: " + this.getCurrentStockLevel() + 
        "\nUsed capacity: " + this.getUsedCapacity() + 
        "\nShipments: " + this.getShipmentCount() + 
        "\nInspections: " + this.getInspectionCount();
        }
    }

    public void updateWarehouse(String name, Location location, String address, double capacity){
        String output;
        if (name == null || location == null || address == null || capacity < 1) {
            //INSERT ERROR HANDLING FOR NULL VALUES
            output = "Name, location and address must be set and capacity must be greater than 0";
        } else {
            this.setName(name);
            this.setLocation(location);
            this.setAddress(address);
            this.setCapacity(capacity);
            output = "Warehouse updated";
        }
        System.out.println(output);
    }

    public void deleteWarehouse(){
        if (this.getName() == null) {
            //INSERT ERROR HANDLING FOR NULL VALUES
            System.out.println("Warehouse does not exist");
        }
        this.setName(null);
        this.setLocation(null);
        this.setAddress(null);
        this.setCapacity(0);
        System.out.println("Warehouse deleted");
    }

    public void addShipmentLog(ShipmentLog shipmentLog) {
        String output = "";
        if (shipmentLog.getDirection() == Direction.INCOMING) {
            if (getCurrentCapacity() < 1) {
                output = "Warehouse is full";
            } else {
                this.currentStockLevel += 1;
                output = "Shipment added";
            }
        } else if (shipmentLog.getDirection() == Direction.OUTGOING) {
            this.currentStockLevel -= 1;
            output = "Shipment added";
        } 
        shipments.add(shipmentLog); 
        System.out.println(output);
    }
    // error handling if its not in the list!
    public void removeShipmentLog(ShipmentLog shipmentLog) {
        String output;
        if (shipmentLog.getDirection() == Direction.INCOMING) {
            this.currentStockLevel -= 1;
        } else {
            this.currentStockLevel += 1;
        }
        this.shipments.remove(shipmentLog);
        output = "Shipment removed";
        System.out.println(output);
    }

    public void addInspection(InspectionLog inspection) {
        String output;
        this.inspections.add(inspection);
        output = "Inspection added";
        System.out.println(output);
    }

    // GETTERS for shipment count, inspection count, current capacity and used capacity:
     
    public double getShipmentCount() {
        return shipments.size();
    }

    public double getInspectionCount() {
        return inspections.size();
    }

    public double getCurrentCapacity() {
        return capacity - currentStockLevel;
    }

    public String getUsedCapacity(){
        return (currentStockLevel/capacity)*100 + "%";
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
