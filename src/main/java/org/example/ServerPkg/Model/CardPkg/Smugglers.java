package org.example.ServerPkg.Model.CardPkg;


import org.example.ServerPkg.ControllerPkg.PlayerStates.*;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.Exceptions.BatteriesLessThenCannonException;
import org.example.ServerPkg.Model.Exceptions.InvalidPositionException;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.ForView.GoodsView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Smugglers extends Enemy implements Serializable {
    private final List<Goods> goodsWinList;
    private final int numGoodsLose;
    private int playersIndex;
    private boolean accept;
    private final int id;

    public Smugglers(int id,int cardLevel, int lostDays, int cannonPower,int goodsLose,List<Goods> goodsWinList) {
        super(cardLevel,  lostDays, cannonPower);
        this.numGoodsLose = goodsLose;
        this.goodsWinList=goodsWinList;
        this.playersIndex = -1;
        this.accept=false;
        this.id = id;
    }

    @Override
    public AdventureCardView createView(){
        List<GoodsView> goods = new ArrayList<>();
        for(Goods good : goodsWinList){
            goods.add(new GoodsView(good));
        }
        String command = """
               You are playing the smugglers card, you can type:
               activate_cannons x y -> x,y are the coordinates of a cannon, you should write a number of x,y based on the number of cannons you want to activate
               use_batteries x y -> x,y are the coordinates of the battery storage, you should write a number of x,y based on the number of batteries you want to use
               end_activate_cannons -> if you want to end the cannon activation phase
              
               accept_reward true/false -> true if you want to accept the reward, false otherwise
               add_good x y numGood -> x,y are the coordinates of the storage where you want to add the good, numGood is the number of goods you want to add
               remove_good x y numGood -> x,y are the coordinates of the storage where you want to remove the good, numGood is the number of goods you want to remove""\";
              
               remove_best_good x y numGood -> x,y are the coordinates of the storage where you want to remove the good, numGood is the number of goods you want to remove
            
              """;
        return new AdventureCardView(command, id, "Smugglers", getLostDays(),0 , 0,getCannonPower(), null, null, goods, null, numGoodsLose,null,null);
    }

    public int getCannonPower() {
        return super.getCannonPower();
    }

    public int getCardLevel() {
        return super.getCardLevel();
    }

    public int getLostDays() {return super.getLostDays();}

    public List<Goods> getGoodsWin() {
        return goodsWinList;
    }

    public int getGoodsLost() {
        return numGoodsLose;
    }

    @Override
    public void setCardState(Game game) {
        do {
            playersIndex++;
        } while (playersIndex < game.getPlayers().size() && game.getPlayers().get(playersIndex).isAbandoned());

        if(playersIndex == game.getPlayers().size()){
            game.Turn();
        } else {
            new GameView(game, new Exception("ACTIVATE CANNONS " + game.getPlayers().get(playersIndex).getName()));
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
            } else if (power == this.getCannonPower()) {
                this.setCardState(game);
            } else {
                new GameView(game, new Exception("REMOVE GOODS  " + game.getPlayers().get(playersIndex).getName()));
                game.getPlayers().get(playersIndex).setPlayerState(new RemoveBestGoodsState(game));
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
            new GameView(game, new Exception("CHANGE GOODS " + game.getPlayers().get(playersIndex).getName()));
            game.getPlayers().get(playersIndex).setPlayerState(new ChangeGoodsState(game));
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

    @Override
    public int getNumGoodsLose(){
        return this.numGoodsLose;
    }
}
