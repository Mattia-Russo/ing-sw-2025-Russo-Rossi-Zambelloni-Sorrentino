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

import java.util.ArrayList;
import java.util.Random;

public class Player {
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

    public Player(int id, String name){
        this.id = id;
        this.position=0;
        this.playerShipBoard=null;
        this.abandoned=false;
        this.onPlanet=false;
        this.readyForCards=false;
        this.numCredits=0;
        this.name=name;
        this.shipBuilded=false;
        this.state = new WaitingState();
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
            playerShipBoard= ShipboardLoader.loadLevel2();
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

    public void abandon(){
        this.abandoned=true;
        this.state = new AbandonedState();
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

    public boolean checkShip() {    // devo controllare solo se i connettori in basso e a dx sono giusti
        for(int i = 0; i < playerShipBoard.getComponentMatrix().length; i++){
            for(int j = 0; j < playerShipBoard.getComponentMatrix()[i].length; j++){
                if (playerShipBoard.validPosition(i,j) && playerShipBoard.getComponentMatrix()[i][j] != null) {
                    Components c = playerShipBoard.getComponentMatrix()[i][j];
                    for (int k = 0; k < 4; k++) {
                        switch ((c.getDirection().ordinal() + k) % 4) {
                            case 0:
                                if (playerShipBoard.validPosition(i, j - 1) && playerShipBoard.getComponentMatrix()[i][j-1]!=null) {
                                    if (playerShipBoard.getComponentMatrix()[i][j - 1].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) == Connector.EMPTY) { //prende il connettore del componente di fianco che punta al componente che stiamo controllando
                                        return false;
                                    }
                                }
                                break;
                            case 1:
                                if (playerShipBoard.validPosition(i + 1, j) && playerShipBoard.getComponentMatrix()[i+1][j]!=null) {
                                    if (playerShipBoard.getComponentMatrix()[i + 1][j].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) == Connector.EMPTY) {
                                        return false;
                                    }
                                    if (playerShipBoard.getComponentMatrix()[i + 1][j].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) != Connector.UNIVERSAL
                                            && c.getDirConnector(Direction.values()[(c.getDirection().ordinal() + k) % 4]) != Connector.UNIVERSAL
                                            && playerShipBoard.getComponentMatrix()[i + 1][j].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) !=
                                            c.getDirConnector(Direction.values()[(c.getDirection().ordinal() + k) % 4])) {
                                        return false;
                                    }
                                }
                                break;
                            case 2:
                                if (playerShipBoard.validPosition(i, j + 1) && playerShipBoard.getComponentMatrix()[i][j+1]!=null) {
                                    if (playerShipBoard.getComponentMatrix()[i][j + 1].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) == Connector.EMPTY) {
                                        return false;
                                    }
                                    if (playerShipBoard.getComponentMatrix()[i][j + 1].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) != Connector.UNIVERSAL
                                            && c.getDirConnector(Direction.values()[(c.getDirection().ordinal() + k) % 4]) != Connector.UNIVERSAL
                                            && playerShipBoard.getComponentMatrix()[i][j + 1].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) !=
                                            c.getDirConnector(Direction.values()[(c.getDirection().ordinal() + k) % 4])) {
                                        return false;
                                    }
                                }
                                break;
                            case 3:
                                if (playerShipBoard.validPosition(i - 1, j) && playerShipBoard.getComponentMatrix()[i-1][j]!=null) {
                                    if (playerShipBoard.getComponentMatrix()[i - 1][j].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) == Connector.EMPTY) {
                                        return false;
                                    }
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
