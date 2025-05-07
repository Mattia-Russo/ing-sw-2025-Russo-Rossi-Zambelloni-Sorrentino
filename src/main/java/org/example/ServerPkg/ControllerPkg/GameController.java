package org.example.ServerPkg.ControllerPkg;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ControllerPkg.PlayerStates.BuildShipState;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.TimerGenerator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.Queue;

public class GameController {
    private Game game;
    private LobbyState lobbyState;
    private final BlockingQueue<Message> messageQueue;
    private volatile boolean isRunning;
    private Thread messageProcessor;

    public GameController(){
        this.game = null;
        this.lobbyState = LobbyState.GAME_CREATION;
        this.messageQueue = new LinkedBlockingQueue<>();
        this.isRunning = true;
        startMessageProcessing();
    }

    private void startMessageProcessing() {
        messageProcessor = new Thread(() -> {
            while (isRunning) {
                try {
                    Message message = messageQueue.take(); // Aspetta finché non c'è un messaggio
                    processMessage(message);
                } catch (InterruptedException e) {
                    if (isRunning) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        messageProcessor.setName("MessageProcessor");
        messageProcessor.start();
    }

    private void processMessage(Message message) {
        try {
            synchronized (this) {
                if (message.getClient()!=null){
                    message.handle(this, message.getClient().getPlayerName());
                } else {
                    message.handle(this, message.getProxy().getPlayerName());
                }
            }
        } catch (Exception e) {
            System.err.println("Error managing the message: " + e.getMessage());
        }
    }

    public void addMessage(Message message) {
        try {
            messageQueue.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error inserting the message", e);
        }
    }


    public Game getGame() {
        return this.game;
    }

    public void setLobbyState(LobbyState lobbyState) {
        this.lobbyState = lobbyState;
    }

    //for testing
    public LobbyState getLobbyState(){
        return lobbyState;
    }

    public void exitGame(Player player){
        if(lobbyState == LobbyState.GAME_FINISHED) {
            game.getPlayers().remove(player);
            if(game.getPlayers().isEmpty()) {
                game=null;
            }
        }else throw new InvalidLobbyStateException("can't call this method");
    }

    public void startGame(){
        if(lobbyState == LobbyState.GAME_CREATION) {
            if(game.getPlayers().size() >= 2) {
                lobbyState = LobbyState.GAME_READY;
                TimerGenerator t= new TimerGenerator();
                game.setPlayersShipboard();
                for(Player player : game.getPlayers()) {
                    player.setPlayerState(new BuildShipState(game, t));
                }
            }else throw new InvalidMinimumNumberPlayerException("not enough players to start");
        }else throw new InvalidLobbyStateException("can't call this method");
    }

    private void addNewPlayer(String name){
        for (Player p : game.getPlayers()) {
            if (p.getName().equals(name)) {
                throw new InvalidUserNameException("The player " + name + " already exists");
            }
        }
        if (game.getPlayers().size() < game.getNumPlayer()) {
            game.getPlayers().add(new Player(game.getPlayers().size(), name));
        } else throw new InvalidAddPlayerException("can't add any more players");
    }

    public void joinLobby(String name){
        if(lobbyState == LobbyState.GAME_CREATION) {
            if (game != null) {
                addNewPlayer(name);
            } else throw new InvalidGameCreationException("Game NOT created");
        }else throw new InvalidLobbyStateException("can't call this method");
    }

    public void createLobby(String name, int numPlayers, int ShipBoardLevel, int GameMode) {
        if(lobbyState == LobbyState.GAME_CREATION) {
            if(game==null) {
                if(numPlayers<=4 && numPlayers>=2 ) {
                    if(GameMode==0||GameMode==1) {
                        if (ShipBoardLevel == 1 || ShipBoardLevel == 2){
                            game = new Game(numPlayers, ShipBoardLevel, GameMode, this);
                            addNewPlayer(name);
                        }else throw new InvalidParameterException("Ship board level must be 1 or 2");
                    }else throw new InvalidParameterException("Game mode must be 0 or 1");
                }else throw new InvalidParameterException("MIN 2 MAX 4 PLAYERS");
            }else throw new InvalidGameCreationException("Game already created");
        }else throw new InvalidLobbyStateException("can't call this method");
    }

}
