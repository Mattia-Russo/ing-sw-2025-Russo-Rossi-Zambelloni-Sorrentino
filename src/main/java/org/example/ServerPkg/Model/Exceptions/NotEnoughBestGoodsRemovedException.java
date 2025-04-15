package org.example.ServerPkg.Model.Exceptions;

public class NotEnoughBestGoodsRemovedException extends RuntimeException {
    public NotEnoughBestGoodsRemovedException(String message) {
        super(message);
    }
}
