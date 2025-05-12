package org.example.ServerPkg.ConnectionsPkg.TCPPkg;

import org.example.MessagePkg.SetPlayerNameMessage;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.MessagePkg.Message;
import org.example.MessagePkg.PingMessage;
import org.example.MessagePkg.PongMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientProxy extends ClientProxy implements Runnable {
    private final Socket socket;
    private final ObjectOutputStream out;
    private long clientAlive;

    public SocketClientProxy(GameController controller, TCPServer TCPServer, Socket socket) throws IOException {
        super(controller, TCPServer);
        this.socket = socket;
        System.out.println("Config server out");
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        this.clientAlive = System.currentTimeMillis();
        checkClientConnection();
    }

    private void checkClientConnection(){
        Thread checkClient = new Thread(() -> {
            try {
                while (!socket.isClosed()) {
                    if (System.currentTimeMillis() - clientAlive > 14999) {
                        disconnect();
                    }
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                System.out.println("Error checking client connection");
                Thread.currentThread().interrupt();
            }
        });
        checkClient.setDaemon(true);
        checkClient.start();
    }

    public void sendMessage(Message message){
        synchronized(this.out){
            try {
                out.reset();
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                System.out.println("Error sending message from server: " + e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        ObjectInputStream in;
        try {
            System.out.println("Config server in");
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error getting input stream, returning");
            e.printStackTrace();
            return;
        }

        while (!socket.isClosed()) {
            try {
                Message message = (Message) in.readObject();

                if (message instanceof PingMessage ping) {
                    System.out.println("ping ricevuto");
                  
                    sendMessage(new PongMessage());
                    System.out.println("Pong from server");
                    clientAlive = System.currentTimeMillis();
                } else if (message instanceof SetPlayerNameMessage setPlayerNameMessage) {
                    message.setProxy(this);
                    setPlayerNameMessage.handle(getController(), null);
                    if (getPlayerName() != null) {
                        joinServer();
                    }
                } else {
                    message.setProxy(this);
                    getController().addMessage(message);
                }
            } catch (Exception e) {
                System.out.println("Error reading from socket: " + e.getMessage());
                break;
            }
        }

        try {
            socket.close();
        } catch (IOException ignored) {}
        super.disconnect();
    }
}