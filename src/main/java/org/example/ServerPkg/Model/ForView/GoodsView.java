package org.example.ServerPkg.Model.ForView;

import org.example.ServerPkg.Model.ComponentsPack.Goods;
import org.example.ServerPkg.Model.ComponentsPack.GoodsColour;

public class GoodsView {
    private final GoodsColour colour;
    public GoodsView(Goods good){
        this.colour = good.getColour();
    }

    public GoodsColour getColour() {
        return colour;
    }
}
