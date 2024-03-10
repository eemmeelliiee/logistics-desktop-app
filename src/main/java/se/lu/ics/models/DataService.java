package se.lu.ics.models;

import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;

public class DataService {

    private static DataService instance;
    private WarehouseHandler warehouseHandler;
    private InspectionLogHandler inspectionLogHandler;
    private ShipmentLogHandler shipmentLogHandler;


    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

    private DataService() {
        warehouseHandler = WarehouseHandler.getInstance();
        inspectionLogHandler = InspectionLogHandler.getInstance();
        shipmentLogHandler = ShipmentLogHandler.getInstance();
    }

    // Get ShipmentLogs for Warehouse and Shipment

    public ObservableList<ShipmentLog> getShipmentLogsForWarehouse(Warehouse warehouse) {
        ObservableList<ShipmentLog> shipmentLogsForWarehouse = FXCollections.observableArrayList();
        for (ShipmentLog shipmentLog : shipmentLogHandler.getShipmentLogs()) {
            if (shipmentLog.getWarehouse().equals(warehouse)) {
                shipmentLogsForWarehouse.add(shipmentLog);
            }
        }
        return shipmentLogsForWarehouse;
    }

    public ObservableList<ShipmentLog> getShipmentLogsForShipment(Shipment shipment) {
        ObservableList<ShipmentLog> shipmentLogsForShipment = FXCollections.observableArrayList();
        for (ShipmentLog shipmentLog : shipmentLogHandler.getShipmentLogs()) {
            if (shipmentLog.getShipment().equals(shipment)) {
                shipmentLogsForShipment.add(shipmentLog);
            }
        }
        return shipmentLogsForShipment;
    }

    // Get InspectionLogs for Warehouse and Shipment

    public ObservableList<InspectionLog> getInspectionsLogsForShipment(Shipment shipment) {
        ObservableList<InspectionLog> inspectionLogsForShipment = FXCollections.observableArrayList();
        for (InspectionLog inspectionLog : inspectionLogHandler.getInspectionLogs()) {
            if (inspectionLog.getShipment().equals(shipment)) {
                inspectionLogsForShipment.add(inspectionLog);
            }
        }
        return inspectionLogsForShipment;
    }

    public ObservableList<InspectionLog> getInspectionLogsForWarehouse(Warehouse warehouse) {
        ObservableList<InspectionLog> inspectionLogsForWarehouse = FXCollections.observableArrayList();
        for (InspectionLog inspectionLog : inspectionLogHandler.getInspectionLogs()) {
            if (inspectionLog.getWarehouse().equals(warehouse)) {
                inspectionLogsForWarehouse.add(inspectionLog);
            }
        }
        return inspectionLogsForWarehouse;
    }

    // Update Warehouse Information

    public void updateMostRecentInspectionDateForWarehouse(Warehouse warehouse) {
        LocalDate mostRecentInspectionDate = LocalDate.MIN;
        for (InspectionLog inspectionLog : getInspectionLogsForWarehouse(warehouse)) {
            if (inspectionLog.getDate().isAfter(mostRecentInspectionDate)) {
                mostRecentInspectionDate = inspectionLog.getDate();
            }
        }
        warehouse.setMostRecentInspectionDate(mostRecentInspectionDate);
    }

    public void updateWarehouseShipmentInformation(Warehouse warehouse) {
        updateStockLevelForWarehouse(warehouse);
        updateCurrentAvailableCapacityForWarehouse(warehouse);
        updateRemainingCapacityInPercentForWarehouse(warehouse);
        updateAverageTimeShipmentSpendsAtWarehouse(warehouse);
    }

    private void updateStockLevelForWarehouse(Warehouse warehouse) {
        double currentStockLevel = 0;
        for (ShipmentLog shipmentLog : getShipmentLogsForWarehouse(warehouse)) {
            if (shipmentLog.getDirection().equals(Direction.INCOMING)) {
                currentStockLevel++;
            } else {
                currentStockLevel--;
            }
        }
        warehouse.setCurrentStockLevel(currentStockLevel);
    }

    private void updateCurrentAvailableCapacityForWarehouse(Warehouse warehouse) {
        double currentAvailableCapacity = warehouse.getCapacity();
        double currentStockLevel = warehouse.getCurrentStockLevel();
        warehouse.setCurrentAvailableCapacity(currentAvailableCapacity - currentStockLevel);
    }

    private void updateRemainingCapacityInPercentForWarehouse(Warehouse warehouse) {
        if (warehouse.getCurrentAvailableCapacity() != 0) {
            double remainingCapacityInPercent = (warehouse.getCurrentAvailableCapacity() / warehouse.getCapacity())
                    * 100;
            warehouse.setRemainingCapacityInPercent(remainingCapacityInPercent);
        } else {
            warehouse.setRemainingCapacityInPercent(0);
        }
    }

    public void updateAverageTimeShipmentSpendsAtWarehouse(Warehouse warehouse) {
        int totalDays = 0;
        int numberOfShipments = 0;
        for (ShipmentLog shipmentLog : getShipmentLogsForWarehouse(warehouse)) {
            if (shipmentLog.getDirection().equals(Direction.INCOMING)) {
                for (ShipmentLog shipmentLog2 : getShipmentLogsForShipment(shipmentLog.getShipment())) {
                    if (shipmentLog2.getDirection().equals(Direction.OUTGOING)) {
                        totalDays += Period.between(shipmentLog.getDate(), shipmentLog2.getDate()).getDays();
                        numberOfShipments++;
                    }
                }
            }
        }
        if (numberOfShipments != 0) {
            Period averageTimeShipmentSpendsAtWarehouse = Period.ofDays(totalDays / numberOfShipments);
            warehouse.setAverageTimeShipmentSpendsAtWarehouse(averageTimeShipmentSpendsAtWarehouse);
        } else {
            warehouse.setAverageTimeShipmentSpendsAtWarehouse(Period.ofDays(0));
            // No shipments exist for warehouse
        }

    }

    // Update Shipment Information

    public void updateShipmentInformation(Shipment shipment) {
        updateCurrentWarehouseForShipment(shipment);
        updateTotalNumberOfWarehousesForShipment(shipment);
    }

    private void updateCurrentWarehouseForShipment(Shipment shipment) {
        Warehouse currentWarehouse = null;
        ObservableList<ShipmentLog> shipmentLogs = getShipmentLogsForShipment(shipment);
        if (!shipmentLogs.isEmpty()) {
            for (ShipmentLog shipmentLog : shipmentLogs) {
                if (shipmentLog.getDirection().equals(Direction.INCOMING)) {
                    currentWarehouse = shipmentLog.getWarehouse();
                }
            }
        }
        shipment.setCurrentWarehouse(currentWarehouse);
    }

    private void updateTotalNumberOfWarehousesForShipment(Shipment shipment) {
        int totalNumberOfWarehouses = 0;
        ObservableList<ShipmentLog> shipmentLogs = getShipmentLogsForShipment(shipment);
        if (!shipmentLogs.isEmpty()) {
            for (ShipmentLog shipmentLog : shipmentLogs) {
                if (shipmentLog.getDirection().equals(Direction.INCOMING)) {
                    totalNumberOfWarehouses++;
                }
            }
        }
        shipment.setTotalNumberOfWarehouses(totalNumberOfWarehouses);
    }

    // public String findBusiestWarehouses() {
    // List<Warehouse> busiestWarehouses = new ArrayList<>();
    // double highestStockLevelRatio = 0;
    // ObservableList<Warehouse> warehouses = WarehouseHandler.getInstance().getWarehouses();

    // for (Warehouse warehouse : warehouses) {
    //     double currentStockLevel = warehouse.getCurrentStockLevel();
    //     double totalCapacity = warehouse.getCapacity();
    //     double stockLevelRatio = currentStockLevel / totalCapacity;
    //     if (stockLevelRatio > highestStockLevelRatio) {
    //         highestStockLevelRatio = stockLevelRatio;
    //         busiestWarehouses.clear();
    //         busiestWarehouses.add(warehouse);
    //     } else if (stockLevelRatio == highestStockLevelRatio) {
    //         busiestWarehouses.add(warehouse);
    //     }
    // }

    // if (busiestWarehouses.isEmpty()) {
    //     return Constants.NO_WAREHOUSES_EXIST;
    // } else {
    //     StringBuilder busiestWarehousesNames = new StringBuilder();
    //     for (Warehouse warehouse : busiestWarehouses) {
    //         if (busiestWarehousesNames.length() > 0) {
    //             busiestWarehousesNames.append(", ");
    //         }
    //         busiestWarehousesNames.append(warehouse.getName());
    //     }
    //     String isOrAre = busiestWarehouses.size() > 1 ? "are" : "is";
    //     return "Busiest warehouse(s) " + isOrAre + ": " + busiestWarehousesNames.toString() + ", using " + (highestStockLevelRatio * 100)
    //             + "% of capacity";
    // }
    // }

    // public String findBusiestWarehouse() {
    //     Warehouse busiestWarehouse = null;
    //     double highestStockLevelRatio = 0;
    //     ObservableList<Warehouse> warehouses = WarehouseHandler.getInstance().getWarehouses();
    
    //     for (Warehouse warehouse : warehouses) {
    //         double currentStockLevel = warehouse.getCurrentStockLevel();
    //         double totalCapacity = warehouse.getCapacity();
    //         double stockLevelRatio = currentStockLevel / totalCapacity;
    //         if (stockLevelRatio > highestStockLevelRatio) {
    //             highestStockLevelRatio = stockLevelRatio;
    //             busiestWarehouse = warehouse;
    //         }
    //         if (getShipmentLogsForWarehouse(busiestWarehouse).isEmpty()) {
    //             return Constants.NO_SHIPMENT_LOGS_EXIST;
    //         }
    //     }
    //     if (busiestWarehouse == null) {
    //         return Constants.NO_WAREHOUSES_EXIST;
    //     } else {
    //         String busiestWarehouseName = busiestWarehouse.getName();
    //         return "Busiest warehouse is: " + busiestWarehouseName + ", using " + (highestStockLevelRatio * 100)
    //                 + "% of capacity";
    //     }
    // }

    // public String findBusiestWarehouse() {
    //     List<Warehouse> busiestWarehouses = new ArrayList<>();
    //     double highestStockLevelRatio = 0;
    //     ObservableList<Warehouse> warehouses = WarehouseHandler.getInstance().getWarehouses();
    
    //     for (Warehouse warehouse : warehouses) {
    //         double currentStockLevel = warehouse.getCurrentStockLevel();
    //         double totalCapacity = warehouse.getCapacity();
    //         double stockLevelRatio = currentStockLevel / totalCapacity;
    //         if (stockLevelRatio > highestStockLevelRatio) {
    //             highestStockLevelRatio = stockLevelRatio;
    //             busiestWarehouses.clear();
    //             busiestWarehouses.add(warehouse);
    //         } else if (stockLevelRatio == highestStockLevelRatio) {
    //             busiestWarehouses.add(warehouse);
    //         }
    //     }
    
    //     if (busiestWarehouses.isEmpty()) {
    //         return Constants.NO_WAREHOUSES_EXIST;
    //     } else {
    //         StringBuilder busiestWarehousesNames = new StringBuilder();
    //         for (Warehouse warehouse : busiestWarehouses) {
    //             if (getShipmentLogsForWarehouse(warehouse).isEmpty()) {
    //                 return Constants.NO_SHIPMENT_LOGS_EXIST;
    //             }
    //             if (busiestWarehousesNames.length() > 0) {
    //                 busiestWarehousesNames.append(", ");
    //             }
    //             busiestWarehousesNames.append(warehouse.getName());
    //         }
    //         String isOrAre = busiestWarehouses.size() > 1 ? "s are" : " is";
    //         return "Busiest warehouse" + isOrAre + ": " + busiestWarehousesNames.toString() + ", using " + (highestStockLevelRatio * 100)
    //                 + "% of capacity";
    //     }
    // }
    public String findBusiestWarehouse() {
        List<Warehouse> busiestWarehouses = new ArrayList<>();
        double highestStockLevelRatio = 0;
        ObservableList<Warehouse> warehouses = warehouseHandler.getWarehouses();
    
        for (Warehouse warehouse : warehouses) {
            double currentStockLevel = warehouse.getCurrentStockLevel();
            double totalCapacity = warehouse.getCapacity();
            double stockLevelRatio = currentStockLevel / totalCapacity;
            if (stockLevelRatio > highestStockLevelRatio) {
                highestStockLevelRatio = stockLevelRatio;
                busiestWarehouses.clear();
                busiestWarehouses.add(warehouse);
            } else if (stockLevelRatio == highestStockLevelRatio) {
                busiestWarehouses.add(warehouse);
            }
        }
    
        if (highestStockLevelRatio == 0) {
            return Constants.NO_SHIPMENT_LOGS_EXIST;
        }
    
        if (busiestWarehouses.isEmpty()) {
            return Constants.NO_WAREHOUSES_EXIST;
        } else {
            StringBuilder busiestWarehousesNames = new StringBuilder();
            for (Warehouse warehouse : busiestWarehouses) {
                if (busiestWarehousesNames.length() > 0) {
                    busiestWarehousesNames.append(", ");
                }
                busiestWarehousesNames.append(warehouse.getName());
            }
            String isOrAre = busiestWarehouses.size() > 1 ? "s are" : " is";
            return "Busiest warehouse" + isOrAre + ": " + busiestWarehousesNames.toString() + ", using " + (highestStockLevelRatio * 100)
                    + "% of capacity";
        }
    }

    public ObservableList<Pair<Location, Double>> getCurrentAvailableCapacityForLocations() {
        ObservableList<Pair<Location, Double>> capacityForLocations = FXCollections.observableArrayList();

        for (Location location : Location.values()) {
            double currentAvailableCapacity = 0;
            for (Warehouse warehouse : warehouseHandler.getWarehouses()) {
                if (warehouse.getLocation().equals(location)) {
                    currentAvailableCapacity += warehouse.getCurrentAvailableCapacity();
                }
            }
            capacityForLocations.add(new Pair<>(location, currentAvailableCapacity));
        }

        return capacityForLocations;
    }

}
