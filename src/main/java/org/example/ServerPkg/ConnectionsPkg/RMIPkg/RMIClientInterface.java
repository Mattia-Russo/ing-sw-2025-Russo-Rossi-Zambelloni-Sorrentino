package org.example.ServerPkg.ConnectionsPkg.RMIPkg;

import org.example.MessagePkg.Message;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {
    // Metodo che il server usa per inviare messaggi al client
}

// tutti i metodi chiamabili dal server che risiedono sul client