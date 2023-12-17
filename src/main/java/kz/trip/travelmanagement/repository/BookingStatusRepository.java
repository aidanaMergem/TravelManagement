package kz.trip.travelmanagement.repository;

import kz.trip.travelmanagement.models.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingStatusRepository extends JpaRepository<BookingStatus, Long> {
    BookingStatus findByName(String name);
}
