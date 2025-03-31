package org.example.Server.Model.Exceptions;

public class OverloadedCapacityException extends RuntimeException {
    public OverloadedCapacityException(String message) {
        super(message);
    }
}
