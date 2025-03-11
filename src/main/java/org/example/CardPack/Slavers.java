package org.example.CardPack;

public class Slavers extends Enemy{
    private final CardEnum cardEnum;
    private final int cardLevel;
    private int numAstronauts;
    private int credits;

    public Slavers(){
        this.cardEnum = CardEnum.Slavers;
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

    public int getNumAstronauts() {
        return numAstronauts;
    }

    public int getCredits() {
        return credits;
    }


}
