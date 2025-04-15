package org.example.ServerPkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class SocketClientProxy extends ClientProxy implements Runnable {
    private String input;
    private String playerName;
    private GameController controller;

    public SocketClientProxy(String name, GameController controller) {
        super(name, controller);
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
