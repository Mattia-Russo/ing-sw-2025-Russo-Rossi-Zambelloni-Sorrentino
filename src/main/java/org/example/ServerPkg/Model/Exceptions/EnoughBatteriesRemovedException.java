package org.example.ServerPkg.Model.Exceptions;

public class EnoughBatteriesRemovedException extends RuntimeException {
    public EnoughBatteriesRemovedException(String message) {
        super(message);
    }
}
