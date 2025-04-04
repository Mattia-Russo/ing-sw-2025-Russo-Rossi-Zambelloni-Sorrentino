package org.example.Server.Controller.States;

import org.example.Server.Model.Exceptions.InvalidDeckNumberException;
import org.example.Server.Model.Exceptions.InvalidPositionException;
import org.example.Server.Model.Exceptions.OccupiedPositionException;
import org.example.Server.Model.Exceptions.TileNotSelectedException;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;
import org.example.Server.Model.Points;

public class BuildShipState extends PlayerState{
    private final Game game;

    public BuildShipState(Game game) {
        this.game = game;
    }

    public void showDeck(int deckPosition){
        try {
            game.getDeck(deckPosition);
        } catch (InvalidDeckNumberException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // p indica chi ha fatto la chiamata
    public void pickComponentTile(Player p){
        p.setCurrentTile(game.pickComponentTile());
    }

    public void RightRotateTile(Player p){
        if(p.getCurrentTile() == null){
            throw new TileNotSelectedException("You've not selected a tile");
        } else {
            p.getCurrentTile().rightRotate();
        }
    }

    public void LeftRotateTile(Player p){
        if(p.getCurrentTile() == null){
            throw new TileNotSelectedException("You've not selected a tile");
        } else {
            p.getCurrentTile().leftRotate();
        }
    }

    public void placeTile(Player player, Points point){
        try {
            player.getPlayerShipBoard().placeComponent(point.getX(), point.getY(), player.getCurrentTile());
        } catch (OccupiedPositionException | InvalidPositionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

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