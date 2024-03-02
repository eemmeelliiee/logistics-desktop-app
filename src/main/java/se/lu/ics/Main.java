package se.lu.ics;
import se.lu.ics.models.Warehouse;
import se.lu.ics.models.Location;
import java.util.ArrayList;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import se.lu.ics.models.Direction;
import se.lu.ics.models.Inspection;
import se.lu.ics.models.ShipmentLog;
import se.lu.ics.models.Shipment;


public class Main {
    public static void main(String[] args) {
        // Creating warehouse objects:
      Warehouse warehouse1 = new Warehouse("Odin's Vault", Location.NORTH, "Valhalla", 1000.0);
      Warehouse warehouse2 = new Warehouse("Asgard", Location.SOUTH, "Bifrost", 1000.0);
      Warehouse warehouse3 = new Warehouse("Midgard", Location.MIDDLE, "Earth", 1000.0);

        // Creating shipment objects:
      Shipment shipment1 = new Shipment("123", true, "label");
      Shipment shipment2 = new Shipment("456", true, "label");
      Shipment shipment3 = new Shipment("789", true, "label");

      Inspection inspection1 = new Inspection(shipment1, warehouse1, LocalDate.now(), "Thor", "OK");
        Inspection inspection2 = new Inspection(shipment2, warehouse1, LocalDate.of(2021,02,06), "Loki", "OK");
        Inspection inspection3 = new Inspection(shipment3, warehouse1, LocalDate.of(2021,02,07), "Odin", "OK");
        Inspection inspection4 = new Inspection(shipment1, warehouse1, LocalDate.of(2021,02,21), "Frigg", "OK");
        Inspection inspection5 = new Inspection(shipment2, warehouse2, LocalDate.of(2021,02,22), "Heimdall", "OK");

        // Creating shipmentLog objects:
        // shipmentlogs are added to the shipment and warehouse objects inside the constructor for ShipmentLog (add-methods declared in Warehouse and Shipment))
      ShipmentLog shipmentLog1 = new ShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
      ShipmentLog shipmentLog2 = new ShipmentLog(LocalDate.of(2021,02,06), Direction.INCOMING, warehouse1, shipment2);
      ShipmentLog shipmentLog3 = new ShipmentLog(LocalDate.of(2021,02,07), Direction.INCOMING, warehouse1, shipment3);
      ShipmentLog shipmentLog4 = new ShipmentLog(LocalDate.of(2021,02,21), Direction.OUTGOING, warehouse1, shipment1);
        ShipmentLog shipmentLog5 = new ShipmentLog(LocalDate.of(2021,02,22), Direction.OUTGOING, warehouse1, shipment2);

// System.out.println(ShipmentLog.shipmentHistory(shipment1));
//System.out.println("warehouse list: " + warehouse1.getShipments());

    double currentCapacityWarehouse1 = warehouse1.getCurrentCapacity(warehouse1.getCapacity(),warehouse1.getCurrentStockLevel());

  //  System.out.println("Current capacity of warehouse " + warehouse1.getName() + ": " + currentCapacityWarehouse1);
  //  System.out.println("Current stock level of warehouse " + warehouse1.getName() + ": " + warehouse1.getCurrentStockLevel());
   // System.out.println("Used capacity of warehouse " + warehouse1.getName() + ": " + warehouse1.getUsedCapacity());
  //  System.out.println("Capacity of warehouse " + warehouse1.getName() + ": " + warehouse1.getCapacity());

    System.out.println("\nShipments in warehouse " + warehouse1.getName() + ": " + warehouse1.getShipments());
    System.out.println("\nInspections in warehouse " + warehouse1.getName() + ": " + warehouse1.getInspections());

    }
}