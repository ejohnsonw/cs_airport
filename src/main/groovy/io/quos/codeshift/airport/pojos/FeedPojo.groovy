package io.quos.codeshift.airport.pojos

import grails.gorm.annotation.Entity
import io.quos.codeshift.airport.domain.Booking
import io.quos.codeshift.airport.domain.Feed
import io.quos.codeshift.airport.domain.Flight
import io.quos.codeshift.airport.utils.JsonUtil
import org.grails.datastore.gorm.GormEntity


class FeedPojo {
    FeedPojo(Feed f){
        flight = new FlightPojo(f.flight)
        subject = f.subject
        content = f.content
        if(f.json){
            advertisement = JsonUtil.toObjectFromString(f.json,Map.class)
        }
        ts = f.ts

    }
    BookingPojo booking
    FlightPojo flight
    String subject
    String content
    Map advertisement
    Date  ts
}
