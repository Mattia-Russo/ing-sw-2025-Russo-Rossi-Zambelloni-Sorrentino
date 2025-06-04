package org.example.ServerPkg.Model.ComponentsPkg;

import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.ShipBoard;

import java.io.Serializable;

public class Shield extends Components implements Serializable {
    private Direction direction2;

    public Shield(int id, Direction direction, Connector[] connectors, Direction direction2) {
        super(direction, connectors, id);
        this.direction2 = direction2;
    }

    @Override
    public ComponentsView createView(){
        return new ComponentsView(getDirection1(), getConnectors(), getId(),"Shield", 0 ,0, null, getDirection2(), null);
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

    @Override
    public void rightRotate(){
         setDirection(Direction.values()[(getDirection().ordinal()+1)%4]);
         direction2 = Direction.values()[(getDirection().ordinal()+1)%4];
    }

    @Override
    public void leftRotate(){
        setDirection(Direction.values()[(getDirection().ordinal()+3)%4]);
        direction2 = Direction.values()[(getDirection().ordinal()+3)%4];
    }
}
