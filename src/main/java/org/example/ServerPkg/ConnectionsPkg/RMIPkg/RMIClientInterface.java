package org.example.ServerPkg.ConnectionsPkg.RMIPkg;

import org.example.ClientPkg.RMIServerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {

    String getPlayerName() throws RemoteException;

    RMIServerInterface getServer() throws RemoteException;

    void updateServerAlive() throws RemoteException;

    void disconnect() throws RemoteException;

}

// tutti i metodi chiamabili dal server che risiedono sul client