package kz.trip.travelmanagement.controllers;

import kz.trip.travelmanagement.dto.TourDto;
import kz.trip.travelmanagement.dto.TourResponse;
import kz.trip.travelmanagement.models.Tour;
import kz.trip.travelmanagement.repository.TourRepository;
import kz.trip.travelmanagement.repository.TourTranslationRepository;
import kz.trip.travelmanagement.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/tours")
public class TourSearchingController {
    private TourService tourService;

    private TourTranslationRepository tourTranslationRepository;


    @Autowired
    TourRepository tourRepo;

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchTours(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "id") String sort,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, defaultValue = "en") String language) {

        try {

            List<Sort.Order> orders = new ArrayList<>();
            orders.add(new Sort.Order(getSortDirection(direction), sort));
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
            Page<Tour> pageTours;

            if (keyword == null) {
                pageTours = tourRepo.findAll(pagingSort);
            } else {
                pageTours = tourRepo.findByTranslationsTourNameContaining(keyword, pagingSort);
            }

            List<Tour> tours = pageTours.getContent();

            // Convert Tour entities to TourDTO
            List<TourDto> tourDTOs = tours.stream()
                    .map(tour -> convertToDTO(tour, language))
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("tours", tourDTOs);
            response.put("currentPage", pageTours.getNumber());
            response.put("totalItems", pageTours.getTotalElements());
            response.put("totalPages", pageTours.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    private TourDto convertToDTO(Tour tour, String language) {
        TourDto dto = new TourDto();
        dto.setId(tour.getId());
        dto.setTourName(tour.getTranslatedTourName(language));
        dto.setDescription(tour.getTranslatedDescription(language));
        dto.setTourType(tour.getTourType());
        dto.setDurationInDays(tour.getDurationInDays());
        dto.setPrice(tour.getPrice());
        dto.setStartDate(tour.getStartDate());
        dto.setCreatedAt(tour.getCreatedAt());
        dto.setModifiedAt(tour.getModifiedAt());

        return dto;
    }



    private Sort.Direction getSortDirection (String direction){
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
}
