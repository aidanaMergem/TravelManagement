package kz.trip.travelmanagement.repository;

import kz.trip.travelmanagement.models.Tour;
import kz.trip.travelmanagement.models.TourTranslation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TourTranslationRepository extends JpaRepository<TourTranslation, Long> {
    List<TourTranslation> findByTourNameContaining(String title, Sort sort);
    Page<TourTranslation> findByTourNameContaining(String title, Pageable pageable);

    List<TourTranslation> findByDescriptionContaining(String desc, Sort sort);

}
