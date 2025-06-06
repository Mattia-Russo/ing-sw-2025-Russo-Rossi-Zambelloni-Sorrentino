package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;

public class BuildShipMessage extends Message{
    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        Player player= controller.getGame().getPlayerByName(playerName);
        player.opShip();
        new GameView(controller.getGame(), null);
    }
}
