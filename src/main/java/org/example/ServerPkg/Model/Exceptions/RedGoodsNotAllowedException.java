package org.example.ServerPkg.Model.Exceptions;

public class RedGoodsNotAllowedException extends RuntimeException {
    public RedGoodsNotAllowedException(String message) {
        super(message);
    }
}
