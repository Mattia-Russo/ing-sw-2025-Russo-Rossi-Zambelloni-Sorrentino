package org.example.Server.Model.Exceptions;

public class InvalidAddPlayerException extends RuntimeException {
    public InvalidAddPlayerException(String message) {
        super(message);
    }
}
