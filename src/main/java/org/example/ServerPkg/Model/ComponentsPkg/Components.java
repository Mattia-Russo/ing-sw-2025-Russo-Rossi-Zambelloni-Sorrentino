package org.example.ServerPkg.Model.ComponentsPkg;


import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.ShipBoard;

import java.io.Serializable;
import java.util.ArrayList;

public class Components implements Serializable {
    private Direction direction;
    private boolean isPositioned;
    private final Connector[] connectors;
    private int posX;
    private int posY;
    private final int id;

    public Components(Direction direction, Connector[] connectors, int id) {
        this.direction = direction;
        this.connectors = connectors;
        this.isPositioned = false;
        this.posX = 0;
        this.posY = 0;
        this.id=id;
    }

    public int getId() {
        return this.id;
    }

    public ComponentsView createView(){
        return new ComponentsView(getDirection(), getConnectors(), 0,null,0 ,0, null, null, null);
    }

    public boolean getIfPositioned() {
        return this.isPositioned;
    }

    public void setPosition(int x, int y) {
        this.isPositioned = true;
        this.posX = x;
        this.posY = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void leftRotate(){
        this.direction = Direction.values()[(this.direction.ordinal()+3)%4];
    }

    public void rightRotate() {
        this.direction = Direction.values()[(this.direction.ordinal()+1)%4];
    }

    public Connector[] getConnectors(){
        return connectors;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Connector getDirConnector(Direction dir){
        return switch (this.direction) {
            case NORTH -> connectors[dir.ordinal()];
            case EAST -> connectors[(dir.ordinal() + 3) % 4];
            case SOUTH -> connectors[(dir.ordinal() + 2) % 4];
            case WEST -> connectors[(dir.ordinal() + 1) % 4];
        };
    }

    public void remove(ShipBoard ship){}

    public void place(ShipBoard ship){}

    public void addLifeSupport(Cabin cabin) {}

    public void addCabin(LifeSupportSystem life){}

    public void removeCabin(LifeSupportSystem life, ShipBoard ship) {}

    public Cannon isDoubleCannon(){
        return null;
    }

    public Cannon isSingleCannon(){
        return null;
    }

    public Engine isDoubleEngine(){
        return null;
    }

    public Storage isStorage() {
        return null;
    }

    public Shield isShield(){return null;}

    public Alien hasAlien(){
        return null;
    }

    public void manageEpidemic(boolean[][] visited, int dimX, int dimY, ShipBoard ship){}

    public void addEpidemicCabin(int x, int y, ArrayList<Cabin> cabins, boolean[][] visited, int dimX, int dimY, ShipBoard ship){}

    public void addStorage(ArrayList<Goods> list){}

    public boolean checkRightCannon(ShipBoard ship){
        return false;
    }

    public boolean checkRightEngine(ShipBoard ship){
        return false;
    }

    public BatteryStorage isBatteryStorage(){
        return null;
    }

    public Cabin isCabin(){return null;}

}