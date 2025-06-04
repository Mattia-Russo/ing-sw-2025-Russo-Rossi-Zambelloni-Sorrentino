package org.example.ServerPkg.Model.CardPkg;

import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.ShipBoard;

import java.io.Serializable;

public class Epidemic extends AdventureCard implements Serializable {
    private final int id;

    public Epidemic(int id, int CardLevel,int lostDays){
        super(CardLevel, lostDays);
        this.id = id;
    }

    @Override
    public AdventureCardView createView(){
        return new AdventureCardView(id, "Epidemic", getLostDays(),0 , 0,0, null, null, null, null, 0,null,null);
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
                new GameView(g, null);
            }
        }
        g.Turn();
    }

    private void checkAdjacentCabins(ShipBoard s){
        boolean[][] visited = new boolean[s.getComponentMatrix()[0].length][s.getComponentMatrix().length];
        for(int i = 4; i < s.getComponentMatrix()[0].length + 4; i++){
            for(int j = 5; j < s.getComponentMatrix().length + 5; j++){
                if(s.getAvailablePositionMatrix()[i - 4][j - 5]){
                    Components c = s.getComponent(i,j);
                    if(c != null){
                        c.manageEpidemic(visited, s.getComponentMatrix()[0].length, s.getComponentMatrix().length, s);
                    }
                }
            }
        }
    }
}
