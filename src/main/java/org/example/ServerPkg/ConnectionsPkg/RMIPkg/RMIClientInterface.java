package org.example.ServerPkg.ConnectionsPkg.RMIPkg;

import org.example.ClientPkg.Client;
import org.example.MessagePkg.Message;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Client, Remote {
    // Metodo che il server usa per inviare messaggi al client
    void receiveMessage(Message message) throws RemoteException;
}