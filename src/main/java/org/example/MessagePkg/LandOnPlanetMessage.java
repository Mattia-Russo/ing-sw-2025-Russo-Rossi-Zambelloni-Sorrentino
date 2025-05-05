package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;

public class LandOnPlanetMessage extends Message {
    private boolean bool;
    private int numPlanet;

    public LandOnPlanetMessage(boolean bool, int numPlanet) {
        this.bool = bool;
        this.numPlanet = numPlanet;
    }

    @Override
    public void handle(ClientProxy proxy) {
        proxy.landOnPlanet(bool, numPlanet);
    }
}
