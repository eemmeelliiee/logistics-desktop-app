package se.lu.ics;
import java.time.LocalDate;
import se.lu.ics.models.InspectionLog;
import se.lu.ics.models.Location;
import se.lu.ics.models.Shipment;
import se.lu.ics.models.ShipmentLog;
import se.lu.ics.models.Warehouse;
import se.lu.ics.models.Direction;



public class Main {
    public static void main(String[] args) {
        Warehouse warehouse1 = new Warehouse("Odin's Vault", Location.NORTH, "Valhalla", 1000.0);
    Warehouse warehouse2 = new Warehouse("Asgard", Location.SOUTH, "Bifrost", 1000.0);

    Shipment shipment1 = new Shipment(false, "OK");
    Shipment shipment2 = new Shipment(false, "OK");

    InspectionLog inspection1 = new InspectionLog(shipment1, warehouse1, LocalDate.now(), "Thor", "OK");
    InspectionLog inspection2 = new InspectionLog(shipment2, warehouse1, LocalDate.of(2021,02,06), "Loki", "OK");

    ShipmentLog shipmentLog1 = new ShipmentLog(LocalDate.now(), Direction.INCOMING, warehouse1, shipment1);
    ShipmentLog shipmentLog2 = new ShipmentLog(LocalDate.of(2021,02,06), Direction.INCOMING, warehouse1, shipment2);

       
    }
}