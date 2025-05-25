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
    // Metodo che il client usa per registrarsi
    void registerClient(RMIClientInterface client) throws RemoteException;

    // Metodo che il client usa per inviare messaggi al server
    void receiveMessage(Message message, String name) throws RemoteException;

    // Metodo per disconnettersi
    void unregisterClient(RMIClientInterface client) throws RemoteException;

    GameController getController() throws RemoteException;

    void updateClientAlive(RMIClientInterface client) throws RemoteException;

    boolean checkName(String name) throws RemoteException;

    void checkConnection() throws RemoteException;

    void notifyCreatingLobby();
}
// tutti i metodi chiamabili dal client che risiedono sul server