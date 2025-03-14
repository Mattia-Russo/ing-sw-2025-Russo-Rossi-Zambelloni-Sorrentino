package org.example.ComponentsPack;

public class Goods {
    private final int colour;
    private Storage storage;

    public Goods(int colour) {
        this.colour = colour;
        this.storage = null;
    }

    public int getColour() {
        return colour;
    }

    public Storage getStorage() {
        return storage;
    }
}
