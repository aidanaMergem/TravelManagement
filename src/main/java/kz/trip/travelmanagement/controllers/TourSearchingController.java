package kz.trip.travelmanagement.controllers;

import kz.trip.travelmanagement.dto.TourResponse;
import kz.trip.travelmanagement.service.TourService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class TourSearchingController {
    private TourService tourService;
    @GetMapping("/")
    public ResponseEntity<TourResponse> getTours(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(tourService.getAllTour(pageNo, pageSize), HttpStatus.OK);
    }

}
