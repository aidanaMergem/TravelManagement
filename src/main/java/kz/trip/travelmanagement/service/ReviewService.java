package kz.trip.travelmanagement.service;

import kz.trip.travelmanagement.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(int pokemonId, ReviewDto reviewDto);
    List<ReviewDto> getReviewsByTourId(int id);
    ReviewDto getReviewById(int reviewId, int pokemonId);
    ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto);
    void deleteReview(int pokemonId, int reviewId);
}
