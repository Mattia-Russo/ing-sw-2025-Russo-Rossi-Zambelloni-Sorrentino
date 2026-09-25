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
    private final GameController controller;

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

        System.out.println("TCP Socket Server is running on " + Settings.TCP_PORT + " port");

        while (true) {
            try {
                final Socket socket = serverSocket.accept();
                System.out.println("New socket connection!");
                SocketClientProxy clientProxy = new SocketClientProxy(controller, this, socket);
                clientsProxies.add(clientProxy);
                threadPool.submit(clientProxy);
            } catch (IOException ignored) {
                System.out.println("ERROR");
                break;
            }
        }

        System.out.println("Shutting down thread pool");
        threadPool.shutdown();
    }

    public boolean getIfSubscribed(Handler handler){
        return clientsProxies.contains((ClientProxy) handler);
    }

    public synchronized void subscribe(ClientProxy clientProxy) {
        System.out.println(clientProxy.getPlayerName() + " subscribed");
    }

    public synchronized void unsubscribe(ClientProxy clientProxy) {
        if (clientsProxies.remove(clientProxy)) {
            System.out.println(clientProxy.getPlayerName() + " unsubscribed");
        }
    }

    @Override
    public Handler getHandlerByName(String name){
        return getClientProxy(name);
    }

    private ClientProxy getClientProxy(String playerName){
        for (ClientProxy clientProxy : clientsProxies) {
            String name = clientProxy.getPlayerName();
            if(name != null && name.equals(playerName)){
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
    public void notifyLobbyJoined(String name){
        getClientProxy(name).notifyLobbyJoined();
    }

    @Override
    public void acceptCreateLobby(String name) {
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

    @Override
    public void notifyGameStarted(){
        for(ClientProxy client : clientsProxies){
            client.notifyGameStarted();
        }
    }

    @Override
    public void notifyGameEnded() throws RemoteException {
        for(ClientProxy client : clientsProxies){
            client.notifyGameEnded();
        }
    }
}
