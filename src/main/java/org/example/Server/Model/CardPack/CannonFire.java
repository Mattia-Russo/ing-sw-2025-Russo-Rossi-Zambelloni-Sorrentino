package org.example.Server.Model.CardPack;

import org.example.Server.Model.ComponentsPack.Direction;

public class CannonFire {
    private final int type;
    private final Direction direction;

    public CannonFire(int type, Direction direction) {
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
