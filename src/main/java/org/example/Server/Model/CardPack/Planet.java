package org.example.Server.Model.CardPack;

import org.example.Server.Model.ComponentsPack.Goods;

public class Planet {
    private int planetNumber;
    private Goods[] goods;
    private boolean isOccupied;

    public Planet(int planetNum, Goods[] goods){
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

    public Goods[] getGoodsList() {
        return goods;
    }





}
