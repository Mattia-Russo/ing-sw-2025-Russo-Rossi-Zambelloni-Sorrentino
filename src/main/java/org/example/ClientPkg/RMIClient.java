package org.example.ClientPkg;

import org.example.MessagePkg.Message;
import org.example.MessagePkg.MessageGenerator;
import org.example.ServerPkg.ConnectionsPkg.RMIPkg.RMIClientInterface;
import org.example.ServerPkg.Model.Game;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RMIClient extends UnicastRemoteObject implements RMIClientInterface {
    private final RMIServerInterface server;
    private String playerName;
    private MessageGenerator msgGen;

    public RMIClient(String host) throws RemoteException {
        this.playerName = null;
        msgGen = new MessageGenerator();
        try {
            Registry registry = LocateRegistry.getRegistry(host, 3600);
            server = (RMIServerInterface) registry.lookup("GameServer");
            server.registerClient(this);

            startKeyboardListener();
        } catch (Exception e) {
            throw new RemoteException("Error connecting to server", e);
        }
    }

    private void startKeyboardListener() {

        Thread listenerThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type a command: ");
            while (true) {
                try {
                    // Legge l'input dell'utente
                    String input = scanner.nextLine();

                    /*if (input.equalsIgnoreCase("exit")) { // Comando per uscire
                        System.out.println("Disconnessione in corso...");
                        unregisterClient();
                        System.exit(0);
                    }*/

                    // Crea un messaggio e lo invia al server
                    Message message = msgGen.create(/*message type, object*/);
                    server.sendMessage(message);

                } catch (Exception e) {
                    System.out.println("Error sending the command: " + e.getMessage());
                }
            }
        });
        listenerThread.setDaemon(true); // Permette al thread di terminare con il programma principale
        listenerThread.start();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String name) {
        if(playerName == null){
            this.playerName = name;
        } else {
            System.out.println("Player name already set");
        }
    }

    public void sendMessage(Message message) throws RemoteException {
        server.sendMessage(message);
    }

    public void disconnect() throws RemoteException {
        server.unregisterClient(this);
    }
}