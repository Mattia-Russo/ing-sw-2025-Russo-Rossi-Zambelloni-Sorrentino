package org.example.Model.ComponentsPack;

import org.example.Model.Exceptions.*;
import org.example.Model.ShipBoard;

import java.util.ArrayList;

public class Cabin extends Components {
    private int numAstronauts;
    private boolean withLifeSupport;
    private ArrayList<LifeSupportSystem> lifeSupportSystemArrayList;
    private final boolean isCentral;
    private Alien alien;

    public Cabin(boolean isCentral, Direction direction, Connector[] connectors) {
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
                throw new OverloadedCapacityException("Cabin full!");
            } else if (numAstronauts + amount < 0) {
                throw new UnderloadedCapacityException("There are not enough Astronauts in this cabin!");
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

    public void addAlien(Alien newAlien) {
        if (this.alien != null) {
            throw new AlreadyAlienException("This cabin already contains an alien!");
        }
        if (!withLifeSupport) {
            throw new WithoutLifeSupportException("This cabin does not have life support!");
        }
        boolean hasMatchingLifeSupport = getLifeSupportSystemArrayList().stream().anyMatch(lss -> lss.getColour() == newAlien.getColour());
        if (!hasMatchingLifeSupport) {
            throw new DifferentLifeSupportColourException("This cabin has life support, but of a different colour!");
        }
        this.numAstronauts = 0;
        this.alien = newAlien;
    }

    public void removeAlien() {
        this.alien = null;
    }

    @Override
    public void remove(ShipBoard ship) {
        ship.setNumAstronauts(this.numAstronauts);
        ship.getAliens().remove(this.alien);
    }

    @Override
    public void place(ShipBoard ship) {
        ship.setNumAstronauts(2);
        if (ship.validPosition(this.getPosX()+1, this.getPosY())){
            ship.getComponent(this.getPosX()+1, this.getPosY()).addLifeSupport(this);
        } else if (ship.validPosition(this.getPosX()-1, this.getPosY())){
            ship.getComponent(this.getPosX()-1, this.getPosY()).addLifeSupport(this);
        } else if (ship.validPosition(this.getPosX(), this.getPosY()+1)){
            ship.getComponent(this.getPosX(), this.getPosY()+1).addLifeSupport(this);
        } else if (ship.validPosition(this.getPosX(), this.getPosY()-1)){
            ship.getComponent(this.getPosX(), this.getPosY()-1).addLifeSupport(this);
        }
    }

    @Override
    public void addCabin(LifeSupportSystem life) {
        if(!getIsCentral()){
            this.changeWithLifeSupport(true);
            this.addLifeSupport(life);
        }
    }

    @Override
    public void removeCabin(LifeSupportSystem life, ShipBoard ship) {
        boolean check = true;
        for (LifeSupportSystem l : this.getLifeSupportSystemArrayList()) {
            if (l != life && l.getColour() == life.getColour()) {
                check = false;
                break;
            }
        }
        if (check) {
            if (this.getAlien().getColour() == life.getColour()) {
                this.removeAlien();
            }
            if (this.getLifeSupportSystemArrayList().isEmpty()) {
                this.changeWithLifeSupport(false);
            }
        }
        this.removeLifeSupport(life);
    }

    @Override
    public Alien hasAlien() {
        if(this.alien != null){
            return this.alien;
        }
        return null;
    }
  
    public void manageEpidemic(boolean[][] visited, int dimX, int dimY){
        ArrayList <Cabin> cabins = new ArrayList<Cabin>();
        addEpidemicCabin(this.getPosX(), this.getPosY(), cabins, visited, dimX, dimY);

        if(cabins.size()>1){
            for (Cabin c : cabins) {
                if(c.getAlien()!=null){
                    c.removeAlien();
                } else {
                    c.changeNumAstronauts(-1);
                }
            }
        }
    }

    @Override
    public void addEpidemicCabin(int x, int y, ArrayList<Cabin> cabins, boolean[][] visited, int dimX, int dimY){
        if(!visited[x][y]){
            visited[x][y] = true;
            if (this.alien == null && this.getNumAstronauts() == 0) {
                return;
            } else {
                cabins.add(this);
            }
            if(x+1 < dimX && visited[x+1][y]){
                addEpidemicCabin(x+1, y, cabins, visited, dimX, dimY);
            }
            if (x-1 > 0 && visited[x-1][y]){
                addEpidemicCabin(x-1, y, cabins, visited, dimX, dimY);
            }
            if (y+1 < dimY && visited[x][y+1]){
                addEpidemicCabin(x, y+1, cabins, visited, dimX, dimY);
            }
            if (y-1 > 0 && visited[x][y-1]){
                addEpidemicCabin(x, y-1, cabins, visited, dimX, dimY);
            }
        }
    }
}
