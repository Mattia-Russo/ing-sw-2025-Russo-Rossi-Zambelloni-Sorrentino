package org.example.CardPack;

import org.example.Player;

import javax.smartcardio.Card;

public abstract class LoseDays implements AdventureCard {
    private int numDays;
    private int cardLevel;

    public int getNumDays() { return numDays;}

    public int getCardLevel(){return  cardLevel;}

    public void playCard(Player p){};
}
