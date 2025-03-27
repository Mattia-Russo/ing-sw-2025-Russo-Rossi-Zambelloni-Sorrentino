package org.example.Model.ComponentsPack;

import org.example.Model.ShipBoard;

public class LifeSupportSystem extends Components {
    private final AlienColour colour;

    public LifeSupportSystem(AlienColour colour, Direction direction, Connector[] connectors) {
        super(direction, connectors);
        this.colour = colour;
    }

    public AlienColour getColour() {
        return colour;
    }


    @Override
    public void remove(ShipBoard ship) {
        if(ship.getComponent(getPosX(), getPosY() + 1)!=null) {
            ship.getComponent(getPosX(), getPosY() + 1).removeCabin(this, ship);
        }
        if(ship.getComponent(getPosX(), getPosY() - 1)!=null) {
            ship.getComponent(getPosX(), getPosY() - 1).removeCabin(this, ship);
        }
        if(ship.getComponent(getPosX()+1, getPosY())!=null) {
            ship.getComponent(getPosX() + 1, getPosY()).removeCabin(this, ship);
        }
        if(ship.getComponent(getPosX()-1, getPosY())!=null) {
            ship.getComponent(getPosX() - 1, getPosY()).removeCabin(this, ship);
        }
    }

    @Override
    public void place(ShipBoard ship) {
        if (ship.validPosition(getPosX()+1,getPosY())) {
            if(ship.getComponent(getPosX()+1,getPosY())!=null) {
                ship.getComponent(getPosX() + 1, getPosY()).addCabin(this);
            }
        }
        if (ship.validPosition(getPosX()-1,getPosY())){
            if(ship.getComponent(getPosX()-1,getPosY())!=null) {
                ship.getComponent(getPosX() - 1, getPosY()).addCabin(this);
            }
        }
        if (ship.validPosition(getPosX(),getPosY()+1)) {
            if(ship.getComponent(getPosX(),getPosY()+1)!=null) {
                ship.getComponent(getPosX(), getPosY()+1).addCabin(this);
            }
        }
        if (ship.validPosition(getPosX(),getPosY()-1)) {
            if(ship.getComponent(getPosX(),getPosY()-1)!=null) {
                ship.getComponent(getPosX(), getPosY()-1).addCabin(this);
            }
        }
    }

    @Override
    public void addLifeSupport(Cabin cabin) {
        cabin.changeWithLifeSupport(true);
        cabin.addLifeSupportList(this);
    }
}
