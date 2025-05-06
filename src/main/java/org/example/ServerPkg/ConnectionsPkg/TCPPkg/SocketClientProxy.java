package org.example.ServerPkg.ConnectionsPkg.TCPPkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.MessagePkg.Message;
import org.example.MessagePkg.PingMessage;
import org.example.MessagePkg.PongMessage;
import org.example.ServerPkg.Model.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SocketClientProxy extends ClientProxy implements Runnable {
    private final Socket socket;
    private final ObjectOutputStream out;
    protected String lastHeartBeat = "initial"; //stringa che serve a stabilire se la connessione è ancora attiva

    //This thread is necessary to handle server input asynchronously
    //This way answering to ping messages is immediate
    //Must be a single thread to avoid synchronization problems
    private final ExecutorService inputHandler = Executors.newSingleThreadExecutor();

    //This thread periodically check the connection to the client
    private final ScheduledExecutorService connectionChecker = Executors.newSingleThreadScheduledExecutor();


    public SocketClientProxy(GameController controller, TCPServer TCPServer, Socket socket) throws IOException {
        super(controller, TCPServer);
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());

        // Start checking connection
        connectionChecker.scheduleAtFixedRate(new ConnectionChecker(this.socket), 1, 4, TimeUnit.SECONDS);
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

    @Override
    public void run() {
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            // Should not happen
            System.out.println("Error getting input stream, returning");
            return;
        }
        while (true) {  // read messages from client
            try {
                Message message = (Message) in.readObject();

                if (message instanceof PongMessage pong) {
                    lastHeartBeat = pong.key();
                } else if (message instanceof PingMessage ping) {
                    sendMessage(new PongMessage(ping.key()));
                } else {
                    message.setProxy(this);
                    getController().addMessage(message);
                }

            } catch (Exception e) {
                System.out.println("Error reading the socket: " + e.getMessage());
                break;
            }
        }
        try {
            socket.close();
        } catch (IOException ignored) {}
        inputHandler.shutdown();
        connectionChecker.shutdown();
        disconnect();
    }

    //This class sends a heartbeat message to the client, if the client doesn't answer with the appropriate message,
    //the socket is closed.
    public class ConnectionChecker implements Runnable {
        private final Socket socket;

        public ConnectionChecker(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            // Generate a random string
            String heartBeat = UUID.randomUUID().toString();

            // Send the string to the server
            sendMessage(new PingMessage(heartBeat));

            // Wait for 1 seconds
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {}

            // If the string did not come back, close the socket
            if(!heartBeat.equals(lastHeartBeat)) {
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }
    }

    public void disconnect() {
        // TBD
        // unsubscribe from server
    }
}