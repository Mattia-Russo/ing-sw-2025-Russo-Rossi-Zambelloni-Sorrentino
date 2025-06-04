package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

import java.rmi.RemoteException;

/**
 * Messaggio utilizzato per richiedere al server l'ID di un componente
 * associato a una posizione (x, y) specifica per un determinato giocatore.
 */
public class RequestComponentByPositionMessage extends Message {
    private final String playerName;
    private final int x;
    private final int y;

    public RequestComponentByPositionMessage(String playerName, int x, int y) {
        this.playerName = playerName;
        this.x = x;
        this.y = y;
    }

    @Override
    public void handle(GameController controller, String senderPlayerName) throws RemoteException {
        try {
            // Ottieni l'ID del componente dalla posizione (x, y)
            int componentId = controller.getComponentByPosition(playerName, x, y);

            // Recupera il client associato e invia il messaggio di risposta
            controller.getClient(playerName).sendMessage(
                    new NotifyClientMessage(String.valueOf(componentId))
            );

            System.out.println("Inviato ID del componente (" + componentId + ") al giocatore: " + playerName);
        } catch (Exception e) {
            System.err.println("Errore nella gestione della richiesta per il componente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
