package se.lu.ics.models;

import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;

public class DataService {

    private static DataService instance;
    private WarehouseHandler warehouseHandler;
    private InspectionLogHandler inspectionLogHandler;
    private ShipmentLogHandler shipmentLogHandler;

    private static final String NO_SHIPMENT_LOGS_EXIST = "No incoming shipmentlogs exist for any warehouse";
    private static final String NO_WAREHOUSES_EXIST = "No warehouses exist yet";

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

    // << ----------------- Get Information ----------------- >>

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

    // Get Inspectors for Warehouse
    public ObservableList<String> getInspectorsForWarehouse(Warehouse warehouse) {
        ObservableList<String> inspectorsForWarehouse = FXCollections.observableArrayList();
        for (InspectionLog inspectionLog : getInspectionLogsForWarehouse(warehouse)) {
            if (!inspectorsForWarehouse.contains(inspectionLog.getInspector())) {
                inspectorsForWarehouse.add(inspectionLog.getInspector());
            }
        }
        return inspectorsForWarehouse;
    }

    // Get Current available capacity for Location
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

    public String getBusiestWarehouse() {
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
            return NO_SHIPMENT_LOGS_EXIST;
        }

        if (busiestWarehouses.isEmpty()) {
            return NO_WAREHOUSES_EXIST;
        } else {
            StringBuilder busiestWarehousesNames = new StringBuilder();
            for (Warehouse warehouse : busiestWarehouses) {
                if (busiestWarehousesNames.length() > 0) {
                    busiestWarehousesNames.append(", ");
                }
                busiestWarehousesNames.append(warehouse.getName());
            }
            String isOrAre = busiestWarehouses.size() > 1 ? "s are" : " is";
            String formattedPercentage = String.format("%.2f", highestStockLevelRatio * 100);
            return "Busiest warehouse" + isOrAre + ": " + busiestWarehousesNames.toString() + ", using "
                    + formattedPercentage + "% of capacity";
        }
    }

    // <<----------------- Update Information ----------------->>

    // For filling with test data
    public void updateAll() throws Exception {
        List<Warehouse> warehouses = warehouseHandler.getWarehouses();
        for (Warehouse warehouse : warehouses) {
            updateWarehouseShipmentInformation(warehouse);
            updateMostRecentInspectionDateForWarehouse(warehouse);
        }
        List<Shipment> shipments = DataManager.getInstance().readShipments();
        for (Shipment shipment : shipments) {
            updateShipmentInformation(shipment);
        }
    }

    // Update Warehouse Information

    public void updateMostRecentInspectionDateForWarehouse(Warehouse warehouse) {
        if (getInspectionLogsForWarehouse(warehouse).isEmpty()) {
            warehouse.setMostRecentInspectionDate(null);
            return;
        }
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
                if (currentStockLevel > 0) {
                    currentStockLevel--;
                }
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
            remainingCapacityInPercent = Math.round(remainingCapacityInPercent * 100.0) / 100.0; // Round to 2 decimal
                                                                                                 // places
            warehouse.setRemainingCapacityInPercent(remainingCapacityInPercent);
        } else {
            warehouse.setRemainingCapacityInPercent(0);
        }
    }

    public void updateAverageTimeShipmentSpendsAtWarehouse(Warehouse warehouse) {
        int totalDays = 0;
        int numberOfShipments = 0;
        List<ShipmentLog> sortedLogs = getShipmentLogsForWarehouse(warehouse).stream()
                .sorted(Comparator.comparing(ShipmentLog::getDate))
                .collect(Collectors.toList());
        Map<Shipment, ShipmentLog> incomingShipments = new HashMap<>();
        for (ShipmentLog log : sortedLogs) {
            if (log.getDirection().equals(Direction.INCOMING)) {
                incomingShipments.put(log.getShipment(), log);
            } else if (log.getDirection().equals(Direction.OUTGOING)) {
                ShipmentLog incoming = incomingShipments.remove(log.getShipment());
                if (incoming != null) {
                    int days = Period.between(incoming.getDate(), log.getDate()).getDays();
                    if (days >= 0) {
                        totalDays += days;
                        numberOfShipments++;
                    }
                }
            }
        }
        String averageTime;
        if (numberOfShipments > 0) {
            int averageDays = totalDays / numberOfShipments;
            averageTime = averageDays + " day" + (averageDays > 1 ? "s" : "");
        } else {
            averageTime = "-";
        }
        warehouse.setAverageTimeShipmentSpendsAtWarehouse(averageTime);
    }

    // Update Shipment Information

    public void updateShipmentInformation(Shipment shipment) {
        updateCurrentWarehouseForShipment(shipment);
        updateTotalNumberOfWarehousesForShipment(shipment);
        shipmentLogHandler.updateAttentionStatus();
    }

    private void updateCurrentWarehouseForShipment(Shipment shipment) {
        Warehouse currentWarehouse = null;
        ObservableList<ShipmentLog> shipmentLogs = getShipmentLogsForShipment(shipment);
        int incomingCount = 0;
        int outgoingCount = 0;

        if (!shipmentLogs.isEmpty()) {
            for (ShipmentLog shipmentLog : shipmentLogs) {
                if (shipmentLog.getDirection().equals(Direction.INCOMING)) {
                    incomingCount++;
                    currentWarehouse = shipmentLog.getWarehouse();
                } else {
                    outgoingCount++;
                }

                // If there's an outgoing shipment for every incoming one, set currentWarehouse
                // to null
                if (incomingCount == outgoingCount) {
                    currentWarehouse = null;
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

}
