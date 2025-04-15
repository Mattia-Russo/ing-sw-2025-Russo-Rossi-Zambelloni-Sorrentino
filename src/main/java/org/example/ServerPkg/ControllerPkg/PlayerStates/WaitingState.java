package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.WaitingStateException;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.util.ArrayList;

public class WaitingState extends PlayerState {

    @Override
    public void activateCannons(ArrayList<Points> cannons){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void useBatteries(ArrayList<Points> batteries){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void endActivateCannons(){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void activateEngines(ArrayList<Points> cannons){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void endActivateEngines(){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void activateShields(ArrayList<Points> shields){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void endActivateShields(){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void removeGood(Points point, int numGood){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void addGood(Points point, int numGood){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void endChangeGoods(){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void landOnAbandon(boolean landed){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void landOnPlanet(boolean landed, int numPlanet){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void removeAstronauts(Points point){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void endRemoveAstronauts(){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void removeBestGood(Points point, int numGood){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void removeBatteries(Points point){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void endRemoveBestGoods(){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void acceptReward(boolean accept){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void endWreckedState(){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void removeTile(Points point, Player player){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void endFixShip(Player player){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void showDeck(Player p, int deckPosition){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void endShowDeck(Player p){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void pickComponentTile(Player p){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void RightRotateTile(Player p){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void LeftRotateTile(Player p){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void placeTile(Player player, Points point){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void endBuildShip(Player player){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void exitGame(Player player){
        throw new WaitingStateException("Game still going, wait for the end of the game");
    }

    @Override
    public void addBrownAlien(Points p){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }

    @Override
    public void addPurpleAlien(Points p){
        throw new WaitingStateException("Cannot do this action now, it's not your turn");
    }
}
