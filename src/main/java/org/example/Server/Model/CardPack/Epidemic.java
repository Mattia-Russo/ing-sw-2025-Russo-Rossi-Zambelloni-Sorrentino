package org.example.Server.Model.CardPack;

import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.ComponentsPack.Components;
import org.example.Server.Model.Player;
import org.example.Server.Model.ShipBoard;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class Epidemic extends AdventureCard{
    public Epidemic(int CardLevel, int lostDays){
        super(CardLevel, lostDays);
    }

    @Override
    public void setCardState(ArrayList<Player> players){
        ArrayList<Player> activePlayers = new ArrayList<Player>();
        for(Player p : players){
           if(!p.isAbandoned()){
               p.setPlayerState(new WaitingState());
               activePlayers.add(p);
           }
        }
        this.playCard(activePlayers);
    }

    @Override
    public void playCard(ArrayList<Player> players){
        for(Player player : players){
            checkAdjacentCabins(player.getPlayerShipBoard());
        }
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
