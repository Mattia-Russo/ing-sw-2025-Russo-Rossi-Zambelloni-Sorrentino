package org.example.CardPack;

public abstract class Meteor extends MeteorCard {
    private final int direction;
    private final int type; // 0 è piccolo, 1 è grande

    public Meteor(int type, int direction) {
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
