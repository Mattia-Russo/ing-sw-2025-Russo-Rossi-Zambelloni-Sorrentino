package org.example.ServerPkg.Model.ComponentsPack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.ServerPkg.Model.ShipBoard;

public class Engine extends Components{
    private final int power;

    @JsonCreator
    public Engine(
            @JsonProperty("power") int power,
            @JsonProperty("direction") Direction direction,
            @JsonProperty("connectors") Connector[] connectors){
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
            ship.setNumDoubleEngines(-1);
        }
    }

    @Override
    public void place(ShipBoard ship) {
        if (this.power == 1){
            ship.setSingleEnginePower(1);
        } else {
            ship.setNumDoubleEngines(1);
        }
    }

    @Override
    public Engine isDoubleEngine() {
        if(this.power==2){
            return this;
        }
        return null;
    }

    @Override
    public boolean checkRightEngine(ShipBoard ship){
        return this.getDirection() != Direction.NORTH || (ship.validPosition(this.getPosX(), this.getPosY() + 1) && ship.getComponentMatrix()[this.getPosX()][this.getPosY() + 1] != null);
    }
}
