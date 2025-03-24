package org.example.ComponentsPack;

import java.util.ArrayList;

public class Cabin extends Components {
    private int numAstronauts;
    private boolean withLifeSupport;
    private ArrayList<LifeSupportSystem> lifeSupportSystemArrayList;
    private final boolean isCentral;
    private Alien alien;

    public Cabin(boolean isCentral, Direction direction, Connector[] connectors ) {
        super(direction, connectors);
        this.numAstronauts = 0;
        this.withLifeSupport = false;
        this.lifeSupportSystemArrayList= new ArrayList<>();
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
            if (numAstronauts + amount > 2) {
                throw new IllegalArgumentException("Cabin full!");
            }
            numAstronauts += amount;
    }

    public void addLifeSupport(LifeSupportSystem l) {
        this.lifeSupportSystemArrayList.add(l);
    }

    public void removeLifeSupport(LifeSupportSystem l) {
        this.lifeSupportSystemArrayList.remove(l);
    }

    public ArrayList<LifeSupportSystem> getLifeSupportSystemArrayList() {
        return lifeSupportSystemArrayList;
    }

    public void addAlien(Alien change){
        if(withLifeSupport){
            boolean found = false;
            for(int i=0; i<getLifeSupportSystemArrayList().size() && !found; i++){
                if (getLifeSupportSystemArrayList().get(i).getColour() == change.getColour()) {
                    found = true;
                }
            }
            if(found){
                alien = change;
            }
        }
    }

}
