package org.example.ServerPkg.Model.Exceptions;

public class InvalidAddPlayerException extends RuntimeException {
    public InvalidAddPlayerException(String message) {
        super(message);
    }
}
