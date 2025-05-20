package org.example.ServerPkg.Model.ComponentsPkg;

import java.io.Serializable;

public class Alien implements Serializable { ;
    private final AlienColour colour;

    public Alien(AlienColour colour) {
        this.colour = colour;
    }

    public AlienColour getColour() {
        return colour;
    }


}
