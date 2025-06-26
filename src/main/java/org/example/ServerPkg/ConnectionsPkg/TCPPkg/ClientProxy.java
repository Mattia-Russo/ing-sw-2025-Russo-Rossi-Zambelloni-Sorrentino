package org.example.ServerPkg.ConnectionsPkg.TCPPkg;

import org.example.MessagePkg.Message;
import org.example.MessagePkg.MessageGenerator;
import org.example.ServerPkg.ConnectionsPkg.Handler;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.UIPkg.GameUpdater;

import java.rmi.RemoteException;

public abstract class ClientProxy implements Handler {
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
        notifyClient("true");
        if(controller.getFileLoaded()){
            controller.setServer(this.playerName, this.TCPServer);
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

    public void setGameUpdater(){}

    public void notifyClient(String message){}

    public void sendMessage(Message message) {}

    public GameUpdater getGameUpdater(){
        return null;
    }

    public void notifyLobbyCreated() {}

    public void notifyLobbyJoined() {}

    public void acceptCreateLobby() {}

    public MessageGenerator getMsgGen(){return null;}

    public void notifyGameStarted(){}

    public void goToAddAlien() throws RemoteException {}
}
