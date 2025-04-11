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

public class WarZone extends AdventureCard{
    private int numAstronauts;
    private int numGoods;
    private List<CannonFire> cannonFireList;
    private String[] penalties;
    private String[] criteria;
    private int pos;
    private int currentPlayer;
    private int currentFire;
    private int rowOrCol;
    private float power;
    private Player loser;
    private boolean done=false;
    private boolean play=false;
    private boolean protect=false;

    @JsonCreator
    public WarZone(
            @JsonProperty("cardLevel") int CardLevel,
            @JsonProperty("lostDays") int lostDays,
            @JsonProperty("numAstronauts") int numAstronauts,
            @JsonProperty("numGoods") int numGoods,
            @JsonProperty("cannonFiresList") List<CannonFire> CannonFireList,
            @JsonProperty("penalties") String[] penalties,
            @JsonProperty("criteria") String[] criteria) {
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
                                g.getPlayers().get(currentPlayer).setPlayerState(new ActivateEnginesState(g));
                            } else {
                                g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState());
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
                                g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g));
                            } else {
                                g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState());
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
                        }catch (InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                            System.out.println("Error" + e.getMessage());
                            g.getPlayers().get(currentPlayer).setPlayerState(new ActivateEnginesState(g));

                        }
                    } else{
                        try {
                            if(power>g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalEnginePower(components, batteries)) {
                                this.power = g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalEnginePower(components, batteries);
                                loser = g.getPlayers().get(currentPlayer);
                            }
                        }catch (InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                            System.out.println("Error" + e.getMessage());
                            g.getPlayers().get(currentPlayer).setPlayerState(new ActivateEnginesState(g));

                        }

                    }
                    break;
                case "LessCannonPower":
                    if (loser == null) {
                        try {
                            power = g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(components, batteries);
                            loser = g.getPlayers().get(currentPlayer);
                        }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                            System.out.println("Error" + e.getMessage());
                            g.getPlayers().get(currentPlayer).setPlayerState(new ActivateCannonsState(g));
                        }

                    } else{
                        try {
                            if (power > g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(components, batteries)) {
                                power = g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalCannonPower(components, batteries);
                                loser = g.getPlayers().get(currentPlayer);
                            }
                        }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
                            System.out.println("Error" + e.getMessage());
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
                    setCardState(g);
                    break;
                case "LoseGoods":
                    pos++;
                    p = loser;
                    done=false;
                    loser=null;
                    p.setPlayerState(new RemoveBestGoodsState(g));
                    break;
                case "cannonFire":
                    chooseRowOrCol(loser);
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
                                    while (wreck == null) {
                                        wreck = loser.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).getDirection(), i);
                                        i++;
                                    }

                                    if (!loser.getPlayerShipBoard().checkIfSplitted(wreck.getPosX(), wreck.getPosY())) {
                                        chooseRowOrCol(loser);
                                        if (!done) {
                                            loser = null;
                                            pos++;
                                        }
                                        setCardState(g);
                                    } else {
                                        chooseRowOrCol(loser);
                                        if (!done) {
                                            p = loser;
                                            loser = null;
                                            pos++;
                                            p.setPlayerState(new ShipWreckedState(g));
                                        } else
                                            loser.setPlayerState(new ShipWreckedState(g));
                                    }
                                } catch (InvalidPositionException | AlreadyEmptyPositionException e) {
                                    System.out.println("Error" + e.getMessage());
                                }
                            } else {
                                try {
                                    if (!loser.getPlayerShipBoard().ShieldProtects(cannonFireList.get(currentFire).getDirection(), components, batteries)) {
                                        Components c = loser.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).getDirection(), rowOrCol);
                                        try {
                                            loser.getPlayerShipBoard().removeComponent(c.getPosX(), c.getPosY());
                                            while (wreck == null) {
                                                wreck = loser.getPlayerShipBoard().getFirstComponent(cannonFireList.get(currentFire).getDirection(), i);
                                                i++;
                                            }

                                            if (!loser.getPlayerShipBoard().checkIfSplitted(wreck.getPosX(), wreck.getPosY())) {
                                                chooseRowOrCol(loser);
                                                if (!done) {
                                                    loser = null;
                                                    pos++;
                                                }
                                                setCardState(g);
                                            } else {
                                                chooseRowOrCol(loser);
                                                if (!done) {
                                                    p = loser;
                                                    loser = null;
                                                    pos++;
                                                    p.setPlayerState(new ShipWreckedState(g));
                                                } else
                                                    loser.setPlayerState(new ShipWreckedState(g));
                                            }
                                        } catch (InvalidPositionException | AlreadyEmptyPositionException e) {
                                            System.out.println("Error" + e.getMessage());
                                        }
                                    }
                                } catch (InvalidPositionException | InvalidParameterException |
                                         BatteriesLessThenCannonException e) {
                                    System.out.println("Error" + e.getMessage());
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
                    p.setPlayerState(new RemoveAstronautsState(g));
                    break;
            }
        }
    }

    private void chooseRowOrCol(Player p) {
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
