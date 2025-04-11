package org.example.Server.Model.CardPack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.Server.Controller.PlayerStates.*;
import org.example.Server.Model.ComponentsPack.Components;
import org.example.Server.Model.ComponentsPack.Direction;
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
    private final int credit;
    private int currentPlayer;
    private boolean accept;
    private boolean playerLost;
    private int currentFire;
    private int rowOrCol;
    private boolean shipWrecked;
    private List<CannonFire> cannonFireList = new ArrayList<CannonFire>();
    @JsonCreator
    public Pirates(
            @JsonProperty("credits") int credit,
            @JsonProperty("cannonFiresList") List<CannonFire> cannonFireList,
            @JsonProperty("cardLevel") int cardLevel,
            @JsonProperty("lostDays") int lostDays,
            @JsonProperty("cannonPower") int cannonPower) {
        super(cardLevel, lostDays, cannonPower);
        this.cannonFireList = cannonFireList;
        this.credit = credit;
        this.accept = false;
        this.playerLost = false;
        this.shipWrecked = false;
        this.currentFire = 0;
        this.currentPlayer = -1;
    }

    @Override
    public void setCardState(Game g) {
        if(!playerLost) {
            do {
                currentPlayer++;
                this.currentFire = 0;
            } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());

            if (currentPlayer < g.getPlayers().size()) {
                if (g.getPlayers().get(currentPlayer).getPlayerShipBoard().getNumDoubleCannon() != 0) {
                    g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g));
                } else {
                    g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState());
                    this.playCard(g, null, null);
                }
            } else {
                g.Turn();
            }
        }
        else{
            Player p= g.getPlayers().get(currentPlayer);
            if (cannonFireList.get(currentFire).getType() == 0) {
                if (p.getPlayerShipBoard().getIfShielded(cannonFireList.get(currentFire).getDirection())) {
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

        if(!playerLost) { // chiamata arriva da setCardState, i components sono cannons
            try {
                if (this.getCannonPower() < g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(components, batteries)) {
                    g.getPlayers().get(currentPlayer).setPlayerState(new WinEnemyState(g));
                } else if (this.getCannonPower() > g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(components, batteries)) {
                    playerLost = true;
                    chooseRowOrCol(g.getPlayers().get(currentPlayer), g);
                    setCardState(g);    // riceve cannonate o passa al player successivo
                }
            }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                System.out.println("Error" + e.getMessage());
                g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g));
            }
        } else { // chiamata arriva da ActivateShieldsState, components sono scudi
            int i=0;
            Components shipWreck = null;
            Player p = g.getPlayers().get(currentPlayer);
            if(components == null || batteries == null || components.isEmpty() || batteries.isEmpty()) {   // non ha nulla attivo
                Components c = p.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).getDirection(), rowOrCol);
                try {
                    if (c != null){
                        p.getPlayerShipBoard().removeComponent(c.getPosX(), c.getPosY());
                        while(shipWreck == null){   // cerco un componente a caso della nave
                            shipWreck = p.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).getDirection(), i);
                            i++;
                        }
                        // nave divisa
                        if(p.getPlayerShipBoard().checkIfSplitted(shipWreck.getPosY(), shipWreck.getPosX())){
                            p.setPlayerState(new ShipWreckedState(g));
                            this.shipWrecked = true;
                        }
                    }
                }catch (InvalidPositionException | AlreadyEmptyPositionException e){
                    System.out.println("Error: " + e.getMessage());
                }
            }else { // ha attivato degli scudi
                try {
                    if (!p.getPlayerShipBoard().ShieldProtects(cannonFireList.get(currentFire).getDirection(), components, batteries)) {    // se scudo non protegge
                        Components c = p.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).getDirection(), rowOrCol);
                        try {
                            p.getPlayerShipBoard().removeComponent(c.getPosX(), c.getPosY());
                            while(shipWreck == null){
                                shipWreck = p.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).getDirection(), i);
                                i++;
                            }
                            if(!p.getPlayerShipBoard().checkIfSplitted(shipWreck.getPosX(), shipWreck.getPosY())){
                                if(currentFire < cannonFireList.size()-1) {
                                    currentFire++;
                                    chooseRowOrCol(p, g);
                                }else{
                                    playerLost =false;
                                }
                                setCardState(g);
                            }else{
                                if(currentFire < cannonFireList.size()-1) {
                                    currentFire++;
                                    chooseRowOrCol(p, g);
                                    p.setPlayerState(new ShipWreckedState(g));
                                }else{
                                    playerLost =false;
                                    p.setPlayerState(new ShipWreckedState(g));
                                }
                            }
                        } catch (InvalidPositionException | AlreadyEmptyPositionException e) {
                            System.out.println("Error" + e.getMessage());
                        }
                    }
                }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                    System.out.println("Error" + e.getMessage());
                    g.getPlayers().get(currentPlayer).setPlayerState(new ActivateShieldsState(g));
                }
            }

            if(!shipWrecked) {
                if(currentFire < cannonFireList.size()-1) {
                    currentFire++;
                    this.chooseRowOrCol(g.getPlayers().get(currentPlayer), g);
                }else{
                    playerLost=false;
                }
                g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState());
                setCardState(g);
            }
        }
    }

    @Override
    public void playCard(Game game){
        if (accept) {
            game.getPlayers().get(currentPlayer).changeCredits(this.credit);
            game.getPlayers().get(currentPlayer).changePosition(-this.getLostDays());
        }
        game.getPlayers().get(currentPlayer).setPlayerState(new WaitingState());
        currentPlayer = -1;
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
        return cannonFireList;
    }

    private void chooseRowOrCol(Player p, Game g) {
        boolean good = false;
        while(!good && currentFire<cannonFireList.size()) {
            if (cannonFireList.get(currentFire).getDirection() == Direction.NORTH || cannonFireList.get(currentFire).getDirection() == Direction.SOUTH) {
                rowOrCol= p.rollDice();
                if(rowOrCol < 7){
                    good = true;
                }else
                    currentFire++;
            } else {
                rowOrCol= p.rollDice();
                if(rowOrCol < 5){
                    good = true;
                }else
                    currentFire++;
            }
        }

        if (currentFire >= cannonFireList.size()){
            playerLost = false;
        }
    }

    @Override
    public void setShipWrecked(boolean shipWrecked) {
        this.shipWrecked = shipWrecked;
    }

    // usage only for tests
    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayer = currentPlayerIndex;
    }

    // usage only for tests
    public boolean getAccept() {
        return accept;
    }
}
