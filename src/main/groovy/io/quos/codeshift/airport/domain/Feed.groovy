package io.quos.codeshift.airport.domain

import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

@Entity
class Feed implements GormEntity<Feed> {
    Booking booking
    Flight flight
    Airport airport
    String subject
    String content
    String json
    String media
    Date  ts
    static  constraints = {
        json nullable:true
        content nullable:true
    }

    static  mapping = {
        json sqlType:"TEXT"
    }
}
