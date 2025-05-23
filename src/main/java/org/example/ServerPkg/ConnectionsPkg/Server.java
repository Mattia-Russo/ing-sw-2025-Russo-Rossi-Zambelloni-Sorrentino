package org.example.ServerPkg.ConnectionsPkg;

import org.example.UIPkg.GameUpdater;

import java.rmi.RemoteException;

public interface Server {

    boolean getIfSubscribed(Handler handler) throws RemoteException;

    GameUpdater getGameUpdater(String name) throws RemoteException;

    void notifyClient(String name, String message) throws RemoteException;
}
