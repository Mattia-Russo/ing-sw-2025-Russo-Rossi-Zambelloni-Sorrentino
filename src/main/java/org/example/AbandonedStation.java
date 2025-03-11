package org.example;

import java.util.ArrayList;
import java.util.List;

public abstract class AbandonedStation extends LoseDays{
    private final int cardLevel;
    private final CardEnum cardEnum;
    private int numAstronauts;
    private List<Goods> goodsList = new ArrayList<Goods>();

    public AbandonedStation(){
        this.cardEnum = CardEnum.AbandonedStation;
        this.cardLevel = 123;
    }

    @Override
    public CardEnum getCardEnum() {
        return cardEnum;
    }

    @Override
    public int getCardLevel() {
        return cardLevel;
    }

    @Override
    public int getNumAstronauts() {
        return numAstronauts;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    @Override
    public void playCard(Player p) { // il controller mi dice quale giocatore ha deciso di attraccare
        
    }
}
