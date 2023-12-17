package kz.trip.travelmanagement.service.impl;

import kz.trip.travelmanagement.dto.BookingDto;
import kz.trip.travelmanagement.exceptions.BookingNotFoundException;
import kz.trip.travelmanagement.exceptions.TourNotFoundException;
import kz.trip.travelmanagement.exceptions.UserNotFoundException;
import kz.trip.travelmanagement.models.Booking;
import kz.trip.travelmanagement.models.Tour;
import kz.trip.travelmanagement.models.UserEntity;
import kz.trip.travelmanagement.repository.BookingRepository;
import kz.trip.travelmanagement.repository.TourRepository;
import kz.trip.travelmanagement.repository.UserRepository;
import kz.trip.travelmanagement.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    private TourRepository tourRepository;
    private UserRepository userRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, TourRepository tourRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.tourRepository = tourRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BookingDto createBooking(int tourId, int userId, BookingDto bookingDto) {
        Booking booking = mapToEntity(bookingDto);

        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new TourNotFoundException("Tour not found"));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        booking.setTour(tour);
        booking.setUser(user);

        Booking newBooking = bookingRepository.save(booking);

        return mapToDto(newBooking);
    }

    @Override
    public List<BookingDto> getBookingsByUserId(int userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);

        return bookings.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public BookingDto getBookingById(int bookingId, int userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        if (booking.getUser().getId() != user.getId()) {
            throw new BookingNotFoundException("This booking does not belong to the user");
        }

        return mapToDto(booking);
    }

    @Override
    public BookingDto updateBooking(int tourId, int userId, int bookingId, BookingDto bookingDto) {

        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new TourNotFoundException("Tour not found"));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        if (booking.getUser().getId() != user.getId()) {
            throw new BookingNotFoundException("This booking does not belong to the user");
        }

        booking.setBookingDateTime(bookingDto.getBookingDateTime());
        // You can update other fields as well based on your requirements

        Booking updatedBooking = bookingRepository.save(booking);

        return mapToDto(updatedBooking);
    }

    @Override
    public void deleteBooking(int tourId, int userId, int bookingId) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new TourNotFoundException("Tour not found"));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        if (booking.getUser().getId() != user.getId()) {
            throw new BookingNotFoundException("This booking does not belong to the user");
        }

        bookingRepository.delete(booking);
    }

    private BookingDto mapToDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setBookingDateTime(booking.getBookingDateTime());
        // You can map other fields as well based on your requirements
        return bookingDto;
    }

    private Booking mapToEntity(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setBookingDateTime(bookingDto.getBookingDateTime());
        // You can map other fields as well based on your requirements
        return booking;
    }
}
