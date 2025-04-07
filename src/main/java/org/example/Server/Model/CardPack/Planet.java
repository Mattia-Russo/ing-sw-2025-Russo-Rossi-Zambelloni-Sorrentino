package org.example.Server.Model.CardPack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.Server.Model.ComponentsPack.Goods;

public class Planet {
    private final int planetNumber;
    private final Goods[] goods;
    private boolean isOccupied;

    public Planet(
            @JsonProperty("planetNumber") int planetNum,
            @JsonProperty("goods") Goods[] goods){
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
