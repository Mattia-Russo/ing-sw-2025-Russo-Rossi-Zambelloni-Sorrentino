package org.example.ServerPkg.Model.Exceptions;

public class NameAlreadyUsedException extends RuntimeException {
    public NameAlreadyUsedException(String message) {
        super(message);
    }
}
