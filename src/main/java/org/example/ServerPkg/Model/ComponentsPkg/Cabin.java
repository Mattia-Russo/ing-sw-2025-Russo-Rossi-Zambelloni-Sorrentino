package org.example.ServerPkg.Model.ComponentsPkg;

import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.ShipBoard;

import java.io.Serializable;
import java.util.ArrayList;

public class Cabin extends Components implements Serializable {
    private int numAstronauts;
    private boolean withLifeSupport;
    private final ArrayList<LifeSupportSystem> lifeSupportSystemArrayList;
    private final boolean isCentral;
    private Alien alien;
    private final int id;
    private final String type;


    public Cabin(int id, boolean isCentral, Direction direction, Connector[] connectors) {
        super(direction, connectors);
        this.numAstronauts = 0;
        this.withLifeSupport = false;
        this.lifeSupportSystemArrayList= new ArrayList<>();
        this.isCentral = isCentral;
        this.alien = null;
        this.id = id;
        if(isCentral)
            type= "Central Cabin";
        else
            type= "Cabin";
    }

    @Override
    public ComponentsView createView(){
        if(getAlien() == null) {
            return new ComponentsView(getPosX(),getPosY(),getDirection(), getConnectors(), id, type, 0, getNumAstronauts(), null, null, null);
        }else
            return new ComponentsView(getPosX(),getPosY(),getDirection(), getConnectors(), id, type, 0, 0, null, null, getAlien().colour());
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

    @Override
    public Cabin isCabin(){
        return this;
    }

    public void changeNumAstronauts(int amount, ShipBoard s) {
        if (numAstronauts + amount > 2) {
            throw new OverloadedCapacityException("Cabin full!");
        } else if (numAstronauts + amount < 0) {
            throw new UnderloadedCapacityException("There are not enough Astronauts in this cabin!");
        }
        numAstronauts += amount;
        s.setNumAstronauts(amount);
    }

    public void addLifeSupportList(LifeSupportSystem l) {
        this.lifeSupportSystemArrayList.add(l);
    }

    public void removeLifeSupport(LifeSupportSystem l) {
        this.lifeSupportSystemArrayList.remove(l);
    }

    public ArrayList<LifeSupportSystem> getLifeSupportSystemArrayList() {
        return lifeSupportSystemArrayList;
    }

    public void addAlien(Alien newAlien, ShipBoard ship) {
        if (this.alien != null) {
            throw new AlreadyAlienException("This cabin already contains an alien!");
        }
        if (!withLifeSupport) {
            throw new WithoutLifeSupportException("This cabin does not have life support!");
        }
        boolean hasMatchingLifeSupport = getLifeSupportSystemArrayList().stream().anyMatch(lss -> lss.getColour() == newAlien.colour());
        if (!hasMatchingLifeSupport) {
            throw new DifferentLifeSupportColourException("This cabin has life support, but of a different colour!");
        }
        this.numAstronauts = 0;
        ship.setNumAstronauts(-1);
        this.alien = newAlien;
    }

    public void removeAlien(ShipBoard ship) {
        this.alien = null;
        ship.setNumAstronauts(-1);
    }

    @Override
    public void remove(ShipBoard ship) {
        if(this.alien==null){
            ship.setNumAstronauts(-this.numAstronauts);
        } else {
            ship.setNumAstronauts(-1);
        }
    }

    @Override
    public void place(ShipBoard ship) {
        ship.setNumAstronauts(2);
        this.numAstronauts = 2;
        if (ship.validPosition(this.getPosY(), this.getPosX()+1) && ship.getComponent(this.getPosY(), this.getPosX()+1)!=null){
            ship.getComponent(this.getPosY(), this.getPosX()+1).addLifeSupport(this);
        }
        if (ship.validPosition(this.getPosY(), this.getPosX()-1) && ship.getComponent(this.getPosY(), this.getPosX()-1)!=null){
            ship.getComponent(this.getPosY(), this.getPosX()-1).addLifeSupport(this);
        }
        if (ship.validPosition(this.getPosY()+1, this.getPosX()) && ship.getComponent(this.getPosY()+1, this.getPosX())!=null){
            ship.getComponent(this.getPosY()+1, this.getPosX()).addLifeSupport(this);
        }
        if (ship.validPosition(this.getPosY()-1, this.getPosX()) && ship.getComponent(this.getPosY()-1, this.getPosX())!=null){
            ship.getComponent(this.getPosY()-1, this.getPosX()).addLifeSupport(this);
        }
    }

    @Override
    public void addCabin(LifeSupportSystem life) {
        if(!getIsCentral()){
            this.changeWithLifeSupport(true);
            this.getLifeSupportSystemArrayList().add(life);
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
            if (this.getAlien()!=null && this.getAlien().colour() == life.getColour()) {
                this.removeAlien(ship);
            }
            this.removeLifeSupport(life);
            if (this.getLifeSupportSystemArrayList().isEmpty()) {
                this.changeWithLifeSupport(false);
            }
        }else {
            this.removeLifeSupport(life);
        }
    }

    @Override
    public Alien hasAlien() {
        if(this.alien != null){
            return this.alien;
        }
        return null;
    }
  
    public void manageEpidemic(boolean[][] visited, int dimX, int dimY, ShipBoard s){
        ArrayList <Cabin> cabins = new ArrayList<>();
        addEpidemicCabin(this.getPosX(), this.getPosY(), cabins, visited, dimX, dimY, s);

        if(cabins.size()>1){
            for (Cabin c : cabins) {
                if(c.getAlien()!=null){
                    c.removeAlien(s);
                } else {
                    c.changeNumAstronauts(-1, s);
                }
            }
        }
    }

    @Override
    public void addEpidemicCabin(int x, int y, ArrayList<Cabin> cabins, boolean[][] visited, int dimX, int dimY, ShipBoard s){
        if(!visited[y-5][x-4]){
            visited[y-5][x-4] = true;
            if (this.alien == null && this.getNumAstronauts() == 0) {
                return;
            }

            cabins.add(this);

            if(x+1 < dimX && !visited[y-5][x-3] && s.getComponent(y,x+1) != null){
                s.getComponent(y,x+1).addEpidemicCabin(x+1, y, cabins, visited, dimX, dimY, s);
            }
            if (x-1 >= 4 && !visited[y-5][x-5] && s.getComponent(y, x-1) != null){
                s.getComponent(y, x-1).addEpidemicCabin(x-1, y, cabins, visited, dimX, dimY, s);
            }
            if (y+1 < dimY && !visited[y-4][x-4] && s.getComponent(y+1, x) != null){
                s.getComponent(y+1, x).addEpidemicCabin(x, y+1, cabins, visited, dimX, dimY, s);
            }
            if (y-1 >= 5 && !visited[y-6][x-4] && s.getComponent(y-1, x) != null){
                s.getComponent(y-1, x).addEpidemicCabin(x, y-1, cabins, visited, dimX, dimY, s);
            }
        }
    }
}
