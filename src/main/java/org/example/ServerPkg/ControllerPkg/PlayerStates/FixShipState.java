package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.ComponentsPkg.Connector;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.Exceptions.AlreadyEmptyPositionException;
import org.example.ServerPkg.Model.Exceptions.InvalidMethodCallException;
import org.example.ServerPkg.Model.Exceptions.InvalidPositionException;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;

public class FixShipState extends PlayerState implements Serializable {
    public FixShipState(Game game) {
        super(game);
    }

    @Override
    public void removeTile(Points point, Player player){
        try {
            player.getPlayerShipBoard().removeComponent(point.getX(), point.getY());
            new GameView(getGame(), null);
        } catch(InvalidPositionException | AlreadyEmptyPositionException e) {
            Exception e1 = new Exception(e.getMessage() + " " + player.getName());
            new GameView(getGame(), e1);
        }
    }

    @Override
    public void endFixShip(Player player){
        player.setShipOK(true);
        if(!player.isAbandoned()) {
            player.setPlayerState(new WaitingState(getGame()));
        }
        getGame().checkAllPlayersShip();
    }

    @Override
    public void AbandonGame(Player player){
        new GameView(getGame(), new InvalidMethodCallException( player.getName() + ", YOU HAVE TO FIX YOUR SHIP"));
    }

    @Override
    public void disconnect(Player p){
        for(int i = 5; i < p.getPlayerShipBoard().getComponentMatrix().length+5; i++){
            for(int j = 4; j < p.getPlayerShipBoard().getComponentMatrix()[0].length+4; j++){
                if (p.getPlayerShipBoard().validPosition(i,j) && p.getPlayerShipBoard().getComponent(i,j) != null) {
                    Components c = p.getPlayerShipBoard().getComponent(i, j);
                    // check connectors
                    for (int k = 0; k < 4; k++) {
                        switch ((c.getDirection().ordinal() + k) % 4) {
                            case 0:
                                if (p.getPlayerShipBoard().validPosition(i, j - 1) && p.getPlayerShipBoard().getComponent(i, j-1)!=null) {
                                    if (p.getPlayerShipBoard().getComponent(i, j - 1).getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) == Connector.EMPTY) { //prende il connettore del componente di fianco che punta al componente che stiamo controllando
                                        p.getPlayerShipBoard().removeComponent(j, i);
                                    }
                                }
                                break;
                            case 1:
                                if (p.getPlayerShipBoard().validPosition(i + 1, j) && p.getPlayerShipBoard().getComponent(i+1, j) !=null) {
                                    if (p.getPlayerShipBoard().getComponent(i + 1, j).getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) == Connector.EMPTY) {
                                        p.getPlayerShipBoard().removeComponent(j, i);
                                    }
                                    if (p.getPlayerShipBoard().getComponent(i + 1, j).getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) != Connector.UNIVERSAL
                                            && c.getDirConnector(Direction.values()[(c.getDirection().ordinal() + k) % 4]) != Connector.UNIVERSAL
                                            && p.getPlayerShipBoard().getComponent(i + 1, j).getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) !=
                                            c.getDirConnector(Direction.values()[(c.getDirection().ordinal() + k) % 4])) {
                                        p.getPlayerShipBoard().removeComponent(j, i);
                                    }
                                }
                                break;
                            case 2:
                                if (p.getPlayerShipBoard().validPosition(i, j + 1) && p.getPlayerShipBoard().getComponent(i, j+1)!=null) {
                                    if (p.getPlayerShipBoard().getComponent(i, j+1).getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) == Connector.EMPTY) {
                                        p.getPlayerShipBoard().removeComponent(j, i);
                                    }
                                    if (p.getPlayerShipBoard().getComponent(i, j+1).getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) != Connector.UNIVERSAL
                                            && c.getDirConnector(Direction.values()[(c.getDirection().ordinal() + k) % 4]) != Connector.UNIVERSAL
                                            && p.getPlayerShipBoard().getComponent(i, j+1).getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) !=
                                            c.getDirConnector(Direction.values()[(c.getDirection().ordinal() + k) % 4])) {
                                        p.getPlayerShipBoard().removeComponent(j, i);
                                    }
                                }
                                break;
                            case 3:
                                if (p.getPlayerShipBoard().validPosition(i - 1, j) && p.getPlayerShipBoard().getComponent(i-1, j)!=null) {
                                    if (p.getPlayerShipBoard().getComponent(i-1, j).getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) == Connector.EMPTY) {
                                        p.getPlayerShipBoard().removeComponent(j, i);
                                    }
                                }
                        }
                    }
                    // check cannon
                    if(c.checkRightCannon(p.getPlayerShipBoard())){
                        p.getPlayerShipBoard().removeComponent(j, i);
                    }
                    if(c.checkRightEngine(p.getPlayerShipBoard())){
                        p.getPlayerShipBoard().removeComponent(j, i);
                    }
                }
            }
        }

        // controllare se è tutta intera
        Components c=null;
        int i=5;
        while(c==null && i < 10){
            c=p.getPlayerShipBoard().getFirstComponent(Direction.WEST, i);
            i++;
        }

        // gestire l'eventuale divisione
        assert c != null;
        if(p.getPlayerShipBoard().checkIfSplit(c.getPosX(), c.getPosY())){
            p.getPlayerShipBoard().removeWreck(c.getPosX(), c.getPosY());
            new GameView(getGame(), null);
            if(getGame().getGameMode()==0) {
                p.setReadyForCards(true);
            }
        }
        getGame().disconnectPlayer(p);
        p.abandon(getGame());
        endFixShip(p);
    }
}
