package org.example.ServerPkg.Model.ForView;

import org.example.ServerPkg.Model.ComponentsPack.Goods;
import org.example.ServerPkg.Model.ComponentsPack.GoodsColour;

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
