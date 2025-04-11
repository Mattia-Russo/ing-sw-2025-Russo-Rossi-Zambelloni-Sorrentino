package org.example.Server.Model.Exceptions;

public class InvalidLobbyStateException extends RuntimeException {
    public InvalidLobbyStateException(String message) {
        super(message);
    }
}
