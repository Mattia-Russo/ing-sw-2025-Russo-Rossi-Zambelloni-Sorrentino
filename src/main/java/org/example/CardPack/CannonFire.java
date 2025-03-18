package org.example.CardPack;

public class CannonFire {
    private final int type;
    private final int direction;

    public CannonFire(int type, int direction) {
        this.direction = direction;
        this.type = type;
    }

    public int getDirection() {
        return direction;
    }

    public int getType() {
        return type;
    }
}
