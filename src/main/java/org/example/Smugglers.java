package org.example;

import java.util.ArrayList;
import java.util.List;

public class Smugglers extends Enemy{
    private final CardEnum cardEnum;
    private final int cardLevel;
    private final List<Goods> goodsWinList = new ArrayList<Goods>();
    private int goodsLose;

    public Smugglers(){
        this.cardEnum = CardEnum.Smugglers;
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

    public List<Goods> getGoodsWinList() {
        return goodsWinList;
    }

    public int getGoodsLose() {
        return goodsLose;
    }
}
