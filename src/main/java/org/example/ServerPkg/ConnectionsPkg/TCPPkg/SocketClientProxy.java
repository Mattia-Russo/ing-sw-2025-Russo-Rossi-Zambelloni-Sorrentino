package org.example.ServerPkg.ConnectionsPkg.TCPPkg;

import org.example.MessagePkg.*;
import org.example.MessagePkg.ToClient.*;
import org.example.MessagePkg.ToServer.SetPlayerNameMessage;
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
    private final MessageGenerator msgGen;

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
        String numPlayers = String.valueOf(getController().getGame().getNumPlayer());
        String shipBoardLevel = String.valueOf(getController().getGame().getShipBoardLevel());
        String gameMode = String.valueOf(getController().getGame().getGameMode());
        List<String> args = new ArrayList<>(List.of(numPlayers, shipBoardLevel, gameMode));
        Message message = msgGen.generate("lobby_created", args);
        sendMessage(message);
    }

    @Override
    public void notifyLobbyJoined(){
        String numPlayers = String.valueOf(getController().getGame().getNumPlayer());
        String shipBoardLevel = String.valueOf(getController().getGame().getShipBoardLevel());
        String gameMode = String.valueOf(getController().getGame().getGameMode());
        List<String> names = getController().getNames();
        List<String> args = new ArrayList<>();
        args.add(numPlayers);
        args.add(shipBoardLevel);
        args.add(gameMode);
        args.addAll(names);
        Message message = msgGen.generate("joined_lobby", args);
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

    @Override
    public MessageGenerator getMsgGen(){
        return msgGen;
    }

    @Override
    public void notifyGameStarted(){
        Message message = msgGen.generate("game_started", null);
        sendMessage(message);
    }

    @Override
    public void goToAddAlien(){
        sendMessage(new NotifyAddAlienMessage());
    }

    @Override
    public void goToAbandoned(){
        sendMessage(new NotifyLandOnAbandonMessage());
    }

    @Override
    public void goToActivateCannons(){
        sendMessage(new NotifyActivateCannonsMessage());
    }

    @Override
    public void goToActivateEngines(){
        sendMessage(new NotifyActivateEnginesMessage());
    }

    @Override
    public void goToActivateShields(){
        sendMessage(new NotifyActivateShieldsMessage());
    }

    @Override
    public void goToChangeGoods(){
        sendMessage(new NotifyChangeGoodsMessage());
    }

    @Override
    public void goToLandOnAbandon(){
        sendMessage(new NotifyLandOnAbandonMessage());
    }

    @Override
    public void goToLandOnPlanet(){
        sendMessage(new NotifyLandOnPlanetMessage());
    }

    @Override
    public void goToRemoveAstronauts(){
        sendMessage(new NotifyRemoveAstronautsMessage());
    }

    @Override
    public void goToRemoveBestGoods(){
        sendMessage(new NotifyRemoveBestGoodsMessage());
    }

    @Override
    public void goToFixShip(){
        sendMessage(new NotifyFixShipMessage());
    }

    @Override
    public void goToShipWreck(){
        sendMessage(new NotifyShipWreckedMessage());
    }

    @Override
    public void goToWaitingState(){
        sendMessage(new NotifyWaitingStateMessage());
    }

    @Override
    public void goToWinEnemy(){
        sendMessage(new NotifyWinEnemyMessage());
    }

    @Override
    public void notifyGameEnded(){
        Message message = new NotifyGameEndedMessage();
        sendMessage(message);
    }
}