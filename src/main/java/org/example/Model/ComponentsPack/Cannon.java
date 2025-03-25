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
        } else {
            ship.setDoubleCannonPower(-1);
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
            ship.setDoubleCannonPower(1);
        }
    }
}
