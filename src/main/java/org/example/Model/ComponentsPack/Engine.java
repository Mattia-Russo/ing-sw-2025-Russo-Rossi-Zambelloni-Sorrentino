package org.example.Model.ComponentsPack;

import org.example.Model.ShipBoard;

public class Engine extends Components{
    private final int power;

    public Engine(int power, Direction direction, Connector[] connectors) {
        super(direction, connectors);
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    @Override
    public void remove(ShipBoard ship) {
        if (this.power==1){
            ship.setSingleEnginePower(-1);
        } else {
            ship.setDoubleEnginePower(-1);
        }
    }

    @Override
    public void place(ShipBoard ship) {
        if (this.power == 1){
            ship.setSingleEnginePower(1);
        } else {
            ship.setDoubleEnginePower(1);
        }
    }
}
