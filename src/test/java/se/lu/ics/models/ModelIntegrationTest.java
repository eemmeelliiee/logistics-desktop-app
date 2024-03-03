package se.lu.ics.models;
import java.time.LocalDate;

public class ModelIntegrationTest {
    public static void main(String[] args) {
        // Creating warehouse objects:
      Warehouse warehouse1 = new Warehouse("Odin's Vault", Location.NORTH, "Valhalla", 1000.0);
      Warehouse warehouse2 = new Warehouse("Asgard", Location.SOUTH, "Bifrost", 1000.0);
      Warehouse warehouse3 = new Warehouse("Midgard", Location.MIDDLE, "Earth", 1000.0);

        // Creating shipment objects:
      Shipment shipment1 = new Shipment(false, "OK");
      Shipment shipment2 = new Shipment(false, "OK");
      Shipment shipment3 = new Shipment(false, "OK");

    

        // Creating inspection objects:
        // inspections are added to the shipment and warehouse objects inside the constructor for InspectionLog (add-methods declared in Warehouse and Shipment)
  

        InspectionLog inspection1 = new InspectionLog(shipment1, warehouse1, LocalDate.now(), "Thor", "OK");
        InspectionLog inspection2 = new InspectionLog(shipment2, warehouse1, LocalDate.of(2021,02,06), "Loki", "OK");
        InspectionLog inspection3 = new InspectionLog(shipment3, warehouse1, LocalDate.of(2021,02,07), "Odin", "OK");
        InspectionLog inspection4 = new InspectionLog(shipment1, warehouse1, LocalDate.of(2021,02,21), "Frigg", "OK");
        InspectionLog inspection5 = new InspectionLog(shipment2, warehouse2, LocalDate.of(2021,02,22), "Heimdall", "OK");

        // Creating shipmentLog objects:
        // shipmentlogs are added to the shipment and warehouse objects inside the constructor for ShipmentLog (add-methods declared in Warehouse and Shipment))
      ShipmentLog shipmentLog1 = new ShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
      ShipmentLog shipmentLog2 = new ShipmentLog(LocalDate.of(2021,02,06), Direction.INCOMING, warehouse1, shipment2);
      ShipmentLog shipmentLog3 = new ShipmentLog(LocalDate.of(2021,02,07), Direction.INCOMING, warehouse1, shipment3);
      ShipmentLog shipmentLog4 = new ShipmentLog(LocalDate.of(2021,02,21), Direction.OUTGOING, warehouse1, shipment1);
        ShipmentLog shipmentLog5 = new ShipmentLog(LocalDate.of(2021,02,22), Direction.OUTGOING, warehouse1, shipment2);

// System.out.println(ShipmentLog.shipmentHistory(shipment1));
//System.out.println("warehouse list: " + warehouse1.getShipments());

  //  System.out.println("Current capacity of warehouse " + warehouse1.getName() + ": " + currentCapacityWarehouse1);
  //  System.out.println("Current stock level of warehouse " + warehouse1.getName() + ": " + warehouse1.getCurrentStockLevel());
   // System.out.println("Used capacity of warehouse " + warehouse1.getName() + ": " + warehouse1.getUsedCapacity());
  //  System.out.println("Capacity of warehouse " + warehouse1.getName() + ": " + warehouse1.getCapacity());

    System.out.println("\nShipments in warehouse " + warehouse1.getName() + ": " + warehouse1.getShipments());
    System.out.println("\nInspections in warehouse " + warehouse1.getName() + ": " + warehouse1.getInspections());
    System.out.println("\nInspections for shipment " + shipment1.getShipmentId() + ": " + shipment1.getInspectionsMade());

    System.out.println("Shipmentlogs of shipment1:" + shipment1.getShipmentLogs());

    System.out.println("get shipment id for shipment1:" + shipment1.getShipmentId());
    }
}