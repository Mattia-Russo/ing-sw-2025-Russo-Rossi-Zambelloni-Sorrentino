package org.example.Server.Model.CardPack;

import org.example.Server.Controller.States.ActivateCannonsState;
import org.example.Server.Controller.States.PlayerState;
import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.Player;
import org.example.Server.Model.Points;

import javax.smartcardio.Card;
import java.util.ArrayList;

public class OpenSpace extends AdventureCard{
    int currentPlayer;
    public OpenSpace(int CardLevel, int lostDays){
        super(CardLevel, lostDays);
        this.currentPlayer = 0;
    }

    @Override
    public void setCardState(ArrayList<Player> players) {
        checkEnginePower(players);
        boolean check=false;
        for(int i=0; i<players.size(); i++){
            if(!players.get(i).isAbandoned()){
                if(!check){
                    players.get(i).setPlayerState(new ActivateCannonsState());
                    currentPlayer=i;
                    check=true;
                }else
                    players.get(i).setPlayerState(new WaitingState());
            }
        }
    };

    @Override
    public void playCard(ArrayList<Player> players, ArrayList<Points> engines, ArrayList<Points> batteries) {
        players.get(currentPlayer).changePosition(players.get(currentPlayer).getPlayerShipBoard().getTotalEnginePower(engines, batteries));
        players.get(currentPlayer).setPlayerState(new WaitingState());
        boolean check=false;
        for(int i=currentPlayer+1; i<players.size()&&!check; i++){
            if(!players.get(i).isAbandoned()){
                if(!check){
                    players.get(i).setPlayerState(new ActivateCannonsState());
                    currentPlayer=i;
                    check=true;
                }
            }
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
