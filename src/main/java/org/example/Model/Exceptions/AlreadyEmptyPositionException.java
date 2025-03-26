package org.example.Model.Exceptions;

public class AlreadyEmptyPositionException extends RuntimeException {
    public AlreadyEmptyPositionException(String message) {
        super(message);
    }
}
