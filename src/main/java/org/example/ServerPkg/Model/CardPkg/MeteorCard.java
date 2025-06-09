
package org.example.ServerPkg.Model.CardPkg;


import org.example.ServerPkg.ControllerPkg.PlayerStates.ActivateCannonsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ActivateShieldsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ShipWreckedState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
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

public class MeteorCard extends AdventureCard implements Serializable {
    private final List<Meteor> meteorList;
    private int currentMeteorIndex;
    private int currentPlayer;
    private int rowOrCol;
    private boolean protect;
    private final int id;
    private boolean first;


    public MeteorCard(int id, int cardLevel, int lostDays, List<Meteor> meteorList) {
        super(cardLevel, lostDays);
        this.meteorList = meteorList;
        this.currentMeteorIndex = 0;
        this.currentPlayer = -1;
        this.rowOrCol = -1;
        this.protect = false;
        this.first = true;
        this.id = id;
    }

    @Override
    public AdventureCardView createView() {
        String command = """
                 You are playing the meteor card, you can type:
                 activate_cannons x y -> x,y are the coordinates of a cannon, you should write a number of x,y based on the number of cannons you want to activate
                 activate_shields x y -> x,y are the coordinates of a shield, you should write a number of x,y based on the number of shields you want to activate
                 use_batteries x y -> x,y are the coordinates of the battery storage, you should write a number of x,y based on the number of batteries you want to use
                
                 end_activate_cannons -> if you want to end the cannon activation phase
                 end_activate_shields -> if you want to end the shield activation phase
                
                 choose_wrecked x y -> x,y are the coordinates of one of the tile from the part you want to keep
                 end_wrecked -> if you want to end the wrecked ship phase
                """;

        return new AdventureCardView(command, id, "MeteorCard", 0, 0, 0, 0, meteorList, null, null, null, 0, null, null);
    }

    private boolean setCurrentPlayer(Game g){
        if(currentPlayer == g.getPlayers().size() || first){
            currentPlayer = -1;
            first = false;
            if(currentMeteorIndex != meteorList.size()-1){
                currentMeteorIndex++;
                chooseRowOrCol(g);
                if(currentMeteorIndex == meteorList.size()){
                    return false;
                }
            }else
                return false;
        }
        do {
            currentPlayer++;
        } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());
        if(currentPlayer == g.getPlayers().size()){
            return setCurrentPlayer(g);
        }else
            return true;
    }

    @Override
    public void setCardState(Game g) {
        if(setCurrentPlayer(g)){
            if (g.getPlayers().get(currentPlayer).getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteorIndex).direction(), rowOrCol) == null) {
                protect = true;
            }
            if (protect) {
                playCard(g, null, null);
            } else {
                Update(g, g.getPlayers().get(currentPlayer));
            }
        }else {
            g.Turn();
        }
    }

    private void Update(Game g, Player p) {
        if (meteorList.get(currentMeteorIndex).type() == 0) {
            if (p.getPlayerShipBoard().getIfExposed(meteorList.get(currentMeteorIndex).direction(), p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteorIndex).direction(), rowOrCol))) {
                if (p.getPlayerShipBoard().getIfShielded(meteorList.get(currentMeteorIndex).direction())) {
                    new GameView(g, new Exception("ACTIVATE SHIELDS  " + p.getName()));
                    p.setPlayerState(new ActivateShieldsState(g));
                } else
                    this.playCard(g, null, null);
            } else {
                protect = true;
                this.playCard(g, null, null);
            }
        } else {
            if (meteorList.get(currentMeteorIndex).direction() == Direction.NORTH) {
                if (p.getPlayerShipBoard().getIfSingleCannon(Direction.NORTH, rowOrCol)) {
                    protect = true;
                    this.playCard(g, null, null);
                } else if (p.getPlayerShipBoard().getIfDoubleCannon(Direction.NORTH, rowOrCol)) {
                    new GameView(g, new Exception("ACTIVATE CANNON  " + p.getName()));
                    p.setPlayerState(new ActivateCannonsState(g));
                } else {
                    this.playCard(g, null, null);
                }
            } else {
                if (p.getPlayerShipBoard().getIfSingleCannon(meteorList.get(currentMeteorIndex).direction(), rowOrCol) || p.getPlayerShipBoard().getIfSingleCannon(meteorList.get(currentMeteorIndex).direction(), rowOrCol - 1) || p.getPlayerShipBoard().getIfSingleCannon(meteorList.get(currentMeteorIndex).direction(), rowOrCol + 1)) {
                    protect = true;
                    this.playCard(g, null, null);
                } else if (p.getPlayerShipBoard().getIfDoubleCannon(meteorList.get(currentMeteorIndex).direction(), rowOrCol) || p.getPlayerShipBoard().getIfDoubleCannon(meteorList.get(currentMeteorIndex).direction(), rowOrCol - 1) || p.getPlayerShipBoard().getIfDoubleCannon(meteorList.get(currentMeteorIndex).direction(), rowOrCol + 1)) {
                    new GameView(g, new Exception("ACTIVATE CANNON  " + p.getName()));
                    p.setPlayerState(new ActivateCannonsState(g));
                } else {
                    this.playCard(g, null, null);
                }

            }
        }
    }

    @Override
    public void playCard(Game g, ArrayList<Points> component, ArrayList<Points> battery) {
        Player p=g.getPlayers().get(currentPlayer);
        if(!protect){
            int i=0;
            if(component==null||battery==null){
                checkWreck(g, p, i);
            }else if(meteorList.get(currentMeteorIndex).type()==0){
                try {
                    if (p.getPlayerShipBoard().shieldsNotProtects(meteorList.get(currentMeteorIndex).direction(), component, battery)) {
                        new GameView(g, null);
                        checkWreck(g, p, i);
                    }else {
                        new GameView(g, null);
                        p.setPlayerState(new WaitingState(g));
                    }
                }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                    System.out.println("Error" + e.getMessage());
                    new GameView(g, new Exception(e.getMessage() + "ACTIVATE SHIELDS " + p.getName()));
                    g.getPlayers().get(currentPlayer).setPlayerState(new ActivateShieldsState(g));
                }
            }else {
                try{
                    if (!p.getPlayerShipBoard().cannonProtects(meteorList.get(currentMeteorIndex).direction(), rowOrCol, component, battery)) {
                        new GameView(g, null);
                        checkWreck(g, p, i);
                    }else {
                        new GameView(g, null);
                        p.setPlayerState(new WaitingState(g));
                    }
                }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                    System.out.println("Error" + e.getMessage());
                    new GameView(g, new Exception(e.getMessage() + "ACTIVATE CANNONS " + p.getName()));
                    g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g));
                }

            }
        }
        protect=false;
        setCardState(g);
    }

    private void checkWreck(Game g, Player p, int i) {
        Components wreck = null;
        Components c=p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteorIndex).direction(), rowOrCol);
        try {
            p.getPlayerShipBoard().removeComponent(c.getPosX(), c.getPosY());
            new GameView(g, null);
            while (wreck == null) {
                wreck = p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteorIndex).direction(), i + 5);
                i++;
            }
            if (!p.getPlayerShipBoard().checkIfSplit(wreck.getPosX(), wreck.getPosY())) {
                protect = false;
                p.setPlayerState(new WaitingState(g));
                setCardState(g);
            } else {
                protect = false;
                new GameView(g, new Exception("SHIP WRECK  " + p.getName()));
                p.setPlayerState(new ShipWreckedState(g, p));
            }
        } catch (InvalidPositionException | AlreadyEmptyPositionException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    private void chooseRowOrCol(Game g) {
        boolean good = false;
        Meteor currentMeteor = meteorList.get(currentMeteorIndex);
        while(!good && currentMeteorIndex < meteorList.size()) {
            if (currentMeteor.direction() == Direction.NORTH || currentMeteor.direction() == Direction.SOUTH) {
                rowOrCol = g.rollDice();
                if(currentMeteor.type() == 0){
                    new GameView(g, new Exception("SMALL METEOR FROM " + currentMeteor.direction() + " AT COLUMN " + rowOrCol));
                } else {
                    new GameView(g, new Exception("BIG METEOR FROM " + currentMeteor.direction() + " AT COLUMN " + rowOrCol));
                }

                if(rowOrCol < 11 && rowOrCol > 3){
                    good = true;
                }else
                    currentMeteorIndex++;
            } else {
                rowOrCol = g.rollDice();
                if(currentMeteor.type() == 0){
                    new GameView(g, new Exception("SMALL METEOR FROM " + currentMeteor.direction() + " AT ROW " + rowOrCol));
                } else {
                    new GameView(g, new Exception("BIG METEOR FROM " + currentMeteor.direction() + " AT ROW " + rowOrCol));
                }
                if(rowOrCol < 10 && rowOrCol > 4){
                    good = true;
                }else
                    currentMeteorIndex++;
            }
        }
    }

    public int getCardLevel() {
        return super.getCardLevel();
    }

    public List<Meteor> getMeteorList() {
        return meteorList;
    }
}
