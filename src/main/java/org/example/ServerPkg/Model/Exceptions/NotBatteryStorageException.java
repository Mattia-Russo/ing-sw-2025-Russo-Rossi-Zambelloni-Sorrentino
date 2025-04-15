package org.example.ServerPkg.Model.Exceptions;

public class NotBatteryStorageException extends RuntimeException {
    public NotBatteryStorageException(String message) {
        super(message);
    }
}
