package io.quos.codeshift.airport.domain

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

@Entity
class Flight implements GormEntity<Flight> {
    String flightNumber
    String arrivalDate
    String departureDate
    Airport origin
    Airport destination
    FlightStatus status
    static constraints = {

    }
}
