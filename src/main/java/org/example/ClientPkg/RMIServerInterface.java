package org.example.ClientPkg;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ConnectionsPkg.RMIPkg.RMIClientInterface;
import org.example.ServerPkg.ControllerPkg.GameController;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {
    // Metodo che il client usa per registrarsi
    void registerClient(RMIClientInterface client) throws RemoteException;

    // Metodo che il client usa per inviare messaggi al server
    void sendMessage(Message message, String name) throws RemoteException;

    // Metodo per disconnettersi
    void unregisterClient(RMIClientInterface client) throws RemoteException;

    boolean getIfSubscribed(RMIClientInterface client) throws RemoteException;

    GameController getController() throws RemoteException;

    void updateClientAlive(RMIClientInterface client) throws RemoteException;

    boolean checkName(String name) throws RemoteException;
}
// tutti i metodi chiamabili dal client che risiedono sul server