package org.example.ServerPkg.ConnectionsPkg.RMIPkg;

import org.example.ClientPkg.RMIServerInterface;
import org.example.MessagePkg.Message;
import org.example.ServerPkg.ControllerPkg.GameController;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {
    private final Map<RMIClientInterface, RMIClientProxy> clientProxies;
    private final GameController controller;
    private static final int RMI_PORT = 1099;

    public RMIServer(GameController controller) throws RemoteException {
        super();
        this.controller = controller;
        this.clientProxies = new ConcurrentHashMap<>();
        startRMIServer();
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
    public void registerClient(RMIClientInterface client) throws RemoteException {
        RMIClientProxy proxy = new RMIClientProxy(controller, this, client);
        clientProxies.put(client, proxy);
        System.out.println("Nuovo client RMI registrato");
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        // Il messaggio viene gestito come nel TCP
        message.setProxy(clientProxies.get(message.getProxy()));
        controller.addMessage(message);
    }

    @Override
    public void unregisterClient(RMIClientInterface client) throws RemoteException {
        clientProxies.remove(client);
        System.out.println("Client RMI rimosso");
    }
}