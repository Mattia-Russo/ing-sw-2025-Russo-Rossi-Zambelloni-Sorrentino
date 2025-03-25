package org.example.Model.Exceptions;

public class FullBookedSlotsException extends RuntimeException {
    public FullBookedSlotsException(String message) {
        super(message);
    }
}
