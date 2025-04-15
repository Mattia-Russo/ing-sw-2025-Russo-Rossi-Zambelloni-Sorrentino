package org.example.ServerPkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SocketClientProxy extends ClientProxy implements Runnable {
    private final Socket socket;
    private final ObjectOutputStream out;
    protected String lastHeartBeat = "iniziale"; //stringa che serve a stabilire se la connessione è ancora attiva

    //This thread is necessary to handle server input asynchronously
    //This way answering to ping messages is immediate
    //Must be a single thread to avoid synchronization problems
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

    private void send(Message message){
        synchronized(this.out){
            try {
                out.reset();
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                System.out.println("Error sending message");
            }
        }
    }

    @Override
    public void run() {
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            // Should not happen
            System.out.println("Error getting input stream, returning");
            return;
        }
        while (true) {
            try {
                Message message = (Message) in.readObject();

                // Handle heartbeat messages right away
                if (message.title().equals("pong"))
                    lastHeartBeat = (String) message.arguments().get("key");
                else if (message.title().equals("ping"))
                    send(new Message("pong", Map.of("key", message.arguments().get("key"))));
                else
                    inputHandler.submit(() -> handleInput(message));
            } catch (Exception e) {
                System.out.println("Error reading from socket: " + e.getMessage());
                break;
            }
        }
        try {
            socket.close();
        } catch (IOException ignored) {
        }
        inputHandler.shutdown();
        connectionChecker.shutdown();
        //disconnect();
    }

        public void handleInput(Message message) {
        switch (message.title()) {
            case "createLobby":
                super.createLobby((int) message.arguments().get("numPlayers"),
                        (int) message.arguments().get("shipboardLevel"),
                        (int) message.arguments().get("gameMode"));
                break;
            case "joinLobby":
                    super.joinLobby();
                    break;
            case "startGame":
                super.startGame();
                break;
            case "exitGame":
                    super.exitGame();
                    break;
            case "activateCannons":
                super.activateCannons((ArrayList<Points>) message.arguments().get("points"));
                break;
            case "useBatteries":
                super.useBatteries((ArrayList<Points>) message.arguments().get("points"));
                break;
            case "endActivateCannons":
                super.endActivateCannons();
                break;
            case "activateEngines":
                super.activateEngines((ArrayList<Points>) message.arguments().get("points"));
                break;
            case "endActivateEngines":
                super.endActivateEngines();
                break;
            case "activateShileds":
                super.activateShields((ArrayList<Points>) message.arguments().get("points"));
                break;
            case "endActivateShileds":
                super.endActivateShields();
                break;
            case "removeGood":
                super.removeGood((Points) message.arguments().get("point"), (int) message.arguments().get("numGood"));
                break;
            case "addGood":
                super.addGood((Points) message.arguments().get("point"), (int) message.arguments().get("numGood"));
                break;
            case "endChangeGoodsState":
                super.endChangeGoodsState();
                break;
            case "landOnAbandon":
                super.landOnAbandon((boolean) message.arguments().get("bool"));
                break;
            case "landOnPlanet":
                super.landOnPlanet((boolean) message.arguments().get("bool"), (int) message.arguments().get("numPlanet"));
                break;
            case "removeAstronauts":
                super.removeAstronauts((Points) message.arguments().get("point"));
                break;
            case "endRemoveAstronauts":
                super.endRemoveAstronauts();
                break;
            case "removeBestGood":
                super.removeBestGood((Points) message.arguments().get("point"), (int) message.arguments().get("numGood"));
                break;
        }


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
            // Generate a random string
            String heartBeat = UUID.randomUUID().toString();

            // Send the string to the server
            send(new Message("ping", Map.of("key", heartBeat)));

            // Wait for 1 seconds
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {}

            // If the string did not come back, close the socket
            if(!heartBeat.equals(lastHeartBeat)) {
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }
    }

    public void updateGame(Game game) {
        //TBD
    }
}
