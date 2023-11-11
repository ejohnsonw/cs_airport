package io.quos.codeshift.airport.services

import grails.gorm.transactions.Transactional
import io.quos.codeshift.airport.domain.Airport
import io.quos.codeshift.airport.domain.Booking
import io.quos.codeshift.airport.domain.City
import io.quos.codeshift.airport.domain.Feed
import io.quos.codeshift.airport.domain.Flight
import io.quos.codeshift.airport.domain.FlightStatus
import io.quos.codeshift.airport.utils.JsonUtil
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
@Transactional
class AirportService {
    @Inject
    QuosClient quosclient

    Map flightUpdated(Map request){
        Flight f = Flight.findByFlightNumber(request.flightNumber)
        Airport origin = Airport.findByIataCode(request.origin)
        Airport destination = Airport.findByIataCode(request.destination)
        f.status = FlightStatus.valueForName(request.status)
        f.save()
        if(f){
            for(Map booking : request.bookings){
                Booking b = Booking.findByBookingId(booking.bookingId)
                Map adrequest = new HashMap()
                adrequest.criteria = request.status
                def ads = quosclient.adsForContext(adrequest)
                for(Map ad : ads){
                    Feed feed = Feed.findBySubjectAndBookingAndAirport(ad.title,b,origin)
                    if(!feed){
                        feed = new Feed()
                    }
                    feed.subject = ad.title
                    feed.flight  = f
                    feed.booking = b
                    feed.airport = origin
                    feed.json = JsonUtil.toStringFromObject(ad)
                    feed.ts = new Date()
                    feed.save()
                }

            }
        }
    }

    @Transactional
    Map processOffer(Map offer) {
        try {
            for (Map a : offer.airports) {
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

            for (Map t : offer.trips) {
                for (Map s : t.segments) {
                    Flight f = Flight.findByFlightNumberAndDepartureDate(s.flightMarketing,s.departureDateTime)
                    if (!f) {
                        f = new Flight()
                        f.flightNumber = s.flightMarketing
                        f.flightNumberOperating = s.flightOperating
                        f.departureDate = s.departureDateTime
                        f.arrivalDate = s.arrivalDateTime
                        f.origin = Airport.findByIataCode(s.DepartureAirport.iata_code)
                        f.destination = Airport.findByIataCode(s.ArrivalAirport.iata_code)
                        f.status = FlightStatus.ACTIVE
                        f.save()
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace()
        }
    }
}
