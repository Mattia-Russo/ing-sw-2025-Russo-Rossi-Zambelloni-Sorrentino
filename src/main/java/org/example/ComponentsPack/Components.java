package org.example.ComponentsPack;


public class Components {
    private TileType tileType;
    private Direction direction;
    private boolean covered;
    private boolean isPositoned;
    private Connector[] connectors;
    private int posX;
    private int posY;


//    public Components(TileType tileType, Direction direction, Connector[] connectors){
//        this.tileType = tileType;
//        this.direction = direction;
//        this.connectors = connectors;
//    }

    public void leftRotate(){
        switch(direction){
            case NORTH:
                direction=Direction.WEST;
            case WEST:
                direction=Direction.SOUTH;
            case SOUTH:
                direction=Direction.EAST;
            case EAST:
                direction=Direction.NORTH;
        }
        Connector tmp = connectors[0];
        connectors[0] = connectors[1];
        connectors[1] = connectors[2];
        connectors[2] = connectors[3];
        connectors[3] = tmp;
    }
    public void rightRotate(){
        switch(direction){
            case NORTH:
                direction=Direction.EAST;
            case WEST:
                direction=Direction.SOUTH;
            case SOUTH:
                direction=Direction.WEST;
            case EAST:
                direction=Direction.NORTH;
        }
        Connector temp = connectors[3];
        connectors[3] = connectors[2];
        connectors[2] = connectors[1];
        connectors[1] = connectors[0];
        connectors[0] = temp;
    }

    public TileType getTileType() {
        return tileType;
    }

    public Connector[] getConnectors(){
        return connectors;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
