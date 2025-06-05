package org.example.ServerPkg.ConnectionsPkg;

import org.example.UIPkg.GameUpdater;

import java.rmi.RemoteException;
import java.util.List;

public interface Server {

    boolean getIfSubscribed(Handler handler) throws RemoteException;

    GameUpdater getGameUpdater(String name) throws RemoteException;

    void notifyClient(String name, String message) throws RemoteException;

    void notifyBroadcast(List<String> exclude, String message) throws RemoteException;

    void notifyLobbyCreated(String name) throws RemoteException;

    void notifyLobbyJoined(String name) throws RemoteException;

    void acceptCreateLobby(String name) throws RemoteException;

    void updatePlayerList(String exclude) throws RemoteException;

    void notifyGameStarted() throws RemoteException;

    Handler getHandlerByName(String name) throws RemoteException;
}
