package org.example.ServerPkg.Model.ComponentsPkg;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Goods implements Serializable {
    private final GoodsColour colour;
    private Storage storage;

    public Goods(@JsonProperty("colour") GoodsColour colour) {
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
