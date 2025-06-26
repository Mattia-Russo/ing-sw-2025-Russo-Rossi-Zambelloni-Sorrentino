package org.example.ServerPkg.Model.CardPkg;

import org.example.ServerPkg.Model.ComponentsPkg.Goods;

import java.io.Serializable;

public class Planet implements Serializable {
    private final int planetNumber;
    private final Goods[] goods;
    private boolean isVisited;

    public Planet(int planetNum,Goods[] goods){
        this.planetNumber=planetNum;
        this.goods=goods;
        this.isVisited =false;
    }

    public int getPlanetNumber() {
        return planetNumber;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setIsVisited() {
        this.isVisited = true;
    }

    public Goods[] getGoodsList() {
        return goods;
    }





}
