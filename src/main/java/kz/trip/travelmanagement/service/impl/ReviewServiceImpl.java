package kz.trip.travelmanagement.service.impl;

import kz.trip.travelmanagement.dto.ReviewDto;
import kz.trip.travelmanagement.exceptions.TourNotFoundException;
import kz.trip.travelmanagement.exceptions.ReviewNotFoundException;
import kz.trip.travelmanagement.models.Review;
import kz.trip.travelmanagement.models.Tour;
import kz.trip.travelmanagement.models.Booking;
import kz.trip.travelmanagement.repository.TourRepository;
import kz.trip.travelmanagement.repository.ReviewRepository;
import kz.trip.travelmanagement.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private TourRepository tourRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, TourRepository tourRepository) {
        this.reviewRepository = reviewRepository;
        this.tourRepository = tourRepository;
    }

    @Override
    public ReviewDto createReview(long pokemonId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);

        Tour tour = tourRepository.findById(pokemonId).orElseThrow(() -> new TourNotFoundException("Pokemon with associated review not found"));

        review.setTour(tour);

        Review newReview = reviewRepository.save(review);

        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewsByTourId(long id) {
        List<Review> reviews = reviewRepository.findByTourId(id);

        return reviews.stream().map(review -> mapToDto(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(long reviewId, long pokemonId) {
        Tour tour = tourRepository.findById(pokemonId).orElseThrow(() -> new TourNotFoundException("Pokemon with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate pokemon not found"));

        if(review.getTour().getId() != tour.getId()) {
            throw new ReviewNotFoundException("This review does not belond to a pokemon");
        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(long pokemonId, long reviewId, ReviewDto reviewDto) {
        Tour tour = tourRepository.findById(pokemonId).orElseThrow(() -> new TourNotFoundException("Pokemon with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate pokemon not found"));

        if(review.getTour().getId() != tour.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a pokemon");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updateReview = reviewRepository.save(review);

        return mapToDto(updateReview);
    }

    @Override
    public void deleteReview(long tourId, long reviewId) {
        Tour tour = tourRepository.findById(tourId).orElseThrow(() -> new TourNotFoundException("Pokemon with associated review not found"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review with associate pokemon not found"));

        if(review.getTour().getId() != tour.getId()) {
            throw new ReviewNotFoundException("This review does not belong to a tour");
        }

        reviewRepository.delete(review);
    }

    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }
}
