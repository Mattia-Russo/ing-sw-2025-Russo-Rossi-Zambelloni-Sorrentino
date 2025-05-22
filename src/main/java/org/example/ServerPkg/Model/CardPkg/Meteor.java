package org.example.ServerPkg.Model.CardPkg;

import org.example.ServerPkg.Model.ComponentsPkg.Direction;

import java.io.Serializable;

public class Meteor implements Serializable {
    private final Direction direction;
    private final int type; // 0 è piccolo, 1 è grande

    public Meteor(int type, Direction direction) {
        this.direction = direction;
        this.type = type;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getType() {
        return type;
    }
}
