package org.example.Server.Model.CardPack;

import org.example.Server.Model.Player;
import org.example.Server.Model.Points;

import java.util.ArrayList;

public abstract class AdventureCard {
    private final int cardLevel;
    private int lostDays;

    public AdventureCard(int cardLevel, int lostDays) {
        this.cardLevel = cardLevel;
        this.lostDays = lostDays;
    }

    public void setCardState(ArrayList<Player> players) {};
    public void playCard(ArrayList<Player> players){};
    public void playCard(ArrayList<Player> players, ArrayList<Points> Engines, ArrayList<Points> Batteries){};
    public int getCardLevel(){return cardLevel;}
    public int getLostDays(){return lostDays;}
}
