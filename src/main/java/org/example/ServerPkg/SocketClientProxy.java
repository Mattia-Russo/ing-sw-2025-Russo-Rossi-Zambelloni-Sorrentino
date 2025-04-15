package org.example.ServerPkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class SocketClientProxy extends ClientProxy implements Runnable {
    private String input;
    private int playerID;
    private GameController controller;

    public SocketClientProxy(int playerId, GameController controller) {
        super(playerId, controller);
    }

    @Override
    public void run() {
        while(true){
            //input=;
            handleInput(input);
        }
    }

    public void handleInput(String input){
        switch(input) {

        }
    }
}
