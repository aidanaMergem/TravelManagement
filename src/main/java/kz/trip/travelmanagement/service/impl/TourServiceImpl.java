package kz.trip.travelmanagement.service.impl;

import kz.trip.travelmanagement.dto.TourDto;
import kz.trip.travelmanagement.dto.TourResponse;
import kz.trip.travelmanagement.exceptions.TourNotFoundException;
import kz.trip.travelmanagement.models.Tour;
import kz.trip.travelmanagement.repository.TourRepository;
import kz.trip.travelmanagement.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {
    private TourRepository tourRepository;

    @Autowired
    public TourServiceImpl(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Override
    public TourDto createTour(TourDto tourDto) {
        Tour tour = new Tour();
        tour.setName(tourDto.getName());
        tour.setType(tourDto.getType());

        Tour newTour = tourRepository.save(tour);

        TourDto pokemonResponse = new TourDto();
        pokemonResponse.setId(newTour.getId());
        pokemonResponse.setName(newTour.getName());
        pokemonResponse.setType(newTour.getType());
        return pokemonResponse;
    }

    @Override
    public TourResponse getAllTour(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Tour> pokemons = tourRepository.findAll(pageable);
        List<Tour> listOfTour = pokemons.getContent();
        List<TourDto> content = listOfTour.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        TourResponse tourResponse = new TourResponse();
        tourResponse.setContent(content);
        tourResponse.setPageNo(pokemons.getNumber());
        tourResponse.setPageSize(pokemons.getSize());
        tourResponse.setTotalElements(pokemons.getTotalElements());
        tourResponse.setTotalPages(pokemons.getTotalPages());
        tourResponse.setLast(pokemons.isLast());

        return tourResponse;
    }

    @Override
    public TourDto getTourById(int id) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new TourNotFoundException("Tour could not be found"));
        return mapToDto(tour);
    }

    @Override
    public TourDto updateTour(TourDto tourDto, int id) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new TourNotFoundException("Tour could not be updated"));

        tour.setName(tourDto.getName());
        tour.setType(tourDto.getType());

        Tour updatedTour = tourRepository.save(tour);
        return mapToDto(updatedTour);
    }

    @Override
    public void deleteTourId(int id) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new TourNotFoundException("Tour could not be delete"));
        tourRepository.delete(tour);
    }

    private TourDto mapToDto(Tour tour) {
        TourDto tourDto = new TourDto();
        tourDto.setId(tour.getId());
        tourDto.setName(tour.getName());
        tourDto.setType(tour.getType());
        return tourDto;
    }

    private Tour mapToEntity(TourDto tourDto) {
        Tour tour = new Tour();
        tour.setName(tourDto.getName());
        tour.setType(tourDto.getType());
        return tour;
    }
}
