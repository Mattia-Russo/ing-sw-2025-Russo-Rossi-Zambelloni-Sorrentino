package org.example.ServerPkg.Model.Exceptions;

public class EnoughAstronautsRemovedException extends RuntimeException {
    public EnoughAstronautsRemovedException(String message) {
        super(message);
    }
}
