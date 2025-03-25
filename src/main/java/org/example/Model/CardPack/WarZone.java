package org.example.Model.CardPack;

import java.util.List;

public class WarZone extends AdventureCard{
    private int numAstronauts;
    private int numGoods;
    private List<CannonFire> cannonFireList;

    public WarZone(int CardLevel, int lostDays, int numAstronauts, int numGoods, List<CannonFire> CannonFireList) {
        super(CardLevel, lostDays);
        this.numAstronauts = numAstronauts;
        this.numGoods = numGoods;
        this.cannonFireList = CannonFireList;
    }

    public int getNumAstronauts() {
        return numAstronauts;
    }

    public int getNumGoods() {
        return numGoods;
    }

    public List<CannonFire> getCannonFireList() {
        return cannonFireList;
    }
}
