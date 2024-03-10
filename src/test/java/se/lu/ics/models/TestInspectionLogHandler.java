package se.lu.ics.models;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TestInspectionLogHandler {

    private DataManager dataManager;
    private InspectionLogHandler inspectionLogHandler;

    @BeforeEach
    public void setUp() {
        dataManager = DataManager.getInstance();
        inspectionLogHandler = InspectionLogHandler.getInstance();
    }

    @AfterEach
    public void tearDown() {
        inspectionLogHandler.clearData(); // Assuming InspectionLogHandler has a clearData method
    }

    @Test
    public void testCreateInspectionLog() throws Exception{
        // Arrange
        Shipment shipment = new Shipment();
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        String inspector = "Inspector";
        String result = "Result";

        // Act
        InspectionLog inspectionLog = inspectionLogHandler.createInspectionLog(shipment, warehouse, date, inspector, result);

        // Assert
        assertNotNull(inspectionLog);
        assertEquals(shipment, inspectionLog.getShipment());
        assertEquals(warehouse, inspectionLog.getWarehouse());
        assertEquals(date, inspectionLog.getDate());
        assertEquals(inspector, inspectionLog.getInspector());
        assertEquals(result, inspectionLog.getResult());
    }

    @Test
    public void testReadInspectionLogs() {
        // Arrange
        ObservableList<InspectionLog> inspectionLogs = FXCollections.observableArrayList();

        // Act
        ObservableList<InspectionLog> returnedInspectionLogs = DataManager.getInstance().readInspectionLogs();

        // Assert
        assertEquals(inspectionLogs, returnedInspectionLogs);
    }

    @Test
    public void testUpdateInspectionLog() throws Exception {
        // Arrange
        Shipment shipment = new Shipment();
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        String inspector = "Inspector";
        String result = "Result";
        InspectionLog inspectionLog = inspectionLogHandler.createInspectionLog(shipment, warehouse, date, inspector, result);
        UpdateFieldInspectionLog field = UpdateFieldInspectionLog.RESULT;
        String newValue = "New Result";

        // Act
        inspectionLogHandler.updateInspectionLog(inspectionLog, field, newValue);

        // Assert
        assertEquals(newValue, inspectionLog.getResult());
    }

    @Test
    public void testDeleteInspectionLog() throws Exception {
        // Arrange
        Shipment shipment = new Shipment();
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        String inspector = "Inspector";
        String result = "Result";
        InspectionLog inspectionLog = inspectionLogHandler.createInspectionLog(shipment, warehouse, date, inspector, result);

        // Act
        inspectionLogHandler.deleteInspectionLog(inspectionLog);

        // Assert
        assertFalse(DataManager.getInstance().readInspectionLogs().contains(inspectionLog));
    }

    @Test
    public void testUpdateInspectionLogShipment() throws Exception{
        // Arrange
        Shipment shipment = new Shipment();
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        String inspector = "Inspector";
        String result = "Result";
        InspectionLog inspectionLog = inspectionLogHandler.createInspectionLog(shipment, warehouse, date, inspector, result);
        Shipment newShipment = new Shipment();

        // Act
        DataManager.getInstance().updateInspectionLog(inspectionLog, UpdateFieldInspectionLog.SHIPMENT, newShipment);

        // Assert
        assertEquals(newShipment, inspectionLog.getShipment());
    }

    @Test
    public void testUpdateInspectionLogWarehouse() throws Exception{
        // Arrange
        Shipment shipment = new Shipment();
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        String inspector = "Inspector";
        String result = "Result";
        InspectionLog inspectionLog = inspectionLogHandler.createInspectionLog(shipment, warehouse, date, inspector, result);
        Warehouse newWarehouse = new Warehouse("New Warehouse", Location.MIDDLE, "New Address", 2000);

        // Act
        DataManager.getInstance().updateInspectionLog(inspectionLog, UpdateFieldInspectionLog.WAREHOUSE, newWarehouse);

        // Assert
        assertEquals(newWarehouse, inspectionLog.getWarehouse());
    }

    @Test
    public void testUpdateInspectionLogDate() throws Exception{
        // Arrange
        Shipment shipment = new Shipment();
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        String inspector = "Inspector";
        String result = "Result";
        InspectionLog inspectionLog = inspectionLogHandler.createInspectionLog(shipment, warehouse, date, inspector, result);
        LocalDate newDate = LocalDate.now().minusDays(1);

        // Act
        DataManager.getInstance().updateInspectionLog(inspectionLog, UpdateFieldInspectionLog.DATE, newDate);

        // Assert
        assertEquals(newDate, inspectionLog.getDate());
    }

    @Test
    public void testUpdateInspectionLogInspector() throws Exception{
        // Arrange
        Shipment shipment = new Shipment();
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        String inspector = "Inspector";
        String result = "Result";
        InspectionLog inspectionLog = inspectionLogHandler.createInspectionLog(shipment, warehouse, date, inspector, result);
        String newInspector = "New Inspector";

        // Act
        DataManager.getInstance().updateInspectionLog(inspectionLog, UpdateFieldInspectionLog.INSPECTOR, newInspector);

        // Assert
        assertEquals(newInspector, inspectionLog.getInspector());
    }

    @Test
    public void testUpdateInspectionLogResult() throws Exception{
        // Arrange
        Shipment shipment = new Shipment();
        Warehouse warehouse = new Warehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        String inspector = "Inspector";
        String result = "Result";
        InspectionLog inspectionLog = inspectionLogHandler.createInspectionLog(shipment, warehouse, date, inspector, result);
        String newResult = "New Result";

        // Act
        DataManager.getInstance().updateInspectionLog(inspectionLog, UpdateFieldInspectionLog.RESULT, newResult);

        // Assert
        assertEquals(newResult, inspectionLog.getResult());
    }
}