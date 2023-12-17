package kz.trip.travelmanagement.repository;


import kz.trip.travelmanagement.models.Booking;
import kz.trip.travelmanagement.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByTourId(int tourId);
    List<Booking> findByUserId(int userId);
}

