package org.example.Server.Model.CardPack;

import org.example.Server.Controller.States.ActivateCannonsState;
import org.example.Server.Controller.States.ChangeGoodsState;
import org.example.Server.Controller.States.LandState;
import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.ComponentsPack.Goods;
import org.example.Server.Model.Game;
import org.example.Server.Model.Points;

import java.util.ArrayList;
import java.util.List;

public class Smugglers extends Enemy{
    private List<Goods> goodsWinList = new ArrayList<Goods>();
    private int goodsLose;
    private int playersIndex;

    public Smugglers(int cardLevel, int lostDays, int cannonPower, int goodsLose, List<Goods> goodsWinList) {
        super(cardLevel,  lostDays, cannonPower);
        this.goodsLose = goodsLose;
        this.goodsWinList=goodsWinList;
        this.playersIndex = 0;
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
        return goodsLose;
    }

    @Override
    public void setCardState(Game game) {
        while (playersIndex < game.getPlayers().size() && !game.getPlayers().get(playersIndex).isAbandoned()){
            playersIndex++;
        }

        game.getPlayers().get(playersIndex).setPlayerState(new ActivateCannonsState(game));

        for (int i = playersIndex + 1; i < game.getPlayers().size(); i++){
            if (!game.getPlayers().get(i).isAbandoned()){
                game.getPlayers().get(i).setPlayerState(new WaitingState());
            }
        }
        playersIndex++;
    }

    @Override
    public void playCard(Game game, ArrayList<Points> cannons, ArrayList<Points> batteries) {
        if (game.getPlayers().get(playersIndex).getPlayerShipBoard().getTotalCannonPower(cannons, batteries) > this.getCannonPower()) {
            game.getPlayers().get(playersIndex).setPlayerState(new ChangeGoodsState(game));
        }else if(game.getPlayers().get(playersIndex).getPlayerShipBoard().getTotalCannonPower(cannons, batteries) == this.getCannonPower()){

        }else{

        }
    }
    //CONTROLLER CALCOLA POTENZA DI FUOCO USANDO UN METODO SUL MODEL , CHIAMA GETCANNONPOWER,
    // CONFRONTA POI O CHIAMA GOODSWIN O LOSE E CAMBIA LE RISORSE
}
