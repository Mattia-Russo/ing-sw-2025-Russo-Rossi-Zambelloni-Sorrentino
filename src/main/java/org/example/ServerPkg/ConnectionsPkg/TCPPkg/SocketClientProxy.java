package org.example.ServerPkg.ConnectionsPkg.TCPPkg;

import org.example.MessagePkg.*;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.UIPkg.GameUpdater;
import org.example.UIPkg.TCPVirtualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketClientProxy extends ClientProxy implements Runnable {
    private final Socket socket;
    private final ObjectOutputStream out;
    private long clientAlive;
    private GameUpdater gameUpdater;
    private MessageGenerator msgGen;

    public SocketClientProxy(GameController controller, TCPServer TCPServer, Socket socket) throws IOException {
        super(controller, TCPServer);
        this.socket = socket;
        this.gameUpdater = null;
        this.msgGen = new MessageGenerator();
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        this.clientAlive = System.currentTimeMillis();
        //checkClientConnection();
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

    public void sendMessage(Message message) {
        synchronized (this.out) {
            try {
                out.reset();
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                System.out.println("Error sending message to server: " + e.getMessage());
            }
        }
    }

    @Override
    public void run() {
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error getting input stream, returning");
            e.printStackTrace();
            return;
        }

        while (!socket.isClosed()) {
            try {
                Message message = (Message) in.readObject();

                if (message instanceof PingMessage) {
                    sendMessage(new PongMessage());
                    clientAlive = System.currentTimeMillis();
                } else if (message instanceof SetPlayerNameMessage setPlayerNameMessage) {
                    message.setHandler(this);
                    message.setServer(getServer());
                    setPlayerNameMessage.handle(getController(), null);
                    if (getPlayerName() != null) {
                        joinServer();
                    }
                } else {
                    message.setServer(getServer());
                    message.setHandler(this);
                    getController().addMessage(message);
                }
            } catch (Exception e) {
                System.out.println("Error reading from socket: " + e.getMessage());
                break;
            }
        }

        try {
            out.flush();
            out.close();
            in.close();
        } catch (IOException e) {
            System.err.println("Error closing server streams: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing server socket: " + e.getMessage());
            }
            super.disconnect();
        }
    }

    @Override
    public void setGameUpdater(){
        this.gameUpdater = new TCPVirtualView(out);
    }

    @Override
    public GameUpdater getGameUpdater(){
        return gameUpdater;
    }

    @Override
    public void notifyClient(String s){
        Message message = msgGen.generate("notify", List.of(s));
        sendMessage(message);
    }

    @Override
    public void notifyLobbyCreated(){
        Message message = msgGen.generate("lobby_created", null);
        sendMessage(message);
    }

    @Override
    public void notifyLobbyJoined(){
        String numPlayers = String.valueOf(getController().getGame().getNumPlayer());
        String shipBoardLevel = String.valueOf(getController().getGame().getShipBoardLevel());
        String gameMode = String.valueOf(getController().getGame().getGameMode());
        Message message = msgGen.generate("joined_lobby", List.of(numPlayers, shipBoardLevel, gameMode));
        sendMessage(message);
    }

    @Override
    public void notifyNameAlreadyUsed(){
        Message message = msgGen.generate("notify", List.of("false"));
        sendMessage(message);
    }

    @Override
    public void acceptCreateLobby(){
        Message message = msgGen.generate("accept_create_lobby", null);
        sendMessage(message);
    }
}