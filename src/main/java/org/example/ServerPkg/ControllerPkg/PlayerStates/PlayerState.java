package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class PlayerState implements Serializable {
    private final Game game;
    private final Player player;
    public PlayerState(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public void activateCannons(ArrayList<Points> cannons, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void useBatteries(ArrayList<Points> batteries, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void endActivateCannons(Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void activateEngines(ArrayList<Points> cannons, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void endActivateEngines(Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void activateShields(ArrayList<Points> shields, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void endActivateShields(Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void removeGood(Points point, int numGood, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void addGood(Points point, int numGood, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void endChangeGoods(Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void landOnAbandon(boolean landed, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void landOnPlanet(boolean landed, int numPlanet, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void removeAstronauts(Points point, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void endRemoveAstronauts(Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void removeBestGood(Points point, int numGood, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void removeBatteries(Points point, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void endRemoveBestGoods(Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void acceptReward(boolean accept, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void chooseWrecked(Points point, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void endWreckedState(Player player) throws RemoteException {
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void removeTile(Points point, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void endFixShip(Player player) throws RemoteException {
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void showDeck(Player p, int deckPosition){
        new GameView(game, new IllegalStateException("You can't do this now " + p.getName()));
    }

    public void endShowDeck(Player p){
        new GameView(game, new IllegalStateException("You can't do this now " + p.getName()));
    }

    public void pickComponentTile(Player p){
        new GameView(game, new IllegalStateException("You can't do this now " + p.getName()));
    }

    public void rightRotateTile(Player p){
        new GameView(game, new IllegalStateException("You can't do this now " + p.getName()));
    }

    public void leftRotateTile(Player p){
        new GameView(game, new IllegalStateException("You can't do this now " + p.getName()));
    }

    public void placeTile(Player player, Points point){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void endBuildShip(Player player) throws RemoteException {
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void addBrownAlien(Points p, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void addPurpleAlien(Points p, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void endAlienState(Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void turnTimer(Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void AbandonGame(Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }

    public void disconnect(Player disconnectingPlayer) throws RemoteException {}

    public void discardComponent(Player p){
        new GameView(game, new IllegalStateException("You can't do this now " + p.getName()));
    }

    public void pickDiscoveredComponent(Player p, int index){
        new GameView(game, new IllegalStateException("You can't do this now " + p.getName()));
    }

    public void bookComponent(Player p){
        new GameView(game, new IllegalStateException("You can't do this now " + p.getName()));
    }

    public void pickBookedTile(int index, Player p){
        new GameView(game, new IllegalStateException("You can't do this now " + p.getName()));
    }

    public void selectPosition(int position, Player player){
        new GameView(game, new IllegalStateException("You can't do this now " + player.getName()));
    }
}
