package org.example.ServerPkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.Points;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public abstract class ClientProxy {
    private String playerName;
    private final GameController controller;
    private final Server server;

    public ClientProxy(GameController controller, Server server) {
        this.server = server;
        this.controller = controller;
        this.playerName = null;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    private boolean checkClient(){
        if(this.playerName == null) {
            System.out.println("You need to set your name first");
            return false;
        } else if (!server.getIfSubscribed(this)) {
            System.out.println("You are not subscribed to the server");
            return false;
        } else {
            return true;
        }
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void joinServer(String name) {
        try {
            if (this.playerName == null) {
                System.out.println("You need to set your name first");
            } else {
                synchronized (server) {
                    if (server.getNames().contains(name)) {
                        throw new NameAlreadyUsedException(name + " already used, type another one");
                    }
                    this.playerName = name;
                    server.subscribe(this);
                }
                System.out.println(name + "joined server successfully");
            }
        } catch (NameAlreadyUsedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void leaveServer() {
        if (this.playerName == null) {
            System.out.println("You need to set your name first");
        } else{
            synchronized (server) {
                if (!server.getNames().contains(this.playerName)) {
                    System.out.println("You need to join first");
                } else {
                    server.unsubscribe(this);
                    System.out.println(playerName + " left server successfully");
                }
            }
        }
    }

    public void createLobby(int numPlayers, int shipboardLevel, int gameMode) {
        if(checkClient()) {
            try{
                controller.createLobby(playerName, numPlayers, shipboardLevel, gameMode);
                System.out.println("Lobby created successfully");
            }catch(InvalidParameterException | InvalidGameCreationException | InvalidLobbyStateException e) {
                System.out.println("ERROR " + e.getMessage());
            }
        }
    }

    public void joinLobby(){
        if(checkClient()) {
            try{
                controller.joinLobby(playerName);
                System.out.println(playerName + "joined the lobby successfully");
            } catch(InvalidGameCreationException | InvalidLobbyStateException e){
                System.out.println("ERROR " + e.getMessage());
            }
        }
    }

    public void startGame() {
        if(checkClient()){
            try{
                controller.startGame();
            }catch(InvalidMinimumNumberPlayerException | InvalidLobbyStateException e){
                System.out.println("ERROR " + e.getMessage());
            }
        }
    }

    public void exitGame(){
<<<<<<< HEAD
        controller.exitGame(controller.getGame().getPlayerByName(playerName));
=======
        if(checkClient()){
            if(controller.getGame().getPlayers().contains(controller.getGame().getPlayerByName(playerName))){
                try {
                    controller.exitGame(controller.getGame().getPlayerByName(playerName));
                } catch (InvalidLobbyStateException e){
                        System.out.println("ERROR " + e.getMessage());
                }
            } else {
                System.out.println("Join a game first");
            }
        }

>>>>>>> 3222d03a3a60169bd81530096f23d0a72470ccd5
    }

    public void activateCannons(ArrayList<Points> cannons){
        if(checkClient()){
            try {
                controller.getGame().getPlayerByName(this.playerName).getState().activateCannons(cannons);
            } catch (AlreadyCannonException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void useBatteries(ArrayList<Points> batteries){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(this.playerName).getState().useBatteries(batteries);
            } catch (AlreadyBatteryException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void endActivateCannons(){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(this.playerName).getState().endActivateCannons();
            } catch (EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void activateEngines(ArrayList<Points> engines){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(this.playerName).getState().activateEngines(engines);
            } catch (AlreadyEngineException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void endActivateEngines(){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(this.playerName).getState().endActivateEngines();
            } catch (EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void activateShields(ArrayList<Points> shields){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(this.playerName).getState().activateShields(shields);
            } catch (AlreadyShieldException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void endActivateShields(){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(this.playerName).getState().endActivateShields();
            } catch (EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void removeGood(Points point, int numGood){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(this.playerName).getState().removeGood(point, numGood);
            } catch (NotStorageException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void addGood(Points point, int numGood){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(this.playerName).getState().addGood(point, numGood);
            } catch (NotStorageException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void endChangeGoodsState(){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(this.playerName).getState().endChangeGoods();
            } catch (EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void landOnAbandon(boolean bool){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(this.playerName).getState().landOnAbandon(bool);
            } catch (EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void landOnPlanet(boolean bool, int numPlanet){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(playerName).getState().landOnPlanet(bool, numPlanet);
            } catch (PlanetAlreadyVisitedException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void removeAstronauts(Points point){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(this.playerName).getState().removeAstronauts(point);
            } catch (EnoughAstronautsRemovedException | NotCabinException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

    }

    public void endRemoveAstronauts(){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(this.playerName).getState().endRemoveAstronauts();
            } catch (NotEnoughAstronautsRemovedException | NotCabinException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void removeBestGood(Points point, int numGood){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(this.playerName).getState().removeBestGood(point, numGood);
            } catch (EnoughBestGoodsRemovedException | NotStorageException | NotCabinException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void removeBatteries(Points point){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(this.playerName).getState().removeBatteries(point);
            } catch (EnoughBatteriesRemovedException | NotBatteryStorageException | RemoveBatteriesBeforeGoodsException | NotCabinException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void endRemoveBestGoods(){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(this.playerName).getState().endRemoveBestGoods();
            } catch (NotEnoughBestGoodsRemovedException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void acceptReward(boolean bool){
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(this.playerName).getState().acceptReward(bool);
            } catch (EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void sendMessage(){}
}
