package org.example.Server.Model.CardPack;

import org.example.Server.Controller.States.ActivateCannonsState;
import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.ComponentsPack.Cannon;
import org.example.Server.Model.Game;
import org.example.Server.Model.Points;

import java.util.ArrayList;
import java.util.List;

public class Pirates extends Enemy{
    private int credit;
    private int currentPlayer;
    private boolean defeated;
    private boolean lost;
    private List<CannonFire> cannonFiresList = new ArrayList<CannonFire>();

    public Pirates(int credit, List<CannonFire> cannonFiresList, int cardLevel, int lostDays, int cannonPower) {
        super(cardLevel, lostDays, cannonPower);
        this.cannonFiresList = cannonFiresList;
        this.credit = credit;
        this.defeated = false;
        this.lost = false;
    }

    @Override
    public void setCardState(Game g) {
        if(!defeated) {
            do {
                currentPlayer++;
            } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());
            if(currentPlayer == g.getPlayers().size()) {
                if (g.getPlayers().get(currentPlayer).getPlayerShipBoard().getNumDoubleCannon() != 0) {
                    g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState());
                } else {
                    g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState());
                    this.playCard(g, null, null);
                }
            }else g.Turn();

        }else
            g.Turn();
    }

    @Override
    public void playCard(Game g, ArrayList<Points> cannons, ArrayList<Points> batteries) {
        if(getCannonPower()<g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(cannons, batteries)){
            defeated = true;
            g.getPlayers().get(currentPlayer).changeCredits(getCredit());
            g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState());
            setCardState(g);
        }else if(getCannonPower()>g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(cannons, batteries)){
            
        }
    }

    public int getCannonPower() {
        return super.getCannonPower();
    }

    public int getCardLevel() {
        return super.getCardLevel();
    }

    public int getCredit() {
        return credit;
    }

    public int getLostDays() {return super.getLostDays();}

    public List<CannonFire> getCannonFireList() {
        return cannonFiresList;
    }

    //CONTROLLER CALCOLA POTENZA DI FUOCO USANDO UN METODO SUL MODEL , CHIAMA GETCANNONPOWER,
    // CONFRONTA POI O CHIAMA credit O prende lista colpi
}
