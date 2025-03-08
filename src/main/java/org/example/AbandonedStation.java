package org.example;

import java.util.ArrayList;
import java.util.List;

public class AbandonedStation extends LoseDays{
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

    public int getNumAstronauts() {
        return numAstronauts;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }
}
