package org.example.Server.Controller.States;

import org.example.Server.Controller.GameController;

public class OutOfGameState extends PlayerState{
    private GameController gameController;

    public OutOfGameState() {}

    // può essere implementato in modo che prenda un LobbyID (id del game) per decidere in quale lobby entrare
    public void joinLobby(){

    }

    public void createLobby(){

    }
}
