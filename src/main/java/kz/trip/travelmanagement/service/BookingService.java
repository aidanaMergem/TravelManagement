package kz.trip.travelmanagement.service;

import kz.trip.travelmanagement.dto.BookingDto;

import java.util.List;

public interface BookingService {
    BookingDto createBooking(int tourId, int userId, BookingDto bookingDto);
    List<BookingDto> getBookingsByUserId(int userId);
    BookingDto getBookingById(int bookingId, int userId);
    BookingDto updateBooking(int tourId, int userId, int bookingId, BookingDto bookingDto);
    void deleteBooking(int tourId, int userId, int bookingId);
}
