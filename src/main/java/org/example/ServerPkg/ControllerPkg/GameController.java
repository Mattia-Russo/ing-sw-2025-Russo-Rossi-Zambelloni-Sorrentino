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
import java.security.InvalidParameterException;

public class GameController{
    private Game game;
    private LobbyState lobbyState;
    private final BlockingQueue<Message> messageQueue;
    private volatile boolean isRunning;
    private Thread messageProcessor;
    private Map<String, GameUpdater> gameUpdaters;
    private ArrayList<String> nameUsed;
    private ArrayList<Player> PlayerToLoad;
    private boolean fileLoaded;
    private ArrayList<Server> serverList;

    public GameController(){
        this.game = null;
        this.lobbyState = LobbyState.GAME_NOT_EXISTS;
        this.messageQueue = new LinkedBlockingQueue<>();
        this.isRunning = true;
        gameUpdaters = new HashMap<>();
        this.nameUsed = new ArrayList<>();
        this.fileLoaded = false;
        this.PlayerToLoad = new ArrayList<>();
        this.serverList = new ArrayList<>();
        startMessageProcessing();
    }

    private void startMessageProcessing() {
        messageProcessor = new Thread(() -> {
            while (isRunning) {
                try {
                    Message message = messageQueue.take();
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

    public void addServer(Server server){
        serverList.add(server);
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
        if(fileLoaded && game.getLobbyState() == LobbyState.GAME_STARTED) {
            throw new InvalidLobbyStateException("can't call this method");
        }

        if(lobbyState == LobbyState.GAME_READY) {
            if (game.getPlayers().size() >= 2) {
                lobbyState = LobbyState.GAME_STARTED;
                game.setLobbyState(lobbyState);
                TimerGenerator t = new TimerGenerator();
                game.setPlayersShipboard();
                for (Player player : game.getPlayers()) {
                    player.setPlayerState(new BuildShipState(game, t));
                }
            } else throw new InvalidMinimumNumberPlayerException("not enough players to start");
        }else throw new InvalidLobbyStateException("can't call this method");
    }

    private void addNewPlayer(String name){
        for (Player p : game.getPlayers()) {
            if (p.getName().equals(name)) {
                throw new InvalidUserNameException("The player " + name + " already exists");
            }
        }
        if (game.getPlayers().size() < game.getNumPlayer()) {
            Player p = new Player(name, game);
            game.getPlayers().add(p);
        } else throw new InvalidAddPlayerException("can't add any more players");
    }

    public void joinLobby(String name){
        if(lobbyState == LobbyState.GAME_READY) {
            if (game != null) {
                if(fileLoaded){
                    if(PlayerToLoad.contains(game.getPlayerByName(name))){
                        PlayerToLoad.remove(game.getPlayerByName(name));
                    }else
                        throw new InvalidUserNameException("The player " + name + " didn't exist");

                    if(PlayerToLoad.isEmpty()){
                        lobbyState = game.getLobbyState();
                        new GameView(game, null);
                    }
                }else {
                    addNewPlayer(name);
                    new GameView(game, new Exception("Player" + name + " joined"));
                }
                game.setGameUpdaters(gameUpdaters);
            } else throw new InvalidGameCreationException("You're the first player to join, create a lobby!");
        }else throw new InvalidLobbyStateException("Wait for the lobby to be set");
    }

    public void createLobby(String name, int numPlayers, int ShipBoardLevel, int GameMode) {
        if(lobbyState == LobbyState.GAME_CREATION || lobbyState == LobbyState.GAME_NOT_EXISTS) {
            if(game==null) {
                if(numPlayers<=4 && numPlayers>=2 ) {
                    if(GameMode==0||GameMode==1) {
                        if (ShipBoardLevel == 1 || ShipBoardLevel == 2){
                            this.game = new Game(numPlayers, ShipBoardLevel, GameMode, this);
                            addNewPlayer(name);
                            game.setGameUpdaters(gameUpdaters);
                            this.lobbyState = LobbyState.GAME_READY;
                            game.setLobbyState(LobbyState.GAME_READY);
                            new GameSaver(this);
                            new GameView(game, new Exception("Game created"));
                        }else throw new InvalidParameterException("Ship board level must be 1 or 2");
                    }else throw new InvalidParameterException("Game mode must be 0 or 1");
                }else throw new InvalidParameterException("MIN 2 MAX 4 PLAYERS");
            }else new GameView(game, new InvalidGameCreationException("Game already created " + name));
        }else throw new InvalidLobbyStateException("can't call this method");
    }

    public synchronized void disconnect(String playerName) {
        if(this.game!=null) {
            Player disconnectingPlayer = game.getPlayerByName(playerName);
            disconnectingPlayer.getState().disconnect(disconnectingPlayer);
        }
    }

    public void addGameUpdater(GameUpdater gameUpdater, String name) {
        this.gameUpdaters.put(name, gameUpdater);
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

    public void setGame(Game game) {
        this.game = game;
        game.setController(this);
        PlayerToLoad.addAll(game.getPlayers());
        for(Player p : game.getPlayers()) {
            nameUsed.add(p.getName());
        }
        fileLoaded = true;
        new GameSaver(this);
    }

    public boolean checkName(String name){
        if(fileLoaded && !nameUsed.contains(name)) {
            return false;
        }else if(!fileLoaded ) {
            for (String s : this.nameUsed) {
                if (s.equals(name)){
                    return false;
                }
            }
        }
        nameUsed.add(name);
        return true;
    }

    public ArrayList<String> getNames(){
        return this.nameUsed;
    }

    public void setGameCreating(){
        this.lobbyState = LobbyState.GAME_CREATION;
    }

    public void notifyBroadcast(List<String> exclude, String message) throws RemoteException {
        for(Server s : serverList){
            s.notifyBroadcast(exclude, message);
        }
    }

    public void updatePlayerList(String exclude) throws RemoteException {
        for(Server s : serverList){
            s.updatePlayerList(exclude);
        }
    }

    public boolean getFile() {
        return fileLoaded;
    }
}
