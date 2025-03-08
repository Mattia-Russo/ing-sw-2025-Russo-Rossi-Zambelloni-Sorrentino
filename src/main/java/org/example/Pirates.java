package org.example;

import java.util.ArrayList;
import java.util.List;

public class Pirates extends Enemy{
    private CardEnum cardEnum;
    private int cardLevel;
    private int coord;
    private int credit;
    private List<CannonFire> cannonFiresList = new ArrayList<CannonFire>();

    public Pirates() {
        this.cardEnum = CardEnum.Pirates;
        this.cardLevel = 123;
    }

    @Override
    public int getCardLevel() {
        return cardLevel;
    }

    @Override
    public CardEnum getCardEnum() {
        return cardEnum;
    }

    //public boolean checkShield{} --> lo metterei nel game

    public int getCredit() {
        return credit;
    }

    public List<CannonFire> getCannonFireList() {
        return cannonFiresList;
    }
}
