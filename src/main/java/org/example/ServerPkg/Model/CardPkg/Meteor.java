package org.example.ServerPkg.Model.CardPkg;

import org.example.ServerPkg.Model.ComponentsPkg.Direction;

import java.io.Serializable;

/**
 * @param type 0 è piccolo, 1 è grande
 */
public record Meteor(int type, Direction direction) implements Serializable {
}
