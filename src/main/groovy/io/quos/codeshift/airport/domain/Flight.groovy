package io.quos.codeshift.airport.domain

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

@Entity
class Flight implements GormEntity<Flight> {
    String flightNumber
    String flightNumberOperating
    String arrivalDate
    String departureDate
    String newDepartureDate
    Airport origin
    Airport destination
    FlightStatus status
    static constraints = {
        newDepartureDate nullable:true
        flightNumberOperating nullable:true
    }
}
