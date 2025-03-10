package org.example;

public class AlienCabins extends ContainersComponent{
    private int colour;
    public AlienCabins(TileType tileType, Direction direction,Connector[] connectors, int quantity, int capacity, int colour) {
        super(tileType, direction, connectors, quantity, capacity);

        colour=this.colour;
    }

    public int getColour() {
        return colour;
    }
}
