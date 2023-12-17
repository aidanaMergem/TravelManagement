package kz.trip.travelmanagement.service.impl;

import kz.trip.travelmanagement.dto.BookingDto;
import kz.trip.travelmanagement.exceptions.BookingNotFoundException;
import kz.trip.travelmanagement.exceptions.TourNotFoundException;
import kz.trip.travelmanagement.exceptions.UserNotFoundException;
import kz.trip.travelmanagement.models.Booking;
import kz.trip.travelmanagement.models.BookingStatus;
import kz.trip.travelmanagement.models.Tour;
import kz.trip.travelmanagement.models.UserEntity;
import kz.trip.travelmanagement.repository.BookingRepository;
import kz.trip.travelmanagement.repository.BookingStatusRepository;
import kz.trip.travelmanagement.repository.TourRepository;
import kz.trip.travelmanagement.repository.UserRepository;
import kz.trip.travelmanagement.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    private TourRepository tourRepository;
    private UserRepository userRepository;
    private BookingStatusRepository bookingStatusRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, TourRepository tourRepository, UserRepository userRepository, BookingStatusRepository bookingStatusRepository) {
        this.bookingRepository = bookingRepository;
        this.tourRepository = tourRepository;
        this.userRepository = userRepository;
        this.bookingStatusRepository = bookingStatusRepository;
    }

    @Override
    public List<BookingDto> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public BookingDto changeBookingStatus(long id, String status) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(()->new BookingNotFoundException("Booking Not Found"));

        existingBooking.setBookingStatus(bookingStatusRepository.findByName(status));


        Booking updatedBooking = bookingRepository.save(existingBooking);

        return mapToDto(updatedBooking);
    }

    @Override
    public BookingDto createBooking(long tourId, long userId) {
        Booking booking = new Booking();
        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new TourNotFoundException("Tour not found"));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        booking.setTour(tour);
        booking.setUser(user);
        booking.setUser(user);
        booking.setBookingStatus(bookingStatusRepository.findByName("Pending"));
        booking.setBookingDateTime(new Date());
        Booking newBooking = bookingRepository.save(booking);

        return mapToDto(newBooking);
    }

    @Override
    public List<BookingDto> getBookingsByUserId(long userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);

        return bookings.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public BookingDto getBookingById(long bookingId, long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        if (booking.getUser().getId() != user.getId()) {
            throw new BookingNotFoundException("This booking does not belong to the user");
        }

        return mapToDto(booking);
    }

    @Override
    public BookingDto updateBooking(long tourId, long userId, long bookingId, BookingDto bookingDto) {

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
    public void deleteBooking(long tourId, long userId, long bookingId) {
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
        bookingDto.setTourId(booking.getTour().getId());
        bookingDto.setUserId(booking.getUser().getId());
        bookingDto.setTourName(booking.getTour().getTranslatedTourName("en"));
        bookingDto.setUserName(booking.getUser().getUsername());
        bookingDto.setBookingDateTime(booking.getBookingDateTime());
        bookingDto.setBookingStatusValue(booking.getBookingStatus().getName());
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
