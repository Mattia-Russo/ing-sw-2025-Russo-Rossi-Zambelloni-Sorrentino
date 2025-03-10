package org.example;

import java.awt.*;

public class Shield extends Components {
    private int direction1;
    private int direction2;
public Shield(TileType tileType, Direction direction, Connector[] connectors,int direction1, int direction2){
    super(tileType,direction,connectors);
    this.direction1 = direction1;
    this.direction2 = direction2;
}
    public int[] getDirection(){
        return new int[]{direction1, direction2};
    }
}
