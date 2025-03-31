package org.example.Server.Model.Exceptions;

public class OccupiedPositionException extends RuntimeException {
    public OccupiedPositionException(String message) {
        super(message);
    }
}
