package org.example.ServerPkg.Model.CardPkg;

import org.example.ServerPkg.ControllerPkg.PlayerStates.ActivateCannonsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ActivateEnginesState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
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

public class OpenSpace extends AdventureCard implements Serializable {
    private int currentPlayer;
    private final int id;


    public OpenSpace(int id,int CardLevel,int lostDays){
        super(CardLevel, lostDays);
        this.currentPlayer = -1;
        this.id = id;
    }

    @Override
    public AdventureCardView createView(){
        String command = """
               You are playing the open space card, you can type:
               activate_engines x y -> x,y are the coordinates of a shield, you should write a number of x,y based on the number of shields you want to activate
               use_batteries x y -> x,y are the coordinates of the battery storage, you should write a number of x,y based on the number of batteries you want to use
           
               end_activate_engines -> if you want to end the engine activation phase
               """;
        return new AdventureCardView(command, id, "OpenSpace", getLostDays(),0 , 0,0, null, null, null, null, 0,null,null);
    }

    @Override
    public void setCardState(Game g) {
        if(currentPlayer==-1 && g.getGameMode()==1) {
           checkEnginePower(g.getPlayers(), g);
        }

        do {
            currentPlayer++;
        } while (currentPlayer < g.getPlayers().size() && g.getPlayers().get(currentPlayer).isAbandoned());

        if(currentPlayer==g.getPlayers().size()) {
            g.Turn();
        }else {
            if (g.getPlayers().get(currentPlayer).getPlayerShipBoard().getNumDoubleEngines() != 0) {
                new GameView(g, new Exception("ACTIVATE ENGINES " + g.getPlayers().get(currentPlayer).getName()));
                g.getPlayers().get(currentPlayer).setPlayerState(new ActivateEnginesState(g));
            }else {
                g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState(g));
                this.playCard(g, null, null);
            }
        }
    }

    @Override
    public void playCard(Game g, ArrayList<Points> engines, ArrayList<Points> batteries) {
        try {
            g.getPlayers().get(currentPlayer).changePosition(g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalEnginePower(engines, batteries) + + g.getOccupiedPositions(g.getPlayers().get(currentPlayer), g.getPlayers().get(currentPlayer).getPlayerShipBoard().getTotalEnginePower(engines, batteries)));
            g.adjustPlayerPositions();
            g.getPlayers().get(currentPlayer).setPlayerState(new WaitingState(g));
            new GameView(g, null);
            setCardState(g);
        }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
            System.out.println("Error" + e.getMessage());
            new GameView(g, new Exception(e.getMessage() + "ACTIVATE ENGINES " + g.getPlayers().get(currentPlayer).getName()));
            g.getPlayers().get(currentPlayer).setPlayerState(new ActivateEnginesState(g));
        }
    }

    private void checkEnginePower(ArrayList<Player> players, Game g){
        for (Player p : players) {
            if (!p.isAbandoned() && ((p.getPlayerShipBoard().getSingleEnginePower() == 0 && (p.getPlayerShipBoard().getNumDoubleEngines()==0 || p.getPlayerShipBoard().getTotalBattery() == 0)))){
                p.abandon(g);
            }
        }
    }
}
