package org.example.MessagePkg;

import org.example.ServerPkg.ConnectionsPkg.Handler;
import org.example.ServerPkg.ConnectionsPkg.RMIPkg.RMIServer;
import org.example.ServerPkg.ConnectionsPkg.Server;
import org.example.ServerPkg.ConnectionsPkg.TCPPkg.ClientProxy;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.UIPkg.Client;

import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public class Message implements Serializable {
    private transient Handler handler;
    private transient Server server;
    private transient Client client;
    @Serial
    private static final long serialVersionUID = 1L;

    public Message() {
        this.handler = null;
        this.server = null;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Handler getHandler(){
        return this.handler;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Server getServer(){
        return this.server;
    }

    // Ogni sottoclasse dovrà implementare questo metodo
    public void handle(GameController controller, String playerName) throws RemoteException {}

    public boolean checkClient() throws RemoteException {
        if(this.getHandler() == null || this.handler.getPlayerName() == null) {
            System.out.println("You need to set your name first");
            return false;
        } else if (!server.getIfSubscribed(handler)) {
            System.out.println("You are not subscribed to the server");
            return false;
        } else {
            return true;
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }
}
