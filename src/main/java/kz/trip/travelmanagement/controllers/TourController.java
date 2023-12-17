package kz.trip.travelmanagement.controllers;


import kz.trip.travelmanagement.dto.TourDto;
import kz.trip.travelmanagement.dto.TourResponse;
import kz.trip.travelmanagement.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    private TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping("/")
    public ResponseEntity<List<TourDto>> getAllTours(
            @RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, defaultValue = "en") String language) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        List<TourDto> tours = tourService.getAllTours(currentLocale.getLanguage());
        return ResponseEntity.ok(tours);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourDto> tourDetail(@PathVariable int id, @RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, defaultValue = "en") String language) {
        return ResponseEntity.ok(tourService.getTourById(id, language));

    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TourDto> createTour(@RequestBody TourDto tourDto) {
        return new ResponseEntity<>(tourService.createTour(tourDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourDto> updateTour(@RequestBody TourDto tourDto, @PathVariable("id") int tourId, @RequestHeader(name = HttpHeaders.ACCEPT_LANGUAGE, defaultValue = "en") String language) {
        TourDto response = tourService.updateTour(tourDto, tourId, language);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTour(@PathVariable("id") int tourId) {
        tourService.deleteTour(tourId);
        return new ResponseEntity<>("Tour delete", HttpStatus.OK);
    }

}
