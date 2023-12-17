package kz.trip.travelmanagement.repository;

import kz.trip.travelmanagement.models.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, Long> {
    Optional<Tour> findByTourType(String type);
    Page<Tour> findByTranslationsTourNameContaining(String title, Pageable pageable);
}
