package io.quos.codeshift.airport.controllers

import grails.gorm.transactions.Transactional
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.quos.codeshift.airport.domain.Airport
import io.quos.codeshift.airport.domain.Booking
import io.quos.codeshift.airport.domain.Feed
import io.quos.codeshift.airport.pojos.FeedPojo

@Controller("/airport/")
@Transactional
class AirportController {
    @Post("/feed")
    List<FeedPojo> feed(@Body Map request){
        List<FeedPojo> pojos = new ArrayList<>()
        if(request.bookingId){
            Feed.findAllByBookingAndAirport(Booking.findByBookingId(request.bookingId), Airport.findByIataCode(request.iataCode)).forEach(f->{
                pojos.add(new FeedPojo(f))
            })
        }
        return pojos
    }
}
