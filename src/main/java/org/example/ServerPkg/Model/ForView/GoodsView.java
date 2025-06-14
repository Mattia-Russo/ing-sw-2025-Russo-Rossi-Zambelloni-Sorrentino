package org.example.ServerPkg.Model.ForView;

import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;

import java.io.Serializable;

public class GoodsView implements Serializable {
    private final GoodsColour colour;
    private final boolean taken;
    public GoodsView(Goods good){
        this.colour = good.getColour();
        this.taken = good.isTaken();
    }

    public GoodsColour getColour() {
        return colour;
    }

    public boolean isTaken() {
        return taken;
    }
}
