package org.example.ServerPkg.Model.Exceptions;

public class InvalidLobbyStateException extends RuntimeException {
    public InvalidLobbyStateException(String message) {
        super(message);
    }
}
