package org.example.ClientPkg;

import org.example.MessagePkg.Message;
import org.example.MessagePkg.MessageGenerator;
import org.example.ServerPkg.ConnectionsPkg.RMIPkg.RMIClientInterface;
import org.example.ServerPkg.ConnectionsPkg.Settings;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.UIPkg.*;
import org.example.UIPkg.GUIPkg.GUI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RMIClient extends UnicastRemoteObject implements RMIClientInterface, Client {
    private final RMIServerInterface server;
    private String playerName;
    private final MessageGenerator msgGen;
    private long serverAlive;
    private final UI userInterface;

    public RMIClient(String host, String UI) throws RemoteException {

        msgGen = new MessageGenerator();
        serverAlive = System.currentTimeMillis();

        try {
            Registry registry = LocateRegistry.getRegistry(host, Settings.RMI_PORT);
            server = (RMIServerInterface) registry.lookup("GameServer");

            if(UI.equals("tui")) {
                this.userInterface = new TUI(this);
            } else {
                this.userInterface = new GUI(this);
            }

            while(this.playerName == null){
                userInterface.askName();
                userInterface.readName();
            }

            System.out.println(playerName + " is connected to RMI server.");

            startUpdateThread();
            checkConnection();

            if(UI.equals("tui")){
                startKeyboardListener();
            }
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

    private void checkConnection() throws RemoteException {
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
        server.checkConnection();
    }

    private void startKeyboardListener() {

        Thread listenerThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            System.out.println("""
                    Type a command:
                       create_lobby int1 int2 int3 -> int1 is number of player, int2 is the level of the shipboard, int3 is the game mode
                       join_lobby -> if you want to join an existing lobby
                       start_game -> if you want to start the game
                    """);

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
                    message.setClient(this);
                    sendMessage(message);
                } catch (Exception e) {
                    if(!(e instanceof NullPointerException)){
                        System.out.println("Error sending the command: " + e.getMessage());
                    }
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

    public void registerName(String name) {
        if(playerName == null){
            try {
                if (server.checkName(name)){
                    this.playerName = name;
                    server.registerClient(this);
                    userInterface.onNameAccepted();
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

    @Override
    public void sendMessage(Message message) throws RemoteException {
        server.receiveMessage(message, this.playerName);
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

    public MessageGenerator getMessageGenerator(){
        return this.msgGen;
    }

    @Override
    public void notifyClient(String message) throws RemoteException {
        this.userInterface.printMessage(message);
    }

    public UI getUserInterface(){
        return this.userInterface;
    }

    @Override
    public void notifyLobbyCreated(int numPlayers, int shipboardLevel, int gameMode){
        this.userInterface.onLobbyCreated(this.playerName + "(You)", numPlayers, shipboardLevel, gameMode);
    }

    @Override
    public void notifyLobbyJoined(int numPlayers, int shipboardLevel, int gameMode, List<String> names){
        this.userInterface.onLobbyJoined(names, numPlayers, shipboardLevel, gameMode);
    }

    @Override
    public void setPlayerName(String name){
        this.playerName = name;
    }

    @Override
    public void setGameUpdater(){}

    @Override
    public void notifyNameAlreadyUsed(){
        userInterface.printNameInvalid();
        this.playerName = null;
    }

    @Override
    public void notifyCreatingLobby() throws RemoteException{
        server.notifyCreatingLobby();
    }

    @Override
    public void acceptCreateLobby(){
        userInterface.onCreateLobbyAccepted();
    }

    @Override
    public void updatePlayerList(List<String> updatedList){
        userInterface.onUpdatePlayerList(updatedList);
    }
}