package org.example.ClientPkg;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.Model.Game;

import java.rmi.RemoteException;

public interface Client {

    public void updateGame(Game game);

    void disconnect() throws RemoteException;

    void sendMessage(Message message) throws RemoteException;
}
