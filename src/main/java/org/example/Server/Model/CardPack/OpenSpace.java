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
        this.currentPlayer = -1;
    }

    @Override
    public void setCardState(Game g) {
        if(currentPlayer==-1) {
            checkEnginePower(g.getPlayers());
        }

        do {
            currentPlayer++;
        } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());

        if(currentPlayer==g.getPlayers().size()) {
            g.Turn();
        }else {
            if (g.getPlayers().get(currentPlayer).getPlayerShipBoard().getNumDoubleCannon() != 0)
                g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState());
            else {
                g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState());
                this.playCard(g, null, null);
            }
        }
    };

    @Override
    public void playCard(Game g, ArrayList<Points> engines, ArrayList<Points> batteries) {
        g.getPlayers().get(currentPlayer).changePosition(g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalEnginePower(engines, batteries));
        g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState());
        setCardState(g);
    }

    public void checkEnginePower(ArrayList<Player> players){
        for (Player p : players) {
            if (!p.isAbandoned() && ((p.getPlayerShipBoard().getSingleEnginePower() == 0 && (p.getPlayerShipBoard().getNumDoubleEngines()==0 || p.getPlayerShipBoard().getTotalBattery() == 0)))){
                p.abandon();
            }
        }
    }
}
