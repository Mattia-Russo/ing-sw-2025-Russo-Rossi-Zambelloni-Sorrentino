package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Exceptions.WaitingStateException;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;
import java.util.ArrayList;

public class WaitingState extends PlayerState implements Serializable {
    public WaitingState(Game game){
        super(game);
    }

    @Override
    public void activateCannons(ArrayList<Points> cannons, Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void useBatteries(ArrayList<Points> batteries, Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void endActivateCannons(Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void activateEngines(ArrayList<Points> cannons, Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void endActivateEngines(Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void activateShields(ArrayList<Points> shields, Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void endActivateShields(Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void removeGood(Points point, int numGood, Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void addGood(Points point, int numGood, Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void endChangeGoods(Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void landOnAbandon(boolean landed, Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void landOnPlanet(boolean landed, int numPlanet, Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void removeAstronauts(Points point, Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void endRemoveAstronauts(Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void removeBestGood(Points point, int numGood, Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void removeBatteries(Points point, Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void endRemoveBestGoods(Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void acceptReward(boolean accept, Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void endWreckedState(Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void removeTile(Points point, Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void endFixShip(Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void showDeck(Player p, int deckPosition){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + p.getName()));
    }

    @Override
    public void endShowDeck(Player p){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + p.getName()));
    }

    @Override
    public void pickComponentTile(Player p){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + p.getName()));
    }

    @Override
    public void rightRotateTile(Player p){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + p.getName()));
    }

    @Override
    public void leftRotateTile(Player p){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + p.getName()));throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void placeTile(Player player, Points point){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void endBuildShip(Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void addBrownAlien(Points p, Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void addPurpleAlien(Points p, Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void turnTimer(Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void discardComponent(Player p){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + p.getName()));
    }

    @Override
    public void pickDiscoveredComponent(Player p, int index){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + p.getName()));
    }

    @Override
    public void bookComponent(Player p){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + p.getName()));
    }

    @Override
    public void pickBookedTile(int index, Player p){
        new GameView(getGame(), new EndStateException("Cannot do this action now, it's not your turn " + p.getName()));
    }

    @Override
    public void chooseWrecked(Points point, Player player){
        new GameView(getGame(), new EndStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void endAlienState(Player player){
        new GameView(getGame(), new EndStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }

    @Override
    public void AbandonGame(Player player){
        player.abandon(getGame());
    }
    
    @Override
    public void disconnect(Player p){
        getGame().disconnectPlayer(p);
    }

    @Override
    public void selectPosition(int position, Player player){
        new GameView(getGame(), new WaitingStateException("Cannot do this action now, it's not your turn " + player.getName()));
    }
}
