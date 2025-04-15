package org.example.ServerPkg;

import org.example.ServerPkg.ControllerPkg.GameController;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SocketClientProxy extends ClientProxy implements Runnable {
    private String input;
    private GameController controller;
    private final Socket socket;
    private final ObjectOutputStream out;

    /**
     * This thread is necessary to handle server input asynchronously
     * This way answering to ping messages is immediate
     * Must be a single thread to avoid synchronization problems
     */
    private final ExecutorService inputHandler = Executors.newSingleThreadExecutor();

    //This thread periodically check the connection to the client
    private final ScheduledExecutorService connectionChecker = Executors.newSingleThreadScheduledExecutor();


    public SocketClientProxy(GameController controller, Server server, Socket socket) throws IOException {
        super(controller, server);
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());

        // Start checking connection
        connectionChecker.scheduleAtFixedRate(new ConnectionChecker(this.socket), 1, 4, TimeUnit.SECONDS);
    }

    public void handleInput(String input) {
        switch (input) {

        }
    }

    @Override
    public void run(){

    }


    //This class sends a heartbeat message to the client, if the client doesn't answer with the appropriate message,
    //the socket is closed.
    public class ConnectionChecker implements Runnable {
        private final Socket socket;

        public ConnectionChecker(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            while (true) {
                //input=;
                handleInput(input);
            }
        }
    }
}
