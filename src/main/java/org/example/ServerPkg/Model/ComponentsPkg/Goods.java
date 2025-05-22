package org.example.ServerPkg.Model.ComponentsPkg;

import java.io.Serializable;

public class Goods implements Serializable {
    private final GoodsColour colour;
    private Storage storage;

    public Goods(GoodsColour colour) {
        this.colour = colour;
        this.storage = null;
    }

    public GoodsColour getColour() {
        return colour;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
