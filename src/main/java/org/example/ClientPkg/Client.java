package org.example.ClientPkg;

import org.example.MessagePkg.Message;

import java.rmi.RemoteException;

public interface Client {

    void disconnect() throws RemoteException;

    void sendMessage(Message message) throws RemoteException;
}
