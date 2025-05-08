package org.example.ServerPkg.ConnectionsPkg.RMIPkg;

import org.example.ClientPkg.RMIClient;
import org.example.ClientPkg.RMIServerInterface;
import org.example.MessagePkg.Message;
import org.example.ServerPkg.ConnectionsPkg.TCPPkg.ClientProxy;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.UI.GameUpdater;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {
    private ArrayList<RMIClientInterface> clients;
    private List<GameUpdater> clientToUpdate;
    private final GameController controller;
    private static final int RMI_PORT = 3600;

    public RMIServer(GameController controller) throws RemoteException {
        super();
        this.controller = controller;
        this.clients = new ArrayList<>();
        clientToUpdate = new ArrayList<>()
    }

    private void startRMIServer() {
        try {
            Registry registry = LocateRegistry.createRegistry(RMI_PORT);
            registry.rebind("GameServer", this);
            System.out.println("RMI Server is running on " + RMI_PORT + " port");

        } catch (RemoteException e) {
            System.err.println("Error starting RMI server: " + e.getMessage());
        }
    }

    @Override
    public synchronized void registerClient(RMIClient client) throws RemoteException {
        System.out.println(client.getPlayerName() + " subscribed");
        clients.add(client);
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        controller.addMessage(message);
    }

    @Override
    public void unregisterClient(RMIClient client) throws RemoteException {
        if(clients.remove(client)) {
            System.out.println(client.getPlayerName() + " unsubscribed");
        }
    }

    public synchronized List<String> getNames(){
        try {
            return clients.stream().map(RMIClientInterface::getPlayerName).toList();
        } catch (RemoteException e){
            System.err.println("Error getting names: " + e.getMessage());
        }
    }

    public boolean getIfSubscribed(RMIClient client){
        return clients.contains(client);
    }

    public GameController getController(){
        return controller;
    }
}