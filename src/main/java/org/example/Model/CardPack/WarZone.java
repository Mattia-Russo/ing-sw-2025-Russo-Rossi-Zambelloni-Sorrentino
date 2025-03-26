package org.example.Model.CardPack;

import org.example.Model.ComponentsPack.Goods;
import java.util.ArrayList;
import java.util.List;

public class WarZone extends AdventureCard{
    private int numAstronauts;
    private int numGoods;
    private int loseDays;
    private List<CannonFire> cannonFireList;
    private String[] penalities;
    private String[] criteria;

    public WarZone(int CardLevel, int lostDays, int numAstronauts, int numGoods, int loseDays, List<CannonFire> CannonFireList, String[] penalities, String[] criteria) {
        super(CardLevel, lostDays);
        this.numAstronauts = numAstronauts;
        this.numGoods = numGoods;
        this.loseDays = loseDays;
        this.cannonFireList = CannonFireList;
        this.penalities = penalities;
        this.criteria = criteria;
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

    public int getLoseDays() {
        return loseDays;
    }

    public String[] getPenalities() {
        return penalities;
    }

    public String[] getCriteria() {
        return criteria;
    }
}
