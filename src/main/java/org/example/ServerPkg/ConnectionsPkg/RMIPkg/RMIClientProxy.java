package org.example.ServerPkg.ConnectionsPkg.RMIPkg;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ConnectionsPkg.ClientProxy;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Game;

import java.rmi.RemoteException;

public class RMIClientProxy extends ClientProxy {
    private final RMIClientInterface client;

    public RMIClientProxy(GameController controller, RMIServer server, RMIClientInterface client) {
        super(controller, null, server);
        this.client = client;
    }

    public void sendMessage(Message message) {
        try {
            client.receiveMessage(message);
        } catch (RemoteException e) {
            System.err.println("Errore nell'invio del messaggio al client RMI: " + e.getMessage());
        }
    }

    public void updateGame(Game game) {
        //TBD
    }

    public void disconnect() {
        // TBD
    }

    public void sendMessage() {
        // TBD
    }
}