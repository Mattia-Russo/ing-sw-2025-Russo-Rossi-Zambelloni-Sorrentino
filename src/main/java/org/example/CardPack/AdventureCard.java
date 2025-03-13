package org.example.CardPack;

import org.example.Player;
import java.util.ArrayList;

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
