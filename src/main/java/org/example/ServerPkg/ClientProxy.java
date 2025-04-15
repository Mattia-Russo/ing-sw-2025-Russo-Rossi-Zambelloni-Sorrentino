package org.example.ServerPkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.Points;

import java.util.ArrayList;

public abstract class ClientProxy {
    private String playerName;
    private final GameController controller;

    public ClientProxy(String name, GameController controller) {
        this.playerName = name;
        this.controller = controller;
    }

    public void createLobby(int numPlayers, int shipboardLevel, int gameMode) {
        controller.createLobby(playerName, numPlayers, shipboardLevel, gameMode);
    }

    public void joinLobby(){
        controller.joinLobby(playerName);
    }

    public void startGame(){
        controller.startGame();
    }

    public void exitGame(){
        controller.exitGame(controller.getGame().getPlayerByName(playerName));
    }

    public void activateCannons(ArrayList<Points> cannons){
        try {
            controller.getGame().getPlayerByName(this.playerName).getState().activateCannons(cannons);
        } catch (AlreadyCannonException | EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void useBatteries(ArrayList<Points> batteries){
        try{
        controller.getGame().getPlayerByName(this.playerName).getState().useBatteries(batteries);
        } catch (AlreadyBatteryException | EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void endActivateCannons(){
        try{
        controller.getGame().getPlayerByName(this.playerName).getState().endActivateCannons();
        } catch (EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void activateEngines(ArrayList<Points> engines){
        try{
            controller.getGame().getPlayerByName(this.playerName).getState().activateEngines(engines);
        } catch (AlreadyEngineException | EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void endActivateEngines(){
        try{
            controller.getGame().getPlayerByName(this.playerName).getState().endActivateEngines();
        } catch (EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void activateShields(ArrayList<Points> shields){
        try{
            controller.getGame().getPlayerByName(this.playerName).getState().activateShields(shields);
        } catch (AlreadyShieldException | EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void endActivateShields(){
        try{
            controller.getGame().getPlayerByName(this.playerName).getState().endActivateShields();
        } catch (EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeGood(Points point, int numGood){
        try{
        controller.getGame().getPlayerByName(this.playerName).getState().removeGood(point, numGood);
        } catch (NotStorageException | EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addGood(Points point, int numGood){
        try{
        controller.getGame().getPlayerByName(this.playerName).getState().addGood(point, numGood);
        } catch (NotStorageException | EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void endChangeGoodsState(){
        try{
            controller.getGame().getPlayerByName(this.playerName).getState().endChangeGoods();
        } catch (EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void landOnAbandon(boolean bool){
        try{
            controller.getGame().getPlayerByName(this.playerName).getState().landOnAbandon(bool);
        } catch (EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void landOnPlanet(boolean bool, int numPlanet){
        try{
            controller.getGame().getPlayerByName(playerName).getState().landOnPlanet(bool, numPlanet);
        } catch (PlanetAlreadyVisitedException | EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeAstronauts(Points point){
        try{
            controller.getGame().getPlayerByName(this.playerName).getState().removeAstronauts(point);
        } catch (EnoughAstronautsRemovedException | NotCabinException | EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void endRemoveAstronauts(){
        try{
            controller.getGame().getPlayerByName(this.playerName).getState().endRemoveAstronauts();
        } catch (NotEnoughAstronautsRemovedException | NotCabinException | EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeBestGood(Points point, int numGood){
        try{
            controller.getGame().getPlayerByName(this.playerName).getState().removeBestGood(point, numGood);
        } catch (EnoughBestGoodsRemovedException | NotStorageException | NotCabinException | EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeBatteries(Points point){
        try{
            controller.getGame().getPlayerByName(this.playerName).getState().removeBatteries(point);
        } catch (EnoughBatteriesRemovedException | NotBatteryStorageException | RemoveBatteriesBeforeGoodsException | NotCabinException | EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void endRemoveBestGoods(){
        try{
            controller.getGame().getPlayerByName(this.playerName).getState().endRemoveBestGoods();
        } catch (NotEnoughBestGoodsRemovedException | EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void acceptReward(boolean bool){
        try{
            controller.getGame().getPlayerByName(this.playerName).getState().acceptReward(bool);
        } catch (EndStateException | WaitingStateException | AbandonedStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
