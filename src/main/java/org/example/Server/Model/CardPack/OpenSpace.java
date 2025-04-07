package org.example.Server.Model.CardPack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.Server.Controller.States.ActivateCannonsState;
import org.example.Server.Controller.States.ActivateEnginesState;
import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.Exceptions.BatteriesLessThenCannonException;
import org.example.Server.Model.Exceptions.InvalidPositionException;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;
import org.example.Server.Model.Points;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class OpenSpace extends AdventureCard{
    int currentPlayer;
    @JsonCreator
    public OpenSpace(
            @JsonProperty("cardLevel") int CardLevel,
            @JsonProperty("lostDays") int lostDays){
        super(CardLevel, lostDays);
        this.currentPlayer = -1;
    }

    @Override
    public void setCardState(Game g) {
//        if(currentPlayer==-1) {
//            checkEnginePower(g.getPlayers());
//        }

        do {
            currentPlayer++;
        } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());

        if(currentPlayer==g.getPlayers().size()) {
            g.Turn();
        }else {
            if (g.getPlayers().get(currentPlayer).getPlayerShipBoard().getNumDoubleEngines() != 0)
                g.getPlayers().get(currentPlayer).setPlayerState(new ActivateEnginesState(g));
            else {
                g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState());
                this.playCard(g, null, null);
            }
        }
    }

    @Override
    public void playCard(Game g, ArrayList<Points> engines, ArrayList<Points> batteries) {
        try {
            g.getPlayers().get(currentPlayer).changePosition(g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalEnginePower(engines, batteries));
            g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState());
            //setCardState(g);
        }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
            System.out.println("Error" + e.getMessage());
            g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g));
        }
    }

    private void checkEnginePower(ArrayList<Player> players){
        for (Player p : players) {
            if (!p.isAbandoned() && ((p.getPlayerShipBoard().getSingleEnginePower() == 0 && (p.getPlayerShipBoard().getNumDoubleEngines()==0 || p.getPlayerShipBoard().getTotalBattery() == 0)))){
                p.abandon();
            }
        }
    }
}
