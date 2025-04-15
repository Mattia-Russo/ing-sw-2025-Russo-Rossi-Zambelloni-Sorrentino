package org.example.ServerPkg.Model.Exceptions;

public class BatteriesLessThenCannonException extends RuntimeException {
    public BatteriesLessThenCannonException(String message) {
        super(message);
    }
}
