package org.example.Server.Model.CardPack;

import org.example.Server.Model.Player;

import java.util.ArrayList;

public abstract class AdventureCard {
    private final int cardLevel;
    private int lostDays;

    public AdventureCard(int cardLevel, int lostDays) {
        this.cardLevel = cardLevel;
        this.lostDays = lostDays;
    }

    public void setStateCard(ArrayList<Player> players) {};
    public void playCardCard(ArrayList<Player> players){};
    public int getCardLevel(){return cardLevel;}
    public int getLostDays(){return lostDays;}
}
