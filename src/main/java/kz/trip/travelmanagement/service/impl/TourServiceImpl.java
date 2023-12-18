package kz.trip.travelmanagement.service.impl;

import kz.trip.travelmanagement.dto.TourDto;
import kz.trip.travelmanagement.dto.TourResponse;
import kz.trip.travelmanagement.exceptions.BookingNotFoundException;
import kz.trip.travelmanagement.exceptions.TourNotFoundException;
import kz.trip.travelmanagement.models.Tour;
import kz.trip.travelmanagement.models.TourTranslation;
import kz.trip.travelmanagement.repository.TourRepository;
import kz.trip.travelmanagement.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepo;

    @Autowired
    public TourServiceImpl(TourRepository tourRepo) {
        this.tourRepo = tourRepo;
    }

    @Override
    public List<TourDto> getAllTours(String lang) {
        List<Tour> tours = tourRepo.findAll();
        List<TourDto> tourDTOList = new ArrayList<>();

        for (Tour tour : tours) {
            TourDto tourDTO = mapToDTO(tour, lang);
            tourDTOList.add(tourDTO);
        }
        return tourDTOList;
    }

    @Override
    public TourDto getTourById(long id, String lang) {
        Tour tour = tourRepo.findById(id)
                .orElseThrow(()->new BookingNotFoundException("No such booking"));
        return mapToDTO(tour, lang);
    }

    @Override
    public Page<TourDto> getAllTours(Pageable pageable) {
        return null;
    }

    @Override
    public Page<TourDto> searchTours(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public List<Tour> getToursByPagination(int pageNo, int pageSize) {
        return null;
    }


    @Override
    public TourDto updateTour( TourDto tourDTO,long id,  String language) {
        Tour existingTour = tourRepo.findById(id)
                .orElseThrow();

        mapDTOToEntity(tourDTO, existingTour);

        Tour updatedTour = tourRepo.save(existingTour);

        return mapToDTO(updatedTour, language);
    }


    @Override
    public void deleteTour(long id) {
        Tour existingTour = tourRepo.findById(id)
                .orElseThrow(()-> new TourNotFoundException("Tour not found!"));

        // Remove all associated Booking entities
        existingTour.getReviews().clear();

        // Now you can safely delete the Tour
        tourRepo.delete(existingTour);
    }


    @Override
    public TourDto createTour(TourDto tourDTO) {
        Tour tour = mapToEntity(tourDTO);
        tourRepo.save(tour);
        tourDTO.setId(tour.getId());

        return tourDTO;
    }

    @Override
    public TourResponse getAllTour(int pageNo, int pageSize) {
        return null;
    }




    private TourDto mapToDTO(Tour tour, String language) {
        TourDto tourDTO = new TourDto();
        tourDTO.setId(tour.getId());
        tourDTO.setTourName(tour.getTranslatedTourName(language));
        tourDTO.setDescription(tour.getTranslatedDescription(language));
        tourDTO.setTourType(tour.getTourType());
        tourDTO.setDurationInDays(tour.getDurationInDays());
        tourDTO.setPrice(tour.getPrice());
        tourDTO.setStartDate(tour.getStartDate());
        tourDTO.setCreatedAt(tour.getCreatedAt());
        tourDTO.setModifiedAt(tour.getModifiedAt());
        return tourDTO;
    }

    private Tour mapToEntity(TourDto tourDTO) {
        Tour tour = new Tour();
        tour.setDurationInDays(tourDTO.getDurationInDays());
        tour.setPrice(tourDTO.getPrice());
        tour.setStartDate(tourDTO.getStartDate());
        tour.setCreatedAt(LocalDateTime.now());
        tour.setTranslations(tourDTO.getTranslations());

        for(int i = 0; i < 3; i++){
            TourTranslation translation = new TourTranslation();
            translation.setLanguage(supportedLanguage.get(i));
            translation.setTourName(tourDTO.getTranslations().get(i).getTourName());
            translation.setDescription(tourDTO.getTranslations().get(i).getDescription());
            tour.addTranslation(translation);
        }

        TourTranslation specifiedLanguageTranslation = getOrCreateTranslation(tour, supportedLanguage.get(0));
        specifiedLanguageTranslation.setTourName(tourDTO.getTourName());
        specifiedLanguageTranslation.setDescription(tourDTO.getDescription());

        return tour;
    }





    private void mapDTOToEntity(TourDto tourDTO, Tour tour) {

        tour.setDurationInDays(tourDTO.getDurationInDays());
        tour.setPrice(tourDTO.getPrice());
        tour.setStartDate(tourDTO.getStartDate());
        tour.setModifiedAt(LocalDateTime.now());


        for(int i = 0; i < 3; i++){
            TourTranslation translation = tour.getTranslations().get(i);
            translation.setLanguage(supportedLanguage.get(i));
            translation.setTourName(tourDTO.getTranslations().get(i).getTourName());
            translation.setDescription(tourDTO.getTranslations().get(i).getDescription());
            tour.addTranslation(translation);
        }
        tour.setTranslations(tourDTO.getTranslations());
        TourTranslation specifiedLanguageTranslation = getOrCreateTranslation(tour, supportedLanguage.get(0));
        specifiedLanguageTranslation.setTourName(tourDTO.getTourName());
        specifiedLanguageTranslation.setDescription(tourDTO.getDescription());


    }

    private TourTranslation getOrCreateTranslation(Tour tour, String language) {
        return tour.getTranslations().stream()
                .filter(t -> t.getLanguage().equals(language))
                .findFirst()
                .orElseGet(() -> {
                    TourTranslation newTranslation = new TourTranslation();
                    newTranslation.setLanguage(language);
                    newTranslation.setTour(tour);
                    tour.getTranslations().add(newTranslation);
                    return newTranslation;
                });
    }

    private List<String> supportedLanguage = List.of("en", "ru", "kz"); // Example languages

}
