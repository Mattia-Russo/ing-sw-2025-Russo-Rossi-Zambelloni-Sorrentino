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

public class WarZone extends AdventureCard implements Serializable {
    private final int numAstronauts;
    private final int numGoods;
    private final List<CannonFire> cannonFireList;
    private final String[] penalties;
    private final String[] criteria;
    private int pos;
    private int currentPlayer;
    private int currentFire;
    private int rowOrCol;
    private float power;
    private Player loser;
    private boolean done=false;
    private boolean play=false;
    private boolean protect=false;
    private final int id;

    public WarZone(int id, int CardLevel,int lostDays,int numAstronauts, int numGoods,List<CannonFire> CannonFireList,String[] penalties,String[] criteria) {
        super(CardLevel, lostDays);
        this.numAstronauts = numAstronauts;
        this.numGoods = numGoods;
        this.cannonFireList = CannonFireList;
        this.penalties = penalties;
        this.criteria = criteria;
        this.pos = 0;
        this.currentPlayer = -1;
        this.currentFire = 0;
        this.loser=null;
        this.power = 0;
        this.id= id;
    }

    @Override
    public AdventureCardView createView(){
        return new AdventureCardView(id, "WarZone", getLostDays(),0 , numAstronauts,0, null, null, null, cannonFireList, numGoods, criteria, penalties);
    }

    @Override
    public void setCardState(Game g){
        if(pos<3) {
            if(!done) {
                switch (criteria[pos]) {
                    case "FewestAstronauts":
                        for (int i = 0; i < g.getPlayers().size(); i++) {
                            if (!g.getPlayers().get(i).isAbandoned()) {
                                if (loser == null) {
                                    loser = g.getPlayers().get(i);
                                } else if (loser.getPlayerShipBoard().getTotalAstronauts() > g.getPlayers().get(i).getPlayerShipBoard().getTotalAstronauts()) {
                                    loser = g.getPlayers().get(i);
                                }
                            }
                        }
                        currentPlayer=-1;
                        done = true;
                        play = true;
                        break;
                    case "LessEnginePower":
                        do {
                            currentPlayer++;
                        } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());

                        if (currentPlayer == g.getPlayers().size()) {
                            done = true;
                            play = true;
                            currentPlayer=-1;
                        } else {
                            if (g.getPlayers().get(currentPlayer).getPlayerShipBoard().getNumDoubleEngines() != 0) {
                                new GameView(g, new Exception("ACTIVATE ENGINES " + g.getPlayers().get(currentPlayer)));
                                g.getPlayers().get(currentPlayer).setPlayerState(new ActivateEnginesState(g));
                            } else {
                                g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState(g));
                                play = true;
                            }
                        }
                        break;
                    case "LessCannonPower":
                        do {
                            currentPlayer++;
                        } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());

                        if (currentPlayer == g.getPlayers().size()) {
                            done = true;
                            play = true;
                            currentPlayer=-1;
                        } else {
                            if (g.getPlayers().get(currentPlayer).getPlayerShipBoard().getNumDoubleCannon() != 0) {
                                new GameView(g, new Exception("ACTIVATE CANNONS " + g.getPlayers().get(currentPlayer)));
                                g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g));
                            } else {
                                g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState(g));
                                play = true;
                            }
                        }
                        break;
                }
                if(play) {
                    play=false;
                    this.playCard(g, null, null);
                }
            }else {
                if (cannonFireList.get(currentFire).getType() == 0) {
                    if (loser.getPlayerShipBoard().getIfShielded(cannonFireList.get(currentFire).getDirection())) {
                        new GameView(g, new Exception("ACTIVATE SHIELDS " + g.getPlayers().get(currentPlayer)));
                        loser.setPlayerState(new ActivateShieldsState(g));
                    }else{
                        this.playCard(g, null, null);
                    }
                }else
                    this.playCard(g, null, null);
            }

        }else {
            g.Turn();
        }
    }

    @Override
    public void playCard(Game g, ArrayList<Points> components, ArrayList<Points> batteries){
        if(!done) {
            switch (criteria[pos]) {
                case "LessEnginePower":
                    if (loser == null) {
                        try {
                            this.power = g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalEnginePower(components, batteries);
                            loser = g.getPlayers().get(currentPlayer);
                            new GameView(g, null);
                        }catch (InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                            System.out.println("Error" + e.getMessage());
                            new GameView(g, new Exception(e.getMessage() + "ACTIVATE ENGINES " + g.getPlayers().get(currentPlayer)));
                            g.getPlayers().get(currentPlayer).setPlayerState(new ActivateEnginesState(g));

                        }
                    } else{
                        try {
                            if(power>g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalEnginePower(components, batteries)) {
                                this.power = g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalEnginePower(components, batteries);
                                loser = g.getPlayers().get(currentPlayer);
                            }
                            new GameView(g, null);
                        }catch (InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                            System.out.println("Error" + e.getMessage());
                            new GameView(g, new Exception(e.getMessage() + "ACTIVATE ENGINES " + g.getPlayers().get(currentPlayer)));
                            g.getPlayers().get(currentPlayer).setPlayerState(new ActivateEnginesState(g));

                        }

                    }
                    break;
                case "LessCannonPower":
                    if (loser == null) {
                        try {
                            power = g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(components, batteries);
                            loser = g.getPlayers().get(currentPlayer);
                            new GameView(g, null);
                        }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                            System.out.println("Error" + e.getMessage());
                            new GameView(g, new Exception(e.getMessage() + "ACTIVATE CANNONS " + g.getPlayers().get(currentPlayer)));
                            g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g));
                        }

                    } else{
                        try {
                            if (power > g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(components, batteries)) {
                                power = g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(components, batteries);
                                loser = g.getPlayers().get(currentPlayer);
                            }
                            new GameView(g, null);
                        }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                            System.out.println("Error" + e.getMessage());
                            new GameView(g, new Exception(e.getMessage() + "ACTIVATE CANNONS " + g.getPlayers().get(currentPlayer)));
                            g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g));
                        }
                    }
                    break;
            }
            setCardState(g);
        }else {
            Player p;
            switch (penalties[pos]) {
                case "LoseDays":
                    loser.changePosition(-getLostDays());
                    pos++;
                    done=false;
                    loser=null;
                    new GameView(g,null);
                    setCardState(g);
                    break;
                case "LoseGoods":
                    pos++;
                    p = loser;
                    done=false;
                    loser=null;
                    new GameView(g, new Exception("REMOVE BEST GOODS " + g.getPlayers().get(currentPlayer)));
                    p.setPlayerState(new RemoveBestGoodsState(g));
                    break;
                case "cannonFire":
                    chooseRowOrCol(loser, g);
                    if (done) {
                        if (loser.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).getDirection(), rowOrCol) == null) {
                            protect = true;
                        }
                        if (!protect) {
                            int i = 0;
                            Components wreck = null;
                            if (components == null || batteries == null) {
                                Components c = loser.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).getDirection(), rowOrCol);
                                try {
                                    loser.getPlayerShipBoard().removeComponent(c.getPosX(), c.getPosY());
                                    new GameView(g, null);
                                    while (wreck == null) {
                                        wreck = loser.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).getDirection(), i);
                                        i++;
                                    }

                                    if (!loser.getPlayerShipBoard().checkIfSplitted(wreck.getPosX(), wreck.getPosY())) {
                                        chooseRowOrCol(loser, g);
                                        if (!done) {
                                            loser = null;
                                            pos++;
                                        }
                                        setCardState(g);
                                    } else {
                                        chooseRowOrCol(loser, g);
                                        if (!done) {
                                            p = loser;
                                            loser = null;
                                            pos++;
                                            new GameView(g, new Exception("SHIP WRECK  " + p.getName()));
                                            p.setPlayerState(new ShipWreckedState(g, p));
                                        } else {
                                            new GameView(g, new Exception("SHIP WRECK  " + loser.getName()));
                                            loser.setPlayerState(new ShipWreckedState(g, loser));
                                        }
                                    }
                                } catch (InvalidPositionException | AlreadyEmptyPositionException e) {
                                    System.out.println("Error" + e.getMessage());
                                }
                            } else {
                                try {
                                    if (loser.getPlayerShipBoard().shieldsNotProtects(cannonFireList.get(currentFire).getDirection(), components, batteries)) {
                                        new GameView(g, null);
                                        Components c = loser.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).getDirection(), rowOrCol);
                                        try {
                                            loser.getPlayerShipBoard().removeComponent(c.getPosX(), c.getPosY());
                                            new GameView(g, null);
                                            while (wreck == null) {
                                                wreck = loser.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).getDirection(), i);
                                                i++;
                                            }

                                            if (!loser.getPlayerShipBoard().checkIfSplitted(wreck.getPosX(), wreck.getPosY())) {
                                                chooseRowOrCol(loser, g);
                                                if (!done) {
                                                    loser = null;
                                                    pos++;
                                                }
                                                setCardState(g);
                                            } else {
                                                chooseRowOrCol(loser, g);
                                                if (!done) {
                                                    p = loser;
                                                    loser = null;
                                                    pos++;
                                                    new GameView(g, new Exception("SHIP WRECK  " + p.getName()));
                                                    p.setPlayerState(new ShipWreckedState(g, p));
                                                } else {
                                                    new GameView(g, new Exception("SHIP WRECK  " + loser.getName()));
                                                    loser.setPlayerState(new ShipWreckedState(g, loser));
                                                }
                                            }
                                        } catch (InvalidPositionException | AlreadyEmptyPositionException e) {
                                            System.out.println("Error" + e.getMessage());
                                        }
                                    }else
                                        new GameView(g, null);
                                } catch (InvalidPositionException | InvalidParameterException |
                                         BatteriesLessThenCannonException e) {
                                    System.out.println("Error" + e.getMessage());
                                    new GameView(g, new Exception(e.getMessage() + "ACTIVATE SHIELDS " + loser.getName()));
                                    loser.setPlayerState(new ActivateShieldsState(g));
                                }
                            }
                        }
                    }else{
                        loser=null;
                        pos++;
                    }
                    setCardState(g);
                    break;
                case "LoseAstronauts":
                    pos++;
                    p = loser;
                    done=false;
                    loser=null;
                    new GameView(g, new Exception("REMOVE ASTRONAUTS " + p.getName()));
                    p.setPlayerState(new RemoveAstronautsState(g));
                    new GameView(g, null);
                    break;
            }
        }
    }

    private void chooseRowOrCol(Player p, Game g) {
        boolean good = false;
        while(!good && currentFire<cannonFireList.size()) {
            if (cannonFireList.get(currentFire).getDirection() == Direction.NORTH || cannonFireList.get(currentFire).getDirection() == Direction.SOUTH) {
                rowOrCol= p.rollDice();
                new GameView(g, new Exception("SHOT  " + rowOrCol));
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
            done = false;
        }

    }

    public int getNumAstronauts() {
        return numAstronauts;
    }

    public int getNumGoods() {
        return numGoods;
    }

    public List<CannonFire> getCannonFireList() {
        return cannonFireList;
    }

    public int getLostDays() {
        return super.getLostDays();
    }

    public String[] getPenalties() {
        return penalties;
    }

    public String[] getCriteria() {
        return criteria;
    }
}
