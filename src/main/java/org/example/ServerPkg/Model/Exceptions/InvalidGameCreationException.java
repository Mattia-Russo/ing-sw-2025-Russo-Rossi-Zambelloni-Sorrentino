package org.example.ServerPkg.Model.Exceptions;

public class InvalidGameCreationException extends RuntimeException {
    public InvalidGameCreationException(String message) {
        super(message);
    }
}
