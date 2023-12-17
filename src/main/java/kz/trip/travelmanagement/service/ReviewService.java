package kz.trip.travelmanagement.service;

import kz.trip.travelmanagement.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(long tourId, ReviewDto reviewDto);
    List<ReviewDto> getReviewsByTourId(long id);
    ReviewDto getReviewById(long reviewId, long tourId);
    ReviewDto updateReview(long tourId, long reviewId, ReviewDto reviewDto);
    void deleteReview(long tourId, long reviewId);
}
