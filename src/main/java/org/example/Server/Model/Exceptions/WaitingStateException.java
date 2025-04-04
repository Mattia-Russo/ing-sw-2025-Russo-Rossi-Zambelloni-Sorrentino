package org.example.Server.Model.Exceptions;

public class WaitingStateException extends RuntimeException {
    public WaitingStateException(String message) {
        super(message);
    }
}
