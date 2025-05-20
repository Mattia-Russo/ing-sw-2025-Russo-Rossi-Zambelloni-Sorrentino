package org.example.ClientPkg;

import org.example.MessagePkg.Message;
import org.example.MessagePkg.MessageGenerator;
import org.example.ServerPkg.ConnectionsPkg.RMIPkg.RMIClientInterface;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.UIPkg.*;

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
    private final MessageGenerator msgGen;
    private long serverAlive;
    private final UI userInterface;

    public RMIClient(String host, UI UI) throws RemoteException {
        this.userInterface = UI;
        msgGen = new MessageGenerator();
        serverAlive = System.currentTimeMillis();
        try {
            Registry registry = LocateRegistry.getRegistry(host, 3600);
            server = (RMIServerInterface) registry.lookup("GameServer");

            Scanner scanner = new Scanner(System.in);
            while(this.playerName == null){
                System.out.println("Type your name:");
                String input = scanner.nextLine();
                this.setPlayerName(input);
            }
            server.registerClient(this);

            System.out.println(playerName + " is connected to RMI server.");

            startUpdateThread();
            //checkConnection();
            startKeyboardListener();
        } catch (Exception e) {
            throw new RemoteException("Error connecting to server", e);
        }
    }

    private void startUpdateThread() {
        Thread UpdateThread = new Thread(() -> {
            try {
                while (true) {
                    server.updateClientAlive(this);
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                System.err.println("Update server connection thread interrupted: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error sending connection update to server: " + e.getMessage());
            }
        });
        UpdateThread.setDaemon(false);  // Usa un thread daemon, così termina automaticamente quando l'applicazione si chiude
        UpdateThread.start();  // Avvia il thread
    }

    private void checkConnection() {
        Thread checkClient = new Thread(() -> {
            try {
                while (true) {
                    if (System.currentTimeMillis() - serverAlive > 14999) {
                        disconnect();
                    }
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                System.out.println("Error checking client connection");
                Thread.currentThread().interrupt();
            } catch (RemoteException e) {
                System.err.println("Error checking connection update to server: " + e.getMessage());
            }
        });
        checkClient.setDaemon(false);
        checkClient.start();
    }

    private void startKeyboardListener() {

        Thread listenerThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Type a command: ");
            while (true) {
                try {
                    // Legge l'input dell'utente
                    String input = scanner.nextLine();

                    // Dividi la riga di input in parole
                    String[] words = input.split("\\s+"); // Divide in base ad uno o più spazi

                    // Salva la prima parola se esiste
                    String cmd = words.length > 0 ? words[0] : "";

                    // Aggiungi le parole successive alla lista
                    List<String> args = new ArrayList<>(Arrays.asList(words).subList(1, words.length));


                    // Crea un messaggio e lo invia al server
                    Message message = msgGen.generate(cmd, args);
                    sendMessage(message);
                } catch (Exception e) {
                    System.out.println("Error sending the command: " + e.getMessage());
                }
            }
        });
        listenerThread.setDaemon(false);
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
            try {
                if (server.checkName(name)){
                    this.playerName = name;
                } else {
                    System.out.println("Name already taken");
                }
            } catch (RemoteException e){
                e.printStackTrace();
            }
        } else {
            System.out.println("Player name already set");
        }
    }

    private void sendMessage(Message message) throws RemoteException {
        server.sendMessage(message, this.playerName);
    }

    public void disconnect() throws RemoteException {
        server.unregisterClient(this);
    }

    public void updateServerAlive() throws RemoteException {
        this.serverAlive = System.currentTimeMillis();
    }

    public void addGameUpdate(GameView gameView) throws RemoteException {
        userInterface.addGameUpdate(gameView);
    }
}