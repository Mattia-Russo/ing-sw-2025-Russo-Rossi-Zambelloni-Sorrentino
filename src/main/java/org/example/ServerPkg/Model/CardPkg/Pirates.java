package org.example.ServerPkg.Model.CardPkg;

import org.example.ServerPkg.ControllerPkg.PlayerStates.*;
import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.Exceptions.AlreadyEmptyPositionException;
import org.example.ServerPkg.Model.Exceptions.BatteriesLessThenCannonException;
import org.example.ServerPkg.Model.Exceptions.InvalidPositionException;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Pirates extends Enemy implements Serializable {
    private final int credit;
    private int currentPlayer;
    private boolean accept;
    private boolean playerLost;
    private int currentFire;
    private int rowOrCol;
    private boolean shipWrecked;
    private final List<CannonFire> cannonFireList;
    private final int id;

    public Pirates(int id, int credit, List<CannonFire> cannonFireList, int cardLevel, int lostDays, int cannonPower) {
        super(cardLevel, lostDays, cannonPower);
        this.cannonFireList = cannonFireList;
        this.credit = credit;
        this.accept = false;
        this.playerLost = false;
        this.shipWrecked = false;
        this.currentFire = 0;
        this.currentPlayer = -1;
        this.id = id;
    }

    @Override
    public AdventureCardView createView() {
        String command = """
               You are playing the pirates card, you can type:
               activate_cannons x y -> x,y are the coordinates of a cannon, you should write a number of x,y based on the number of cannons you want to activate
               activate_shields x y -> x,y are the coordinates of a shield, you should write a number of x,y based on the number of shields you want to activate
               use_batteries x y -> x,y are the coordinates of the battery storage, you should write a number of x,y based on the number of batteries you want to use
            
               end_activate_cannons -> if you want to end the cannon activation phase
               end_activate_shields -> if you want to end the shield activation phase
              
               choose_wrecked x y -> x,y are the coordinates of one of the tile from the part you want to keep
               end_wrecked -> if you want to end the wrecked ship phase
              
               accept_reward true/false -> true if you want to accept the reward, false otherwise
              
              """;
        return new AdventureCardView(command, id, "Pirates", getLostDays(), credit, 0, getCannonPower(), null, null, null, cannonFireList, 0, null, null);
    }

    @Override
    public void setCardState(Game g) {
        if (!playerLost) {
            do {
                currentPlayer++;
                this.currentFire = 0;
            } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());

            if (currentPlayer < g.getPlayers().size()) {
                if (g.getPlayers().get(currentPlayer).getPlayerShipBoard().getNumDoubleCannon() != 0) {
                    new GameView(g, new Exception("ACTIVATE CANNON  " + g.getPlayers().get(currentPlayer).getName()));
                    g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g));
                } else {
                    g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState(g));
                    this.playCard(g, null, null);
                }
            } else {
                g.Turn();
            }
        } else {
            Player p = g.getPlayers().get(currentPlayer);
            if (cannonFireList.get(currentFire).type() == 0) {
                if (p.getPlayerShipBoard().getIfShielded(cannonFireList.get(currentFire).direction())) {
                    new GameView(g, new Exception("ACTIVATE SHIELD " + g.getPlayers().get(currentPlayer).getName()));
                    p.setPlayerState(new ActivateShieldsState(g));
                } else {
                    this.playCard(g, null, null);
                }
            } else
                this.playCard(g, null, null);
        }
    }

    @Override
    public void playCard(Game g, ArrayList<Points> components, ArrayList<Points> batteries) {

        if (!playerLost) { // chiamata arriva da setCardState, i components sono cannons
            try {
                float power = g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(components, batteries);
                new GameView(g, null);
                if (this.getCannonPower() < power) {
                    new GameView(g, new Exception("WIN ENEMY " + g.getPlayers().get(currentPlayer).getName()));
                    g.getPlayers().get(currentPlayer).setPlayerState(new WinEnemyState(g));
                } else if (this.getCannonPower() > power) {
                    playerLost = true;
                    chooseRowOrCol(g.getPlayers().get(currentPlayer), g);
                    setCardState(g);    // riceve cannonate o passa al player successivo
                } else {
                    setCardState(g);
                }
            } catch (InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e) {
                System.out.println("Error" + e.getMessage());
                new GameView(g, new Exception(e.getMessage() + "ACTIVATE CANNONS " + g.getPlayers().get(currentPlayer).getName()));
                g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g));
            }
        } else { // chiamata arriva da ActivateShieldsState, components sono scudi
            int i = 0;
            Components shipWreck = null;
            Player p = g.getPlayers().get(currentPlayer);
            if (components == null || batteries == null) {   // non ha nulla attivo
                Components c = p.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).direction(), rowOrCol);
                try {
                    if (c != null) {
                        p.getPlayerShipBoard().removeComponent(c.getPosX(), c.getPosY());
                        new GameView(g, null);
                        while (shipWreck == null) {   // cerco un componente a caso della nave
                            shipWreck = p.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).direction(), i);
                            i++;
                        }
                        // nave divisa
                        if (p.getPlayerShipBoard().checkIfSplit(shipWreck.getPosX(), shipWreck.getPosY())) {
                            new GameView(g, new Exception("SHIP WRECK  " + p.getName()));
                            p.setPlayerState(new ShipWreckedState(g, p));
                            this.shipWrecked = true;
                        }
                    }
                } catch (InvalidPositionException | AlreadyEmptyPositionException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else { // ha attivato degli scudi
                try {
                    if (p.getPlayerShipBoard().shieldsNotProtects(cannonFireList.get(currentFire).direction(), components, batteries)) {    // se scudo non protegge
                        if (p.getPlayerShipBoard().shieldsNotProtects(cannonFireList.get(currentFire).direction(), components, batteries)) {    // se scudo non protegge
                            new GameView(g, null);
                            Components c = p.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).direction(), rowOrCol);
                            try {
                                p.getPlayerShipBoard().removeComponent(c.getPosX(), c.getPosY());
                                new GameView(g, null);
                                while (shipWreck == null) {
                                    shipWreck = p.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).direction(), i);
                                    i++;
                                }
                                if (!p.getPlayerShipBoard().checkIfSplit(shipWreck.getPosX(), shipWreck.getPosY())) {
                                    if (currentFire < cannonFireList.size() - 1) {
                                        currentFire++;
                                        chooseRowOrCol(p, g);
                                    } else {
                                        playerLost = false;
                                    }
                                    setCardState(g);
                                } else {
                                    if (currentFire < cannonFireList.size() - 1) {
                                        currentFire++;
                                        chooseRowOrCol(p, g);
                                    } else {
                                        playerLost = false;
                                    }
                                    new GameView(g, new Exception("SHIP WRECK  " + p.getName()));
                                    p.setPlayerState(new ShipWreckedState(g, p));
                                }
                            } catch (InvalidPositionException | AlreadyEmptyPositionException e) {
                                System.out.println("Error" + e.getMessage());
                            }
                        } else
                            new GameView(g, null);
                    }
                }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                        System.out.println("Error" + e.getMessage());
                        new GameView(g, new Exception(e.getMessage() + "ACTIVATE SHIELDS " + p.getName()));
                        g.getPlayers().get(currentPlayer).setPlayerState(new ActivateShieldsState(g));
                }

                if (!shipWrecked) {
                    if (currentFire < cannonFireList.size() - 1) {
                        currentFire++;
                        this.chooseRowOrCol(g.getPlayers().get(currentPlayer), g);
                    } else {
                        playerLost = false;
                    }
                    g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState(g));
                    setCardState(g);
                }
            }
        }
    }

    @Override
    public void playCard (Game game){
        if (accept) {
            game.getPlayers().get(currentPlayer).changeCredits(this.credit);
            game.adjustPlayerPositions();
            game.getPlayers().get(currentPlayer).changePosition(-this.getLostDays() + game.getOccupiedPositions(game.getPlayers().get(currentPlayer), -this.getLostDays()));
            new GameView(game, null);
        }
        game.getPlayers().get(currentPlayer).setPlayerState(new WaitingState(game));
        currentPlayer = -1;
        game.Turn();
    }

    @Override
    public void playCard (Player disconnectingPlayer, Game game){
        currentPlayer = -1;
        game.Turn();
    }

    @Override
    public void setAccept ( boolean accept){
        this.accept = accept;
    }

    public int getCannonPower () {
        return super.getCannonPower();
    }

    public int getCardLevel () {
        return super.getCardLevel();
    }

    public int getCredit () {
        return credit;
    }

    public int getLostDays () {
        return super.getLostDays();
    }

    public List<CannonFire> getCannonFireList () {
        return cannonFireList;
    }

    private void chooseRowOrCol (Player p, Game g){
        boolean good = false;
        while (!good && currentFire < cannonFireList.size()) {
            if (cannonFireList.get(currentFire).direction() == Direction.NORTH || cannonFireList.get(currentFire).direction() == Direction.SOUTH) {
                rowOrCol = g.rollDice();
                if(cannonFireList.get(currentFire).type()== 0){
                    new GameView(g, new Exception("SMALL SHOT FROM " + cannonFireList.get(currentFire).direction() + " AT COLUMN " + rowOrCol));
                } else {
                    new GameView(g, new Exception("BIG SHOT FROM " + cannonFireList.get(currentFire).direction() + " AT COLUMN " + rowOrCol));
                }

                if(rowOrCol < 11 && rowOrCol > 3){
                    good = true;
                }else
                    currentFire++;
            } else {
                rowOrCol = g.rollDice();
                if(cannonFireList.get(currentFire).type()== 0){
                    new GameView(g, new Exception("SMALL SHOT FROM " + cannonFireList.get(currentFire).direction() + " AT ROW " + rowOrCol));
                } else {
                    new GameView(g, new Exception("BIG SHOT FROM " + cannonFireList.get(currentFire).direction() + " AT ROW " + rowOrCol));
                }
                if(rowOrCol < 10 && rowOrCol > 4){
                    good = true;
                }else
                    currentFire++;
            }
        }

        if (currentFire >= cannonFireList.size()) {
            playerLost = false;
        }
    }

    @Override
    public void setShipWrecked ( boolean shipWrecked){
        this.shipWrecked = shipWrecked;
    }

    // usage only for tests
    public void setCurrentPlayerIndex ( int currentPlayerIndex){
        this.currentPlayer = currentPlayerIndex;
    }

    // usage only for tests
    public boolean getAccept () {
        return accept;
    }
}
