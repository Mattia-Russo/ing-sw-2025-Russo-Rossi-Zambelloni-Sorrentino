package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.MessagePkg.NotifyLandOnPlanetMessage;
import org.example.ServerPkg.ConnectionsPkg.Handler;
import org.example.ServerPkg.Model.Exceptions.PlanetAlreadyVisitedException;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.io.Serializable;
import java.rmi.RemoteException;

public class LandOnPlanetsState extends PlayerState implements Serializable {
    public LandOnPlanetsState(Game game, Player player) throws RemoteException {
        super(game, player);
        game.getController().getNameServerMap().get(getPlayer().getName()).getHandlerByName(getPlayer().getName()).goToLandOnPlanet();
    }

    @Override
    public void landOnPlanet(boolean landed, int numPlanet, Player player) throws RemoteException {
        if(!player.isAbandoned()) {
            player.setPlayerState(new WaitingState(getGame(), player));
        }

        if(landed){
            if(getGame().getCurrentCard().getPlanetsList().get(numPlanet).isVisited()){
                new GameView(getGame(), new PlanetAlreadyVisitedException("Planet " + numPlanet + " already visited, choose another one " + player.getName()));
            } else {
                getGame().getCurrentCard().playCard(getGame(), numPlanet);
            }
        } else {
            getGame().getCurrentCard().setCardState(getGame());
        }
    }

    @Override
    public void AbandonGame(Player player) throws RemoteException {
        player.abandon(getGame());
        landOnPlanet(false, 0, player);
    }
  
    @Override
    public void disconnect(Player disconnectingPlayer) throws RemoteException {
        getGame().disconnectPlayer(disconnectingPlayer);
        disconnectingPlayer.abandon(getGame());
        landOnPlanet(false, 0, disconnectingPlayer);
    }

}
