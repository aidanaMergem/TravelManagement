package kz.trip.travelmanagement.controllers;

import kz.trip.travelmanagement.dto.ReviewDto;
import kz.trip.travelmanagement.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/tours/{tourId}/reviews")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "tourId") int tourId, @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.createReview(tourId, reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("/tours/{tourId}/reviews")
    public List<ReviewDto> getReviewsByTourId(@PathVariable(value = "tourId") int tourId) {
        return reviewService.getReviewsByTourId(tourId);
    }

    @GetMapping("/tours/{tourId}/reviews/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "id") int reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(tourId, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PutMapping("/tours/{tourId}/reviews/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "id") int reviewId,
                                                  @RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReview = reviewService.updateReview(tourId, reviewId, reviewDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/tours/{tourId}/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "tourId") int tourId, @PathVariable(value = "id") int reviewId) {
        reviewService.deleteReview(tourId, reviewId);
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }
}
