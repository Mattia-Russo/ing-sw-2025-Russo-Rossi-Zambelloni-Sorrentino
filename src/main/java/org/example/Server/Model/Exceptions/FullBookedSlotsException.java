package org.example.Server.Model.Exceptions;

public class FullBookedSlotsException extends RuntimeException {
    public FullBookedSlotsException(String message) {
        super(message);
    }
}
