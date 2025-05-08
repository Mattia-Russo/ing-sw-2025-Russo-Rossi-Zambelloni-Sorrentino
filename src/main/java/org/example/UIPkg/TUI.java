package org.example.UIPkg;

import org.example.ServerPkg.Model.ForView.GameView;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TUI implements UI{

    private final BlockingQueue<GameView> gameUpdatesQueue;

    public TUI() {
        gameUpdatesQueue = new LinkedBlockingQueue<>();
    }

    // thread che continua a leggere i game update in coda con un while(true)

    @Override
    public void addGameUpdate(GameView game) {
        try {
            gameUpdatesQueue.put(game);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error inserting game update", e);
        }
    }
}
// metodi per stampare a video la conformazione della view
// string builder al posto di print
// chiedere a chat come rappresentare i colori
// metodi per stampare plancia o le navi (chiede quale)
// coda con update della view