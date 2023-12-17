package kz.trip.travelmanagement.repository;


import kz.trip.travelmanagement.dto.BookingDto;
import kz.trip.travelmanagement.models.Booking;
import kz.trip.travelmanagement.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByTourId(long tourId);
    List<Booking> findByUserId(long userId);

}

