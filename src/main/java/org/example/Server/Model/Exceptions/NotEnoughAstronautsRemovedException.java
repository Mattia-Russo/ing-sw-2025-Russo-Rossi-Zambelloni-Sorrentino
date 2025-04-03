package org.example.Server.Model.Exceptions;

public class NotEnoughAstronautsRemovedException extends RuntimeException {
    public NotEnoughAstronautsRemovedException(String message) {
        super(message);
    }
}
