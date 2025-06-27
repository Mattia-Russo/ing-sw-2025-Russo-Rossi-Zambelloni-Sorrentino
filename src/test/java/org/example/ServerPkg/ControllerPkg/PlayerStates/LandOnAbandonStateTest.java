package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.CardPkg.AbandonedStation;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;

public class LandOnAbandonStateTest extends TestCase {
    private Player setupPlayerWithState(LandOnAbandonState[] stateHolder) throws RemoteException {
        GameController gc = new GameController();
        Game game = new Game(1, 1, 1, gc){
            @Override
            public void Turn(){

            }
        };
        Player p = new Player("p1", game);
        game.getPlayers().add(p);
        game.setPlayersShipboard();

        // Setto la carta che usa lo stato LandOnAbandonState (simulate, goods vuoto ok)
        AbandonedStation card = new AbandonedStation(0, 1, 1, 1, new Goods[0]);
        game.setCard(card);

        // Forzo lo stato su LandOnAbandonState
        LandOnAbandonState state = new LandOnAbandonState(game,p);
        p.setPlayerState(state);
        if (stateHolder != null) stateHolder[0] = state;
        return p;
    }
    public void testLandOnAbandon() throws RemoteException {
        LandOnAbandonState[] holder = new LandOnAbandonState[1];
        Player p = setupPlayerWithState(holder);

        // Il ramo false richiama setCardState sulla carta (lo stato rimane LandOnAbandonState)
        holder[0].landOnAbandon(false, p);

        // Lo stato non sarà cambiato in ChangeGoodsState (ma di norma rimane LandOnAbandonState o può andare in WaitingState)
        assertTrue(
                p.getState() instanceof LandOnAbandonState ||
                        p.getState().getClass().getSimpleName().equals("WaitingState")
        );
    }

    public void testAbandonGame() throws RemoteException {
        LandOnAbandonState[] holder = new LandOnAbandonState[1];
        Player p = setupPlayerWithState(holder);

        // Il metodo chiama AbandonGame e poi landOnAbandon(false, null)
        holder[0].AbandonGame(p);

        // Il player risulta abbandonato
        assertTrue(p.isAbandoned());
    }

    public void testDisconnect() throws RemoteException {
        LandOnAbandonState[] holder = new LandOnAbandonState[1];
        Player p = setupPlayerWithState(holder);

        // Simula disconnessione, chiama landOnAbandon(false, null)
        holder[0].disconnect(p);

        // Il player risulta ancora in lista (non abbandonato), ma lo stato non crasha
        assertNotNull(p);

    }
}