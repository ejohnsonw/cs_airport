package io.quos.codeshift.airport.pojos

import io.quos.codeshift.airport.domain.Booking


class BookingPojo {
    BookingPojo(Booking b) {
        bookingId = b.bookingId
    }
    String bookingId
}
