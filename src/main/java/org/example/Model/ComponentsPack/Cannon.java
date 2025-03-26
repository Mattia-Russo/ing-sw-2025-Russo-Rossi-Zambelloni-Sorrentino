package org.example.Model.ComponentsPack;

import org.example.Model.ShipBoard;

public class Cannon extends Components {
    private final int power;

    public Cannon(int power, Direction direction, Connector[] connectors) {
        super(direction, connectors);
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    @Override
    public void remove(ShipBoard ship) {
        if (this.power==1){
            if(this.getDirection()==Direction.NORTH){
                ship.setSingleCannonPower(-1);
            } else {
                ship.setSingleCannonPower(-0.5F);
            }
        }
    }

    @Override
    public void place(ShipBoard ship) {
        if (this.power == 1){
            if (this.getDirection() == Direction.NORTH) {
                ship.setSingleCannonPower(1);
            } else {
                ship.setSingleCannonPower(0.5F);
            }
        }
    }

    @Override
    public Cannon isDoubleCannon(){
        if(this.getPower()==2){
            return this;
        }
        return null;
    }

    @Override
    public boolean checkRightCannon(ShipBoard ship){
        switch (this.getDirection()) {
            case NORTH:
                if (ship.validPosition(this.getPosX(), this.getPosY() - 1) && ship.getComponentMatrix()[this.getPosX()][this.getPosY() - 1] != null) {
                    return true;
                }
                break;
            case EAST:
                if (ship.validPosition(this.getPosX() +1, this.getPosY()) && ship.getComponentMatrix()[this.getPosX()+1][this.getPosY()] != null) {
                    return true;
                }
                break;
            case SOUTH:
                if (ship.validPosition(this.getPosX(), this.getPosY() + 1) && ship.getComponentMatrix()[this.getPosX()][this.getPosY() + 1] != null) {
                    return true;
                }
                break;
            case WEST:
                if (ship.validPosition(this.getPosX() -1, this.getPosY()) && ship.getComponentMatrix()[this.getPosX()-1][this.getPosY()] != null) {
                    return true;
                }
        }
      return false;
      }

    @Override
    public Cannon isCannon(){
        if(this.getPower()==1){
            return this;
        }
        return null;
    }
}
