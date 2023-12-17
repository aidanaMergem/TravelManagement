package kz.trip.travelmanagement.service;

import kz.trip.travelmanagement.dto.BookingDto;

import java.util.List;

public interface BookingService {
    List<BookingDto> getAllBookings();

    BookingDto changeBookingStatus(long id, String status);

    BookingDto createBooking(long tourId, long userId);
    List<BookingDto> getBookingsByUserId(long userId);
    BookingDto getBookingById(long bookingId, long userId);
    BookingDto updateBooking(long tourId, long userId, long bookingId, BookingDto bookingDto);
    void deleteBooking(long tourId, long userId, long bookingId);
}
