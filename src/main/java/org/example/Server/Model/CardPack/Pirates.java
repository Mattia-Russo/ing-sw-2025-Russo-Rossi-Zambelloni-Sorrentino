package org.example.Server.Model.CardPack;

import org.example.Server.Controller.States.ActivateCannonsState;
import org.example.Server.Controller.States.ChangeGoodsState;
import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Controller.States.WinEnemyState;
import org.example.Server.Model.ComponentsPack.Cannon;
import org.example.Server.Model.Game;
import org.example.Server.Model.Points;

import java.util.ArrayList;
import java.util.List;

public class Pirates extends Enemy{
    private int credit;
    private int currentPlayer;
    private boolean accept;
    private boolean lost;
    private int currentFire;
    private List<CannonFire> cannonFiresList = new ArrayList<CannonFire>();

    public Pirates(int credit, List<CannonFire> cannonFiresList, int cardLevel, int lostDays, int cannonPower) {
        super(cardLevel, lostDays, cannonPower);
        this.cannonFiresList = cannonFiresList;
        this.credit = credit;
        this.accept = false;
        this.lost = false;
        this.currentFire = 0;
    }

    @Override
    public void setCardState(Game g) {
        do {
            currentPlayer++;
        } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());

        if(currentPlayer == g.getPlayers().size()) {
            if (g.getPlayers().get(currentPlayer).getPlayerShipBoard().getNumDoubleCannon() != 0) {
                g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g));
            } else {
                g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState());
                this.playCard(g, null, null);
            }
        }else g.Turn();
    }

    @Override
    public void playCard(Game g, ArrayList<Points> cannons, ArrayList<Points> batteries) {
        if (getCannonPower() < g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(cannons, batteries)) {
            g.getPlayers().get(currentPlayer).setPlayerState(new WinEnemyState(g));
        } else if (getCannonPower() > g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(cannons, batteries)) {
            lost = true;
        }
        setCardState(g);
    }

    @Override
    public void playCard(Game game){
        if (accept){
            game.getPlayers().get(currentPlayer).changeCredits(getCredit());
            game.getPlayers().get(currentPlayer).changePosition(-getLostDays());
        } else {
            this.playCard(game, 0);
        }
    }

    @Override
    public void playCard(Game game, int ignore){
        currentPlayer = -1;
        game.getPlayers().get(currentPlayer).setPlayerState(new WaitingState());
        game.Turn();
    }

    @Override
    public void setAccept(boolean accept) {
        this.accept = accept;
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
