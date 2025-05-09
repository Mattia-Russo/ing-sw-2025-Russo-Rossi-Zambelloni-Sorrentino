// cd target/classes
//java org.example.ServerPkg.ServerMain

package org.example.ServerPkg;

import org.example.ServerPkg.ConnectionsPkg.RMIPkg.RMIServer;
import org.example.ServerPkg.ConnectionsPkg.TCPPkg.TCPServer;
import org.example.ServerPkg.ControllerPkg.GameController;

public class ServerMain {
    public static void main(String[] args) {

        GameController gameController = new GameController();

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
}