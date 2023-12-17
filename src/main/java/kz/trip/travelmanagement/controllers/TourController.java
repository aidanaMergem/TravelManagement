package kz.trip.travelmanagement.controllers;


import kz.trip.travelmanagement.dto.TourDto;
import kz.trip.travelmanagement.dto.TourResponse;
import kz.trip.travelmanagement.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class TourController {

    private TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping("tour")
    public ResponseEntity<TourResponse> getTours(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(tourService.getAllTour(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("tour/{id}")
    public ResponseEntity<TourDto> tourDetail(@PathVariable int id) {
        return ResponseEntity.ok(tourService.getTourById(id));

    }

    @PostMapping("tour/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TourDto> createTour(@RequestBody TourDto tourDto) {
        return new ResponseEntity<>(tourService.createTour(tourDto), HttpStatus.CREATED);
    }

    @PutMapping("tour/{id}/update")
    public ResponseEntity<TourDto> updateTour(@RequestBody TourDto tourDto, @PathVariable("id") int tourId) {
        TourDto response = tourService.updateTour(tourDto, tourId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("tour/{id}/delete")
    public ResponseEntity<String> deleteTour(@PathVariable("id") int tourId) {
        tourService.deleteTourId(tourId);
        return new ResponseEntity<>("Tour delete", HttpStatus.OK);
    }

}
