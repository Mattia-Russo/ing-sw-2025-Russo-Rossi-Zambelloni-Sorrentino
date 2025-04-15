package org.example.ServerPkg.Model.Exceptions;

public class OccupiedPositionException extends RuntimeException {
    public OccupiedPositionException(String message) {
        super(message);
    }
}
