package org.example.ServerPkg.Model;

import org.example.ServerPkg.ControllerPkg.PlayerStates.AbandonedState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.PlayerState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.CardPkg.AdventureCard;
import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.ComponentsPkg.Connector;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.Exceptions.PlayerAbandonedException;
import org.example.ServerPkg.Model.Exceptions.TilesEndedExceptions;
import org.example.ServerPkg.Utils.ShipboardLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Player implements Serializable {
    private final int id;
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
    private Components currentTile;
    private ArrayList<AdventureCard> deckShowed;

    public Player(int id, String name, Game game){
        this.id = id;
        this.position=0;
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

    public int getID(){
        return this.id;
    }

    public int getPosition(){
        return this.position;
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
        for(int i = 0; i < playerShipBoard.getComponentMatrix().length; i++){
            for(int j = 0; j < playerShipBoard.getComponentMatrix()[i].length; j++){
                if (playerShipBoard.validPosition(i,j) && playerShipBoard.getComponentMatrix()[i][j] != null) {
                    Components c = playerShipBoard.getComponentMatrix()[i][j];
                    for (Direction dir : Direction.values()) {
                        int ni = i + dy(dir);
                        int nj = j + dx(dir);
                        if (playerShipBoard.validPosition(ni, nj) && playerShipBoard.getComponentMatrix()[ni][nj] != null){
                            Components neighbor = playerShipBoard.getComponentMatrix()[ni][nj];
                            Direction mySide = rotateRelative(dir, c.getDirection());
                            Direction neighborSide = rotateRelative(opposite(dir), neighbor.getDirection());

                            Connector myConn = c.getDirConnector(mySide);
                            Connector theirConn = neighbor.getDirConnector(neighborSide);

                            if (theirConn == Connector.EMPTY) return false;

                            if (myConn != Connector.UNIVERSAL && theirConn != Connector.UNIVERSAL && myConn != theirConn)
                                return false;
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

    private Direction rotateRelative(Direction dir, Direction rotation) {
        int index = (dir.ordinal() - rotation.ordinal() + 4) % 4;
        return Direction.values()[index];
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

    public boolean getReadyForCards(){return readyForCards;}
}
