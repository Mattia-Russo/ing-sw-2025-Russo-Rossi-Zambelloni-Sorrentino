package org.example.ServerPkg.ConnectionsPkg.RMIPkg;

import org.example.ClientPkg.RMIServerInterface;
import org.example.MessagePkg.Message;
import org.example.ServerPkg.ConnectionsPkg.Settings;
import org.example.ServerPkg.ControllerPkg.GameController;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {
    private final GameController controller;
    private final Map<RMIClientInterface, Long> clients;

    public RMIServer(GameController controller) throws RemoteException {
        super();
        this.controller = controller;
        this.clients = new ConcurrentHashMap<>();
        checkConnection();
    }

    public void startRMIServer() {
        try {
            System.setProperty("java.rmi.server.hostname", Settings.SERVER_NAME);
            Registry registry = LocateRegistry.createRegistry(Settings.RMI_PORT);
            registry.rebind("GameServer", this);
            System.out.println("RMI Server is running on " + Settings.RMI_PORT + " port");

        } catch (RemoteException e) {
            System.err.println("Error starting RMI server: " + e.getMessage());
        }
    }

    private void checkConnection() {
        Thread checkClient = new Thread(() -> {
            while (true) {
                try {
                    long currentTime = System.currentTimeMillis();

                    clients.entrySet().removeIf(entry -> {
                        if (currentTime - entry.getValue() > 14999) {
                            try {
                                entry.getKey().disconnect();
                                System.out.println("Client disconnected due to inactivity: " + entry.getKey());
                            } catch (RemoteException e) {
                                System.err.println("Error disconnecting client: " + e.getMessage());
                            }
                            return true;
                        }
                        return false;
                    });
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("Error checking client connection");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        checkClient.setDaemon(true); // Usa un thread daemon, così termina automaticamente con l'applicazione
        checkClient.start();
    }

    @Override
    public synchronized void registerClient(RMIClientInterface client) throws RemoteException {
        System.out.println(client.getPlayerName() + " subscribed");
        clients.put(client, System.currentTimeMillis());
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        controller.addMessage(message);
    }

    @Override
    public synchronized void unregisterClient(RMIClientInterface client) throws RemoteException {
        if (clients.remove(client) != null) { // Rimuove il client dalla mappa
            System.out.println(client.getPlayerName() + " unsubscribed");
        } else {
            System.out.println("Client not found for unsubscription");
        }
    }

    public synchronized List<String> getNames() {
        return clients.keySet().stream()
                .map(client -> {
                    try {
                        return client.getPlayerName();
                    } catch (RemoteException e) {
                        System.err.println("Error retrieving player name: " + e.getMessage());
                        return null;
                    }
                })
                .filter(name -> name != null) // Esclude eventuali nomi null (in caso di eccezioni)
                .toList();
    }

    public boolean getIfSubscribed(RMIClientInterface client) throws RemoteException {
        return clients.containsKey(client);
    }

    public GameController getController() throws RemoteException {
        return controller;
    }

    public void updateClientAlive(RMIClientInterface client) throws RemoteException {
        if (clients.containsKey(client)) {
            clients.put(client, System.currentTimeMillis());
            client.updateServerAlive();
            System.out.println("RMI server alive for client: " + client.getPlayerName());
        } else {
            System.out.println("Client not registered, cannot update timestamp");
        }
    }
}