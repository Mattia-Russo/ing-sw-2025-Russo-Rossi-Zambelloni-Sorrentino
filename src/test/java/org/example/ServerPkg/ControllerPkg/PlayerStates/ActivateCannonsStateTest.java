package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.Exceptions.AlreadyBatteryException;
import org.example.ServerPkg.Model.Exceptions.AlreadyCannonException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ActivateCannonsStateTest extends TestCase {
        private Game game;
        private  ArrayList<Player> players;

        @BeforeEach
        public void setUp() {
            Player p1 = new Player(12, "a");
            Player p2 = new Player( 7, "a");
            Player p3 = new Player(14, "a");
            Player p4 = new Player( 9, "a");

            p1.changePosition(4);
            players = new ArrayList<>();
            players.add(p1);
            players.add(p2);
            players.add(p3);
            players.add(p4);

            game=new Game(4,1,  1, null);
        }

        public void testActivateCannons() {
            ActivateCannonsState state = new ActivateCannonsState(game);

            ArrayList<Points> cannons1 = new ArrayList<>();
            cannons1.add(new Points(1, 2));
            cannons1.add(new Points(2, 3));

            assertDoesNotThrow(() -> state.activateCannons(cannons1));

            ArrayList<Points> cannons2 = new ArrayList<>();
            cannons2.add(new Points(3, 4));

            assertThrows(AlreadyCannonException.class, () -> state.activateCannons(cannons2));
        }

        public void testUseBatteries() {
            ActivateCannonsState state = new ActivateCannonsState(game);

            ArrayList<Points> batteries1 = new ArrayList<>();
            batteries1.add(new Points(0, 1));
            batteries1.add(new Points(1, 1));

            assertDoesNotThrow(() -> state.useBatteries(batteries1));

            ArrayList<Points> batteries2 = new ArrayList<>();
            batteries2.add(new Points(2, 2));

            assertThrows(AlreadyBatteryException.class, () -> state.useBatteries(batteries2));

    }

}