package org.example.Model.CardPack;

import org.example.Model.ComponentsPack.Goods;

import java.util.ArrayList;
import java.util.List;

public class Planet {
    private int planetNumber;
    private List<Goods> goods = new ArrayList<Goods>();
    private boolean isOccupied;
    public Planet(int planetNum, List<Goods> goods){
        this.planetNumber=planetNum;
        this.goods=goods;
        this.isOccupied=false;
    }
    public int getPlanetNumber() {
        return planetNumber;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }
    public void changeIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }
    public List<Goods> getGoodsList() {
        return goods;
    }





}
