package org.example.ComponentsPack;

public class PowerComponent extends Components{
    private int power;

    public PowerComponent(TileType tileType, Direction direction, Connector[] connectors,int power){
        super(tileType,direction,connectors);
        this.power = power;
    }
    public int getPower() {
        return power;
    }
}
