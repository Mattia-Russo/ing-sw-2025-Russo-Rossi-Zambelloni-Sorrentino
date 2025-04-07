package org.example.Server.Model.CardPack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.Server.Model.ComponentsPack.Components;
import org.example.Server.Model.Game;
import org.example.Server.Model.ShipBoard;

public class Epidemic extends AdventureCard{
    @JsonCreator
    public Epidemic(
            @JsonProperty("cardLevel") int CardLevel,
            @JsonProperty("lostDays") int lostDays){
        super(CardLevel, lostDays);
    }

    public int getCardLevel(){
        return super.getCardLevel();
    }

    public int getLostDays(){
        return super.getLostDays();
    }

    @Override
    public void setCardState(Game g){
        this.playCard(g);
    }

    @Override
    public void playCard(Game g){
        for(int i=0; i<g.getPlayers().size(); i++){
            if(!g.getPlayers().get(i).isAbandoned()){
                checkAdjacentCabins(g.getPlayers().get(i).getPlayerShipBoard());
            }
        }
        g.Turn();
    }

    private void checkAdjacentCabins(ShipBoard s){
        boolean[][] visited = new boolean[s.getComponentMatrix().length][s.getComponentMatrix()[0].length];
        for(int i = 0; i < s.getComponentMatrix().length; i++){
            for(int j = 0; j < s.getComponentMatrix()[0].length; j++){
                if(s.getAvailablePositionMatrix()[i][j]){
                    Components c = s.getComponent(i,j);
                    if(c != null){
                        c.manageEpidemic(visited, s.getComponentMatrix().length, s.getComponentMatrix()[0].length, s);
                    }
                }
            }
        }
    }
}
