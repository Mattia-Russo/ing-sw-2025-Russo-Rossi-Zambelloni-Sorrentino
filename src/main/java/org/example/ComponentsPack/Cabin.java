package org.example.ComponentsPack;

public class Cabin extends Components {
    private int numAstronauts;
    private boolean withLifeSupport;
    private boolean isCentral;
    private Alien alien;

    public int getNumAstronauts() {
        return numAstronauts;
    }

    public Alien getAlien(){
        return alien;
    }

    public boolean getWithLifeSupport() {
        return withLifeSupport;
    }

    public boolean getIsCentral() {
        return isCentral;
    }

    public void changeNumAstronauts(int amount) {
        numAstronauts = numAstronauts + amount;
    }

    public void addAlien(Alien change){
        alien = change;
    }

}
