package org.example;

import java.util.ArrayList;

public interface AdventureCard {

    public CardEnum getCardEnum();

    public int getCardLevel();

    public void playCard(ArrayList<Player> p);
}
