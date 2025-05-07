package org.example.ClientPkg;

import org.example.MessagePkg.Message;
import org.example.MessagePkg.MessageGenerator;
import org.example.ServerPkg.ConnectionsPkg.RMIPkg.RMIClientInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

                    // Crea una lista per contenere le parole successive

                    // Dividi la riga di input in parole
                    String[] words = input.split("\\s+"); // Divide in base ad uno o più spazi

                    // Salva la prima parola se esiste
                    String cmd = words.length > 0 ? words[0] : "";

                    // Aggiungi le parole successive alla lista
                    List<String> args = new ArrayList<>(Arrays.asList(words).subList(1, words.length));


                    // Crea un messaggio e lo invia al server
                    Message message = msgGen.generate(cmd, args);
                    if(message != null){
                        message.setClient(this);
                        server.sendMessage(message);
                    }
                } catch (Exception e) {
                    System.out.println("Error sending the command: " + e.getMessage());
                }
            }
        });
        listenerThread.setDaemon(true); // Permette al thread di terminare con il programma principale
        listenerThread.start();
    }

    public RMIServerInterface getServer() {
        return server;
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