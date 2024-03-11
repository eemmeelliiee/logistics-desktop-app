package se.lu.ics.models;

import java.util.ArrayList;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InspectionLogHandler {

    private ObservableList<InspectionLog> inspectionLogs;
    private static InspectionLogHandler instance;

    private InspectionLogHandler() {
        inspectionLogs = FXCollections.observableList(new ArrayList<>());

    }

    public static InspectionLogHandler getInstance() {
        if (instance == null) {
            instance = new InspectionLogHandler();
        }
        return instance;
    }

    public ObservableList<InspectionLog> getInspectionLogs() {
        return inspectionLogs;
    }

    public InspectionLog createInspectionLog(Shipment shipment, Warehouse warehouse, LocalDate date, String inspector,
            String result) {
        InspectionLog inspectionLog = new InspectionLog(shipment, warehouse, date, inspector, result);
        inspectionLogs.add(inspectionLog);
        return inspectionLog;
    }

    public void updateInspectionLog(InspectionLog inspectionLog, UpdateFieldInspectionLog field, Object newValue)
            throws Exception {
        if (newValue == null || newValue.equals("")) {
            throw new Exception(Constants.CANNOT_BE_EMPTY);
        }
        switch (field) {
            case SHIPMENT:
                updateInspectionLogShipment(inspectionLog, (Shipment) newValue);
                break;
            case WAREHOUSE:
                updateInspectionLogWarehouse(inspectionLog, (Warehouse) newValue);
                break;
            case DATE:
                updateInspectionLogDate(inspectionLog, (LocalDate) newValue);
                break;
            case INSPECTOR:
                updateInspectionLogInspector(inspectionLog, (String) newValue);
                break;
            case RESULT:
                updateInspectionLogResult(inspectionLog, (String) newValue);
                // break;
                // default:
                // throw new Exception(Constants.INVALID_FIELD);
        }
    }

    private void updateInspectionLogShipment(InspectionLog inspectionLog, Shipment newShipment) {
        inspectionLog.setShipment(newShipment);
    }

    private void updateInspectionLogWarehouse(InspectionLog inspectionLog, Warehouse newWarehouse) {
        inspectionLog.setWarehouse(newWarehouse);
    }

    private void updateInspectionLogDate(InspectionLog inspectionLog, LocalDate newDate) {
        inspectionLog.setDate(newDate);
    }

    private void updateInspectionLogInspector(InspectionLog inspectionLog, String newInspector) {
        inspectionLog.setInspector(newInspector);
    }

    private void updateInspectionLogResult(InspectionLog inspectionLog, String newResult) {
        inspectionLog.setResult(newResult);
    }

    public void deleteInspectionLog(InspectionLog inspectionLog) {
        inspectionLogs.remove(inspectionLog);
        // Shipment shipment = inspectionLog.getShipment();
        // if (shipment != null) {
        //     boolean hasOtherInspectionLogs = false;
        //     for (InspectionLog log : inspectionLogs) {
        //         if (log.getShipment() == shipment) {
        //             hasOtherInspectionLogs = true;
        //             break;
        //         }
        //     }
        //     if (!hasOtherInspectionLogs) {
        //         shipment.setInspected(false);
        //     }
        
    }

    public void clearData() {
        inspectionLogs.clear();
    }

}
