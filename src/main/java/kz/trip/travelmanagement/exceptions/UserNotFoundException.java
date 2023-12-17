package kz.trip.travelmanagement.exceptions;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVerisionUID = 3;

    public UserNotFoundException(String message) {
        super(message);
    }
}