package org.example.ServerPkg.Model.Exceptions;

public class NotEnoughAstronautsRemovedException extends RuntimeException {
    public NotEnoughAstronautsRemovedException(String message) {
        super(message);
    }
}
