package org.example.ClientPkg;

import org.example.MessagePkg.*;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.UIPkg.GUI;
import org.example.UIPkg.TUI;
import org.example.UIPkg.UI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.util.*;

public class TCPClient {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final UI userInterface;
    private final MessageGenerator msgGen;
    private long serverAlive;
    private boolean nameSet = false;

    public TCPClient(String serverAddress, int port, UI UI) throws IOException {
        this.userInterface = UI;
        this.msgGen = new MessageGenerator();
        this.serverAlive = System.currentTimeMillis();

        // Connessione al server
        this.socket = new Socket(serverAddress, port);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        this.in = new ObjectInputStream(socket.getInputStream());

        Scanner scanner = new Scanner(System.in);

        while(!nameSet){
            System.out.println("Type your name: ");
            String input = scanner.nextLine();
            List<String> args = new ArrayList<>();
            args.add(input);
            System.out.println("Name read: " + args.getFirst());
            this.registerName(args);

            try {
                Object obj = in.readObject();
                if (obj instanceof NotifyClientMessage) {
                    if (((NotifyClientMessage) obj).getMessage().equals("true")) {
                        nameSet = true;
                    } else {
                        System.out.println("Name already taken");
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Error reading NotifyClientMessage: " + e.getMessage());
            }
        }

        System.out.println("is connected to TCP server.");

        // Avvia un thread per ascoltare i messaggi in arrivo dal server
        startPingThread();
        //checkServerConnection();
        startListening();
        startKeyboardListener();
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
                while (!socket.isClosed()) {  // Continua finché il socket è aperto
                    // Crea il messaggio Ping
                    PingMessage pingMessage = new PingMessage();

                    // Invia il messaggio al server
                    sendMessage(pingMessage);
                    //System.out.println("Sending Ping from client");

                    // Attendi 5 secondi prima del prossimo invio
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                System.err.println("Ping thread interrupted: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error sending PingMessage: " + e.getMessage());
            }
        });
        pingThread.setDaemon(false);
        pingThread.start();  // Avvia il thread
    }

    // Thread di ascolto per i messaggi in arrivo dal server
    private void startListening() {
        Thread listenerThread = new Thread(() -> {
            try {
                while (!socket.isClosed()) {
                    Object obj = in.readObject();
                    if (obj instanceof Message) {
                        if (obj instanceof PongMessage) {
                            serverAlive = System.currentTimeMillis();
                        } else if (obj instanceof NotifyClientMessage notifyClientMessage){
                            System.out.println(notifyClientMessage.getMessage());
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

            while (!socket.isClosed()) {
                try {
                    System.out.println("Type a command:\n" +
                            "   create_lobby int1 int2 int3 -> int1 is number of player, int2 is the level of the shipboard, int3 is the game mode\n" +
                            "   join_lobby -> if you want to join an existing lobby\n\n");
                    String input = scanner.nextLine();

                    // Dividi la riga di input in parole
                    String[] words = input.split("\\s+"); // Divide in base ad uno o più spazi

                    // Salva la prima parola se esiste
                    String cmd = words.length > 0 ? words[0] : "";

                    if (Objects.equals(cmd, "disconnect")) {
                        disconnect();
                        return;
                    }

                    // Aggiungi le parole successive alla lista
                    List<String> args = new ArrayList<>(Arrays.asList(words).subList(1, words.length));


                    // Crea un messaggio e lo invia al server
                    Message message = msgGen.generate(cmd, args);
                    if (message != null) {
                        sendMessage(message);
                    }
                } catch (Exception e) {
                    System.out.println("Error sending the command: " + e.getMessage());
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

    public void registerName(List<String> args){
        Message message = msgGen.generate("set_name", args);
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
            }
        }
    }
}