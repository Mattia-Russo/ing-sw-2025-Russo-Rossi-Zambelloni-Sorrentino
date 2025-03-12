package org.example.ComponentsPack;

public class Goods {
    private final int colour;
    private Components storage;

    public Goods(int colour) {
        this.colour = colour;
    }

    public int getColour() {
        return colour;
    }

    public Components getStorage() {
        return storage;
    }
}
