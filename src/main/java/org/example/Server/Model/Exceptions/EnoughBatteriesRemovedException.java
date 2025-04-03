package org.example.Server.Model.Exceptions;

public class EnoughBatteriesRemovedException extends RuntimeException {
    public EnoughBatteriesRemovedException(String message) {
        super(message);
    }
}
