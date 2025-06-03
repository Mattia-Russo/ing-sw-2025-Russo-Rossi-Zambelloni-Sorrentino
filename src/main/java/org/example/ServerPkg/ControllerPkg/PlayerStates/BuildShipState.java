package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.TimerGenerator;

import java.io.Serializable;

public class BuildShipState extends PlayerState implements Serializable {
    private final TimerGenerator timer;
    public BuildShipState(Game game, TimerGenerator timer) {
        super(game);
        this.timer = timer;
    }

    @Override
    public void turnTimer(Player player){
        if(getGame().getGameMode()==0){
            return;
        }
        try{
            if(getGame().getTimerTurned() < 3) {
                timer.start();
                getGame().setTimerTurned();
                new GameView(getGame(), new Exception(player.getName() + " TURNED THE TIMER"));
            }else if(player.getShipBuilded()){
                timer.start();
                for(Player p : getGame().getPlayers()){
                    if(!p.getShipBuilded() && getGame().getGameMode()==0){
                        setPosition(p);
                    }
                }
                endBuildShip(player);
            }
        }catch(InvalidMethodCallException e){
            new GameView(getGame(), e);
        }
    }

    @Override
    public void showDeck(Player p, int deckPosition){
        try {
            if(getGame().getGameMode()!=0){
                p.setDeckShowed(getGame().getDeck(deckPosition));
                new GameView(getGame(), null);
            }else new GameView(getGame(), new InvalidMethodCallException("can't call this method in this game mode" + p.getName()));
        } catch (InvalidDeckNumberException | InvalidMethodCallException e) {
            Exception e1 = new Exception(e.getMessage() + " " + p.getName());
            new GameView(getGame(), e1);
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
            if (p.getCurrentTile() == null) {
                if (!p.getShipBuilded()) {
                    p.setCurrentTile(getGame().pickComponentTile());
                    new GameView(getGame(), null);
                }
            }
        }else {
            new GameView(getGame(), new PickTileWithDeckException("You cannot pick a card while the deck is showed " + p.getName()));
        }
    }

    @Override
    public void pickDiscoveredComponent(Player p, int index){
        if (p.getDeckShowed() == null){
            if(!p.getShipBuilded()) {
                p.setCurrentTile(getGame().pickDiscoveredComponent(index));
                new GameView(getGame(), null);
            }
        } else {
            new GameView(getGame(), new PickTileWithDeckException("You cannot pick a card while the deck is showed " + p.getName()));
        }
    }

    @Override
    public void rightRotateTile(Player p){
        if(p.getCurrentTile() == null){
            new GameView(getGame(), new TileNotSelectedException("You've not selected a tile " + p.getName()));
        } else {
            p.getCurrentTile().rightRotate();
            new GameView(getGame(), null);
        }
    }

    @Override
    public void leftRotateTile(Player p){
        if(p.getCurrentTile() == null){
            new GameView(getGame(), new TileNotSelectedException("You've not selected a tile " + p.getName()));
        } else {
            p.getCurrentTile().leftRotate();
            new GameView(getGame(), null);
        }
    }

    @Override
    public void discardComponent(Player p){
        if(p.getCurrentTile() != null) {
            getGame().addDiscoveredComponent(p.getCurrentTile());
            p.setCurrentTile(null);
            new GameView(getGame(), null);
        }
    }

    @Override
    public void placeTile(Player player, Points point){
        try {
            if(!player.getShipBuilded()) {
                player.getPlayerShipBoard().placeComponent(point.getX(), point.getY(), player.getCurrentTile());
                player.setCurrentTile(null);
                new GameView(getGame(), null);
            }
        } catch (OccupiedPositionException | InvalidPositionException e) {
            Exception e1 = new Exception(e.getMessage() + " " + player.getName());
            new GameView(getGame(), e1);
        }
    }

    @Override
    public void endBuildShip(Player player){
        if(!player.getShipBuilded() && getGame().getGameMode()==0){
            setPosition(player);
        }
        for(Player p : getGame().getPlayers()){
            if(!p.isAbandoned()) {
                if (!p.getShipBuilded()) {
                    return;
                }
            }
        }
        getGame().checkAllPlayersShip();
    }

    private void setPosition(Player player){
        int pos=0;
        for(Player p : getGame().getPlayers()){
            if(p.getShipBuilded())
                pos--;
        }
        player.setShipBuilded();
        player.setPosition(pos);
        new GameView(getGame(), new Exception("SHIP BUILD STATE ENDED FOR " + player.getName()));
    }

    @Override
    public void pickBookedTile(int index,Player p){
        if(p.getCurrentTile() == null){
            if((index ==0 || index == 1)) {
                p.setCurrentTile(p.getPlayerShipBoard().getBookedComponents()[index]);
                p.getPlayerShipBoard().getBookedComponents()[index] = null;
                new GameView(getGame(), null );
            }else new GameView(getGame(),  new PickTileWithDeckException("INDEX MUST BE 0 or 1 " + p.getName()));
        }else new GameView(getGame(), new PickTileWithDeckException("You already have a tile " + p.getName()));
    }

    @Override
    public void bookComponent(Player p){
        if(p.getCurrentTile() != null) {
            try {
                p.getPlayerShipBoard().bookComponents(p.getCurrentTile());
                p.setCurrentTile(null);
                new GameView(getGame(), null);
            }catch(FullBookedSlotsException e){
                new GameView(getGame(), e);
            }
        }
    }
  
    @Override
    public void disconnect(Player disconnectingPlayer){
        getGame().disconnectPlayer( disconnectingPlayer);
        setPosition(disconnectingPlayer);
        endBuildShip(disconnectingPlayer);
    }
}