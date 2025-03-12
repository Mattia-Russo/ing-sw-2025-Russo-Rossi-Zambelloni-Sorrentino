package org.example.ComponentsPack;

public class Alien {
    private Cabin cabin;
    private final AlienColour colour;

    public Alien(AlienColour colour) {
        this.colour = colour;
        this.cabin = null;
    }

    public AlienColour getColour() {
        return colour;
    }

    public Cabin getCabin() {return cabin;}

}
