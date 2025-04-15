package org.example.Server.Model.Exceptions;

public class InvalidMethodCallException extends RuntimeException {
    public InvalidMethodCallException(String message) {
        super(message);
    }
}
