package org.example.ClientPkg;

import org.example.MessagePkg.*;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.UIPkg.Client;
import org.example.UIPkg.GUIPkg.GUI;
import org.example.UIPkg.TUI;
import org.example.UIPkg.UI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.util.*;


public class TCPClient implements Client {

    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final UI userInterface;
    private final MessageGenerator msgGen;
    private long serverAlive;
    private String playerName;

    public TCPClient(String serverAddress, int port, String UI) throws IOException {
        this.msgGen = new MessageGenerator();
        this.serverAlive = System.currentTimeMillis();

        // Connessione al server
        this.socket = new Socket(serverAddress, port);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        this.in = new ObjectInputStream(socket.getInputStream());

        if(UI.equals("tui")) {
            this.userInterface = new TUI(this);
        } else {
            this.userInterface = new GUI(this);
            Thread guiThread = new Thread(userInterface::startGui);
            guiThread.start();
        }
        boolean nameSet = false;
        while(!nameSet){
            userInterface.askName();
            userInterface.readName();
            try {
                Object obj = in.readObject();
                if (obj instanceof NotifyClientMessage) {
                    if (((NotifyClientMessage) obj).getMessage().equals("true")) {
                        nameSet = true;
                        userInterface.onNameAccepted();
                    } else {
                        userInterface.printNameInvalid();
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Error reading NotifyClientMessage: " + e.getMessage());
            }
        }

        System.out.println("Connected to TCP server.");

        //startPingThread();
        //checkServerConnection();
        startListening();
        if(UI.equals("tui")){
            startKeyboardListener();
        }
    }

    private void checkServerConnection(){
        Thread checkClient = new Thread(() -> {
            try {
                while (!socket.isClosed()) {
                    if (System.currentTimeMillis() - serverAlive > 14999) {
                        disconnect();
                    }
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                System.out.println("Error checking client connection");
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        checkClient.setDaemon(true);
        checkClient.start();
    }

    private void startPingThread() {
        Thread pingThread = new Thread(() -> {
            try {
                while (!socket.isClosed()) {
                    PingMessage pingMessage = new PingMessage();

                    sendMessage(pingMessage);

                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                System.err.println("Ping thread interrupted: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error sending PingMessage: " + e.getMessage());
            }
        });
        pingThread.setDaemon(false);
        pingThread.start();
    }

    private void startListening() {
        Thread listenerThread = new Thread(() -> {
            try {
                while (!socket.isClosed()) {
                    Object obj = in.readObject();
                    if (obj instanceof Message message) {
                        message.setClient(this);
                        if (obj instanceof PongMessage) {
                            serverAlive = System.currentTimeMillis();
                        } else if (obj instanceof NotifyClientMessage notifyClientMessage){
                            userInterface.manageNotification(notifyClientMessage);
                        } else {
                            message.handle(null, this.playerName);
                        }
                    } else if (obj instanceof GameView) {
                        userInterface.addGameUpdate((GameView) obj);
                    } else {
                        System.err.println("Object not recognized: " + obj.getClass().getName());
                    }
                }
            } catch (StreamCorruptedException e) {
                System.err.println("Error: stream corrupted");
            } catch (ClassNotFoundException e) {
                System.err.println("Error: class not found during deserialization, " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                System.err.println("I/O error during deserialization: " + e.getMessage());
                e.printStackTrace();
            }
        });
        listenerThread.setDaemon(false);
        listenerThread.start();
    }

    private void startKeyboardListener() {

        Thread KeyBoardListenerThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            System.out.println("""
                    Type a command:
                       create_lobby int1 int2 int3 -> int1 is number of player, int2 is the level of the shipboard, int3 is the game mode
                       join_lobby -> if you want to join an existing lobby
                       start_game -> if you want to start the game
                    """);

            while (!socket.isClosed()) {
                try {
                    String input = scanner.nextLine();

                    // Dividi la riga di ingresso in parole
                    String[] words = input.split("\\s+"); // Divide in base a uno o più spazi

                    // Salva la prima parola se esiste
                    String cmd = words.length > 0 ? words[0] : "";

                    if (Objects.equals(cmd, "disconnect")) {
                        disconnect();
                        return;
                    }

                    List<String> args = new ArrayList<>(Arrays.asList(words).subList(1, words.length));

                    Message message = msgGen.generate(cmd, args);
                    message.setClient(this);
                    sendMessage(message);
                } catch (Exception e) {
                    System.out.println("Try again");
                }
            }
        });
        KeyBoardListenerThread.setDaemon(false);
        KeyBoardListenerThread.start();
    }

    public void disconnect() throws IOException {
        try {
            out.flush();
            out.close();
            in.close();
        } catch (IOException e) {
            System.err.println("Error closing client stream: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }

    }

    public void registerName(String name){
        this.playerName = name;
        Message message = msgGen.generate("set_name", List.of(name));
        if(message != null){
            sendMessage(message);
        }
    }

    public void sendMessage(Message message){
        synchronized(this.out){
            try {
                out.reset();
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                System.out.println("Error sending message from client: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public MessageGenerator getMessageGenerator(){
        return this.msgGen;
    }

    public UI getUserInterface(){
        return this.userInterface;
    }

    public void notifyCreatingLobby(){
        Message message = msgGen.generate("creating_lobby", null);
        sendMessage(message);
    }

    @Override
    public String getPlayerName(){
        return this.playerName;
    }
}