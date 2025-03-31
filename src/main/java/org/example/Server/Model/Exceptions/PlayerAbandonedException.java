package org.example.Server.Model.Exceptions;

public class PlayerAbandonedException extends RuntimeException {
    public PlayerAbandonedException(String message) {
        super(message);
    }
}
