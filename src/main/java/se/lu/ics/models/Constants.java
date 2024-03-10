package se.lu.ics.models;

public class Constants {
// alla måste vara public static final
    public static final String NO_ROW_SELECTED = "Error: No row selected";
    public static final String CANNOT_BE_EMPTY = "Error: Field cannot be empty";
    public static final String INVALID_FIELD = "Error: Invalid field";
    public static final String INVALID_DATE = "Error: Invalid date";
	public static final String NO_SHIPMENT_LOGS_EXIST_FOR_SHIPMENT = "Error: No shipmentlogs exist for the given shipment";

    // Warehouse
    public static final String CAPACITY_MUST_BE_GREATER_THAN_0 = "Error: Capacity must be greater than 0!";

    // ShipmentLogHandler
    public static final String SHIPMENT_IS_AT_OTHER_WAREHOUSE = "Error: Shipment is currently incoming at another warehouse";
    public static final String SHIPMENT_ALREADY_INCOMING_ON_THIS_WAREHOUSE = "Error: Shipment is already incoming at this wareshouse";
    public static final String SAME_SHIPMENT = "Error: ShipmentLog already has this shipment";
    public static final String SAME_WAREHOUSE = "Error: ShipmentLog already has this warehouse";
    public static final String SAME_DIRECTION = "Error: ShipmentLog already has this direction";
    public static final String SAME_DATE = "Error: ShipmentLog already has this date";


    // Shipment
    public static final String ALREADY_EXISTS_SHIPMENT_WITH_ID = "Error: A shipment with given ID already exists";
    



}
