package io.quos.codeshift.airport.domain

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

@Entity
class Gate implements GormEntity<Gate> {
    String code
    String name
    Flight flight
    String screenId
    static constraints = {
        flight  nullable : true
        screenId  nullable : true
    }

}
