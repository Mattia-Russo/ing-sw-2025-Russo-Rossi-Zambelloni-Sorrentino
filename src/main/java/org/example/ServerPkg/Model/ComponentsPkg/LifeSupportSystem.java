package org.example.ServerPkg.Model.ComponentsPkg;


import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.ShipBoard;

import java.io.Serializable;

public class LifeSupportSystem extends Components implements Serializable {
    private final AlienColour colour;

    public LifeSupportSystem(int id, AlienColour colour, Direction direction, Connector[] connectors) {
        super(direction, connectors, id);
        this.colour = colour;
    }

    @Override
    public ComponentsView createView(){
        return new ComponentsView(getDirection(), getConnectors(), getId(),"LifeSupportSystem", 0 ,0, null, null, getColour());
    }

    public AlienColour getColour() {
        return colour;
    }


    @Override
    public void remove(ShipBoard ship) {
        if(ship.getComponent(getPosY() + 1, getPosX())!=null) {
            ship.getComponent(getPosY() + 1, getPosX()).removeCabin(this, ship);
        }
        if(ship.getComponent(getPosY() - 1, getPosX())!=null) {
            ship.getComponent(getPosY() - 1, getPosX()).removeCabin(this, ship);
        }
        if(ship.getComponent(getPosY(), getPosX()+1)!=null) {
            ship.getComponent(getPosY(), getPosX()+1).removeCabin(this, ship);
        }
        if(ship.getComponent(getPosY(), getPosX()-1)!=null) {
            ship.getComponent(getPosY(), getPosX()-1).removeCabin(this, ship);
        }
    }

    @Override
    public void place(ShipBoard ship) {
        if (ship.validPosition(getPosY(),getPosX()+1)) {
            if(ship.getComponent(getPosY(), getPosX()+1)!=null) {
                ship.getComponent(getPosY(), getPosX()+1).addCabin(this);
            }
        }
        if (ship.validPosition(getPosY(), getPosX()-1)){
            if(ship.getComponent(getPosY(), getPosX()-1)!=null) {
                ship.getComponent(getPosY(), getPosX()-1).addCabin(this);
            }
        }
        if (ship.validPosition(getPosY() + 1, getPosX())) {
            if(ship.getComponent(getPosY() + 1, getPosX())!=null) {
                ship.getComponent(getPosY() + 1, getPosX()).addCabin(this);
            }
        }
        if (ship.validPosition(getPosY() - 1, getPosX())) {
            if(ship.getComponent(getPosY() - 1, getPosX())!=null) {
                ship.getComponent(getPosY() - 1, getPosX()).addCabin(this);
            }
        }
    }

    @Override
    public void addLifeSupport(Cabin cabin) {
        cabin.changeWithLifeSupport(true);
        cabin.addLifeSupportList(this);
    }
}
