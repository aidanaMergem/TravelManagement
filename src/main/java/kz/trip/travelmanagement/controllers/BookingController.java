package kz.trip.travelmanagement.controllers;

import kz.trip.travelmanagement.dto.BookingDto;
import kz.trip.travelmanagement.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/")
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @GetMapping("/bookings")
    public List<BookingDto> showAllBookings() {
        return bookingService.getAllBookings();
    }


    @GetMapping("/bookings/{id}/changeStatus")
    public BookingDto changeBookingStatus(@RequestParam String status, @PathVariable(value = "id")  int id) {
        return bookingService.changeBookingStatus(id, status);
    }



    @PostMapping("/tours/{tourId}/users/{userId}/book")
    public ResponseEntity<BookingDto> createBooking(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "userId") int userId) {
        return new ResponseEntity<>(bookingService.createBooking(tourId, userId), HttpStatus.CREATED);
    }

    @GetMapping("/tours/{tourId}/users/{userId}/bookTour")
    public ResponseEntity<BookingDto> bookTour(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "userId") int userId) {
        return new ResponseEntity<>(bookingService.createBooking(tourId, userId), HttpStatus.CREATED);
    }
    @GetMapping("/users/{userId}/bookings")
    public List<BookingDto> getBookingsByUserId(@PathVariable(value = "userId") int userId) {
        return bookingService.getBookingsByUserId(userId);
    }

    @GetMapping("/users/{userId}/bookings/{id}")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable(value = "userId") int userId, @PathVariable(value = "id") int bookingId) {
        BookingDto bookingDto = bookingService.getBookingById(bookingId, userId);
        return new ResponseEntity<>(bookingDto, HttpStatus.OK);
    }

    @PutMapping("/tours/{tourId}/users/{userId}/bookings/{id}")
    public ResponseEntity<BookingDto> updateBooking(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "userId") int userId,
                                                    @PathVariable(value = "id") int bookingId, @RequestBody BookingDto bookingDto) {
        BookingDto updatedBooking = bookingService.updateBooking(tourId, userId, bookingId, bookingDto);
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }

    @DeleteMapping("/tours/{tourId}/users/{userId}/bookings/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "userId") int userId,
                                                @PathVariable(value = "id") int bookingId) {
        bookingService.deleteBooking(tourId, userId, bookingId);
        return new ResponseEntity<>("Booking deleted successfully", HttpStatus.OK);
    }
}
