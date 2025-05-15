package org.example.ClientPkg;

import org.example.MessagePkg.Message;
import org.example.MessagePkg.MessageGenerator;
import org.example.MessagePkg.PingMessage;
import org.example.MessagePkg.PongMessage;
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

    public TCPClient(String serverAddress, int port, String name, String UI) throws IOException {
        if(UI.equalsIgnoreCase("gui")){
            this.userInterface = new GUI();
        } else {
            this.userInterface = new TUI();
        }
        this.msgGen = new MessageGenerator();
        this.serverAlive = System.currentTimeMillis();

        // Connessione al server
        this.socket = new Socket(serverAddress, port);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        this.in = new ObjectInputStream(socket.getInputStream());

        System.out.println("Connesso al server TCP.");

        // Avvia un thread per ascoltare i messaggi in arrivo dal server
        startPingThread();
        checkServerConnection();
        startListening();
        startKeyboardListener();

        List<String> args = new ArrayList<>();
        args.add(name);
        this.registerName(args);
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
                        }
                    } else if (obj instanceof GameView) {
                        System.out.println("GameView updated for tcp.");
                        userInterface.addGameUpdate((GameView) obj);
                        System.out.println("GameView added to the UI queue.");
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
            System.out.println("Type a command: ");
            while (!socket.isClosed()) {
                try {
                    // Legge l'input dell'utente
                    String input = scanner.nextLine();

                    // Crea una lista per contenere le parole successive

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
                    if(message != null){
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