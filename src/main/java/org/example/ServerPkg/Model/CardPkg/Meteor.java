package org.example.ServerPkg.Model.CardPkg;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;

public class Meteor {
    private final Direction direction;
    private final int type; // 0 è piccolo, 1 è grande

    public Meteor(
            @JsonProperty("type") int type,
            @JsonProperty("direction") Direction direction) {
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
