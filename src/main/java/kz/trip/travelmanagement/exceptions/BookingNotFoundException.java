package kz.trip.travelmanagement.exceptions;

public class BookingNotFoundException extends RuntimeException {
    private static final long serialVerisionUID = 4;

    public BookingNotFoundException(String message) {
        super(message);
    }
}