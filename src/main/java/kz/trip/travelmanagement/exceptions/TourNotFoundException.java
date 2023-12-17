package kz.trip.travelmanagement.exceptions;

public class TourNotFoundException extends RuntimeException {
    private static final long serialVerisionUID = 1;

    public TourNotFoundException(String message) {
        super(message);
    }
}
