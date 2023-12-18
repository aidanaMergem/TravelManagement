package kz.trip.travelmanagement.exceptions;

public class NoAccessException extends RuntimeException {
    private static final long serialVerisionUID = 6;
    public NoAccessException(String message) {
        super(message);
    }
}
