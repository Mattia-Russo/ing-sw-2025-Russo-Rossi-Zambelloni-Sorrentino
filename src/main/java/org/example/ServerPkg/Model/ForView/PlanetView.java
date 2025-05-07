package org.example.ServerPkg.Model.ForView;

import org.example.ServerPkg.Model.CardPack.Planet;
import org.example.ServerPkg.Model.ComponentsPack.Goods;

import java.util.ArrayList;
import java.util.List;

public class PlanetView {
    private final int planetNumber;
    private final List<GoodsView> goods= new ArrayList<GoodsView>();
    public PlanetView(Planet planet) {
        planetNumber = planet.getPlanetNumber();
        for(int i=0; i<planet.getGoodsList().length; i++){
            goods.add(new GoodsView(planet.getGoodsList()[i]));
        }
    }

    public int getPlanetNumber() {
        return planetNumber;
    }

    public List<GoodsView> getGoods() {
        return goods;
    }
}
