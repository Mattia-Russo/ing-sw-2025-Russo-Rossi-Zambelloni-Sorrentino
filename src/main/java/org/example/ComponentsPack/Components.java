package org.example.ComponentsPack;

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

    public void putPosition() {
        this.isPositoned = true;
    }

    public Direction getDirection() {
        return direction;
    }

    public void leftRotate(){
        switch(direction){
            case NORTH:
                direction=Direction.WEST;
            case WEST:
                direction=Direction.SOUTH;
            case SOUTH:
                direction=Direction.EAST;
            case EAST:
                direction=Direction.NORTH;
        }

    }

    public void rightRotate() {
        switch (direction) {
            case NORTH:
                direction = Direction.EAST;
            case WEST:
                direction = Direction.NORTH;
            case SOUTH:
                direction = Direction.WEST;
            case EAST:
                direction = Direction.SOUTH;
        }
    }

    public Connector[] getConnectors(){
        return connectors;
    }

    public boolean getIfExposed(Direction dir, Components component){   // dir è la direnzione in cui vogliamo capire se c'è conn esposto
        if (component.getDirection() == dir) {
            return component.getConnectors()[0] != Connector.EMPTY;
        } else if ((component.getDirection().ordinal()+1)%4 == dir.ordinal()) { // ordinal() converte il nome dell'enum in un int in base alla posizione
            return component.getConnectors()[1] != Connector.EMPTY;
        } else if ((component.getDirection().ordinal()+2)%4 == dir.ordinal()) {
            return component.getConnectors()[2] != Connector.EMPTY;
        } else {
            return component.getConnectors()[3] != Connector.EMPTY;
        }
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Connector getDirConnector(Direction dir){ //restituisce il connettore che cè nella direzione passata in modo assoluto
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
}