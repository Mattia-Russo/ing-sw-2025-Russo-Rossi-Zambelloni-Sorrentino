package org.example.Model.ComponentsPack;

public class Goods {
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
