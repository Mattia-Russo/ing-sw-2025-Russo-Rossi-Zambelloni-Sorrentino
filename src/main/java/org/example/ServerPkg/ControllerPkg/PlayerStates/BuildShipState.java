package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.example.ServerPkg.Model.TimerGenerator;

import java.io.Serializable;
import java.rmi.RemoteException;

public class BuildShipState extends PlayerState implements Serializable {
    private final TimerGenerator timer;
    public BuildShipState(Game game, TimerGenerator timer) {
        super(game, null);
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
            }else if(player.getShipBuilt()){
                timer.start();
                for(Player p : getGame().getPlayers()){
                    if(!p.getShipBuilt()){
                        p.setShipBuilt();
                        if(getGame().getGameMode()==0) {
                            setPosition(p);
                        }
                        new GameView(getGame(), new Exception("SHIP BUILD STATE ENDED FOR " + p.getName()));
                    }
                }
                endBuildShip(player);
            }
        }catch(InvalidMethodCallException | RemoteException e){
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
                if (!p.getShipBuilt()) {
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
            if(!p.getShipBuilt()) {
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
        if(p.getCurrentTile() != null && !p.getCurrentTile().getBooked()) {
            getGame().addDiscoveredComponent(p.getCurrentTile());
            p.setCurrentTile(null);
            new GameView(getGame(), null);
        } else if (p.getCurrentTile().getBooked()){
            new GameView(getGame(), new InvalidMethodCallException("You cannot discard a card that was booked"));
        }
    }

    @Override
    public void placeTile(Player player, Points point){
        try {
            if(!player.getShipBuilt()) {
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
    public void endBuildShip(Player player) throws RemoteException {
        if(!player.getShipBuilt()){
            if(getGame().getGameMode()==0) {
                setPosition(player);
            }
            player.setShipBuilt();
            new GameView(getGame(), new Exception("SHIP BUILD STATE ENDED FOR " + player.getName()));
        }
        for(Player p : getGame().getPlayers()){
            if(!p.isAbandoned()) {
                if (!p.getShipBuilt()) {
                    return;
                }
                p.setPlayerState(new WaitingState(getGame()));
            }
        }
        getGame().checkAllPlayersShip();
    }

    private void setPosition(Player player){
        int pos=0;
        for(Player p : getGame().getPlayers()){
            if(p.getShipBuilt())
                pos--;
        }
        if(pos == 0){ // la prima posizione è più avanzata
            pos++;
        }
        player.setPosition(pos);
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
    public void disconnect(Player disconnectingPlayer) throws RemoteException {
        getGame().disconnectPlayer( disconnectingPlayer);
        setPosition(disconnectingPlayer);
        disconnectingPlayer.abandon(getGame());
        endBuildShip(disconnectingPlayer);
    }
}