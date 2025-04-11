package org.example.Server.Controller.PlayerStates;

import org.example.Server.Model.Exceptions.EndStateException;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;
import org.example.Server.Model.Points;

import java.util.ArrayList;

public class EndState extends PlayerState{
    private Game game;

    public EndState(Game game){
        this.game = game;
    }

    @Override
    public void activateCannons(ArrayList<Points> cannons){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void useBatteries(ArrayList<Points> batteries){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void endActivateCannons(){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void activateEngines(ArrayList<Points> cannons){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void endActivateEngines(){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void activateShields(Points shields){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void endActivateShields(){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void removeGood(Points point, int numGood){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void addGood(Points point, int numGood){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void endChangeGoods(){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void landOnAbandon(boolean landed){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void landOnPlanet(boolean landed, int numPlanet){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void removeAstronauts(Points point){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void endRemoveAstronauts(){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void removeBestGood(Points point, int numGood){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void removeBatteries(Points point){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void endRemoveBestGoods(){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void acceptReward(boolean accept){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void endWreckedState(){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void removeTile(Points point, Player player){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void endFixShip(Player player){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void showDeck(Player p, int deckPosition){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void endShowDeck(Player p){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void pickComponentTile(Player p){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void RightRotateTile(Player p){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void LeftRotateTile(Player p){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void placeTile(Player player, Points point){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }

    @Override
    public void endBuildShip(Player player){
        throw new EndStateException("The game has ended, cannot do any action anymore");
    }
}
