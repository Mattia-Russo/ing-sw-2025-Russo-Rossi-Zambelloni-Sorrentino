package org.example.ServerPkg;

import org.example.ServerPkg.ControllerPkg.GameController;
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

    public void endGame(){
        controller.exitGame(controller.getGame().getPlayerByName(playerName));
    }

    public void activateCannons(ArrayList<Points> cannons){
        controller.getGame().getPlayerByName(this.playerName).getState().activateCannons(cannons);
    }

    public void useBatteries(ArrayList<Points> batteries){
        controller.getGame().getPlayerByName(this.playerName).getState().useBatteries(batteries);
    }

    public void endActivateCannons(){
        controller.getGame().getPlayerByName(this.playerName).getState().endActivateCannons();
    }

    public void activateEngines(ArrayList<Points> engines){
        controller.getGame().getPlayerByName(this.playerName).getState().activateEngines(engines);
    }

    public void endActivateEngines(){
        controller.getGame().getPlayerByName(this.playerName).getState().endActivateEngines();
    }

    public void activateShields(ArrayList<Points> shields){
        controller.getGame().getPlayerByName(this.playerName).getState().activateShields(shields);
    }

    public void endActivateShields(){
        controller.getGame().getPlayerByName(this.playerName).getState().endActivateShields();
    }

    public void removeGood(Points point, int numGood){
        controller.getGame().getPlayerByName(this.playerName).getState().removeGood(point, numGood);
    }

    public void addGood(Points point, int numGood){
        controller.getGame().getPlayerByName(this.playerName).getState().addGood(point, numGood);
    }

    public void endChangeGoodsState(){
        controller.getGame().getPlayerByName(this.playerName).getState().endChangeGoods();
    }

    public void landOnAbandon(boolean bool){
        controller.getGame().getPlayerByName(this.playerName).getState().landOnAbandon(bool);
    }

    public void landOnPlanet(boolean bool, int numPlanet){
        controller.getGame().getPlayerByName(playerName).getState().landOnPlanet(bool, numPlanet);
    }

    public void removeAstronauts(Points point){
        controller.getGame().getPlayerByName(this.playerName).getState().removeAstronauts(point);
    }

    public void endRemoveAstronauts(){
        controller.getGame().getPlayerByName(this.playerName).getState().endRemoveAstronauts();
    }

    public void removeBestGood(Points point, int numGood){
        controller.getGame().getPlayerByName(this.playerName).getState().removeBestGood(point, numGood);
    }

    public void 

    /*
+removeAstronauts(Points): void
+endRemoveAstronauts: void
+removeBestGood(Points, int): void
+removeBatteries(Points): void
+endRemoveBestGoods: void
+acceptReward(boolean): void
+ void exitGame (player p)
+ discconect(player p)
    * */
}
