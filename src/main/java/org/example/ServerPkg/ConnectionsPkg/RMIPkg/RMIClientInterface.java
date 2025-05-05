package org.example.ServerPkg.ConnectionsPkg.RMIPkg;

import org.example.MessagePkg.Message;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {
    // Metodo che il server usa per inviare messaggi al client
    void receiveMessage(Message message) throws RemoteException;
}