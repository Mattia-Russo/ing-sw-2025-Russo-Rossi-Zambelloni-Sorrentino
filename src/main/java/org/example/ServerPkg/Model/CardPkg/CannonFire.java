package org.example.ServerPkg.Model.CardPkg;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;

import java.io.Serializable;

public class CannonFire implements Serializable {
    private final int type;
    private final Direction direction;

    public CannonFire(
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
