package org.example.ServerPkg.ConnectionsPkg.TCPPkg;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ConnectionsPkg.Handler;
import org.example.ServerPkg.ConnectionsPkg.Server;
import org.example.ServerPkg.ConnectionsPkg.Settings;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.NoSuchPlayerException;
import org.example.UIPkg.GameUpdater;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer implements Server {
    public ArrayList<ClientProxy> clientsProxies;
    private GameController controller;

    public TCPServer(GameController controller){
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
                clientsProxies.add(clientProxy);

                // Let the thread pool handle the communication with the client
                threadPool.submit(clientProxy);
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

    public boolean getIfSubscribed(Handler handler){
        return clientsProxies.contains((ClientProxy) handler);
    }

    public synchronized void subscribe(ClientProxy clientProxy) {
        controller.getNames().add(clientProxy.getPlayerName());
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

    public GameUpdater getGameUpdater(String name){
        return getClientProxy(name).getGameUpdater();
    }

    public void notifyClient(String name, String message) {
        getClientProxy(name).notifyClient(message);
    }

    @Override
    public void notifyLobbyCreated(String name) {
        getClientProxy(name).notifyLobbyCreated();
    }

    @Override
    public void notifyBroadcast(List<String> exclude, String message) {
        for(ClientProxy client : clientsProxies){
            if(!exclude.contains(client.getPlayerName()))
                client.notifyClient(message);
        }
    }

    @Override
    public void notifyLobbyJoined(String name) throws RemoteException {
        getClientProxy(name).notifyLobbyJoined();
    }

    @Override
    public void acceptCreateLobby(String name) throws RemoteException {
        getClientProxy(name).acceptCreateLobby();
    }

    public void updatePlayerList(String exclude){
        for(ClientProxy client : clientsProxies){
            if(!exclude.equals(client.getPlayerName())){
                Message message = client.getMsgGen().generate("update_names", controller.getNames());
                client.sendMessage(message);
            }
        }
    }
}
