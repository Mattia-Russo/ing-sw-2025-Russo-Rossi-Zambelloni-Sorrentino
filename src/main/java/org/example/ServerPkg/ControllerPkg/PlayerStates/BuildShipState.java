package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.TimerGenerator;

public class BuildShipState extends PlayerState{
    private final Game game;
    private final TimerGenerator timer;
    boolean stopTurn=false;
    public BuildShipState(Game game, TimerGenerator timer) {
        this.game = game;
        this.timer = timer;
    }

    public void turnTimer(){
        try{
            if(!stopTurn) {
                stopTurn = timer.start();
            }else throw new InvalidMethodCallException("can't call this method");
        }catch(InvalidMethodCallException e){
            System.out.println("ERROR" + e.getMessage());
        }
    }

    @Override
    public void showDeck(Player p, int deckPosition){
        try {
            p.setDeckShowed(game.getDeck(deckPosition));
        } catch (InvalidDeckNumberException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void endShowDeck(Player p){
        p.setDeckShowed(null);
    }

    // p indica chi ha fatto la chiamata
    @Override
    public void pickComponentTile(Player p){
        if (p.getDeckShowed() == null){
            p.setCurrentTile(game.pickComponentTile());
        } else {
            throw new PickTileWithDeckException("You cannot pick a card while the deck is showed");
        }

    }

    @Override
    public void RightRotateTile(Player p){
        if(p.getCurrentTile() == null){
            throw new TileNotSelectedException("You've not selected a tile");
        } else {
            p.getCurrentTile().rightRotate();
        }
    }

    @Override
    public void LeftRotateTile(Player p){
        if(p.getCurrentTile() == null){
            throw new TileNotSelectedException("You've not selected a tile");
        } else {
            p.getCurrentTile().leftRotate();
        }
    }

    @Override
    public void placeTile(Player player, Points point){
        try {
            player.getPlayerShipBoard().placeComponent(point.getX(), point.getY(), player.getCurrentTile());
            player.setCurrentTile(null);
        } catch (OccupiedPositionException | InvalidPositionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void endBuildShip(Player player){
        player.setShipBuilded();
        int pos = 0;
        for(Player p : game.getPlayers()){
            if(p.getShipBuilded()){
               pos--;
            }
        }
        player.setPosition(pos);

        for(Player p : game.getPlayers()){
            if(!p.getShipBuilded()){
                return;
            }
        }
        game.checkAllPlayersShip();
    }
}