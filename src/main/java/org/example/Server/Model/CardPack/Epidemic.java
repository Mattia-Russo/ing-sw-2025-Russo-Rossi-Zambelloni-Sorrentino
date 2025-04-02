package org.example.Server.Model.CardPack;

import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.ComponentsPack.Components;
import org.example.Server.Model.Player;
import org.example.Server.Model.ShipBoard;

import java.util.ArrayList;

public class Epidemic extends AdventureCard{
    public Epidemic(int CardLevel, int lostDays){
        super(CardLevel, lostDays);
    }

    @Override
    public void setStateCard(ArrayList<Player> players){
        players.get(0).setPlayerState(new WaitingState());
        players.get(1).setPlayerState(new WaitingState());
        players.get(2).setPlayerState(new WaitingState());
        players.get(3).setPlayerState(new WaitingState());
        this.playCardCard(players);
    }

    @Override
    public void playCardCard(ArrayList<Player> players){

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
