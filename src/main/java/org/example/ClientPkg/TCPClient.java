package org.example.ClientPkg;

import org.example.MessagePkg.Message;
import org.example.MessagePkg.PingMessage;
import org.example.MessagePkg.PongMessage;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.UI.UI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPClient {
    private Socket socket;
    private final ObjectOutputStream out;
    private ObjectInputStream in;
    private UI userInterface;
    protected String serverAlive = "initial";

    public TCPClient(String serverAddress, int port, UI userInterface) throws IOException {
        this.userInterface = userInterface;

        // Connessione al server
        this.socket = new Socket(serverAddress, port);
        // Configurazione degli stream
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        System.out.println("Connesso al server TCP.");

        // Avvia un thread per ascoltare i messaggi in arrivo dal server
        startListening();
    }

    // Thread di ascolto per i messaggi in arrivo dal server
    private void startListening() {
        Thread listenerThread = new Thread(() -> {
            try {
                while (true) {

                    // Leggi l'oggetto inviato dal server
                    Object obj = in.readObject();

                    if (obj instanceof PongMessage pong) {
                        serverAlive = pong.key();
                    } else if (obj instanceof PingMessage ping) {
                        sendMessage(new PongMessage(ping.key()));
                    } else {
                        userInterface.addGameUpdate((GameView) obj);
                    }
                }
            } catch (Exception e) {
                System.err.println("Connessione al server interrotta: " + e.getMessage());
            }
        });
        listenerThread.start();
    }



    // da implementare
    public void updateGame(Game game){}

    public void disconnect() {

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
