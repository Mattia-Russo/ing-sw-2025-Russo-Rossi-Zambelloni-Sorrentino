package org.example.ServerPkg.Model.Exceptions;

public class EmptyComponentListException extends RuntimeException {
    public EmptyComponentListException(String message) {
        super(message);
    }
}
