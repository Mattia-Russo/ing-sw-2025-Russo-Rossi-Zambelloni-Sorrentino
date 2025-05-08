package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.AbandonedStateException;
import org.example.ServerPkg.Model.Exceptions.AlreadyShieldException;
import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Exceptions.WaitingStateException;
import org.example.ServerPkg.Model.Points;

import java.util.ArrayList;

public class ActivateShieldsMessage extends Message {
    private ArrayList<Points> shields;

    public ActivateShieldsMessage(ArrayList<Points> shields){
        this.shields=shields;
    }

    @Override
    public void handle(GameController controller, String playerName){
        if(checkClient()){
            try{
                getClient().getServer().getController().getGame().getPlayerByName(getClient().getPlayerName()).getState().activateShields(shields);
            } catch (AlreadyShieldException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
