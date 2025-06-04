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
    int currentMeteor;
    int currentPlayer;
    int rowOrCol;
    boolean protect;
    boolean dice;
    private final int id;


    public MeteorCard(int id,int cardLevel,int lostDays,List<Meteor> meteorList){
        super(cardLevel, lostDays);
        this.meteorList=meteorList;
        this.currentMeteor=0;
        this.currentPlayer=-1;
        this.rowOrCol =-1;
        this.protect=false;
        this.dice=true;
        this.id = id;
    }

    @Override
    public AdventureCardView createView(){
        return new AdventureCardView(id, "MeteorCard", 0,0 , 0,0, meteorList, null, null, null, 0,null,null);
    }

    @Override
    public void setCardState(Game g){
        do {
            currentPlayer++;
        } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());

        if(currentPlayer == g.getPlayers().size()){
            currentPlayer=-1;
            do{
                currentPlayer++;
            } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());
        }

        chooseRowOrCol(g.getPlayers().get(currentPlayer), g);

        if(dice) {
            if (g.getPlayers().get(currentPlayer).getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), rowOrCol) == null) {
                protect = true;
            }
            if (protect) {
                playCard(g, null, null);
            } else
                Update(g, g.getPlayers().get(currentPlayer));
        }else
            g.Turn();
    }

    private void Update(Game g, Player p) {
        if (meteorList.get(currentMeteor).getType() == 0) {
            if (p.getPlayerShipBoard().getIfExposed(meteorList.get(currentMeteor).getDirection(), p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), rowOrCol))) {
                if (p.getPlayerShipBoard().getIfShielded(meteorList.get(currentMeteor).getDirection())) {
                    new GameView(g, new Exception("ACTIVATE SHIELDS  " + p.getName()));
                    p.setPlayerState(new ActivateShieldsState(g));
                } else
                    this.playCard(g, null, null);
            } else {
                protect = true;
                this.playCard(g, null, null);
            }
        } else {
            if (meteorList.get(currentMeteor).getDirection() == Direction.NORTH) {
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
                if (p.getPlayerShipBoard().getIfSingleCannon(meteorList.get(currentMeteor).getDirection(), rowOrCol) || p.getPlayerShipBoard().getIfSingleCannon(meteorList.get(currentMeteor).getDirection(), rowOrCol - 1) || p.getPlayerShipBoard().getIfSingleCannon(meteorList.get(currentMeteor).getDirection(), rowOrCol + 1)) {
                    protect = true;
                    this.playCard(g, null, null);
                } else if (p.getPlayerShipBoard().getIfDoubleCannon(meteorList.get(currentMeteor).getDirection(), rowOrCol) || p.getPlayerShipBoard().getIfDoubleCannon(meteorList.get(currentMeteor).getDirection(), rowOrCol - 1) || p.getPlayerShipBoard().getIfDoubleCannon(meteorList.get(currentMeteor).getDirection(), rowOrCol + 1)) {
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
            }else if(meteorList.get(currentMeteor).getType()==0){
                try {
                    if (p.getPlayerShipBoard().shieldsNotProtects(meteorList.get(currentMeteor).getDirection(), component, battery)) {
                        new GameView(g, null);
                        checkWreck(g, p, i);
                    }else
                        new GameView(g, null);
                }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                    System.out.println("Error" + e.getMessage());
                    new GameView(g, new Exception(e.getMessage() + "ACTIVATE SHIELDS " + p.getName()));
                    g.getPlayers().get(currentPlayer).setPlayerState(new ActivateShieldsState(g));
                }
            }else {
                try{
                    if (!p.getPlayerShipBoard().CannonProtects(meteorList.get(currentMeteor).getDirection(), rowOrCol, component, battery)) {
                        new GameView(g, null);
                        checkWreck(g, p, i);
                    }else
                        new GameView(g, null);
                }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                    System.out.println("Error" + e.getMessage());
                    new GameView(g, new Exception(e.getMessage() + "ACTIVATE CANNONS " + p.getName()));
                    g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g));
                }

            }
        }
        protect=false;
        p.setPlayerState(new WaitingState(g));
        setCardState(g);
    }

    private void checkWreck(Game g, Player p, int i) {
        Components wreck = null;
        Components c=p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), rowOrCol);
        try {
            p.getPlayerShipBoard().removeComponent(c.getPosX(), c.getPosY());
            new GameView(g, null);
            while (wreck == null) {
                wreck = p.getPlayerShipBoard().getFirstComponent(meteorList.get(currentMeteor).getDirection(), i);
                i++;
            }
            if (!p.getPlayerShipBoard().checkIfSplitted(wreck.getPosX(), wreck.getPosY())) {
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

    private void chooseRowOrCol(Player p, Game g) {
        boolean good = false;
        while(!good && currentMeteor<meteorList.size()) {
            if (meteorList.get(currentMeteor).getDirection() == Direction.NORTH || meteorList.get(currentMeteor).getDirection() == Direction.SOUTH) {
                rowOrCol = p.rollDice();
                new GameView(g, new Exception("METEOR  " + rowOrCol));
                if(rowOrCol < 11 && rowOrCol > 3){
                    good = true;
                }else
                    currentMeteor++;
            } else {
                rowOrCol = p.rollDice();
                new GameView(g, new Exception("METEOR  " + rowOrCol));
                if(rowOrCol < 10 && rowOrCol > 4){
                    good = true;
                }else
                    currentMeteor++;
            }
        }
        if (currentMeteor >= meteorList.size()){
            dice=false;
        }
    }

    public int getCardLevel() {
        return super.getCardLevel();
    }

    public List<Meteor> getMeteorList() {
        return meteorList;
    }
}
