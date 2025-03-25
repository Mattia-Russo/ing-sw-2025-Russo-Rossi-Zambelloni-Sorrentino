package org.example.Model.CardPack;

public abstract class AdventureCard {
    private final int cardLevel;
    private int lostDays;

    public AdventureCard(int cardLevel, int lostDays) {
        this.cardLevel = cardLevel;
        this.lostDays = lostDays;
    }

    public int getCardLevel(){return cardLevel;}
    public int getLostDays(){return lostDays;}
}
