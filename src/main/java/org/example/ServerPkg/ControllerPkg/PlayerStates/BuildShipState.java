package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.TimerGenerator;

import java.io.Serializable;
import java.util.Arrays;

public class BuildShipState extends PlayerState implements Serializable {
    private final Game game;
    private final TimerGenerator timer;
    private int stopTurn;
    public BuildShipState(Game game, TimerGenerator timer) {
        this.game = game;
        this.timer = timer;
        this.stopTurn=0;
    }

    @Override
    public void turnTimer(Player player){
        if(game.getGameMode()==0){
            return;
        }
        try{
            if(stopTurn < 3) {
                stopTurn = timer.start();
            }else if(player.getShipBuilded()){
                timer.start();
                for(Player p : game.getPlayers()){
                    if(!p.getShipBuilded()){
                        setPosition(p);
                    }
                }
                endBuildShip(player);
            }
        }catch(InvalidMethodCallException e){
            new GameView(game, e);
        }
    }

    @Override
    public void showDeck(Player p, int deckPosition){
        try {
            if(game.getGameMode()!=0){
                p.setDeckShowed(game.getDeck(deckPosition));
            }else new GameView(game, new InvalidMethodCallException("can't call this method in this game mode" + p.getName()));
        } catch (InvalidDeckNumberException | InvalidMethodCallException e) {
            Exception e1 = new Exception(e.getMessage() + " " + p.getName());
            new GameView(game, e1);
        }
    }

    @Override
    public void endShowDeck(Player p){
        p.setDeckShowed(null);
    }

    // p indica chi ha fatto la chiamata
    @Override
    public void pickComponentTile(Player p){
        if (p.getDeckShowed() == null) {
            if (p.getCurrentTile() != null) {
                if (!p.getShipBuilded()) {
                    p.setCurrentTile(game.pickComponentTile());
                    System.out.println("picked tile: " + p.getCurrentTile().toString());
                    System.out.println("Connectors: " + Arrays.toString(p.getCurrentTile().getConnectors()));
                    new GameView(game, null);
                }
            } else {
                new GameView(game, new PickTileWithDeckException("You cannot pick a card while the deck is showed " + p.getName()));
            }
        }
    }

    @Override
    public void pickDiscoveredComponent(Player p, int index){
        if (p.getDeckShowed() == null){
            if(!p.getShipBuilded()) {
                p.setCurrentTile(game.pickDiscoveredComponent(index));
                System.out.println("picked tile: " + p.getCurrentTile().toString());
                System.out.println("Connectors: " + Arrays.toString(p.getCurrentTile().getConnectors()));
                new GameView(game, null);
            }
        } else {
            new GameView(game, new PickTileWithDeckException("You cannot pick a card while the deck is showed " + p.getName()));
        }
    }

    @Override
    public void rightRotateTile(Player p){
        if(p.getCurrentTile() == null){
            new GameView(game, new TileNotSelectedException("You've not selected a tile " + p.getName()));
        } else {
            p.getCurrentTile().rightRotate();
            new GameView(game, null);
        }
    }

    @Override
    public void leftRotateTile(Player p){
        if(p.getCurrentTile() == null){
            new GameView(game, new TileNotSelectedException("You've not selected a tile " + p.getName()));
        } else {
            p.getCurrentTile().leftRotate();
            new GameView(game, null);
        }
    }

    @Override
    public void discardComponent(Player p){
        game.addDiscoveredComponent(p.getCurrentTile());
        p.setCurrentTile(null);
        new GameView(game, null);
    }

    @Override
    public void placeTile(Player player, Points point){
        try {
            if(!player.getShipBuilded()) {
                player.getPlayerShipBoard().placeComponent(point.getX(), point.getY(), player.getCurrentTile());
                player.setCurrentTile(null);
                new GameView(game, null);
            }
        } catch (OccupiedPositionException | InvalidPositionException e) {
            Exception e1 = new Exception(e.getMessage() + " " + player.getName());
            new GameView(game, e1);
        }
    }

    @Override
    public void endBuildShip(Player player){
        if(!player.getShipBuilded()){
            setPosition(player);
        }

        for(Player p : game.getPlayers()){
            if(!p.isAbandoned()) {
                if (!p.getShipBuilded()) {
                    return;
                }
            }
        }
        game.checkAllPlayersShip();
    }

    @Override
    public void setPosition(Player player){
        int pos=0;
        for(Player p : game.getPlayers()){
            if(p.getShipBuilded())
                pos--;
        }
        player.setShipBuilded();
        player.setPosition(pos);
    }

    @Override
    public void bookComponent(Player p){
        p.getPlayerShipBoard().bookComponents(p.getCurrentTile());
        p.setCurrentTile(null);
        new GameView(game, null);
    }
  
    @Override
    public void disconnect(Player disconnectingPlayer){
        game.disconnectPlayer( disconnectingPlayer);
        setPosition(disconnectingPlayer);
        endBuildShip(disconnectingPlayer);
    }
}