package io.quos.codeshift.airport.services

import grails.gorm.transactions.Transactional
import io.quos.codeshift.airport.domain.Airport
import io.quos.codeshift.airport.domain.Booking
import io.quos.codeshift.airport.domain.City
import io.quos.codeshift.airport.domain.Flight
import io.quos.codeshift.airport.domain.FlightStatus
import jakarta.inject.Singleton

@Singleton
class BookingService {
    @Transactional
    Map book(Map data) {
        try {
            for (Map t : data.offer.trips) {
                for (Map s : t.segments) {
                    Flight f = Flight.findByFlightNumberAndDepartureDate(s.flightMarketing,s.departureDateTime)
                    if(!f){
                        f = Flight.findByFlightNumberOperatingAndDepartureDate(s.flightOperating,s.departureDateTime)
                    }
                    Booking b = Booking.findByBookingIdAndFlight(data.bookingId,f)
                    if(!b){
                        b = new Booking()
                        b.bookingId = data.bookingId
                        b.flight = f
                        b.save()
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace()
        }
    }
}
