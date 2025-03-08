package org.example;

public class Player {
    private int position;
    private ShipBoard playerShipBoard;
    private boolean abandoned;
    private boolean onPlanet;

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

}
