package org.example.ServerPkg.Model.ComponentsPack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.ShipBoard;

public class Shield extends Components {
    private final Direction direction2;
    private final int id;

    @JsonCreator
    public Shield(
            @JsonProperty("id") int id,
            @JsonProperty("direction") Direction direction,
            @JsonProperty("connectors") Connector[] connectors,
            @JsonProperty("direction2") Direction direction2) {
        super(direction, connectors);
        this.direction2 = direction2;
        this.id = id;
    }

    @Override
    public ComponentsView createView(){
        return new ComponentsView(getDirection1(), getConnectors(), id,"Shield", 0 ,0, null, getDirection2());
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

    @Override
    public Shield isShield(){
        return this;
    }
}
