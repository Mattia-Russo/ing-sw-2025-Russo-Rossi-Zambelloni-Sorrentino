package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.util.ArrayList;

public class PlayerState {

    public void activateCannons(ArrayList<Points> cannons){}

    public void useBatteries(ArrayList<Points> batteries){}

    public void endActivateCannons(){}

    public void activateEngines(ArrayList<Points> cannons){}

    public void endActivateEngines(){}

    public void activateShields(ArrayList<Points> shields){}

    public void endActivateShields(){}

    public void removeGood(Points point, int numGood){}

    public void addGood(Points point, int numGood){}

    public void endChangeGoods(){}

    public void landOnAbandon(boolean landed){}

    public void landOnPlanet(boolean landed, int numPlanet){}

    public void removeAstronauts(Points point){}

    public void endRemoveAstronauts(){}

    public void removeBestGood(Points point, int numGood){}

    public void removeBatteries(Points point){}

    public void endRemoveBestGoods(){}

    public void acceptReward(boolean accept){}

    public void chooseWrecked(Points point){    }

    public void endWreckedState(){}

    public void removeTile(Points point, Player player){}

    public void endFixShip(Player player){}

    public void showDeck(Player p, int deckPosition){}

    public void endShowDeck(Player p){}

    public void pickComponentTile(Player p){}

    public void rightRotateTile(Player p){}

    public void leftRotateTile(Player p){}

    public void placeTile(Player player, Points point){}

    public void endBuildShip(Player player){}

    public void addBrownAlien(Points p){}

    public void addPurpleAlien(Points p){}

    public void endAlienState(){}

    public void turnTimer(Player player){}

    public void AbandonGame(Player player){}

    public void disconnect(Player disconnectingPlayer, Game game){}

}
