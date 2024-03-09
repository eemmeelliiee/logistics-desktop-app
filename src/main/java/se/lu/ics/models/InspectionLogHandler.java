package se.lu.ics.models;

import java.util.ArrayList;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class InspectionLogHandler {

    private ObservableList<InspectionLog> inspectionLogs;

    public InspectionLogHandler() {
        inspectionLogs = FXCollections.observableList(new ArrayList<>());

    }

    public ObservableList<InspectionLog> getInspectionLogs() {
        return inspectionLogs;
    }

    // A setter should not be needed for this. Why give other classes access to that?
    /*
    public void setInspectionLogs()) {
        this.inspectionLogs = inspectionLogs;
    }
    */

    public InspectionLog createInspectionLog(Shipment shipment, Warehouse warehouse, LocalDate date, String inspector, String result) {
        InspectionLog inspectionLog = new InspectionLog (shipment, warehouse, date, inspector, result);
        inspectionLogs.add(inspectionLog);
        return inspectionLog;
    }

    // does the same thing as getInspectionLogs right now, should this return a list of Strings by doing .toString on each inspectionLog?
    // or will that kind of thing be handled in the view?
    // public ObservableList<InspectionLog> readInspectionLogs() {
    //     return inspectionLogs;
    // }

    public void updateInspectionLog(InspectionLog inspectionLog, UpdateFieldInspectionLog field, Object newValue) throws Exception {
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
            //     break;
            // default:
            //     throw new Exception(Constants.INVALID_FIELD);
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
    }

    public void clearData() {
        inspectionLogs.clear();
    }




}
