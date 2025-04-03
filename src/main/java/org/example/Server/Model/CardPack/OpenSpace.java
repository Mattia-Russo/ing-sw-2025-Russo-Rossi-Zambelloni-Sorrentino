package org.example.Server.Model.CardPack;

import org.example.Server.Controller.States.ActivateCannonsState;
import org.example.Server.Controller.States.PlayerState;
import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;
import org.example.Server.Model.Points;
import java.util.ArrayList;

public class OpenSpace extends AdventureCard{
    int currentPlayer;
    public OpenSpace(int CardLevel, int lostDays){
        super(CardLevel, lostDays);
        this.currentPlayer = 0;
    }

    @Override
    public void setCardState(Game g) {
        checkEnginePower(g.getPlayers());
        boolean check=false;
        for(int i=0; i<g.getPlayers().size()&&!check; i++){
            if(!g.getPlayers().get(i).isAbandoned()){
                g.getPlayers().get(i).setPlayerState(new ActivateCannonsState());
                currentPlayer=i;
                check=true;
            }
        }
    };

    @Override
    public void playCard(Game g, ArrayList<Points> engines, ArrayList<Points> batteries) {
        g.getPlayers().get(currentPlayer).changePosition(g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalEnginePower(engines, batteries));
        g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState());
        boolean check=false;
        for(int i=currentPlayer+1; i<g.getPlayers().size()&&!check; i++){
            if(!g.getPlayers().get(i).isAbandoned()){
                g.getPlayers().get(i).setPlayerState(new ActivateCannonsState());
                currentPlayer=i;
                check=true;
            }
        }
        if(!check){
            g.Turn();
        }
    }

    public void checkEnginePower(ArrayList<Player> players){
        for (Player p : players) {
            if (!p.isAbandoned() && ((p.getPlayerShipBoard().getSingleEnginePower() == 0 && (p.getPlayerShipBoard().getNumDoubleEngines()==0 || p.getPlayerShipBoard().getTotalBattery() == 0)))){
                p.abandon();
            }
        }
    }
}
