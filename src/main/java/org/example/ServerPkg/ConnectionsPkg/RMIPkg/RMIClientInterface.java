package org.example.ServerPkg.ConnectionsPkg.RMIPkg;

import org.example.ClientPkg.RMIServerInterface;
import org.example.MessagePkg.Message;
import org.example.ServerPkg.ConnectionsPkg.Handler;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.UIPkg.GameUpdater;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIClientInterface extends Remote, Handler {

    String getPlayerName() throws RemoteException;

    RMIServerInterface getServer() throws RemoteException;

    void updateServerAlive() throws RemoteException;

    void disconnect() throws RemoteException;

    void addGameUpdate(GameView gameView) throws RemoteException;

    void notifyClient(String message) throws RemoteException;

    void notifyLobbyCreated(int numPlayers, int shipboardLevel, int gameMode) throws RemoteException;

    void notifyLobbyJoined(int numPlayers, int shipboardLevel, int gameMode, List<String> names) throws RemoteException;

    void setGameUpdater() throws RemoteException;

    void notifyNameAlreadyUsed() throws RemoteException;

    void acceptCreateLobby() throws RemoteException;

    void updatePlayerList(List<String> updatedList) throws RemoteException;

    void notifyGameStarted() throws RemoteException;
}

// tutti i metodi chiamabili dal server che risiedono sul client