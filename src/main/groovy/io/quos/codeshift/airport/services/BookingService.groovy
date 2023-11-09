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
            for (Map a : data.offer.airports) {
                City c
                if (a.city) {
                    c = City.findByCode(a.city.id)
                    if (!c) {
                        c = new City()
                        c.name = a.city.name
                        c.countryCode = a.city.iata_country_code
                        c.code = a.city.iata_code
                        c.save()
                    }
                }
                Airport ap = Airport.findByIataCode(a.iata_code)
                if (!ap) {
                    ap = new Airport()
                    ap.name = a.name
                    ap.iataCode = a.iata_code
                    ap.lat = a.latitude
                    ap.lon = a.longitude
                    ap.city = c
                    ap.save()
                }
            }

            for (Map t : data.offer.trips) {
                for (Map s : t.segments) {
                    Flight f = Flight.findByFlightNumberAndDepartureDate(s.flightMarketing,s.departureDateTime)
                    if (!f) {
                        f = new Flight()
                        f.flightNumber = s.flightMarketing
                        f.departureDate = s.departureDateTime
                        f.arrivalDate = s.arrivalDateTime
                        f.origin = Airport.findByIataCode(s.DepartureAirport.iata_code)
                        f.destination = Airport.findByIataCode(s.ArrivalAirport.iata_code)
                        f.status = FlightStatus.ACTIVE
                        f.save()
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
