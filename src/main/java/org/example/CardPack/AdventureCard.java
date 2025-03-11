package org.example.CardPack;

import org.example.Player;
import java.util.ArrayList;

public interface AdventureCard {

    public CardEnum getCardEnum();

    public int getCardLevel();

    public int getNumAstronauts();

    public void playCard(ArrayList<Player> p);

    public void playCard(Player p);
}
