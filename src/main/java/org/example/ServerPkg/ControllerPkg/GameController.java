package org.example.ServerPkg.ControllerPkg;

import org.example.ServerPkg.ControllerPkg.PlayerStates.BuildShipState;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.TimerGenerator;

import java.security.InvalidParameterException;

public class GameController {
    Game game;
    LobbyState lobbyState;

    public GameController(){
        this.game = null;
        this.lobbyState = LobbyState.GAME_CREATION;
    }

    public Game getGame() {
        return this.game;
    }

    public void setLobbyState(LobbyState lobbyState) {
        this.lobbyState = lobbyState;
    }

    public void exitGame(Player player){
        try{
            if(lobbyState == LobbyState.GAME_FINISHED) {
                game.getPlayers().remove(player);
                if(game.getPlayers().isEmpty()) {
                    game=null;
                }
            }else throw new InvalidLobbyStateException("can't call this method");
        }catch (InvalidLobbyStateException e){
            System.out.println("ERROR " + e.getMessage());
        }
    }

    public void startGame(){
        try{
            if(lobbyState == LobbyState.GAME_CREATION) {
                if(game.getPlayers().size() >= 2) {
                    lobbyState = LobbyState.GAME_READY;
                    for(Player player : game.getPlayers()) {
                        player.setPlayerState(new BuildShipState(game, new TimerGenerator()));
                    }
                }else throw new InvalidMinimumNumberPlayerException("not enough players to start");
            }else throw new InvalidLobbyStateException("can't call this method");
        }catch(InvalidMinimumNumberPlayerException | InvalidLobbyStateException e){
            System.out.println("ERROR " + e.getMessage());
        }
    }

    private void addNewPlayer(String name){
        try {
            for (Player p : game.getPlayers()) {
                if (p.getName().equals(name)) {
                    throw new InvalidUserNameException("The player " + name + " already exists");
                }
            }
            if (game.getPlayers().size() < game.getNumPlayer()) {
                game.getPlayers().add(new Player(game.getPlayers().size(), name));
            } else throw new InvalidAddPlayerException("can't add any more players");
        }catch(InvalidAddPlayerException | InvalidUserNameException e) {
            System.out.println("ERROR" + e.getMessage());
        }
    }

    public void joinLobby(String name){
        try {
            if(lobbyState == LobbyState.GAME_CREATION) {
                if (game != null) {
                    addNewPlayer(name);
                } else throw new InvalidGameCreationException("Game NOT created");
            }else throw new InvalidLobbyStateException("can't call this method");
        }catch(InvalidGameCreationException | InvalidLobbyStateException e){
            System.out.println("ERROR " + e.getMessage());
        }
    }

    public void createLobby(String name, int numPlayers, int ShipBoardLevel, int GameMode) {
        try{
            if(lobbyState == LobbyState.GAME_CREATION) {
                if(game==null) {
                    if(numPlayers<=4) {
                        if(GameMode==0||GameMode==1) {
                            if (ShipBoardLevel == 1 || ShipBoardLevel == 2){
                                game = new Game(numPlayers, ShipBoardLevel, GameMode, this);
                                addNewPlayer(name);
                            }else throw new InvalidParameterException("Ship board level must be 1 or 2");
                        }else throw new InvalidParameterException("Game mode must be 0 or 1");
                    }else throw new InvalidParameterException("MAX 4 PLAYERS");
                }else throw new InvalidGameCreationException("Game already created");
            }else throw new InvalidLobbyStateException("can't call this method");
        }catch(InvalidParameterException | InvalidGameCreationException | InvalidLobbyStateException e) {
            System.out.println("ERROR " + e.getMessage());
        }
    }

}
