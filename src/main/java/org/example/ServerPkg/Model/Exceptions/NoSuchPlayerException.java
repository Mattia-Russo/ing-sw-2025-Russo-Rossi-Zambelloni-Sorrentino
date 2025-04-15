package org.example.ServerPkg.Model.Exceptions;

public class NoSuchPlayerException extends RuntimeException {
    public NoSuchPlayerException(String message) {
        super(message);
    }
}
