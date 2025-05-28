package org.example.ClientPkg;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ConnectionsPkg.Handler;
import org.example.ServerPkg.ConnectionsPkg.RMIPkg.RMIClientInterface;
import org.example.ServerPkg.ConnectionsPkg.Server;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.UIPkg.GameUpdater;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIServerInterface extends Remote {

    void registerClient(RMIClientInterface client) throws RemoteException;

    void receiveMessage(Message message, Handler handler) throws RemoteException;

    void unregisterClient(RMIClientInterface client) throws RemoteException;

    GameController getController() throws RemoteException;

    void updateClientAlive(RMIClientInterface client) throws RemoteException;

    boolean checkName(String name) throws RemoteException;

    void checkConnection() throws RemoteException;

    void notifyCreatingLobby(String name) throws RemoteException;
}
// tutti i metodi chiamabili dal client che risiedono sul server