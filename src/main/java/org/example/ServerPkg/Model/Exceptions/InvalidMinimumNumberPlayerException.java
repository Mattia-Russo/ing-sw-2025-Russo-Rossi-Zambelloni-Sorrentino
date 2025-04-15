package org.example.ServerPkg.Model.Exceptions;

public class InvalidMinimumNumberPlayerException extends RuntimeException {
    public InvalidMinimumNumberPlayerException(String message) {
        super(message);
    }
}
