package org.example.ComponentsPack;


public class Components {
    private Direction direction;
    private boolean covered;
    private boolean isPositoned;
    private Connector[] connectors;
    private int posX;
    private int posY;

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

    }
    public void rightRotate() {
        switch (direction) {
            case NORTH:
                direction = Direction.EAST;
            case WEST:
                direction = Direction.SOUTH;
            case SOUTH:
                direction = Direction.WEST;
            case EAST:
                direction = Direction.NORTH;
        }
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
