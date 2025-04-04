package org.example.Server.Controller.States;

import org.example.Server.Model.Exceptions.WaitingStateException;
import org.example.Server.Model.Points;

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
    public void activateShields(Points shields){
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
}
