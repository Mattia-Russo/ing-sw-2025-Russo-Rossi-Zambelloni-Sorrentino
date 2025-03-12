package org.example.ComponentsPack;

public class Goods {
    private Components storage;
    private final int colour;

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
