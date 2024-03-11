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

    @BeforeEach
    public void setUp() throws Exception {
        dataManager = DataManager.getInstance();
    }

    @AfterEach
    public void tearDown() {
        dataManager.clearData(); // Assuming InspectionLogHandler has a clearData method
    }

    @Test
    public void testCreateInspectionLog() throws Exception{
        // Arrange
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        String inspector = "Inspector";
        String result = "Result";

        // Act
        InspectionLog inspectionLog = dataManager.createInspectionLog(shipment, warehouse, date, inspector, result);

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
        ObservableList<InspectionLog> returnedInspectionLogs = dataManager.readInspectionLogs();

        // Assert
        assertEquals(inspectionLogs, returnedInspectionLogs);
    }

    @Test
    public void testUpdateInspectionLog() throws Exception {
        // Arrange
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        String inspector = "Inspector";
        String result = "Result";
        InspectionLog inspectionLog = dataManager.createInspectionLog(shipment, warehouse, date, inspector, result);
        UpdateFieldInspectionLog field = UpdateFieldInspectionLog.RESULT;
        String newValue = "New Result";

        // Act
        dataManager.updateInspectionLog(inspectionLog, field, newValue);

        // Assert
        assertEquals(newValue, inspectionLog.getResult());
    }

    @Test
    public void testDeleteInspectionLog() throws Exception {
        // Arrange
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        String inspector = "Inspector";
        String result = "Result";
        InspectionLog inspectionLog = dataManager.createInspectionLog(shipment, warehouse, date, inspector, result);

        // Act
        dataManager.deleteInspectionLog(inspectionLog);

        // Assert
        assertFalse(dataManager.readInspectionLogs().contains(inspectionLog));
    }

    @Test
    public void testUpdateInspectionLogShipment() throws Exception{
        // Arrange
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        String inspector = "Inspector";
        String result = "Result";
        InspectionLog inspectionLog = dataManager.createInspectionLog(shipment, warehouse, date, inspector, result);
        Shipment newShipment = new Shipment();

        // Act
        dataManager.updateInspectionLog(inspectionLog, UpdateFieldInspectionLog.SHIPMENT, newShipment);

        // Assert
        assertEquals(newShipment, inspectionLog.getShipment());
    }

    @Test
    public void testUpdateInspectionLogWarehouse() throws Exception{
        // Arrange
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        String inspector = "Inspector";
        String result = "Result";
        InspectionLog inspectionLog = dataManager.createInspectionLog(shipment, warehouse, date, inspector, result);
        Warehouse newWarehouse = new Warehouse("New Warehouse", Location.MIDDLE, "New Address", 2000);

        // Act
        dataManager.updateInspectionLog(inspectionLog, UpdateFieldInspectionLog.WAREHOUSE, newWarehouse);

        // Assert
        assertEquals(newWarehouse, inspectionLog.getWarehouse());
    }

    @Test
    public void testUpdateInspectionLogDate() throws Exception{
        // Arrange
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        String inspector = "Inspector";
        String result = "Result";
        InspectionLog inspectionLog = dataManager.createInspectionLog(shipment, warehouse, date, inspector, result);
        LocalDate newDate = LocalDate.now().minusDays(1);

        // Act
       dataManager.updateInspectionLog(inspectionLog, UpdateFieldInspectionLog.DATE, newDate);

        // Assert
        assertEquals(newDate, inspectionLog.getDate());
    }

    @Test
    public void testUpdateInspectionLogInspector() throws Exception{
        // Arrange
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        String inspector = "Inspector";
        String result = "Result";
        InspectionLog inspectionLog = dataManager.createInspectionLog(shipment, warehouse, date, inspector, result);
        String newInspector = "New Inspector";

        // Act
        dataManager.updateInspectionLog(inspectionLog, UpdateFieldInspectionLog.INSPECTOR, newInspector);

        // Assert
        assertEquals(newInspector, inspectionLog.getInspector());
    }

    @Test
    public void testUpdateInspectionLogResult() throws Exception{
        // Arrange
        Shipment shipment = dataManager.createShipment();
        Warehouse warehouse = dataManager.createWarehouse("Test Warehouse", Location.MIDDLE, "Test Address", 1000);
        LocalDate date = LocalDate.now();
        String inspector = "Inspector";
        String result = "Result";
        InspectionLog inspectionLog = dataManager.createInspectionLog(shipment, warehouse, date, inspector, result);
        String newResult = "New Result";

        // Act
        dataManager.updateInspectionLog(inspectionLog, UpdateFieldInspectionLog.RESULT, newResult);

        // Assert
        assertEquals(newResult, inspectionLog.getResult());
    }
}