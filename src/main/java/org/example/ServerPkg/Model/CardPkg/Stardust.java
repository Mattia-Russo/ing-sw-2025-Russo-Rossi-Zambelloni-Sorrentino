package org.example.ServerPkg.Model.CardPkg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;

import java.io.Serializable;

public class Stardust extends AdventureCard implements Serializable {
    private final int id;
    @JsonCreator
    public Stardust(
            @JsonProperty("id") int id,
            @JsonProperty("cardLevel") int CardLevel,
            @JsonProperty("lostDays") int lostDays){
        super(CardLevel, lostDays);
        this.id = id;
    }

    @Override
    public AdventureCardView createView(){
        return new AdventureCardView(id, "Stardust", getLostDays(),0 ,0,0, null, null, null, null, 0,null,null);
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
        new GameView(g, null);
        g.Turn();
    }
}
