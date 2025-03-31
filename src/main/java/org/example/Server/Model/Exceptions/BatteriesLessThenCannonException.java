package org.example.Server.Model.Exceptions;

public class BatteriesLessThenCannonException extends RuntimeException {
    public BatteriesLessThenCannonException(String message) {
        super(message);
    }
}
