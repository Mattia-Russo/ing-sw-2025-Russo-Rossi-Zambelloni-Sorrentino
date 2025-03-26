package org.example.Model.Exceptions;

public class BatteriesLessThenCannonException extends RuntimeException {
    public BatteriesLessThenCannonException(String message) {
        super(message);
    }
}
