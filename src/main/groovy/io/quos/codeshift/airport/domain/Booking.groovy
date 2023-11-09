package io.quos.codeshift.airport.domain

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

@Entity
class Booking implements GormEntity<Booking> {
    String bookingId
    Flight flight
}
