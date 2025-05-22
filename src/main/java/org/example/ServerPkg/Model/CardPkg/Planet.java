package org.example.ServerPkg.Model.CardPkg;

import org.example.ServerPkg.Model.ComponentsPkg.Goods;

import java.io.Serializable;

public class Planet implements Serializable {
    private final int planetNumber;
    private final Goods[] goods;
    private boolean isOccupied;

    public Planet(int planetNum,Goods[] goods){
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
