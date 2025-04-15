package org.example.ServerPkg.Model.Exceptions;

public class WaitingStateException extends RuntimeException {
    public WaitingStateException(String message) {
        super(message);
    }
}
