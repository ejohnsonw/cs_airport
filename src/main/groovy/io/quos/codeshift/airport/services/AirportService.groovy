package io.quos.codeshift.airport.services

import grails.gorm.transactions.Transactional
import io.quos.codeshift.airport.domain.*
import io.quos.codeshift.airport.utils.JsonUtil
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
@Transactional
class AirportService {
    @Inject
    QuosClient quosclient
    private static final Logger LOGGER = LoggerFactory.getLogger(AirportService.class);

    Map flightUpdated(Map request) {
        try {
            Flight f = Flight.findByFlightNumber(request.flightNumber)
            Airport origin = Airport.findByIataCode(request.origin)
            Airport destination = Airport.findByIataCode(request.destination)
            f.status = FlightStatus.valueForName(request.status)
            f.save()
            LOGGER.debug("AIRPORT: updating fligh ${request.flightNumber}")
            if (f) {
                for (Map booking : request.bookings) {
                    Booking b = Booking.findByBookingId(booking.bookingId)
                    LOGGER.debug("AIRPORT: updating booking ${booking.bookingId}")
                    Map adrequest = new HashMap()
                    adrequest.criteria = request.status
                    LOGGER.debug("AIRPORT: requesting ads with ${adrequest}")
                    def ads = quosclient.adsForContext(adrequest)
                    LOGGER.debug("****** AIRPORT:  ${ads.size()} returned")
                    for (Map ad : ads) {
                        Feed feed = Feed.findBySubjectAndBookingAndAirport(ad.title, b, origin)
                        if (!feed) {
                            feed = new Feed()
                        }
                        LOGGER.debug("****** AIRPORT: creating add  ${ad.title} ")
                        feed.subject = ad.title
                        feed.flight = f
                        feed.booking = b
                        feed.airport = origin
                        feed.json = JsonUtil.toStringFromObject(ad)
                        feed.ts = new Date()
                        feed.save()
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace()
            LOGGER.error(ex.getLocalizedMessage())
        }
    }

    @Transactional
    Map processOffer(Map offer) {
        LOGGER.debug("****** AIRPORT:  Using offer to seed information")
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
                    Flight f = Flight.findByFlightNumberAndDepartureDate(s.flightMarketing, s.departureDateTime)
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
            LOGGER.error(ex.getLocalizedMessage())
        }
    }
}
