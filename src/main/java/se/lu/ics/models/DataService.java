package se.lu.ics.models;

import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.Period;

import javafx.collections.FXCollections;

public class DataService {

    private static DataManager dataManager;

    public DataService() {
        dataManager = DataManager.getInstance();
    }

    public static void updateWarehouseInformation(Warehouse warehouse){
        updateStockLevelForWarehouse(warehouse);
        updateMostRecentInspectionDateForWarehouse(warehouse);
        updateCurrentAvailableCapacityForWarehouse(warehouse);
        updateRemainingCapacityInPercentForWarehouse(warehouse);
        updateAverageTimeShipmentSpendsAtWarehouse(warehouse);
    }

    public static ObservableList<ShipmentLog> getShipmentLogsForWarehouse(Warehouse warehouse){
        ObservableList<ShipmentLog> shipmentLogsForWarehouse = FXCollections.observableArrayList();
        for (ShipmentLog shipmentLog : dataManager.getShipmentLogHandler().getShipmentLogs()){
            if (shipmentLog.getWarehouse().equals(warehouse)){
                shipmentLogsForWarehouse.add(shipmentLog);
            }
        } return shipmentLogsForWarehouse;
    }

    public static ObservableList<ShipmentLog> getShipmentLogsForShipment(Shipment shipment){
        ObservableList<ShipmentLog> shipmentLogsForShipment = FXCollections.observableArrayList();
        for (ShipmentLog shipmentLog : dataManager.getShipmentLogHandler().getShipmentLogs()){
            if (shipmentLog.getShipment().equals(shipment)){
                shipmentLogsForShipment.add(shipmentLog);
            }
        } return shipmentLogsForShipment;
    }

    public static ObservableList<InspectionLog> getInspectionsLogsForShipment(Shipment shipment){
        ObservableList<InspectionLog> inspectionLogsForShipment = FXCollections.observableArrayList();
        for (InspectionLog inspectionLog : dataManager.getInspectionLogHandler().getInspectionLogs()){
            if (inspectionLog.getShipment().equals(shipment)){
                inspectionLogsForShipment.add(inspectionLog);
            }  
        } return inspectionLogsForShipment;
    }

    public static ObservableList<InspectionLog> getInspectionLogsForWarehouse(Warehouse warehouse){
        ObservableList<InspectionLog> inspectionLogsForWarehouse = FXCollections.observableArrayList();
        for (InspectionLog inspectionLog : dataManager.getInspectionLogHandler().getInspectionLogs()){
            if (inspectionLog.getWarehouse().equals(warehouse)){
                inspectionLogsForWarehouse.add(inspectionLog);
            }
        } return inspectionLogsForWarehouse;
    }

    private static void updateStockLevelForWarehouse(Warehouse warehouse){
        double currentStockLevel = 0;
        for (ShipmentLog shipmentLog : getShipmentLogsForWarehouse(warehouse)){
            if (shipmentLog.getDirection().equals(Direction.INCOMING)){
                currentStockLevel++;
            } else {
                currentStockLevel--;
            }
        }
        warehouse.setCurrentStockLevel(currentStockLevel);
    }

    private static void updateMostRecentInspectionDateForWarehouse(Warehouse warehouse){
        LocalDate mostRecentInspectionDate = LocalDate.MIN;
        for (InspectionLog inspectionLog : getInspectionLogsForWarehouse(warehouse)){
            if (inspectionLog.getDate().isAfter(mostRecentInspectionDate)){
                mostRecentInspectionDate = inspectionLog.getDate();
            }
        }
        warehouse.setMostRecentInspectionDate(mostRecentInspectionDate);
    }

    private static void updateCurrentAvailableCapacityForWarehouse(Warehouse warehouse){
        double currentAvailableCapacity = warehouse.getCapacity();
        double currentStockLevel = warehouse.getCurrentStockLevel();
        warehouse.setCurrentAvailableCapacity(currentAvailableCapacity-currentStockLevel);
    }

    private static void updateRemainingCapacityInPercentForWarehouse(Warehouse warehouse){
        if (warehouse.getCurrentAvailableCapacity() != 0){
            double remainingCapacityInPercent = (warehouse.getCurrentAvailableCapacity()/warehouse.getCapacity())*100;
            warehouse.setRemainingCapacityInPercent(remainingCapacityInPercent);
        } else {
            warehouse.setRemainingCapacityInPercent(0);
        }
    }

    private static void updateAverageTimeShipmentSpendsAtWarehouse(Warehouse warehouse){
        int totalDays = 0;
        int numberOfShipments = 0;
        for (ShipmentLog shipmentLog : getShipmentLogsForWarehouse(warehouse)){
            if (shipmentLog.getDirection().equals(Direction.INCOMING)){
                for (ShipmentLog shipmentLog2 : getShipmentLogsForShipment(shipmentLog.getShipment())){
                    if (shipmentLog2.getDirection().equals(Direction.OUTGOING)){
                        totalDays += Period.between(shipmentLog.getDate(), shipmentLog2.getDate()).getDays();
                        numberOfShipments++;
                    }
                }
            }
        }
        if (numberOfShipments != 0){
            Period averageTimeShipmentSpendsAtWarehouse = Period.ofDays(totalDays / numberOfShipments);
            warehouse.setAverageTimeShipmentSpendsAtWarehouse(averageTimeShipmentSpendsAtWarehouse);
        } else {
            warehouse.setAverageTimeShipmentSpendsAtWarehouse(Period.ofDays(0));
            // No shipments exist for warehouse
        }
       
    }

    public static ObservableList<Pair<Location, Double>> showCurrentAvailableCapacityForLocations() {
        ObservableList<Pair<Location, Double>> capacityForLocations = FXCollections.observableArrayList();

        for (Location location : Location.values()) {
            double currentAvailableCapacity = 0;
            for (Warehouse warehouse : dataManager.getWarehouseHandler().getWarehouses()) {
                if (warehouse.getLocation().equals(location)) {
                    currentAvailableCapacity += warehouse.getCurrentAvailableCapacity();
                }
            }
            capacityForLocations.add(new Pair<>(location, currentAvailableCapacity));
        }

        return capacityForLocations;
    }

    public String findBusiestWarehouse() {
        Warehouse busiestWarehouse = null;
        double highestStockLevelRatio = 0;
        ObservableList<Warehouse> warehouses = dataManager.getWarehouseHandler().getWarehouses();
    
        for (Warehouse warehouse : warehouses){
            double currentStockLevel = warehouse.getCurrentStockLevel();
            double totalCapacity = warehouse.getCapacity();
            double stockLevelRatio = currentStockLevel / totalCapacity;
            if (stockLevelRatio > highestStockLevelRatio) {
                highestStockLevelRatio = stockLevelRatio;
                busiestWarehouse = warehouse;
            }
        }
        String busiestWarehouseName = busiestWarehouse != null ? busiestWarehouse.getName() : "None";
        return "Busiest warehouse is: " + busiestWarehouseName + ", using " + (highestStockLevelRatio*100) + "% of capacity";
    }

    public void updateCurrentWarehouseForShipment(Shipment shipment) {
        Warehouse currentWarehouse = null;
        ObservableList<ShipmentLog> shipmentLogs = getShipmentLogsForShipment(shipment);
        if (!shipmentLogs.isEmpty()) {
            for (ShipmentLog shipmentLog : shipmentLogs){
                if (shipmentLog.getDirection().equals(Direction.INCOMING)){
                    currentWarehouse = shipmentLog.getWarehouse();
                }
            }
        }
        shipment.setCurrentWarehouse(currentWarehouse);
    }

    public void updateTotalNumberOfWarehousesForShipment(Shipment shipment) {
        double totalNumberOfWarehouses = 0;
        ObservableList<ShipmentLog> shipmentLogs = getShipmentLogsForShipment(shipment);
        if (!shipmentLogs.isEmpty()) {
            for (ShipmentLog shipmentLog : shipmentLogs){
                if (shipmentLog.getDirection().equals(Direction.INCOMING)){
                    totalNumberOfWarehouses++;
                }
            }
        }
        shipment.setTotalNumberOfWarehouses(totalNumberOfWarehouses);
    }
   
} 

   