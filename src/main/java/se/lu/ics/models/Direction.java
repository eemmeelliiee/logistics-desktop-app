package se.lu.ics.models;

public enum Direction {
    INCOMING,
    OUTGOING;

    Direction opposite() {
        if (this == INCOMING) {
            return OUTGOING;
        } else {
            return INCOMING;
        }
    }
}
