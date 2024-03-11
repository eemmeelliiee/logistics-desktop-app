package se.lu.ics.models;

import javafx.util.StringConverter;

public class LocationStringConverter extends StringConverter<Location> {
    @Override
    public String toString(Location location) {
        return location != null ? location.toString() : "";
    }

    @Override
    public Location fromString(String string) {
        return Location.fromString(string);
    }
}