package org.example.ServerPkg.ConnectionsPkg.TCPPkg;

import org.example.ServerPkg.ConnectionsPkg.Settings;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.NoSuchPlayerException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {
    public ArrayList<ClientProxy> clientsProxies;
    private GameController controller;

    public TCPServer(GameController controller) {
        this.clientsProxies = new ArrayList<>();
        this.controller = controller;
    }

    public void startSocket() {
        ServerSocket serverSocket;
        // Define a fixed pool of threads to handle clientsProxies connections
        final ExecutorService threadPool = Executors.newFixedThreadPool(8);

        // Create the server socket to accept clientsProxies connections
        try {
            serverSocket = new ServerSocket(Settings.TCP_PORT);
        } catch (IOException e) {
            System.out.println("Failed to start socket server");
            return;
        }

        System.out.println("Socket Server ready on port: " + serverSocket.getLocalPort());

        // Keep accepting connections
        while (true) {
            try {
                final Socket socket = serverSocket.accept();
                System.out.println("New socket connection!");
                SocketClientProxy clientProxy = new SocketClientProxy(controller, this, socket);
                subscribe(clientProxy);

                // Let the thread pool handle the communication with the client
                threadPool.submit(new SocketClientProxy(controller,this, socket));
            } catch (IOException ignored) {
                System.out.println("ERROR");
                break;
            }
        }

        System.out.println("Shutting down thread pool");
        threadPool.shutdown();
    }

    public synchronized List<String> getNames(){
        return clientsProxies.stream().map(ClientProxy::getPlayerName).toList();
    }

    public boolean getIfSubscribed(ClientProxy proxy){
        return clientsProxies.contains(proxy);
    }

    public synchronized void subscribe(ClientProxy clientProxy) {
        System.out.println(clientProxy.getPlayerName() + " subscribed");
        clientsProxies.add(clientProxy);
    }

    public synchronized void unsubscribe(ClientProxy clientProxy) {
        if (clientsProxies.remove(clientProxy)) {
            System.out.println(clientProxy.getPlayerName() + " unsubscribed");
        }
    }

    private ClientProxy getClientProxy(String playerName){
        for (ClientProxy clientProxy : clientsProxies) {
            if(clientProxy.getPlayerName().equals(playerName)){
                return clientProxy;
            }
        }
        throw new NoSuchPlayerException("Player " + playerName + " not exists");
    }
}
