package org.example.Model.ComponentsPack;

public class Engine extends Components{
    private final int power;

    public Engine(int power, Direction direction, Connector[] connectors) {
        super(direction, connectors);
        this.power = power;
    }

    public int getPower() {
        return power;
    }
}
