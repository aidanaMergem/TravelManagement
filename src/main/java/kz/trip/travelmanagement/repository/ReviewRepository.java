package kz.trip.travelmanagement.repository;

import kz.trip.travelmanagement.models.Booking;
import kz.trip.travelmanagement.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTourId(long tourId);
}
