package org.example;

import org.example.ComponentsPack.*;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private final int id;
    private int position;
    private final ShipBoard playerShipBoard;
    private boolean abandoned;
    private boolean onPlanet;
    private int numCredits;

    public Player(ShipBoard shipBoard, int id){
        this.id = id;
        this.position=0;
        this.playerShipBoard=shipBoard;
        this.abandoned=false;
        this.onPlanet=false;
        this.numCredits=0;
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

    public ShipBoard getPlayerShipBoard() {
        return playerShipBoard;
    }

    public void changeOnPlanet(){
        this.onPlanet = !onPlanet;
    }

    public void abandon(){
        this.abandoned=true;
    }

    public void changePosition(int val){
        this.position+=val;
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
        Random random = new Random();
        return components.get(random.nextInt(components.size()));
    }

    public boolean checkShip() {
        for(int i = 0; i < playerShipBoard.getComponentMatrix().length; i++){
            for(int j = 0; j < playerShipBoard.getComponentMatrix()[i].length; j++){
                Components c=playerShipBoard.getComponentMatrix()[i][j];
                if(playerShipBoard.validPosition(i,j)){
                    for(int k=0; k<4; k++){
                        if(c.getConnectors()[k]!= Connector.EMPTY) {
                            if(c.getConnectors()[k] == Connector.UNIVERSAL ){
                                switch ((c.getDirection().ordinal()+k)%4){
                                    case 0:
                                        if(playerShipBoard.validPosition(i, j-1)) {
                                            if(playerShipBoard.getComponentMatrix()[i][j-1].getDirConnector(Direction.values()[((c.getDirection().ordinal()+k+2)%4)])==Connector.EMPTY){ //prende il connettore del componente di fianco che punta al componente che stiamo controllando
                                                return false;
                                            }
                                        }
                                    case 1:
                                        if(playerShipBoard.validPosition(i+1, j)) {
                                            if(playerShipBoard.getComponentMatrix()[i+1][j].getDirConnector(Direction.values()[((c.getDirection().ordinal()+k+2)%4)])==Connector.EMPTY){
                                                return false;
                                            }
                                        }
                                    case 2:
                                        if(playerShipBoard.validPosition(i, j+1)) {
                                            if(playerShipBoard.getComponentMatrix()[i][j+1].getDirConnector(Direction.values()[((c.getDirection().ordinal()+k+2)%4)])==Connector.EMPTY){
                                                return false;
                                            }
                                        }
                                    case 3:
                                        if(playerShipBoard.validPosition(i-1, j)) {
                                            if(playerShipBoard.getComponentMatrix()[i-1][j].getDirConnector(Direction.values()[((c.getDirection().ordinal()+k+2)%4)])==Connector.EMPTY){
                                                return false;
                                            }
                                        }
                                }
                            }
                            else {
                                switch ((c.getDirection().ordinal()+k)%4){
                                    case 0:
                                        if(playerShipBoard.validPosition(i, j-1)) {
                                            if(playerShipBoard.getComponentMatrix()[i][j-1].getDirConnector(Direction.values()[((c.getDirection().ordinal()+k+2)%4)])!=c.getDirConnector(Direction.values()[(c.getDirection().ordinal()+k)%4])){
                                                return false;
                                            }
                                        }
                                    case 1:
                                        if(playerShipBoard.validPosition(i+1, j)) {
                                            if(playerShipBoard.getComponentMatrix()[i+1][j].getDirConnector(Direction.values()[((c.getDirection().ordinal()+k+2)%4)])!=c.getDirConnector(Direction.values()[(c.getDirection().ordinal()+k)%4])){
                                                return false;
                                            }
                                        }
                                    case 2:
                                        if(playerShipBoard.validPosition(i, j+1)) {
                                            if(playerShipBoard.getComponentMatrix()[i][j+1].getDirConnector(Direction.values()[((c.getDirection().ordinal()+k+2)%4)])!=c.getDirConnector(Direction.values()[(c.getDirection().ordinal()+k)%4])){
                                                return false;
                                            }
                                        }
                                    case 3:
                                        if(playerShipBoard.validPosition(i-1, j)) {
                                            if(playerShipBoard.getComponentMatrix()[i-1][j].getDirConnector(Direction.values()[((c.getDirection().ordinal()+k+2)%4)])!=c.getDirConnector(Direction.values()[(c.getDirection().ordinal()+k)%4])){
                                                return false;
                                            }
                                        }
                                }
                            }
                        }
                    }
                }
                if (c instanceof Cannon){
                    switch (c.getDirection()){
                        case NORTH:
                            if(playerShipBoard.validPosition(i, j-1) && playerShipBoard.getComponentMatrix()[i][j-1] != null){
                                return false;
                            }
                        case EAST:
                            if(playerShipBoard.validPosition(i+1, j) && playerShipBoard.getComponentMatrix()[i+1][j] != null){
                                return false;
                            }
                        case SOUTH:
                            if(playerShipBoard.validPosition(i, j+1) && playerShipBoard.getComponentMatrix()[i][j+1] != null){
                                return false;
                            }
                        case WEST:
                            if(playerShipBoard.validPosition(i-1, j) && playerShipBoard.getComponentMatrix()[i-1][j] != null){
                                return false;
                            }
                    }
                }
                if(c instanceof Engine) {
                    if (c.getDirection() != Direction.SOUTH) {
                        return false;
                    }
                    if(playerShipBoard.validPosition(i, j+1) && playerShipBoard.getComponentMatrix()[i][j+1] != null){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
