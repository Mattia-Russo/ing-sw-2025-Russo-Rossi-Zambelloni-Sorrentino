package org.example.MessagePkg;

import org.example.ClientPkg.RMIClient;
import org.example.ServerPkg.ConnectionsPkg.RMIPkg.RMIClientInterface;
import org.example.ServerPkg.ConnectionsPkg.TCPPkg.ClientProxy;
import org.example.ServerPkg.ControllerPkg.GameController;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Message implements Serializable {
    private transient ClientProxy proxy;
    private transient RMIClientInterface client;
    private static final long serialVersionUID = 1L;

    public Message() {
        this.proxy = null;
        this.client = null;
    }

    public void setProxy(ClientProxy proxy) {
        this.proxy = proxy;
    }

    public ClientProxy getProxy(){
        return this.proxy;
    }

    public void setClient(RMIClient client) {
        this.client = client;
    }

    public RMIClientInterface getClient(){
        return this.client;
    }

    // Ogni sottoclasse dovrà implementare questo metodo
    public void handle(GameController controller, String playerName) throws RemoteException {}

    public boolean checkClient() throws RemoteException {
        if(this.proxy != null) {
            if(this.proxy.getPlayerName() == null) {
                System.out.println("You need to set your name first");
                return false;
            } else if (!proxy.getServer().getIfSubscribed(proxy)) {
                System.out.println("You are not subscribed to the server");
                return false;
            } else {
                return true;
            }
        } else if (this.client != null) {
            if(this.client.getPlayerName() == null) {
                System.out.println("You need to set your name first");
                return false;
            } else try {
                if (!client.getServer().getIfSubscribed(client)) {
                    System.out.println("You are not subscribed to the server");
                    return false;
                } else {
                    return true;
                }
            } catch (java.rmi.RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return false; // non dovremmo arrivare mai a questa istruzione
    }
}
