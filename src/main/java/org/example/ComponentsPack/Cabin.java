package org.example.ComponentsPack;

public class Cabin extends Components {
    private int numAstronauts;
    private boolean withLifeSupport;
    private final boolean isCentral;
    private Alien alien;

    public Cabin(boolean isCentral, Direction direction, Connector[] connectors ) {
        super(direction, connectors);
        this.numAstronauts = 0;
        this.withLifeSupport = false;
        this.isCentral = isCentral;
        this.alien = null;
    }

    public int getNumAstronauts() {
        return numAstronauts;
    }

    public Alien getAlien(){
        return alien;
    }

    public boolean getWithLifeSupport() {
        return withLifeSupport;
    }

    public void changeWithLifeSupport(boolean withLifeSupport) {
        this.withLifeSupport=withLifeSupport;
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
