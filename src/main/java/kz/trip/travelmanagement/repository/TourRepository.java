package kz.trip.travelmanagement.repository;

import kz.trip.travelmanagement.models.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, Integer> {
    Optional<Tour> findByType(String type);
}
