package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.PickTileWithDeckException;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.util.ArrayList;

public class PlayerState {

    public void activateCannons(ArrayList<Points> cannons, Player player){}

    public void useBatteries(ArrayList<Points> batteries, Player player){}

    public void endActivateCannons(Player player){}

    public void activateEngines(ArrayList<Points> cannons, Player player){}

    public void endActivateEngines(Player player){}

    public void activateShields(ArrayList<Points> shields, Player player){}

    public void endActivateShields(Player player){}

    public void removeGood(Points point, int numGood, Player player){}

    public void addGood(Points point, int numGood, Player player){}

    public void endChangeGoods(Player player){}

    public void landOnAbandon(boolean landed, Player player){}

    public void landOnPlanet(boolean landed, int numPlanet, Player player){}

    public void removeAstronauts(Points point, Player player){}

    public void endRemoveAstronauts(Player player){}

    public void removeBestGood(Points point, int numGood, Player player){}

    public void removeBatteries(Points point, Player player){}

    public void endRemoveBestGoods(Player player){}

    public void acceptReward(boolean accept, Player player){}

    public void chooseWrecked(Points point, Player player){    }

    public void endWreckedState(Player player){}

    public void removeTile(Points point, Player player){}

    public void endFixShip(Player player){}

    public void showDeck(Player p, int deckPosition){}

    public void endShowDeck(Player p){}

    public void pickComponentTile(Player p){}

    public void rightRotateTile(Player p){}

    public void leftRotateTile(Player p){}

    public void placeTile(Player player, Points point){}

    public void endBuildShip(Player player){}

    public void addBrownAlien(Points p, Player player){}

    public void addPurpleAlien(Points p, Player player){}

    public void endAlienState(Player player){}

    public void turnTimer(Player player){}

    public void AbandonGame(Player player){}

    public void disconnect(Player disconnectingPlayer){}

    public void discardComponent(Player p){}

    public void pickDiscoveredComponent(Player p, int index){}

    public void setPosition(Player player){}
}
