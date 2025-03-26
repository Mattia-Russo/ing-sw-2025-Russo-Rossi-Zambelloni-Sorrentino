package org.example.Model.CardPack;

import org.example.Model.ComponentsPack.Cabin;
import org.example.Model.ComponentsPack.Components;
import org.example.Model.ShipBoard;

public class Epidemic extends AdventureCard{
    public Epidemic(int CardLevel, int lostDays){
        super(CardLevel, lostDays);
    }

    public void checkAdjacentCabins(ShipBoard s){
        boolean[][] visited = new boolean[s.getComponentMatrix().length][s.getComponentMatrix()[0].length];
        for(int i = 0; i < s.getComponentMatrix().length; i++){
            for(int j = 0; j < s.getComponentMatrix()[0].length; j++){
                if(s.getAvailablePositionMatrix()[i][j]){
                    Components c = s.getComponent(i,j);
                    c.manageEpidemic(visited, s.getComponentMatrix().length, s.getComponentMatrix()[0].length);
                }
            }
        }
    }
}
