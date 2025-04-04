package org.example.Server.Model.CardPack;

import org.example.Server.Controller.States.*;
import org.example.Server.Model.ComponentsPack.Cannon;
import org.example.Server.Model.ComponentsPack.Components;
import org.example.Server.Model.Exceptions.AlreadyEmptyPositionException;
import org.example.Server.Model.Exceptions.BatteriesLessThenCannonException;
import org.example.Server.Model.Exceptions.InvalidPositionException;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;
import org.example.Server.Model.Points;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Pirates extends Enemy{
    private int credit;
    private int currentPlayer;
    private boolean accept;
    private boolean lost;
    private int currentFire;
    private int rowOrCol;
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
        if(!lost) {
            do {
                currentPlayer++;
            } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());

            if (currentPlayer == g.getPlayers().size()) {
                if (g.getPlayers().get(currentPlayer).getPlayerShipBoard().getNumDoubleCannon() != 0) {
                    g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g));
                } else {
                    g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState());
                    this.playCard(g, null, null);
                }
            } else g.Turn();
        }else{
            Player p= g.getPlayers().get(currentPlayer);
            if (cannonFiresList.get(currentFire).getType() == 0) {
                if (p.getPlayerShipBoard().getIfShielded(cannonFiresList.get(currentFire).getDirection())) {
                    p.setPlayerState(new ActivateShieldsState(g));
                }else{
                    this.playCard(g, null, null);
                }
            }else
                this.playCard(g, null, null);
        }
    }

    @Override
    public void playCard(Game g, ArrayList<Points> components, ArrayList<Points> batteries) {
        if(!lost) {
            try {
                if (getCannonPower() < g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(components, batteries)) {
                    g.getPlayers().get(currentPlayer).setPlayerState(new WinEnemyState(g));
                } else if (getCannonPower() > g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(components, batteries)) {
                    lost = true;
                    rowOrCol = g.getPlayers().get(currentPlayer).rollDice();
                }
                setCardState(g);
            }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                System.out.println("Error" + e.getMessage());
                g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g));
            }

        }else{
            Player p=g.getPlayers().get(currentPlayer);
            if(components==null||batteries==null) {
                Components c=p.getPlayerShipBoard().getFirstComponent(cannonFiresList.get(currentFire).getDirection(), rowOrCol);
                try {
                    p.getPlayerShipBoard().removeComponent(c.getPosX(), c.getPosY());
                }catch (InvalidPositionException | AlreadyEmptyPositionException e){
                    System.out.println("Error" + e.getMessage());
                }
            }else {
                try {
                    if (!p.getPlayerShipBoard().ShieldProtects(cannonFiresList.get(currentFire).getDirection(), components, batteries)) {
                        Components c = p.getPlayerShipBoard().getFirstComponent(cannonFiresList.get(currentFire).getDirection(), rowOrCol);
                        try {
                            p.getPlayerShipBoard().removeComponent(c.getPosX(), c.getPosY());
                        } catch (InvalidPositionException | AlreadyEmptyPositionException e) {
                            System.out.println("Error" + e.getMessage());
                        }
                    }
                }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                    System.out.println("Error" + e.getMessage());
                    g.getPlayers().get(currentPlayer).setPlayerState(new ActivateShieldsState(g));
                }
            }

            if(cannonFiresList.iterator().hasNext()) {
                currentFire++;
                rowOrCol=p.rollDice();
            }else{
                lost=false;
            }
            setCardState(g);
        }


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
