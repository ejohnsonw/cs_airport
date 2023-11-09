package io.quos.codeshift.airport.domain

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

@Entity
class Airport implements GormEntity<Airport> {
    String iataCode
    String name
    Float lat
    Float lon
    City city
    String linkDelayed
    String linkCancelled
    String website
    static constraints = {
        city nullable:true
        linkDelayed nullable:true
        linkCancelled nullable:true
        website nullable:true
    }
    static  hasMany = [flights:Flight, terminals:Terminal]
}
