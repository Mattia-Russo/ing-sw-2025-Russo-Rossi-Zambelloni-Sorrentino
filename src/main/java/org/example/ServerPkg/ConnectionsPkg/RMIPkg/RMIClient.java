package org.example.ServerPkg.ConnectionsPkg.RMIPkg;

import org.example.MessagePkg.Message;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends UnicastRemoteObject implements RMIClientInterface {
    private final RMIServerInterface server;

    public RMIClient(String host) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(host, 1099);
            server = (RMIServerInterface) registry.lookup("GameServer");
            server.registerClient(this);
        } catch (Exception e) {
            throw new RemoteException("Errore nella connessione al server", e);
        }
    }

    @Override
    public void receiveMessage(Message message) throws RemoteException {
        // Qui gestisci il messaggio ricevuto dal server
        System.out.println("Messaggio ricevuto dal server");
    }

    public void sendMessage(Message message) throws RemoteException {
        server.sendMessage(message);
    }

    public void disconnect() throws RemoteException {
        server.unregisterClient(this);
    }
}