package org.example.Server.Model.Exceptions;

public class PlanetAlreadyVisitedException extends RuntimeException {
    public PlanetAlreadyVisitedException(String message) {
        super(message);
    }
}
