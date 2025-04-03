package org.example.Server.Model.CardPack;

import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.ComponentsPack.Components;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;
import org.example.Server.Model.ShipBoard;
import java.util.ArrayList;

public class Epidemic extends AdventureCard{
    public Epidemic(int CardLevel, int lostDays){
        super(CardLevel, lostDays);
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

    public void checkAdjacentCabins(ShipBoard s){
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
