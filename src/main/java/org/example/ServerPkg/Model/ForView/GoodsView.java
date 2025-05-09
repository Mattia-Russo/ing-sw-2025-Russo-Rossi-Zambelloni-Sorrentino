package org.example.ServerPkg.Model.ForView;

import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;

import java.io.Serializable;

public class GoodsView implements Serializable {
    private final GoodsColour colour;
    public GoodsView(Goods good){
        this.colour = good.getColour();
    }

    public GoodsColour getColour() {
        return colour;
    }
}
