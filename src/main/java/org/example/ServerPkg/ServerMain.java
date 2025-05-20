// cd target/classes
// java org.example.ServerPkg.ServerMain

package org.example.ServerPkg;

import org.example.ServerPkg.ConnectionsPkg.RMIPkg.RMIServer;
import org.example.ServerPkg.ConnectionsPkg.TCPPkg.TCPServer;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ServerMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        GameController gameController = new GameController();
        if(args.length != 0) {
            Game game = loadGame(args[0]);
            gameController.setGame(game);
        }

        TCPServer TCPServer = new TCPServer(gameController);
        Thread TCPServerThread = new Thread(() -> {
            System.out.println("Starting server TCP...");
            TCPServer.startSocket();
        });
        TCPServerThread.start();

        try {
            RMIServer RMIServer = new RMIServer(gameController);
            Thread RMIServerThread = new Thread(() -> {
                System.out.println("Starting server RMI...");
                RMIServer.startRMIServer();
            });
            RMIServerThread.start();
        } catch (Exception e) {
            System.err.println("Error starting RMI server: " + e.getMessage());
        }
    }


    public static Game loadGame(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        Game game = (Game) objectIn.readObject();
        objectIn.close();
        fileIn.close();
        return game;
    }
}