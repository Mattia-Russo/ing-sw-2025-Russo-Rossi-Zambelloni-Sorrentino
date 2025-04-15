package org.example.ServerPkg;

import org.example.ServerPkg.ControllerPkg.GameController;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientProxy extends ClientProxy implements Runnable {
    private String input;
    private GameController controller;
    private final Socket socket;
    private final ObjectOutputStream out;

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
