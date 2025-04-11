package org.example.Server.Model.Exceptions;

public class InvalidGameCreationException extends RuntimeException {
    public InvalidGameCreationException(String message) {
        super(message);
    }
}
