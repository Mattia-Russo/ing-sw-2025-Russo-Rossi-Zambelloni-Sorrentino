package org.example;

public class Meteor extends MeteorCard {
    private final int direction;
    private final int type;

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
