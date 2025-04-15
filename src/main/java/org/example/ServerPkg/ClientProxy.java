package org.example.ServerPkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public abstract class ClientProxy {
    private int playerId;
    private GameController controller;

    public ClientProxy(int playerId, GameController controller) {
        this.playerId = playerId;
        this.controller = controller;
    }

    public void addNewPlayer() {

    }
}
