package org.example.ServerPkg.Model;

import org.example.ServerPkg.ControllerPkg.PlayerStates.AbandonedState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.PlayerState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.CardPkg.AdventureCard;
import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Exceptions.PlayerAbandonedException;
import org.example.ServerPkg.Model.Exceptions.TilesEndedExceptions;
import org.example.ServerPkg.Utils.ShipboardLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Player implements Serializable {
    private final String name;
    private int position;
    private ShipBoard playerShipBoard;
    private boolean abandoned;
    private boolean onPlanet;
    private boolean shipBuilded;
    private boolean readyForCards;
    private String rocketColour;
    private int numCredits;
    private PlayerState state;
    private boolean shipOK;
    private boolean posValid;
    private Components currentTile;
    private ArrayList<AdventureCard> deckShowed;

    public Player(String name, Game game){
        this.position = 0;
        this.posValid = false;
        this.playerShipBoard=null;
        this.abandoned=false;
        this.onPlanet=false;
        this.readyForCards=false;
        this.numCredits=0;
        this.name=name;
        this.shipBuilded=false;
        this.state = new WaitingState(game);
        this.shipOK=true;
        this.currentTile = null;
        this.deckShowed = null;
    }

    public void setRocketColour(String rocketColour){
        this.rocketColour = rocketColour;
    }

    public String getRocketColour(){
        return rocketColour;
    }

    public void setPlayerShipboard(int level){
        if(level==2){
            playerShipBoard = ShipboardLoader.loadLevel2();
        }else{
            playerShipBoard= ShipboardLoader.loadLevel1();
        }
    }

    public int getPosition(){
        return this.position;
    }

    public boolean isPosValid(){
        return this.posValid;
    }

    public boolean isAbandoned() {
        return abandoned;
    }

    public boolean isOnPlanet() {
        return onPlanet;
    }

    public String getName(){
        return this.name;
    }

    public ShipBoard getPlayerShipBoard() {
        return playerShipBoard;
    }

    public void changeOnPlanet(){
        this.onPlanet = !onPlanet;
    }

    public void abandon(Game game){
        this.abandoned=true;
        this.state = new AbandonedState(game);
    }

    public void changePosition(int val){
        if (!this.abandoned) {
            this.position += val;
        } else {
            throw new PlayerAbandonedException("The player has abandoned");
        }
    }

    public void setPosition(int val){
        this.position = val;
        this.posValid = true;
    }

    public int getNumCredits(){
        return numCredits;
    }

    public int rollDice() {
        Random random = new Random();
        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;
        return die1 + die2;
    }

    public void changeCredits(int num){
        numCredits+=num;
    }

    public Components pickComponent(ArrayList<Components> components){
        if (components == null || components.isEmpty()) {
            throw new TilesEndedExceptions("No more components tiles available");
        }
        Random random = new Random();
        return components.get(random.nextInt(components.size()));
    }

    public boolean checkShip() {
        for(int i = 4; i < playerShipBoard.getComponentMatrix().length + 4; i++){
            for(int j = 5; j < playerShipBoard.getComponentMatrix()[i].length + 5; j++){
                if (playerShipBoard.validPosition(i,j) && playerShipBoard.getComponentMatrix()[i][j] != null) {
                    Components c = playerShipBoard.getComponentMatrix()[i][j];
                    for (Direction dir : Direction.values()) {
                        int ni = i + dy(dir);
                        int nj = j + dx(dir);
                        if ((ni < playerShipBoard.getComponentMatrix().length + 4 && ni > 4) && (nj > 5 && nj < playerShipBoard.getComponentMatrix()[ni].length + 5)){
                            if (playerShipBoard.validPosition(ni, nj) && playerShipBoard.getComponentMatrix()[ni][nj] != null) {
                                Components neighbor = playerShipBoard.getComponentMatrix()[ni][nj];
                                Connector myConn = c.getDirConnector(dir);
                                Connector theirConn = neighbor.getDirConnector(opposite(dir));

                                if ((myConn != Connector.UNIVERSAL && theirConn != Connector.UNIVERSAL && myConn != theirConn) || (theirConn == Connector.EMPTY && theirConn != myConn))
                                    return false;
                            }
                        }
                    }
                    if(c.checkRightCannon(this.playerShipBoard)){
                        return false;
                    }

                    if(c.checkRightEngine(this.playerShipBoard)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Direction opposite(Direction dir) {
        return Direction.values()[(dir.ordinal() + 2) % 4];
    }

    private int dx(Direction dir) {
        return switch (dir) {
            case EAST -> 1;
            case WEST -> -1;
            default -> 0;
        };
    }

    private int dy(Direction dir) {
        return switch (dir) {
            case SOUTH -> 1;
            case NORTH -> -1;
            default -> 0;
        };
    }

    public PlayerState getState() {
        return this.state;
    }

    public void setPlayerState(PlayerState state) {
        this.state = state;
    }

    public boolean getShipOK(){
        return shipOK;
    }

    public void setShipOK(boolean change){
        shipOK = change;
    }

    public void setCurrentTile(Components currentTile) {
        this.currentTile = currentTile;
    }

    public Components getCurrentTile() {
        return this.currentTile;
    }

    public boolean getShipBuilded(){
        return this.shipBuilded;
    }

    public void setShipBuilded(){
        this.shipBuilded = true;
    }

    public ArrayList<AdventureCard> getDeckShowed() {
        return this.deckShowed;
    }

    public void setDeckShowed(ArrayList<AdventureCard> deckShowed) {
        this.deckShowed = deckShowed;
    }

    public void setReadyForCards(boolean readyForCards) {
        this.readyForCards = readyForCards;
    }

    public boolean getReadyForCards(){
        return readyForCards;
    }

    public void opShip(){
        for(int i = 4; i < playerShipBoard.getComponentMatrix().length + 4; i++){
            for(int j = 5; j < playerShipBoard.getComponentMatrix()[i].length + 5; j++){
                if (playerShipBoard.validPosition(i,j) && playerShipBoard.getComponentMatrix()[i][j] != null) {
                    if(i!=7 && j!=7) {
                        getPlayerShipBoard().removeComponent(i, j);
                    }
                }
            }
        }
        getPlayerShipBoard().placeComponent(6,5, new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.UNIVERSAL, Connector.EMPTY}));
        getPlayerShipBoard().placeComponent(6,6, new BatteryStorage(0,3, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}));
        getPlayerShipBoard().placeComponent(7,6, new Cannon(0,2, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}));
        getPlayerShipBoard().placeComponent(8,5, new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.UNIVERSAL, Connector.EMPTY}));
        getPlayerShipBoard().placeComponent(5,6, new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY}));
        getPlayerShipBoard().placeComponent(8,6, new Cabin(0,false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}));
        getPlayerShipBoard().placeComponent(9,6, new Cannon(0,1, Direction.NORTH, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL}));
        getPlayerShipBoard().placeComponent(4,7, new Cannon(0,1, Direction.WEST, new Connector[]{Connector.EMPTY, Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL}));
        getPlayerShipBoard().placeComponent(5,7, new Shield(0, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.WEST));
        getPlayerShipBoard().placeComponent(6,7, new Storage(0,true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, 3));
        getPlayerShipBoard().placeComponent(8, 7, new LifeSupportSystem(0, AlienColour.BROWN, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}));
        getPlayerShipBoard().placeComponent(9, 7, new Cabin(0, false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}));
        getPlayerShipBoard().placeComponent(10, 7, new Cannon(0,1, Direction.EAST, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY}));
        getPlayerShipBoard().placeComponent(4, 8, new Cannon(0,1, Direction.WEST, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}));
        getPlayerShipBoard().placeComponent(5, 8, new Shield(0, Direction.SOUTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, Direction.EAST));
        getPlayerShipBoard().placeComponent(6,8, new BatteryStorage(0,3, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}));
        getPlayerShipBoard().placeComponent(7, 8, new Engine(0, 1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.UNIVERSAL}));
        getPlayerShipBoard().placeComponent(8, 8, new Storage(0, true, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}, 3));
        getPlayerShipBoard().placeComponent(9, 8, new Cabin(0, false, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}));
        getPlayerShipBoard().placeComponent(10, 8, new Cannon(0, 1, Direction.EAST, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}));
        getPlayerShipBoard().placeComponent(4, 9, new Engine(0, 1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.EMPTY}));
        getPlayerShipBoard().placeComponent(5, 9, new Cannon(0, 1, Direction.SOUTH, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}));
        getPlayerShipBoard().placeComponent(6, 9, new Engine(0, 1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.EMPTY, Connector.UNIVERSAL}));
        getPlayerShipBoard().placeComponent(8, 9, new Engine(0, 1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.EMPTY, Connector.EMPTY}));
        getPlayerShipBoard().placeComponent(9, 9, new Cannon(0, 1, Direction.SOUTH, new Connector[]{Connector.EMPTY, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL}));
        getPlayerShipBoard().placeComponent(10, 9, new Engine(0, 1, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.EMPTY, Connector.UNIVERSAL}));
    }
}
