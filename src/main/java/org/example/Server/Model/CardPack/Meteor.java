package org.example.Server.Model.CardPack;

import org.example.Server.Model.ComponentsPack.Direction;

public class Meteor {
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
