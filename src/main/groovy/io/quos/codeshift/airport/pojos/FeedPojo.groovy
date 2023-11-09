package io.quos.codeshift.airport.pojos

import grails.gorm.annotation.Entity
import io.quos.codeshift.airport.domain.Booking
import io.quos.codeshift.airport.domain.Feed
import io.quos.codeshift.airport.domain.Flight
import org.grails.datastore.gorm.GormEntity


class FeedPojo {
    FeedPojo(Feed f){
        flight = new FlightPojo(f.flight)
        subject = f.subject
        content = f.content
        ts = f.ts

    }
    BookingPojo booking
    FlightPojo flight
    String subject
    String content
    Date  ts
}
