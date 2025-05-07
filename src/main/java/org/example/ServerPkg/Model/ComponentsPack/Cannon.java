package org.example.ServerPkg.Model.ComponentsPack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.ShipBoard;

public class Cannon extends Components {
    private final int power;
    private final int id;
    private final String type;

    @JsonCreator
    public Cannon(
            @JsonProperty("id") int id,
            @JsonProperty("power") int power,
            @JsonProperty("direction") Direction direction,
            @JsonProperty("connectors") Connector[] connectors) {
        super(direction, connectors);
        this.power = power;
        this.id = id;
        if(power ==1) {
            this.type = "Cannon";
        }else
            this.type = "DoubleCannon";
    }

    @Override
    public ComponentsView createView(){
        return new ComponentsView(getDirection(), getConnectors(), id,type, 0 ,0, null, null);
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
    public boolean checkRightCannon(ShipBoard ship){    // rotirna true se non va bene, false se va bene
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
    public Cannon isSingleCannon(){
        if(this.getPower()==1){
            return this;
        }
        return null;
    }
}
