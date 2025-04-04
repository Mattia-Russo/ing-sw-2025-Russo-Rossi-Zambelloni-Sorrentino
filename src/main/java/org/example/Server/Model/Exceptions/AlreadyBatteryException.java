package org.example.Server.Model.Exceptions;

public class AlreadyBatteryException extends RuntimeException {
    public AlreadyBatteryException(String message) {
        super(message);
    }
}
