package org.example.ComponentsPack;

public class Cannon extends Components {
    private final int power;

    public Cannon(int power, Direction direction, Connector[] connectors) {
        super(direction, connectors);
        this.power = power;
    }

    public int getPower() {
        return power;
    }

}
