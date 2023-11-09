package io.quos.codeshift.airport.domain

enum FlightStatus {

    ACTIVE("Active"), DELAYED("Delayed"), CANCELLED("Cancelled"), COMPLETE("Complete")

    public final String name;

    private FlightStatus(String name) {
        this.name = name;
    }

    public static FlightStatus valueForName(String name) {
        for (FlightStatus e : values()) {
            if (e.name.toLowerCase().equals(name.toLowerCase())) {
                return e;
            }
        }
        return null;
    }
}
