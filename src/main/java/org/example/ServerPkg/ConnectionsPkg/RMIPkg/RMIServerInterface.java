package org.example.ServerPkg.ConnectionsPkg.RMIPkg;

import org.example.MessagePkg.Message;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {
    // Metodo che il client usa per registrarsi
    void registerClient(RMIClientInterface client) throws RemoteException;

    // Metodo che il client usa per inviare messaggi al server
    void sendMessage(Message message) throws RemoteException;

    // Metodo per disconnettersi
    void unregisterClient(RMIClientInterface client) throws RemoteException;
}