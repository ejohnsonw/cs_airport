package io.quos.codeshift.airport.controllers

import grails.gorm.transactions.Transactional
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import io.quos.codeshift.airport.domain.Airport
import io.quos.codeshift.airport.domain.Booking
import io.quos.codeshift.airport.domain.Feed
import io.quos.codeshift.airport.pojos.FeedPojo
import io.quos.codeshift.airport.services.QuosClient
import jakarta.inject.Inject

@Controller("/airport/")
@Transactional
@ExecuteOn(TaskExecutors.IO)
class AirportController {

    @Inject
    QuosClient quosClient

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

    @Post("/search")
    List<Map> search(@Body Map request){
        return quosClient.airports(request)
    }
}
