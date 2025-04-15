package org.example.ServerPkg.Model.Exceptions;

public class AbandonedStateException extends RuntimeException {
    public AbandonedStateException(String message) {
        super(message);
    }
}
