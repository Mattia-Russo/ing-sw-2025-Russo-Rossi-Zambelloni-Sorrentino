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
import java.rmi.RemoteException;
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
    private boolean done= false;
    private boolean protect= false;
    private boolean fire = true;
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
        this.currentFire = -1;
        this.loser=null;
        this.power = 0;
        this.id= id;
    }

    @Override
    public AdventureCardView createView(){
        String command = """
               You are playing the war zone card, you can type:
               activate_cannons x y -> x,y are the coordinates of a cannon, you should write a number of x,y based on the number of cannons you want to activate
               activate_engines x y -> x,y are the coordinates of an engine, you should write a number of x,y based on the number of engines you want to activate
               activate_shields x y -> x,y are the coordinates of a shield, you should write a number of x,y based on the number of shields you want to activate
               use_batteries x y -> x,y are the coordinates of the battery storage, you should write a number of x,y based on the number of batteries you want to use
            
               end_activate_cannons -> if you want to end the cannon activation phase
               end_activate_engines -> if you want to end the engine activation phase
               end_activate_shields -> if you want to end the shield activation phase
               
               remove_astronauts x y -> x,y are the coordinates of the component where you want to remove the astronauts
               remove_best_good x y -> x,y are the coordinates of the component where you want to remove the goods
               remove_batteries x y -> x,y are the coordinates of the battery storage where you want to remove the battery
               end_remove_best_goods -> if you want to end the remove best goods phase
               end_remove_astronauts -> if you want to end the remove astronauts phase
               
               """;
        return new AdventureCardView(command, id, "WarZone", getLostDays(),0 , numAstronauts,0, null, null, null, cannonFireList, numGoods, criteria, penalties);
    }

    @Override
    public void setCardState(Game g) throws RemoteException {
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
                    if(penalties[pos].equals("cannonFire")){
                        protect = true;
                    }
                    this.playCard(g, null, null);
                    break;
                case "LessEnginePower":
                    do {
                        currentPlayer++;
                    } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());

                    if (currentPlayer == g.getPlayers().size()) {
                        done = true;
                        currentPlayer=-1;
                        if(penalties[pos].equals("cannonFire")){
                            protect = true;
                        }
                        this.playCard(g, null, null);
                    } else {
                        if (g.getPlayers().get(currentPlayer).getPlayerShipBoard().getNumDoubleEngines() != 0) {
                            new GameView(g, new Exception("ACTIVATE ENGINES " + g.getPlayers().get(currentPlayer).getName()));
                            g.getPlayers().get(currentPlayer).setPlayerState(new ActivateEnginesState(g,  g.getPlayers().get(currentPlayer)));
                        } else {
                            g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState(g, g.getPlayers().get(currentPlayer)));
                            this.playCard(g, null, null);
                        }
                    }
                    break;
                case "LessCannonPower":
                    do {
                        currentPlayer++;
                    } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());

                    if (currentPlayer == g.getPlayers().size()) {
                        done = true;
                        currentPlayer=-1;
                        if(penalties[pos].equals("cannonFire")){
                            protect = true;
                        }
                        this.playCard(g, null, null);
                    } else {
                        if (g.getPlayers().get(currentPlayer).getPlayerShipBoard().getNumDoubleCannon() != 0) {
                            new GameView(g, new Exception("ACTIVATE CANNONS " + g.getPlayers().get(currentPlayer).getName()));
                            g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g, g.getPlayers().get(currentPlayer)));
                        } else {
                            g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState(g, g.getPlayers().get(currentPlayer)));
                            this.playCard(g, null, null);
                        }
                    }
                    break;
            }

        }else {
            currentFire++;
            chooseRowOrCol(g);
            if(fire) {
                if (loser.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).direction(), rowOrCol) == null) {
                    protect = true;
                    this.playCard(g, null, null);
                } else if (cannonFireList.get(currentFire).type() == 0) {
                    if (loser.getPlayerShipBoard().getIfShielded(cannonFireList.get(currentFire).direction())) {
                        new GameView(g, new Exception("ACTIVATE SHIELDS " + loser.getName()));
                        loser.setPlayerState(new ActivateShieldsState(g, loser));
                    } else {
                        this.playCard(g, null, null);
                    }
                } else {
                    this.playCard(g, null, null);
                }
            }else {
                g.Turn();
            }
        }
    }

    @Override
    public void playCard(Game g, ArrayList<Points> components, ArrayList<Points> batteries) throws RemoteException {
        if(!done) {
            switch (criteria[pos]) {
                case "LessEnginePower":
                    if (loser == null) {
                        try {
                            this.power = g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalEnginePower(components, batteries);
                            loser = g.getPlayers().get(currentPlayer);
                            setCardState(g);
                        }catch (InvalidPositionException | InvalidParameterException |
                                BatteriesLessThenCannonException | RemoteException e){
                            System.out.println("Error" + e.getMessage());
                            new GameView(g, new Exception(e.getMessage() + "ACTIVATE ENGINES " + g.getPlayers().get(currentPlayer).getName()));
                            g.getPlayers().get(currentPlayer).setPlayerState(new ActivateEnginesState(g,  g.getPlayers().get(currentPlayer)));
                        }
                    } else{
                        try {
                            if(power>g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalEnginePower(components, batteries)) {
                                this.power = g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalEnginePower(components, batteries);
                                loser = g.getPlayers().get(currentPlayer);
                            }
                            setCardState(g);
                        }catch (InvalidPositionException | InvalidParameterException |
                                BatteriesLessThenCannonException | RemoteException e){
                            System.out.println("Error" + e.getMessage());
                            new GameView(g, new Exception(e.getMessage() + "ACTIVATE ENGINES " + g.getPlayers().get(currentPlayer).getName()));
                            g.getPlayers().get(currentPlayer).setPlayerState(new ActivateEnginesState(g,  g.getPlayers().get(currentPlayer)));
                        }
                    }
                    break;
                case "LessCannonPower":
                    if (loser == null) {
                        try {
                            power = g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(components, batteries);
                            loser = g.getPlayers().get(currentPlayer);
                            setCardState(g);
                        }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException |
                               RemoteException e){
                            System.out.println("Error" + e.getMessage());
                            new GameView(g, new Exception(e.getMessage() + "ACTIVATE CANNONS " + g.getPlayers().get(currentPlayer).getName()));
                            g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g, g.getPlayers().get(currentPlayer)));
                        }

                    } else{
                        try {
                            if (power > g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(components, batteries)) {
                                power = g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(components, batteries);
                                loser = g.getPlayers().get(currentPlayer);
                            }
                            setCardState(g);
                        }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                            System.out.println("Error" + e.getMessage());
                            new GameView(g, new Exception(e.getMessage() + "ACTIVATE CANNONS " + g.getPlayers().get(currentPlayer).getName()));
                            g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g, g.getPlayers().get(currentPlayer)));
                        }
                    }
                    break;
            }
        }else {
            Player p;
            switch (penalties[pos]) {
                case "LoseDays":
                    loser.changePosition(-getLostDays() + g.getOccupiedPositions(loser, -getLostDays()));
                    g.adjustPlayerPositions();
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
                    new GameView(g, new Exception("REMOVE BEST GOODS " + p.getName()));
                    p.setPlayerState(new RemoveBestGoodsState(g, p));
                    break;
                case "cannonFire":
                    if (!protect) {
                        int i = 5;
                        if (components == null || batteries == null) {
                            checkLoser(g, i);
                        } else {
                            try {
                                if (loser.getPlayerShipBoard().shieldsNotProtects(cannonFireList.get(currentFire).direction(), components, batteries)) {
                                    new GameView(g, null);
                                    checkLoser(g, i);
                                }else
                                    new GameView(g, null);
                            } catch (InvalidPositionException | InvalidParameterException |
                                     BatteriesLessThenCannonException e) {
                                System.out.println("Error" + e.getMessage());
                                new GameView(g, new Exception(e.getMessage() + "ACTIVATE SHIELDS " + loser.getName()));
                                loser.setPlayerState(new ActivateShieldsState(g, loser));
                            }
                        }
                    }
                    protect=false;
                    setCardState(g);
                    break;
                case "LoseAstronauts":
                    pos++;
                    p = loser;
                    done=false;
                    loser=null;
                    new GameView(g, new Exception("REMOVE ASTRONAUTS " + p.getName()));
                    p.setPlayerState(new RemoveAstronautsState(g, p));
                    break;
            }
        }
    }

    private void checkLoser(Game g, int i) {
        Components wreck = null;
        Player p;
        Components c = loser.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).direction(), rowOrCol);
        try {
            loser.getPlayerShipBoard().removeComponent(c.getPosX(), c.getPosY());
            new GameView(g, null);
            while (wreck == null) {
                wreck = loser.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).direction(), i);
                i++;
            }

            if (!loser.getPlayerShipBoard().checkIfSplit(wreck.getPosX(), wreck.getPosY())) {
                if (!done) {
                    loser = null;
                    pos++;
                }
            } else {
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
        } catch (InvalidPositionException | AlreadyEmptyPositionException | RemoteException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    private void chooseRowOrCol(Game g) {
        boolean good = false;
        while(!good && currentFire<cannonFireList.size()) {
            if (cannonFireList.get(currentFire).direction() == Direction.NORTH || cannonFireList.get(currentFire).direction() == Direction.SOUTH) {
                rowOrCol= g.rollDice();
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
                rowOrCol= g.rollDice();
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
        if (currentFire >= cannonFireList.size()){
            fire = false;
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

    // for test

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

    public void setLoser(Player player){
        this.loser = loser;
    }

    public void setCurrentFire(int currentFire) {
        this.currentFire = currentFire;
    }

    public void setRowOrCol(int rowOrCol) {
        this.rowOrCol = rowOrCol;
    }
}
