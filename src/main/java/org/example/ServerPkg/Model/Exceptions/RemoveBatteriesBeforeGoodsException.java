package org.example.ServerPkg.Model.Exceptions;

public class RemoveBatteriesBeforeGoodsException extends RuntimeException {
    public RemoveBatteriesBeforeGoodsException(String message) {
        super(message);
    }
}
