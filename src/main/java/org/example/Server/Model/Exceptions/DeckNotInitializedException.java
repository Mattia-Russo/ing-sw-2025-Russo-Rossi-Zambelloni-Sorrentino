package org.example.Server.Model.Exceptions;

public class DeckNotInitializedException extends RuntimeException {
    public DeckNotInitializedException(String message) {
        super(message);
    }
}
