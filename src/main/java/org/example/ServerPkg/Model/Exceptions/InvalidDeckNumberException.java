package org.example.ServerPkg.Model.Exceptions;

public class InvalidDeckNumberException extends RuntimeException {
    public InvalidDeckNumberException(String message) {
        super(message);
    }
}
