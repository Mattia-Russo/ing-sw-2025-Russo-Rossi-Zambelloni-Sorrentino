package org.example.ServerPkg.ConnectionsPkg.RMIPkg;

import org.example.ClientPkg.RMIServerInterface;
import org.example.MessagePkg.Message;
import org.example.ServerPkg.ConnectionsPkg.Settings;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Game;
import org.example.UIPkg.GameUpdater;
import org.example.UIPkg.RMIVirtualView;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {
    private final GameController controller;
    private final Map<RMIClientInterface, Long> clients;
    private Map<String, GameUpdater> gameUpdater;

    public RMIServer(GameController controller) throws RemoteException {
        super();
        this.controller = controller;
        this.gameUpdater = new ConcurrentHashMap<>();
        this.clients = new ConcurrentHashMap<>();
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

    public void checkConnection() {
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
    public void registerClient(RMIClientInterface client) throws RemoteException {
        clients.put(client, System.currentTimeMillis());
        setGameUpdater(client);
        controller.getNames().add(client.getPlayerName());
        System.out.println(client.getPlayerName() + " subscribed");
    }

    @Override
    public void sendMessage(Message message, String name) throws RemoteException {
        message.setServer(this);
        message.setClient(name);
        controller.addMessage(message);
    }

    public RMIClientInterface getClientByName(String name){
        for(RMIClientInterface client : clients.keySet()){
            try {
                if(client.getPlayerName().equals(name))
                    return client;
            } catch (RemoteException e) {
                System.err.println("Error retrieving player name: " + e.getMessage());
            }
        }
        System.out.println("Client not found");
        return null;
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
                .filter(Objects::nonNull) // Esclude eventuali nomi null (in caso di eccezioni)
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
        } else {
            System.out.println("Client not registered, cannot update timestamp");
        }
    }

    public void setGameUpdater(RMIClientInterface client) throws RemoteException {
        this.gameUpdater.put(client.getPlayerName() ,new RMIVirtualView(client));
    }

    public void addGameUpdater(GameController controller, String name){
        controller.addGameUpdater(gameUpdater.get(name), name);
    }

    @Override
    public boolean checkName(String name) throws RemoteException {
        return controller.checkName(name);
    }
}