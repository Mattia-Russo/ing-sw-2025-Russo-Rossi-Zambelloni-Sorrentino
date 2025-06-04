package org.example.ServerPkg.Model.ComponentsPkg;

import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.ShipBoard;

import java.io.Serializable;

public class Engine extends Components implements Serializable {
    private final int power;
    private final String type;
    private final int id;

    public Engine(int id, int power, Direction direction, Connector[] connectors) {
        super(direction, connectors);
        this.power = power;
        this.id = id;
        if(power == 1){
            this.type = "Engine";
        }else {
            this.type = "DoubleEngine";
        }
    }

    @Override
    public ComponentsView createView(){
        return new ComponentsView(getPosX(),getPosY(), getDirection(), getConnectors(), id,type, 0 ,0, null, null, null);
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
        return this.getDirection() != Direction.NORTH || (ship.validPosition(this.getPosY()+1, this.getPosX()) && ship.getComponent(this.getPosY()+1, this.getPosX()) != null);
    }
}
