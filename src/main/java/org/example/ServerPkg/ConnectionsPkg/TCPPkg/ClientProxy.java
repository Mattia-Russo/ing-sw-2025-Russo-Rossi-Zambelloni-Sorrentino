package org.example.ServerPkg.ConnectionsPkg.TCPPkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.Points;
import org.example.UIPkg.GameUpdater;
import org.example.UIPkg.TCPVirtualView;
import org.example.UIPkg.TUI;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public abstract class ClientProxy {
    private String playerName;
    private final GameController controller;
    private final TCPServer TCPServer;

    public ClientProxy(GameController controller, TCPServer TCPServer) {
        this.TCPServer = TCPServer;
        this.controller = controller;
        this.playerName = null;
    }

    public GameController getController() {
        return controller;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public TCPServer getServer() {
        return TCPServer;
    }

    public void setPlayerName(String playerName) {
        try {
            if (TCPServer.getNames().contains(playerName)) {
                throw new NameAlreadyUsedException(playerName + " already used, type another one");
            }
            this.playerName = playerName;
        } catch (NameAlreadyUsedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    protected synchronized void joinServer() {
        TCPServer.subscribe(this);
    }

    private synchronized void leaveServer() {
        TCPServer.unsubscribe(this);
    }

    protected void disconnect() {
        try {
            if(playerName != null)
                controller.disconnect(playerName);
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        leaveServer();
    }

    public void setUI(String UI, GameController controller){}
}
