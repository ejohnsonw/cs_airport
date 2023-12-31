package io.quos.codeshift.airport.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import io.quos.codeshift.airport.services.AirportService
import io.quos.codeshift.airport.services.BookingService
import jakarta.inject.Inject
import jakarta.inject.Singleton


@Singleton
@KafkaListener(offsetReset = OffsetReset.EARLIEST, groupId = 'airport')
class BookingListener {
    @Inject
    BookingService service

    @Inject
    AirportService airportService

    @Topic("cs_booking_created")
    void bookingCreated(@KafkaKey String bookingId, String data){
        ObjectMapper om = new ObjectMapper()
        Map m = om.readValue(data,Map.class)
        airportService.processOffer(m.offer)
        service.book(m)

    }

    @Topic("cs_offer")
    void flights(@KafkaKey String offerId, String offer){
//        ObjectMapper om = new ObjectMapper()
//        Map m = om.readValue(offer,Map.class)
//        airportService.processOffer(m)
    }


}
