package org.example.Server.Model.ComponentsPack;

import org.example.Server.Model.ShipBoard;

public class Shield extends Components {
    private final Direction direction2;

    public Shield(Direction direction, Connector[] connectors, Direction direction2) {
        super(direction, connectors);
        this.direction2 = direction2;
    }

    public Direction getDirection1() {
        return super.getDirection();
    }

    public Direction getDirection2(){
        return direction2;
    }

    @Override
    public void remove(ShipBoard ship){
        ship.decreaseShieldInDirection(this.getDirection1());
        ship.decreaseShieldInDirection(this.getDirection2());
    }

    @Override
    public void place(ShipBoard ship){
        ship.addShieldInDirection(this.getDirection1());
        ship.addShieldInDirection(this.getDirection2());
    }
}
