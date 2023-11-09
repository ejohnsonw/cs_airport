package io.quos.codeshift.airline.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import io.quos.codeshift.airport.services.AirportService
import jakarta.inject.Inject
import jakarta.inject.Singleton


@Singleton
@KafkaListener(offsetReset = OffsetReset.EARLIEST, groupId = 'airport')
class AirlineListener {
    @Inject
    AirportService service

    @Topic("flight_updated")
    void flightUpdated(@KafkaKey String flightNumber, String data){
        ObjectMapper om = new ObjectMapper()
        Map m = om.readValue(data,Map.class)
        service.flightUpdated(m)
    }


}
