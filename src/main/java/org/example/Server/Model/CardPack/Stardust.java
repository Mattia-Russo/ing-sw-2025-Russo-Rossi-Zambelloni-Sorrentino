package org.example.Server.Model.CardPack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.Server.Model.Game;

public class Stardust extends AdventureCard{
    @JsonCreator
    public Stardust(
            @JsonProperty("cardLevel") int CardLevel,
            @JsonProperty("lostDays") int lostDays){
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
