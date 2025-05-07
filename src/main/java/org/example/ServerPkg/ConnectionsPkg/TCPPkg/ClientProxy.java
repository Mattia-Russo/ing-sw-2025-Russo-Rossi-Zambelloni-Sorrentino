package org.example.ServerPkg.ConnectionsPkg.TCPPkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.Points;

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
        this.playerName = playerName;
    }

    public void joinServer(String name) {
        try {
            if (this.playerName == null) {
                System.out.println("You need to set your name first");
            } else {
                synchronized (TCPServer) {
                    if (TCPServer.getNames().contains(name)) {
                        throw new NameAlreadyUsedException(name + " already used, type another one");
                    }
                    this.playerName = name;
                    TCPServer.subscribe(this);
                }
                System.out.println(name + "joined server successfully");
            }
        } catch (NameAlreadyUsedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // entra in automatico quando creiamo il tcpClient
    public void leaveServer() {
        if (this.playerName == null) {
            System.out.println("You need to set your name first");
        } else{
            synchronized (TCPServer) {
                if (!TCPServer.getNames().contains(this.playerName)) {
                    System.out.println("You need to join first");
                } else {
                    TCPServer.unsubscribe(this);
                    System.out.println(playerName + " left server successfully");
                }
            }
        }
    }

    protected void disconnect() {
        try {
            if(playerName != null)
                controller.disconnect(playerName);
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }

        TCPServer.unsubscribe(this);
    }
}
