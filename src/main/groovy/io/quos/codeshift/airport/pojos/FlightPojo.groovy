package io.quos.codeshift.airport.pojos

import io.quos.codeshift.airport.domain.Flight
import io.quos.codeshift.airport.domain.FlightStatus


class FlightPojo {

    FlightPojo(Flight f){
        flightNumber = f.flightNumber
        arrivalDate = f.arrivalDate
        departureDate = f.departureDate
        origin = f.origin
        destination = f.destination
        status = f.status

    }
    String flightNumber
    String arrivalDate
    String departureDate
    String origin
    String destination
    FlightStatus status

    List<BookingPojo> bookings = new ArrayList<>()
}
