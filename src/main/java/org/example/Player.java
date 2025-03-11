package org.example;

import java.util.Random;

public class Player {
    private Random random;
    private int position;
    private ShipBoard playerShipBoard;
    private boolean abandoned;
    private boolean onPlanet;
    private int numCredits;

    public Player(ShipBoard shipBoard){
        this.position=0;
        this.playerShipBoard=shipBoard;
        this.abandoned=false;
        this.onPlanet=false;
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
        if(onPlanet){
            this.onPlanet=false;
        }
        else{
            this.onPlanet=true;
        }
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
        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;
        return die1 + die2;
    }

    public void changeCredits(int num){
        numCredits+=num;
    }
}
