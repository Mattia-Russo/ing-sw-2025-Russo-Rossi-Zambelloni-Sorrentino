package org.example.Model.ComponentsPack;

public class LifeSupportSystem extends Components{
    private final AlienColour colour;

    public LifeSupportSystem(AlienColour colour, Direction direction, Connector[] connectors) {
        super(direction, connectors);
        this.colour = colour;
    }

    public AlienColour getColour() {
        return colour;
    }
}
