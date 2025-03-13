package org.example.ComponentsPack;

public class Shield extends Components {
    private final Direction direction2;

    public Shield(Direction direction, Connector[] connectors, Direction direction2) {
        super(direction, connectors);
        this.direction2 = direction2;
    }

    public Direction[] getDirections(){
        Direction[] directions = new Direction[2];
        directions[0] = super.getDirection();
        directions[1] = direction2;
        return directions;
    }
}
