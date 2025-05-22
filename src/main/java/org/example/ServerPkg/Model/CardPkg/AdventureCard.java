package org.example.ServerPkg.Model.CardPkg;

import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class AdventureCard implements Serializable {
    private final int cardLevel;
    private final int lostDays;

    public AdventureCard(int cardLevel, int lostDays) {
        this.cardLevel = cardLevel;
        this.lostDays = lostDays;
    }

    public AdventureCardView createView(){
        return new AdventureCardView(0, null,0, 0,0,0, null,null,null,null,0, null, null);
    }

    public void setCardState(Game game) {};

    public void playCard(Player disconnectingPlayer, Game game){};

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

    public boolean[] getPlanetsVisited(){
        return null;
    }

    public int getCurrentPlayerIndex(){
        return -1;
    }

    public void setAccept(boolean accept) {}

    public int getNumGoodsLose(){
        return -1;
    }

    public void setShipWrecked(boolean shipWrecked) {}
}
