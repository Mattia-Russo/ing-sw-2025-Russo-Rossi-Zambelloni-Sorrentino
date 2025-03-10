package org.example;

public class LifeSupportSystem extends Components{
    private int colour;

    public LifeSupportSystem(TileType tileType, Direction direction, Connector[] connectors,int colour) {
        super(tileType,direction,connectors);
        this.colour = colour;
    }

    public int getColour() {
        return colour;
    }
}
