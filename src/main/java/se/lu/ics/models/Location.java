package se.lu.ics.models;
public enum Location {
    NORTH,
    MIDDLE,
    SOUTH;

    // Implement this with GUI to be able to update warehouse location:
    public static Location fromString(String string) {
        switch (string.toLowerCase()) {
            case "north":
                return NORTH;
            case "middle":
                return MIDDLE;
            case "south":
                return SOUTH;
            default:
                throw new IllegalArgumentException("Unknown location: " + string);
        }
    }

    
   
}

