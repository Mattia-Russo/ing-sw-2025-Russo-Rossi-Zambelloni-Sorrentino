package org.example.Server.Model.Exceptions;

public class EnoughAstronautsRemovedException extends RuntimeException {
    public EnoughAstronautsRemovedException(String message) {
        super(message);
    }
}
