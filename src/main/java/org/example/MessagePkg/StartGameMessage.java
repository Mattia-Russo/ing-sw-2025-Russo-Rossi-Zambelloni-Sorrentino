package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.InvalidLobbyStateException;
import org.example.ServerPkg.Model.Exceptions.InvalidMinimumNumberPlayerException;

public class StartGameMessage extends Message {
    @Override
    public void handle(GameController controller, String playerName) {
        if(checkClient()){
            try{
                controller.startGame();
            }catch(InvalidMinimumNumberPlayerException | InvalidLobbyStateException e){
                System.out.println("ERROR " + e.getMessage());
            }
        }
    }
}
