package kz.trip.travelmanagement.service;

import kz.trip.travelmanagement.dto.TourDto;
import kz.trip.travelmanagement.dto.TourResponse;
import kz.trip.travelmanagement.models.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TourService {
    TourDto createTour(TourDto tourDto);
    TourResponse getAllTour(int pageNo, int pageSize);

    void deleteTour(long id);

    TourDto updateTour( TourDto tourDTO,  long id,String language);

    List<TourDto> getAllTours(String lang);
    TourDto getTourById(long id,String lang);

    Page<TourDto> getAllTours(Pageable pageable);

    Page<TourDto> searchTours(String keyword, Pageable pageable);

    List<Tour> getToursByPagination(int pageNo, int pageSize);
}
