package org.example.ServerPkg.Model.CardPkg;

import org.example.ServerPkg.ControllerPkg.PlayerStates.*;
import org.example.ServerPkg.Model.Exceptions.BatteriesLessThenCannonException;
import org.example.ServerPkg.Model.Exceptions.InvalidPositionException;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Slavers extends Enemy implements Serializable {
    private final int numAstronauts;
    private final int credits;
    private int playersIndex;
    private boolean accept;
    private final int id;

    public Slavers(int id,int cardLevel,int lostDays, int cannonPower,int numAstronauts,int credits) {
        super(cardLevel, lostDays, cannonPower);
        this.numAstronauts = numAstronauts;
        this.credits = credits;
        this.playersIndex = -1;
        this.accept=false;
        this.id = id;
    }

    @Override
    public AdventureCardView createView(){
        String command = """
               You are playing the slavers card, you can type:
               activate_cannons x y -> x,y are the coordinates of a cannon, you should write a number of x,y based on the number of cannons you want to activate
               use_batteries x y -> x,y are the coordinates of the battery storage, you should write a number of x,y based on the number of batteries you want to use
               end_activate_cannons -> if you want to end the cannon activation phase
              
               remove_astronauts x y -> x,y are the coordinates of the cabin where you want to remove the astronauts
               accept_reward true/false -> true if you want to accept the reward, false otherwise
              """;
        return new AdventureCardView(command, id, "Slavers", getLostDays(),credits , numAstronauts,getCannonPower(), null, null, null, null, 0,null,null);
    }

    public int getCannonPower() {
        return super.getCannonPower();
    }

    public int getCardLevel() {
        return super.getCardLevel();
    }

    public int getLostDays() {return super.getLostDays();}

    public int getNumAstronauts() {
        return numAstronauts;
    }

    public int getCredits() {
        return credits;
    }

    @Override
    public void setCardState(Game game) {
        do {
            playersIndex++;
        } while (playersIndex < game.getPlayers().size() && game.getPlayers().get(playersIndex).isAbandoned());

        if(playersIndex == game.getPlayers().size()){
            game.Turn();
        } else {
            new GameView(game, new Exception("ACTIVATE CANNON  " + game.getPlayers().get(playersIndex).getName()));
            game.getPlayers().get(playersIndex).setPlayerState(new ActivateCannonsState(game));
        }

        for (int i = playersIndex + 1; i < game.getPlayers().size(); i++){
            if (!game.getPlayers().get(i).isAbandoned()){
                game.getPlayers().get(i).setPlayerState(new WaitingState(game));
            }
        }
    }

    @Override
    public void playCard(Game game, ArrayList<Points> cannons, ArrayList<Points> batteries) {
        try {
            float power = game.getPlayers().get(playersIndex).getPlayerShipBoard().getTotalCannonPower(cannons, batteries);
            new GameView(game, null);
            if ( power > this.getCannonPower()) {
                new GameView(game, new Exception("WIN ENEMY  " + game.getPlayers().get(playersIndex).getName()));
                game.getPlayers().get(playersIndex).setPlayerState(new WinEnemyState(game));
            }else if(power == this.getCannonPower()){
                this.setCardState(game);
            } else {
                new GameView(game, new Exception("REMOVE ASTRONAUTS  " + game.getPlayers().get(playersIndex).getName()));
                game.getPlayers().get(playersIndex).setPlayerState(new RemoveAstronautsState(game));
            }
        }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
            System.out.println("Error" + e.getMessage());
            new GameView(game, new Exception(e.getMessage() + "ACTIVATE CANNONS " + game.getPlayers().get(playersIndex).getName()));
            game.getPlayers().get(playersIndex).setPlayerState(new ActivateCannonsState(game));
        }
    }

    @Override
    public void playCard(Game game){
        if (accept){
            game.getPlayers().get(playersIndex).changeCredits(getCredits());
            game.getPlayers().get(playersIndex).changePosition(-getLostDays());
            new GameView(game, null);
        } else {
            this.playCard(game, 0);
        }
    }

    @Override
    public void playCard(Game game, int ignore){
        game.getPlayers().get(playersIndex).setPlayerState(new WaitingState(game));
        playersIndex = -1;
        game.Turn();
    }

    @Override
    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    //usage only in test
    public boolean getAccept(){
        return accept;
    }

}
