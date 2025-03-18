package org.example.ComponentsPack;

public class Shield extends Components {
    private final Direction direction2;

    public Shield(Direction direction, Connector[] connectors, Direction direction2) {
        super(direction, connectors);
        this.direction2 = direction2;
    }

    public Direction getDirection1() {
        return super.getDirection();
    }

    public Direction getDirection2(){
        return direction2;
    }
}
