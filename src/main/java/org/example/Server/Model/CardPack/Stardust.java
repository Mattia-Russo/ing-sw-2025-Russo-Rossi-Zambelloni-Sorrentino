package org.example.Server.Model.CardPack;

import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;

import java.util.ArrayList;

public class Stardust extends AdventureCard{
    public Stardust(int CardLevel, int lostDays){
        super(CardLevel, lostDays);
    }

    @Override
    public void setCardState(Game g){
        this.playCard(g);
    }

    @Override
    public void playCard(Game g){
        for(int i=g.getPlayers().size()-1; i>=0; i--) {
            if (!g.getPlayers().get(i).isAbandoned()) {
                g.getPlayers().get(i).changePosition(-g.getPlayers().get(i).getPlayerShipBoard().getTotalExposedConnectors());
            }
        }
        g.Turn();
    }
}
