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
        getGame().checkAllPlayersShip();
    }

    @Override
    public void AbandonGame(Player player){
        new GameView(getGame(), new InvalidMethodCallException("You have to fix your ship " + player.getName()));
    }

    @Override
    public void disconnect(Player p){
        // controllare la nave
        // eliminare eventuali componenti sbagliati
        for(int i = 0; i < p.getPlayerShipBoard().getComponentMatrix().length; i++){
            for(int j = 0; j < p.getPlayerShipBoard().getComponentMatrix()[i].length; j++){
                if (p.getPlayerShipBoard().validPosition(i,j) && p.getPlayerShipBoard().getComponentMatrix()[i][j] != null) {
                    Components c = p.getPlayerShipBoard().getComponentMatrix()[i][j];
                    // check connectors
                    for (int k = 0; k < 4; k++) {
                        switch ((c.getDirection().ordinal() + k) % 4) {
                            case 0:
                                if (p.getPlayerShipBoard().validPosition(i, j - 1) && p.getPlayerShipBoard().getComponentMatrix()[i][j-1]!=null) {
                                    if (p.getPlayerShipBoard().getComponentMatrix()[i][j - 1].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) == Connector.EMPTY) { //prende il connettore del componente di fianco che punta al componente che stiamo controllando
                                        p.getPlayerShipBoard().removeComponent(i, j);
                                    }
                                }
                                break;
                            case 1:
                                if (p.getPlayerShipBoard().validPosition(i + 1, j) && p.getPlayerShipBoard().getComponentMatrix()[i+1][j]!=null) {
                                    if (p.getPlayerShipBoard().getComponentMatrix()[i + 1][j].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) == Connector.EMPTY) {
                                        p.getPlayerShipBoard().removeComponent(i, j);
                                    }
                                    if (p.getPlayerShipBoard().getComponentMatrix()[i + 1][j].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) != Connector.UNIVERSAL
                                            && c.getDirConnector(Direction.values()[(c.getDirection().ordinal() + k) % 4]) != Connector.UNIVERSAL
                                            && p.getPlayerShipBoard().getComponentMatrix()[i + 1][j].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) !=
                                            c.getDirConnector(Direction.values()[(c.getDirection().ordinal() + k) % 4])) {
                                        p.getPlayerShipBoard().removeComponent(i, j);
                                    }
                                }
                                break;
                            case 2:
                                if (p.getPlayerShipBoard().validPosition(i, j + 1) && p.getPlayerShipBoard().getComponentMatrix()[i][j+1]!=null) {
                                    if (p.getPlayerShipBoard().getComponentMatrix()[i][j + 1].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) == Connector.EMPTY) {
                                        p.getPlayerShipBoard().removeComponent(i, j);
                                    }
                                    if (p.getPlayerShipBoard().getComponentMatrix()[i][j + 1].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) != Connector.UNIVERSAL
                                            && c.getDirConnector(Direction.values()[(c.getDirection().ordinal() + k) % 4]) != Connector.UNIVERSAL
                                            && p.getPlayerShipBoard().getComponentMatrix()[i][j + 1].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) !=
                                            c.getDirConnector(Direction.values()[(c.getDirection().ordinal() + k) % 4])) {
                                        p.getPlayerShipBoard().removeComponent(i, j);
                                    }
                                }
                                break;
                            case 3:
                                if (p.getPlayerShipBoard().validPosition(i - 1, j) && p.getPlayerShipBoard().getComponentMatrix()[i-1][j]!=null) {
                                    if (p.getPlayerShipBoard().getComponentMatrix()[i - 1][j].getDirConnector(Direction.values()[((c.getDirection().ordinal() + k + 2) % 4)]) == Connector.EMPTY) {
                                        p.getPlayerShipBoard().removeComponent(i, j);
                                    }
                                }
                        }
                    }
                    // check cannon
                    if(c.checkRightCannon(p.getPlayerShipBoard())){
                        p.getPlayerShipBoard().removeComponent(i, j);
                    }
                    if(c.checkRightEngine(p.getPlayerShipBoard())){
                        p.getPlayerShipBoard().removeComponent(i, j);
                    }
                }
            }
        }

        // controllare se è tutta intera
        Components c=null;
        int i=0;
        while(c==null && i < 5){
            c=p.getPlayerShipBoard().getFirstComponent(Direction.WEST, i);
            i++;
        }

        // gestire l'eventuale divisione
        if(p.getPlayerShipBoard().checkIfSplitted(c.getPosX(), c.getPosY())){
            p.getPlayerShipBoard().removeWreck(c.getPosY(), c.getPosX());
            new GameView(getGame(), null);
            if(getGame().getGameMode()==0) {
                p.setReadyForCards(true);
            }
        }
        getGame().disconnectPlayer(p);
        endFixShip(p);
    }
}
