package org.example.ClientPkg;

import org.example.MessagePkg.Message;
import org.example.MessagePkg.MessageGenerator;
import org.example.MessagePkg.PingMessage;
import org.example.MessagePkg.PongMessage;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.UIPkg.UI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class TCPClient {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final UI userInterface;
    private MessageGenerator msgGen;
    private long serverAlive;

    public TCPClient(String serverAddress, int port, UI userInterface) throws IOException {
        this.userInterface = userInterface;
        this.msgGen = new MessageGenerator();
        this.serverAlive = System.currentTimeMillis();

        // Connessione al server
        this.socket = new Socket(serverAddress, port);
        // Configurazione degli stream
        System.out.println("Config client out");
        this.out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        System.out.println("Config client in");
        this.in = new ObjectInputStream(socket.getInputStream());

        System.out.println("Connesso al server TCP.");

        // Avvia un thread per ascoltare i messaggi in arrivo dal server
        startPingThread();
        startListening();
        startKeyboardListener();
    }

    private void startPingThread() {
        Thread pingThread = new Thread(() -> {
            try {
                while (!socket.isClosed()) {  // Continua finché il socket è aperto
                    // Crea il messaggio Ping
                    PingMessage pingMessage = new PingMessage();

                    // Invia il messaggio al server
                    sendMessage(pingMessage);
                    System.out.println("Ping from client");

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
                while (true) {
                    // Leggi l'oggetto inviato dal server
                    Object obj = in.readObject();

                    if (obj instanceof PongMessage pong) {
                        System.out.println("pong ricevuto");
                        if(System.currentTimeMillis() - serverAlive > 14999){
                            disconnect();
                        } else {
                            serverAlive = System.currentTimeMillis();
                        }
                    } else {
                        userInterface.addGameUpdate((GameView) obj);
                    }
                }
            } catch (Exception e) {
                System.err.println("Connection with server interrupted: " + e.getMessage());
            }
        });
        listenerThread.setDaemon(false);
        listenerThread.start();
    }

    private void startKeyboardListener() {

        Thread KeyBoardListenerThread = new Thread(() -> {
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
        socket.close();
    }

    public void registerName(String name){
        Message message = msgGen.generate("set_name", List.of(name));
        sendMessage(message); ///aggiunto per mandarlo attraverso la rete
    }

    public void sendMessage(Message message){
        synchronized(this.out){
            try {
                out.reset();
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                System.out.println("Error sending message");
            }
        }
    }
}
