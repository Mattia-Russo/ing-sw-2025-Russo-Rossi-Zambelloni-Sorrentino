package org.example.ServerPkg.Model.CardPkg;


import org.example.ServerPkg.Model.ComponentsPkg.Direction;

import java.io.Serializable;

public record CannonFire(int type, Direction direction) implements Serializable {
}
