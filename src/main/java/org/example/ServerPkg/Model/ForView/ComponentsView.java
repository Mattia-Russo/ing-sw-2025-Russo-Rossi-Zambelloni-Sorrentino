package org.example.ServerPkg.Model.ForView;

import org.example.ServerPkg.Model.ComponentsPack.Components;
import org.example.ServerPkg.Model.ComponentsPack.Connector;
import org.example.ServerPkg.Model.ComponentsPack.Direction;
import org.example.ServerPkg.Model.ComponentsPack.Goods;

import java.util.ArrayList;

public class ComponentsView{
    private final Direction direction;
    private final Connector[] connectors;
    private final int id;
    private final String type;
    private final int numBattery;
    private final int numAstronauts;
    private Goods[] goods = new Goods[3];
    private final Direction[] shieldedDirections = new Direction[2];

    public ComponentsView(Direction direction, Connector[] connectors, int id, String type, int numBattery, int numAstronauts, Goods[] goods, Direction shieldedDirections) {
        this.direction = direction;
        this.connectors = connectors;
        this.id = id;
        this.type = type;
        this.numBattery = numBattery;
        this.numAstronauts = numAstronauts;
        this.goods = goods;
        this.shieldedDirections[0] = direction;
        this.shieldedDirections[1] = shieldedDirections;
    }

    public Direction getDirection() {
        return direction;
    }

    public Connector[] getConnectors() {
        return connectors;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getNumBattery() {
        return numBattery;
    }

    public int getNumAstronauts() {
        return numAstronauts;
    }

    public Goods[] getGoods() {
        return goods;
    }

    public Direction[] getShieldedDirections() {
        return shieldedDirections;
    }


}
