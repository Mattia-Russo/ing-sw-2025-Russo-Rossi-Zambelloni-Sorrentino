package org.example.ServerPkg.ControllerPkg.PlayerStates;

import junit.framework.TestCase;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.*;
import org.example.ServerPkg.Model.CardPkg.AdventureCard;
import org.example.ServerPkg.Model.CardPkg.OpenSpace;
import org.example.ServerPkg.Model.ComponentsPkg.Cabin;
import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.ComponentsPkg.Connector;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.Exceptions.FullBookedSlotsException;
import org.example.ServerPkg.Model.Exceptions.InvalidDeckNumberException;
import org.example.ServerPkg.Model.Exceptions.PickTileWithDeckException;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class BuildShipStateTest extends TestCase {

    public void testTurnTimer() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();

        BuildShipState state= new BuildShipState(game,null);
        state.turnTimer(player);


    }
    public void testTurnTimer2() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();

        BuildShipState state= new BuildShipState(game,new TimerGenerator(2));
        state.turnTimer(player);


    }
    public void testTurnTimer3() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game){
            @Override
            public void Turn(){

            }
        };
        Player player = new Player("Test",game );
        Player player2 = new Player("Test",game );
        game.getPlayers().add(player);
        game.getPlayers().add(player2);
        ShipBoard sb = player.getPlayerShipBoard();
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        player2.setPlayerShipboard(1);
        player.setShipBuilt();
        BuildShipState state= new BuildShipState(game,new TimerGenerator(6));
        for(int i=0; i<4;i++) {
            game.setTimerTurned();
        }

        state.turnTimer(player);


    }



    public void testShowDeck() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        game.setCard(new OpenSpace(1,1,1));
        BuildShipState state= new BuildShipState(game,null);
        state.showDeck(player,1);
    }
    public void testShowDeck4() throws RemoteException {
        GameController Game= new GameController();
        Game game = new Game(3, 2, 1, Game);
        Player player = new Player("Test",game );
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ShipBoard sb = player.getPlayerShipBoard();
        game.setCard(new OpenSpace(1,1,1));
        BuildShipState state= new BuildShipState(game,null);
        try {
            state.showDeck(player, -50);
        }catch(InvalidDeckNumberException e){

        }
    }


    public void testEndShowDeck() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        BuildShipState state = new BuildShipState(game, null);

        // Semplicemente chiama il metodo: dovrebbe coprire la linea!
        state.endShowDeck(player);
    }

    public void testEndShowDeck1() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        ArrayList<AdventureCard> cards = new ArrayList<>();
        cards.add(new OpenSpace(1,1,1));
        player.setDeckShowed(cards);
        BuildShipState state = new BuildShipState(game, null);

        // Semplicemente chiama il metodo: dovrebbe coprire la linea!
        try {
            state.endShowDeck(player);
        }catch(PickTileWithDeckException e){
        }
    }

    public void testPickComponentTile() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        BuildShipState state = new BuildShipState(game, null);

        // Di solito qui si chiama direttamente pickComponentTile e si controlla che non lanci
        state.pickComponentTile(player);
    }
    public void testPickComponentTile1() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        Cabin cabin = new Cabin(1, false, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        cabin.place(player.getPlayerShipBoard());
        player.setCurrentTile(cabin);
        BuildShipState state = new BuildShipState(game, null);

        // Di solito qui si chiama direttamente pickComponentTile e si controlla che non lanci
        state.pickComponentTile(player);
    }

    public void testPickDiscoveredComponent() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        Cabin cabin = new Cabin(1, false, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        cabin.place(player.getPlayerShipBoard());
        player.setCurrentTile(cabin);
        BuildShipState state = new BuildShipState(game, null);
        state.discardComponent(player);
        // Testa la selezione del primo (o unico) componente scoperto
        state.pickDiscoveredComponent(player, 0);
    }
    public void testPickDiscoveredComponent1() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        Cabin cabin = new Cabin(1, false, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        ArrayList<AdventureCard> cards = new ArrayList<>();
        cards.add(new OpenSpace(1,1,1));
        player.setDeckShowed(cards);
        cabin.place(player.getPlayerShipBoard());
        player.setCurrentTile(cabin);
        BuildShipState state = new BuildShipState(game, null);
        state.discardComponent(player);
        // Testa la selezione del primo (o unico) componente scoperto
        state.pickDiscoveredComponent(player, 0);
    }

    public void testRightRotateTile() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        BuildShipState state = new BuildShipState(game, null);

        state.rightRotateTile(player);
    }

    public void testRightRotateTile2() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        Cabin cabin = new Cabin(1, false, Direction.EAST, new Connector[]{Connector.SINGLE,Connector.SINGLE,Connector.EMPTY, Connector.EMPTY});
        player.setCurrentTile(cabin);
        BuildShipState state = new BuildShipState(game, null);

        state.rightRotateTile(player);
    }
    public void testLeftRotateTile() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        BuildShipState state = new BuildShipState(game, null);

        state.leftRotateTile(player);
    }
    public void testLeftRotateTile2() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        Cabin cabin = new Cabin(1, false, Direction.EAST, new Connector[]{Connector.SINGLE,Connector.SINGLE,Connector.EMPTY, Connector.EMPTY});
        player.setCurrentTile(cabin);
        BuildShipState state = new BuildShipState(game, null);

        state.leftRotateTile(player);
    }

    public void testDiscardComponent() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        Cabin cabin = new Cabin(1, false, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        cabin.place(player.getPlayerShipBoard());
        player.setCurrentTile(cabin);
        BuildShipState state = new BuildShipState(game, null);

        state.discardComponent(player);
    }
    public void testDiscardComponent1() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        Cabin cabin = new Cabin(1, false, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        cabin.place(player.getPlayerShipBoard());
        player.setCurrentTile(cabin);
        player.getCurrentTile().setBooked();
        BuildShipState state = new BuildShipState(game, null);

        state.discardComponent(player);
    }

    public void testPlaceTile() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        Cabin cabin = new Cabin(1, false, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        cabin.place(player.getPlayerShipBoard());
        player.setCurrentTile(cabin);
        BuildShipState state = new BuildShipState(game, null);

        // Prova a piazzare la tile in una posizione
        state.placeTile(player,new Points(7,7));
    }
    public void testPlaceTile1() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setPlayerShipboard(1);
        Cabin cabin = new Cabin(1, false, Direction.NORTH, new Connector[]{
                Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY
        });
        cabin.place(player.getPlayerShipBoard());
        player.setCurrentTile(cabin);
        BuildShipState state = new BuildShipState(game, null);

        // Prova a piazzare la tile in una posizione
        state.placeTile(player,new Points(10,10));
    }

    public void testEndBuildShip() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        BuildShipState state = new BuildShipState(game, null);

        state.endBuildShip(player);
    }

    public void testPickBookedTile() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        BuildShipState state = new BuildShipState(game, null);

        // Simula la pick della tile "booked"
        state.pickBookedTile(1,player);
    }
    public void testPickBookedTile1() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        BuildShipState state = new BuildShipState(game, null);

        // Simula la pick della tile "booked"
        try {
            state.pickBookedTile(3, player);
        }catch (PickTileWithDeckException e) {}
        state.pickBookedTile(0, player);
        try{
            state.pickBookedTile(0, player);
        }catch (PickTileWithDeckException e) {}
    }
    public void testPickBookedTile2() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        player.setCurrentTile(new Cabin(1,false,Direction.NORTH,new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY}));
        BuildShipState state = new BuildShipState(game, null);

        state.pickBookedTile(0, player);
        try{
            state.pickBookedTile(0, player);
        }catch (PickTileWithDeckException e){}
    }

    public void testBookComponent() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        BuildShipState state = new BuildShipState(game, null);

        state.bookComponent(player);
    }
    public void testBookComponent1() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game);
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        BuildShipState state = new BuildShipState(game, null);
        player.setCurrentTile(new Cabin(1,false,Direction.NORTH,new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY}));
        state.bookComponent(player);
        player.setCurrentTile(new Cabin(1,false,Direction.NORTH,new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY}));
        state.bookComponent(player);
        player.setCurrentTile(new Cabin(1,false,Direction.NORTH,new Connector[]{Connector.SINGLE, Connector.EMPTY, Connector.EMPTY, Connector.EMPTY}));
        try {
            state.bookComponent(player);
        }catch(FullBookedSlotsException e){}
    }

    public void testDisconnect() throws RemoteException {
        GameController Game = new GameController();
        Game game = new Game(3, 2, 0, Game){
            @Override
            public void Turn(){}
        };
        Player player = new Player("Test", game);
        game.getPlayers().add(player);
        game.setPlayersShipboard();
        BuildShipState state = new BuildShipState(game, null);

        state.disconnect(player);
    }
}