package kz.trip.travelmanagement.service;

import kz.trip.travelmanagement.dto.TourDto;
import kz.trip.travelmanagement.dto.TourResponse;

public interface TourService {
    TourDto createTour(TourDto tourDto);
    TourResponse getAllTour(int pageNo, int pageSize);
    TourDto getTourById(int id);
    TourDto updateTour(TourDto tourDto, int id);
    void deleteTourId(int id);
}
