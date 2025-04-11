package org.example.Server.Controller.States;

import org.example.Server.Model.Player;
import org.example.Server.Model.Points;

import java.util.ArrayList;

public class PlayerState {

    public void activateCannons(ArrayList<Points> cannons){}

    public void useBatteries(ArrayList<Points> batteries){}

    public void endActivateCannons(){}

    public void activateEngines(ArrayList<Points> cannons){}

    public void endActivateEngines(){}

    public void activateShields(Points shields){}

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

    public void chooseWrecked(Player player, Points point){    }

    public void endWreckedState(){}

    public void removeTile(Points point, Player player){}

    public void endFixShip(Player player){}

    public void showDeck(Player p, int deckPosition){}

    public void endShowDeck(Player p){}

    public void pickComponentTile(Player p){}

    public void RightRotateTile(Player p){}

    public void LeftRotateTile(Player p){}

    public void placeTile(Player player, Points point){}

    public void endBuildShip(Player player){}

    public void exitGame(Player player){}
}
