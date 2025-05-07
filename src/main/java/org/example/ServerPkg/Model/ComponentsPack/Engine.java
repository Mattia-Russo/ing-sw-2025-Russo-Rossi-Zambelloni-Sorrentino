package org.example.ServerPkg.Model.ComponentsPack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.ShipBoard;

public class Engine extends Components{
    private final int power;
    private final int id;
    private final String type;

    @JsonCreator
    public Engine(
            @JsonProperty("id") int id,
            @JsonProperty("power") int power,
            @JsonProperty("direction") Direction direction,
            @JsonProperty("connectors") Connector[] connectors) {
        super(direction, connectors);
        this.power = power;
        this.id = id;
        if(power == 1){
            this.type = "Engine";
        }else {
            this.type = "Double Engine";
        }
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
