package org.example.Model.CardPack;

import org.example.Model.ComponentsPack.Goods;

import java.util.ArrayList;
import java.util.List;

public class AbandonedStation extends AdventureCard{
    private final int numAstronauts;
    private List<Goods> goodsList = new ArrayList<Goods>();


    public AbandonedStation(int cardLevel, int lostDays, int numAstronauts, List<Goods> goodsList) {
        super(cardLevel, lostDays);
        this.numAstronauts = numAstronauts;
        this.goodsList = goodsList;
    }

    public int getCardLevel() {
        return super.getCardLevel();
    }

    public int getLostDays() {
        return super.getLostDays();
    }

    public int getNumAstronauts() {
        return numAstronauts;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }


        // il controller passa il giocatore che vuole attraccare
        // redistribuzione gestita dal controller (addGood, removeGood)
        // scarico merci gestita dal controller (shipboard.removeGoods(good, storage))
        // il controller sposta la posizione del player (p.changePosition)
}
