package se.lu.ics.models;
import java.time.LocalDate;


public class Warehouse {
    private String name;
    private Location location;
    private String address;
    private double capacity;
    private double currentStockLevel; // Get/set ????
    private LocalDate mostRecentInspectionDate; // get/set ????

    public Warehouse(String name, Location location, String address, double capacity) throws Exception {
        if (capacity < 0) {
            throw new Exception(Constants.CAPACITY_MUST_BE_GREATER_THAN_0);
        }
        this.name = name;
        this.location = location;
        this.address = address;
        this.capacity = capacity;
        this.currentStockLevel = 0;
        this.mostRecentInspectionDate = null; // is this needed?
    }

    public String getName() {
        return name;
    }
    public void setName(String newName) {
        this.name = newName;
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
    
    public void setCapacity(double capacity) throws Exception {
        if (capacity < 1) {
            throw new Exception(Constants.CAPACITY_MUST_BE_GREATER_THAN_0);
        } else {
            this.capacity = capacity;
        }
    }


}



    // public double getCurrentStockLevel() {
    //     if (shipments == null) {
    //         return 0;
    //     } else {     
    //     return shipments.size();
    //     }
    // }

//     public ArrayList<ShipmentLog> getShipments() {
//         return shipments;
//     }

//     public String getShipmentHistory() {
//         StringBuilder sb = new StringBuilder();
//         for (ShipmentLog log : shipments) {
//             sb.append(log.toStringForWarehouse()).append("\n");
//         }
//         return sb.toString();
//     }

//     public void setShipments(ArrayList<ShipmentLog> shipments) {
//         this.shipments = shipments;
//     }

//     public ArrayList<InspectionLog> getInspections() {
//         return inspections;
//     }

//     public String getInspectionHistory() {
//         StringBuilder stringBuilder = new StringBuilder();
//         for (InspectionLog log : inspections) {
//             stringBuilder.append(log.toStringForWarehouse()).append("\n");
//         }
//         return stringBuilder.toString();
//     }

//     public void setInspections(ArrayList<InspectionLog> inspections) {
//         this.inspections = inspections;
//     }

//     // CRUD OPERATIONS:

//     public void createWarehouse(String name, Location location, String address, double capacity){
//         Warehouse warehouse = new Warehouse();
//         warehouse.setName(name);
//         warehouse.setLocation(location);
//         warehouse.setAddress(address);
//         warehouse.setCapacity(capacity);

//     }

//     public void updateWarehouse(String name, Location location, String address, double capacity){
//         String output;
//         if (name == null || location == null || address == null || capacity < 1) {
//             //INSERT ERROR HANDLING FOR NULL VALUES
//             output = "Name, location and address must be set and capacity must be greater than 0";
//         } else {
//             this.setName(name);
//             this.setLocation(location);
//             this.setAddress(address);
//             this.setCapacity(capacity);
//             output = "Warehouse updated";
//         }
//         System.out.println(output);
//     }

//     public void deleteWarehouse(){
//         if (this.getName() == null) {
//             //INSERT ERROR HANDLING FOR NULL VALUES
//             System.out.println("Warehouse does not exist");
//         }
//         this.setName(null);
//         this.setLocation(null);
//         this.setAddress(null);
//         this.setCapacity(0);
//         System.out.println("Warehouse deleted");
//     }

//     public void addShipmentLog(ShipmentLog shipmentLog) {
//         String output = "";
//         if (shipmentLog.getDirection() == Direction.INCOMING) {
//             if (getCurrentCapacity() < 1) {
//                 output = "Warehouse is full";
//             } else {
//                 this.currentStockLevel += 1;
//                 output = "Shipment added";
//             }
//         } else if (shipmentLog.getDirection() == Direction.OUTGOING) {
//             this.currentStockLevel -= 1;
//             output = "Shipment added";
//         } 
//         shipments.add(shipmentLog); 
//         System.out.println(output);
//     }
//     // error handling if its not in the list!
//     public void removeShipmentLog(ShipmentLog shipmentLog) {
//         String output;
//         if (shipmentLog.getDirection() == Direction.INCOMING) {
//             this.currentStockLevel -= 1;
//         } else {
//             this.currentStockLevel += 1;
//         }
//         this.shipments.remove(shipmentLog);
//         output = "Shipment removed";
//         System.out.println(output);
//     }

//     public void addInspection(InspectionLog inspection) {
//         String output;
//         this.inspections.add(inspection);
//         output = "Inspection added";
//         System.out.println(output);
//     }

//     // GETTERS for shipment count, inspection count, current capacity and used capacity:
     
//     public double getShipmentCount() {
//         return shipments.size();
//     }

//     public double getInspectionCount() {
//         return inspections.size();
//     }

//     public double getCurrentCapacity() {
//         return capacity - currentStockLevel;
//     }

//     public String getUsedCapacity(){
//         return (currentStockLevel/capacity)*100 + "%";
//     }

//     // ------- tests done until here ----------------------------------------

//     // ------- new methods: --------------------------

//     public LocalDate getMostRecentInspectionDate() {
//         LocalDate mostRecentDate = LocalDate.MIN;
//         for (InspectionLog inspection : inspections) {
//             if (inspection.getDate().isAfter(mostRecentDate)) {
//                 mostRecentDate = inspection.getDate();
//             }
//         }
//         return mostRecentDate;
//     }

//     public void readWarehouseInfo(){
//         System.out.println("Warehouse: " + this.getName() +
//         "\nLocation: " + this.getLocation() +
//         "\nAddress: " + this.getAddress() +
//         "\nCapacity: " + this.getCapacity() +
//         "\nCurrent stock level: " + this.getCurrentStockLevel() +
//         "\nUsed capacity: " + this.getUsedCapacity() +
//         "\nShipments: " + this.getShipmentCount() +
//         "\nInspections: " + this.getInspectionCount());
//     }

//     public void printShipmentLogs(){
//         for (ShipmentLog shipmentLog : shipments) {
//             System.out.println(shipmentLog);
//         }
//     }
//     public double calculateAverageShipmentTime() {
//     double totalDays = 0;
//     int count = 0;

//     for (int i = 0; i < shipments.size(); i += 2) {
//         if (i + 1 < shipments.size()) {
//             ShipmentLog incoming = shipments.get(i);
//             ShipmentLog outgoing = shipments.get(i + 1);

//             if (incoming.getDirection() == Direction.INCOMING && outgoing.getDirection() == Direction.OUTGOING) {
//                 long daysBetween = ChronoUnit.DAYS.between(incoming.getDate(), outgoing.getDate());
//                 totalDays += daysBetween;
//                 count++;
//             }
//         }
//     }

//     return count > 0 ? totalDays / count : 0;
//     }
//     //print current capacities for all warehouses
//     public void printAllCurrentCapacities(ArrayList<Warehouse> warehouses) {
//         for (Warehouse warehouse : warehouses) {
//             System.out.println(warehouse.getName() + " currently has a capacity of " + warehouse.getCurrentCapacity() + " and is currently at " + warehouse.getCurrentStockLevel() + " items");
//         }
//     }

    



// // COMPARING TWO DATES:
//     // // Assuming inspections is your List of Inspection objects
// // Collections.sort(inspections, new Comparator<Inspection>() {
//   //   public int compare(Inspection i1, Inspection i2) {
//  //        return i2.getDate().compareTo(i1.getDate());
//  ///   }
// //});

// // // Now the first item in the list is the most recent inspection
// // Inspection lastInspection = inspections.get(0);
    
// }
