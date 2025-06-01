package org.example.ServerPkg.Model.ForView;

import org.example.ServerPkg.Model.ComponentsPkg.*;

import java.io.Serializable;

public class ComponentsView implements Serializable {
    private final Direction direction;
    private final Connector[] connectors;
    private final int id;
    private final String type;
    private final int numBattery;
    private final int numAstronauts;
    private final AlienColour alienColour;
    private final GoodsView[] goods;
    private final Direction[] shieldedDirections = new Direction[2];

    public ComponentsView(Direction direction, Connector[] connectors, int id, String type, int numBattery, int numAstronauts, GoodsView[] goods, Direction shieldedDirections, AlienColour alienColour) {
        this.direction = direction;
        this.connectors = connectors != null ? connectors : new Connector[0];
        this.id = id;
        this.type = type;
        this.numBattery = numBattery;
        this.numAstronauts = numAstronauts;
        this.goods = goods;
        this.alienColour = alienColour;
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

    public AlienColour getAlienColour() {
        return alienColour;
    }

    public int getNumBattery() {
        return numBattery;
    }

    public int getNumAstronauts() {
        return numAstronauts;
    }

    public GoodsView[] getGoods() {
        return goods;
    }

    public Direction[] getShieldedDirections() {
        return shieldedDirections;
    }


}
