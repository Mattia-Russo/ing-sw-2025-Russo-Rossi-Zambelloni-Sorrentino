package org.example.Server.Controller.PlayerStates;

import org.example.Server.Model.Exceptions.AbandonedStateException;
import org.example.Server.Model.Player;
import org.example.Server.Model.Points;

import java.util.ArrayList;

public class AbandonedState extends PlayerState{

    @Override
    public void activateCannons(ArrayList<Points> cannons){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void useBatteries(ArrayList<Points> batteries){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void endActivateCannons(){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void activateEngines(ArrayList<Points> cannons){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void endActivateEngines(){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void activateShields(Points shields){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void endActivateShields(){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void removeGood(Points point, int numGood){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void addGood(Points point, int numGood){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void endChangeGoods(){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void landOnAbandon(boolean landed){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void landOnPlanet(boolean landed, int numPlanet){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void removeAstronauts(Points point){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void endRemoveAstronauts(){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void removeBestGood(Points point, int numGood){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void removeBatteries(Points point){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void endRemoveBestGoods(){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void acceptReward(boolean accept){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void chooseWrecked(Points point){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void endWreckedState(){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void removeTile(Points point, Player player){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void endFixShip(Player player){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void showDeck(Player p, int deckPosition){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void endShowDeck(Player p){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void pickComponentTile(Player p){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void RightRotateTile(Player p){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void LeftRotateTile(Player p){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void placeTile(Player player, Points point){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void endBuildShip(Player player){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void exitGame(Player player){
        throw new AbandonedStateException("Game still going, wait for the end of the game");
    }

    @Override
    public void addBrownAlien(Points p){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }

    @Override
    public void addPurpleAlien(Points p){
        throw new AbandonedStateException("You've abandoned, wait for the end of the game");
    }
}
