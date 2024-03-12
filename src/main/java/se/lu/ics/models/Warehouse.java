package se.lu.ics.models;
import java.time.LocalDate;
import java.time.Period;
import java.util.Observable;

import javafx.collections.ObservableList;


public class Warehouse {

    private String name;
    private Location location;
    private String address;
    private double capacity;
    private double currentStockLevel; 
    private LocalDate mostRecentInspectionDate; 
    private double currentAvailableCapacity;
    private double remainingCapacityInPercent;
    private String averageTimeShipmentSpendsAtWarehouse;
    private ObservableList<String> inspectors;

    public static final String CAPACITY_MUST_BE_GREATER_THAN_0 = "Error: Capacity must be greater than 0";

    public Warehouse(String name, Location location, String address, double capacity) throws Exception {
        this.name = name;
        this.location = location;
        this.address = address;
        setCapacity(capacity);
        this.currentStockLevel = 0;
        this.mostRecentInspectionDate = null;
        this.currentAvailableCapacity = capacity;
        this.remainingCapacityInPercent = 100;
        this.averageTimeShipmentSpendsAtWarehouse = null;

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
            throw new Exception(CAPACITY_MUST_BE_GREATER_THAN_0);
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

    public LocalDate getMostRecentInspectionDate() {
        return mostRecentInspectionDate;
    }

    public void setMostRecentInspectionDate(LocalDate mostRecentInspectionDate) {
        this.mostRecentInspectionDate = mostRecentInspectionDate;
    }

    public String getRemainingCapacityInPercent() {
        return remainingCapacityInPercent + "%";
    }

    public void setRemainingCapacityInPercent(double remainingCapacity) {
        this.remainingCapacityInPercent = remainingCapacity;
    }

    public double getCurrentAvailableCapacity() {
        return currentAvailableCapacity;
    }

    public void setCurrentAvailableCapacity(double currentAvailableCapacity) {
        this.currentAvailableCapacity = currentAvailableCapacity;
    }

    public String getAverageTimeShipmentSpendsAtWarehouse() {
        return averageTimeShipmentSpendsAtWarehouse;
    }

    public void setAverageTimeShipmentSpendsAtWarehouse(String averageTimeShipmentSpendsAtWarehouse) {
        this.averageTimeShipmentSpendsAtWarehouse = averageTimeShipmentSpendsAtWarehouse;
    }

    public ObservableList<String> getInspectors() {
        return DataService.getInstance().getInspectorsForWarehouse(this);
    }

    public void setInspectors(ObservableList<String> inspectors) {
        this.inspectors = inspectors;
    }

    @Override
    public String toString() {
        return name;
    }
}