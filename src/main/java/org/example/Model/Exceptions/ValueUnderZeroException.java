package org.example.Model.Exceptions;

public class ValueUnderZeroException extends RuntimeException {
    public ValueUnderZeroException(String message) {
        super(message);
    }
}
