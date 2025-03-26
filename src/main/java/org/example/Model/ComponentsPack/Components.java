package org.example.Model.ComponentsPack;

import org.example.Model.ShipBoard;

import java.util.ArrayList;

public class Components {
    private Direction direction;
    private boolean covered;
    private boolean isPositoned;
    private Connector[] connectors;
    private int posX;
    private int posY;

    public Components(Direction direction, Connector[] connectors) {
        this.direction = direction;
        this.connectors = connectors;
        this.covered = true;
        this.isPositoned = false;
        this.posX = 0;
        this.posY = 0;
    }

    public void uncover(){
        this.covered = false;
    }

    public boolean getIfCovered() {
        return this.covered;
    }

    public boolean getIfPositioned() {
        return this.isPositoned;
    }

    public void setPosition(int x, int y) { // controllo se posizioni valide va fatto prima di chiamare questo metodo
        this.isPositoned = true;
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

    public Connector getDirConnector(Direction dir){ //restituisce il connettore che c'è nella direzione passata in modo assoluto
        switch(this.direction){
            case NORTH:
                return connectors[dir.ordinal()];
            case EAST:
                return connectors[(dir.ordinal()+3)%4];
            case SOUTH:
                return connectors[(dir.ordinal()+2)%4];
            case WEST:
                return connectors[(dir.ordinal()+1)%4];
            default:
                return null;
        }
    }

    public void remove(ShipBoard ship){}

    public void place(ShipBoard ship){}

    public void addLifeSupport(Cabin cabin) {}

    public void addCabin(LifeSupportSystem life){}

    public void removeCabin(LifeSupportSystem life, ShipBoard ship) {}

    public Cannon isDoubleCannon(){
        return null;
    }

    public Engine isDoubleEngine(){
        return null;
    }

    public Alien hasAlien(){
        return null;
    }

    public void manageEpidemic(boolean[][] visited, int dimX, int dimY){}

    public void addEpidemicCabin(int x, int y, ArrayList<Cabin> cabins, boolean[][] visited, int dimX, int dimY){}

    public void addStorage(ArrayList<Goods> list){}

}