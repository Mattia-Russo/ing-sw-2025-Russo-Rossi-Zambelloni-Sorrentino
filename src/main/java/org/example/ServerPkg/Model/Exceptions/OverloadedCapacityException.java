package org.example.ServerPkg.Model.Exceptions;

public class OverloadedCapacityException extends RuntimeException {
    public OverloadedCapacityException(String message) {
        super(message);
    }
}
