package org.example.ServerPkg.ControllerPkg;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ConnectionsPkg.Server;
import org.example.ServerPkg.ControllerPkg.PlayerStates.BuildShipState;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.TimerGenerator;

import org.example.UIPkg.GameUpdater;

import java.io.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameController implements Serializable{
    private Game game;
    private LobbyState lobbyState;
    private final BlockingQueue<Message> messageQueue;
    private transient Map<String, GameUpdater> gameUpdaters;
    private transient Map<String, Server> nameUsed;
    private boolean fileLoaded;

    public GameController(){
        this.game = null;
        this.lobbyState = LobbyState.GAME_NOT_EXISTS;
        this.messageQueue = new LinkedBlockingQueue<>();
        this.gameUpdaters = new HashMap<>();
        this.nameUsed = new HashMap<>();
        this.fileLoaded = false;
        startMessageProcessing();
    }

    private void startMessageProcessing() {
        Thread messageProcessor = new Thread(() -> {
            while (true) {
                try {
                    Message message = messageQueue.take();
                    processMessage(message);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        messageProcessor.setName("MessageProcessor");
        messageProcessor.start();
    }

    private void processMessage(Message message) {
        try {
            synchronized (this) {
                if (message.getHandler()!=null){
                    message.handle(this, message.getHandler().getPlayerName());
                } else {
                    System.out.println("Error processing the message: it was generated without sender");
                }
            }
        } catch (Exception e) {
            System.err.println("Error managing the message: " + e.getMessage());
        }
    }

    public Map<String, Server> getNameServerMap() {
        return nameUsed;
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

    public void setServer(String name, Server server){
        nameUsed.put(name, server);
    }

    public void startGame(){
        if(lobbyState == LobbyState.GAME_READY) {
            if (game.getPlayers().size() >= 2) {
                lobbyState = LobbyState.GAME_STARTED;
                TimerGenerator t = new TimerGenerator();
                game.setPlayersShipboard();
                if(game.getGameMode()==1) {
                    t.start();
                    getGame().setTimerTurned();
                }
                for (Player player : game.getPlayers()) {
                    player.setPlayerState(new BuildShipState(game, t));
                }
            } else throw new InvalidMinimumNumberPlayerException("not enough players to start");
        }else throw new InvalidLobbyStateException("can't call this method");
    }

    private void addNewPlayer(String name){
        for(Player player : game.getPlayers()) {
            if(player.getName().equals(name)) {
                throw new InvalidAddPlayerException("You are already in the game");
            }
        }
        if (game.getPlayers().size() < game.getNumPlayer()) {
            Player p = new Player(name, game);
            game.getPlayers().add(p);
        } else new GameView(game, new InvalidAddPlayerException("can't add any more players"));
    }

    public void joinLobby(String name){
        if(lobbyState == LobbyState.GAME_READY) {
            if (game != null) {
                addNewPlayer(name);
                new GameView(game, new Exception("Player " + name + " joined"));
                game.setGameUpdaters(gameUpdaters);
            } else throw new InvalidGameCreationException("You're the first player to join, create a lobby!");
        }else throw new InvalidLobbyStateException("Wait for the lobby to be set");
    }

    public void createLobby(String name, int numPlayers, int shipBoardLevel, int gameMode) throws RemoteException {
        if(lobbyState == LobbyState.GAME_CREATION || lobbyState == LobbyState.GAME_NOT_EXISTS) {
            if(game==null) {
                if(numPlayers<=4 && numPlayers>=2 ) {
                    if(gameMode==0||gameMode==1) {
                        if (shipBoardLevel == 1 || shipBoardLevel == 2){
                            this.game = new Game(numPlayers, shipBoardLevel, gameMode, this);
                            addNewPlayer(name);
                            game.setGameUpdaters(gameUpdaters);
                            this.lobbyState = LobbyState.GAME_READY;
                            nameUsed.get(name).notifyLobbyCreated(name);
                            System.out.println("Lobby created successfully, numPl: " + numPlayers + " shipLev: " + shipBoardLevel + " gameMode: " + gameMode);
                            new GameSaver(this);
                            new GameView(game, new Exception("Game created"));
                        }else notifyClient(name, "Ship board level must be 1 or 2");
                    }else notifyClient(name, "Game mode must be 0 or 1");
                }else notifyClient(name, "MIN 2 MAX 4 PLAYERS");
            }else new GameView(game, new InvalidGameCreationException("Game already created " + name));
        }else notifyClient(name, "can't call this method");
    }

    private void notifyClient(String name, String message) throws RemoteException {
        nameUsed.get(name).notifyClient(name, message);
    }

    public synchronized void disconnect(String playerName) throws RemoteException {
        if(this.game!=null) {
            Player disconnectingPlayer = game.getPlayerByName(playerName);
            disconnectingPlayer.getState().disconnect(disconnectingPlayer);
        }
    }

    public void addGameUpdater(GameUpdater gameUpdater, String name) {
        this.gameUpdaters.put(name, gameUpdater);
        if(fileLoaded){
            game.setGameUpdaters(gameUpdaters);
        }
    }

    public void saveGame(String path){
        if(getLobbyState().equals(LobbyState.GAME_FINISHED)){
            return;
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(game);
            objectOut.close();
            fileOut.close();
        } catch (Exception e) {
            System.out.println("Saving game failed");
        }
    }

    public void setGame() {
        fileLoaded = true;
        nameUsed = new HashMap<>();
        gameUpdaters = new HashMap<>();
        startMessageProcessing();
        new GameSaver(this);
    }

    public boolean getFileLoaded(){
        return fileLoaded;
    }

    public boolean checkName(String name, Server server){
        if(fileLoaded) {
            for(Player p : game.getPlayers()){
                if(p.getName().equals(name) && !nameUsed.containsKey(name)){
                    nameUsed.put(name, server);
                    return true;
                }
            }
            return false;
        }else{
            for (String s : nameUsed.keySet()) {
                if (s.equals(name)){
                    return false;
                }
            }
            nameUsed.put(name, server);
            return true;
        }
    }

    public ArrayList<String> getNames(){
        return new ArrayList<>(nameUsed.keySet());
    }

    public void setGameCreating(){
        this.lobbyState = LobbyState.GAME_CREATION;
    }

    public void notifyBroadcast(List<String> exclude, String message) throws RemoteException {
        for(String s : nameUsed.keySet()){
            nameUsed.get(s).notifyBroadcast(exclude, message);
        }
    }

    public void updatePlayerList(String exclude) throws RemoteException {
        for(String s : nameUsed.keySet()){
            nameUsed.get(s).updatePlayerList(exclude);
        }
    }

    public void notifyGameStarted() throws RemoteException {
        for(String s : nameUsed.keySet()){
            nameUsed.get(s).notifyGameStarted();
        }
    }
}
