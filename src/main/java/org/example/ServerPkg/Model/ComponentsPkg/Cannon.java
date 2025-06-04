package org.example.ServerPkg.Model.ComponentsPkg;

import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.ShipBoard;

import java.io.Serializable;

public class Cannon extends Components implements Serializable {
    private final int power;
    private final String type;

    public Cannon(int id, int power, Direction direction, Connector[] connectors) {
        super(direction, connectors, id);
        this.power = power;
        if(power ==1) {
            this.type = "Cannon";
        }else
            this.type = "DoubleCannon";
    }

    @Override
    public ComponentsView createView(){
        return new ComponentsView(getDirection(), getConnectors(), getId(),type, 0 ,0, null, null, null);
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
        } else {
            ship.setNumDoubleCannon(-1);
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
        } else {
            ship.setNumDoubleCannon(1);
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
    public boolean checkRightCannon(ShipBoard ship){    // returns true se non va bene, false se va bene
        switch (this.getDirection()) {
            case NORTH:
                if (ship.validPosition(this.getPosY()-1, this.getPosX()) && ship.getComponent(this.getPosY()-1, this.getPosX()) != null) {
                    return true;
                }
                break;
            case EAST:
                if (ship.validPosition(this.getPosY(), this.getPosX()+1) && ship.getComponent(this.getPosY(), this.getPosX()+1) != null) {
                    return true;
                }
                break;
            case SOUTH:
                if (ship.validPosition(this.getPosY()+1, this.getPosX()) && ship.getComponent(this.getPosY()+1, this.getPosX()) != null) {
                    return true;
                }
                break;
            case WEST:
                if (ship.validPosition(this.getPosY(), this.getPosX()-1) && ship.getComponent(this.getPosY(), this.getPosX()-1) != null) {
                    return true;
                }
        }
      return false;
      }

    @Override
    public Cannon isSingleCannon(){
        if(this.getPower()==1){
            return this;
        }
        return null;
    }
}
