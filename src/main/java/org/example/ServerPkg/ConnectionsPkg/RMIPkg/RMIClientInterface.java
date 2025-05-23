package org.example.ServerPkg.ConnectionsPkg.RMIPkg;

import org.example.ClientPkg.RMIServerInterface;
import org.example.MessagePkg.Message;
import org.example.ServerPkg.ConnectionsPkg.Handler;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.UIPkg.GameUpdater;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote, Handler {

    String getPlayerName() throws RemoteException;

    RMIServerInterface getServer() throws RemoteException;

    void updateServerAlive() throws RemoteException;

    void disconnect() throws RemoteException;

    void addGameUpdate(GameView gameView) throws RemoteException;

    void notifyClient(String message) throws RemoteException;

    void notifyLobbyCreated() throws RemoteException;

    void notifyLobbyJoined() throws RemoteException;

    void setGameUpdater() throws RemoteException;

}

// tutti i metodi chiamabili dal server che risiedono sul client