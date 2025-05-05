package org.example.MessagePkg;

public class LandOnPlanetMessage extends Message {
    private boolean bool;
    private int numPlanet;

    public LandOnPlanetMessage(boolean bool, int numPlanet) {
        this.bool = bool;
        this.numPlanet = numPlanet;
    }

    @Override
    public void handle() {
        super.getProxy().landOnPlanet(bool, numPlanet);
    }
}
