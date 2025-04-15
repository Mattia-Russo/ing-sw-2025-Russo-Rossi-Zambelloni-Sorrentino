package org.example.ServerPkg.Model.Exceptions;

public class AlreadyBatteryException extends RuntimeException {
    public AlreadyBatteryException(String message) {
        super(message);
    }
}
