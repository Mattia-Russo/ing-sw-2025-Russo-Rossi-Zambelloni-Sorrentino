package org.example.Server.Model.CardPack;

import org.example.Server.Model.ComponentsPack.Goods;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;
import org.example.Server.Model.Points;

import java.util.ArrayList;

public abstract class AdventureCard {
    private final int cardLevel;
    private int lostDays;
    private int playerIndex;

    public AdventureCard(int cardLevel, int lostDays) {
        this.cardLevel = cardLevel;
        this.lostDays = lostDays;
    }

    public void setCardState(Game game) {};

    public void playCard(Game game){};

    public void playCard(Game game, int numPlanet){};

    public void playCard(Game game, ArrayList<Points> Engines, ArrayList<Points> Batteries){};

    public int getCardLevel(){return cardLevel;}

    public int getLostDays(){return lostDays;}

    public int getNumAstronauts(){
        return 0;
    }

    public Goods[] getGoodsList(){
        return null;
    }

    public void setChangeGoodsFlag(boolean changeGoodsFlag) {}

    public boolean[] isPlanetsVisited(){
        return null;
    }
}
