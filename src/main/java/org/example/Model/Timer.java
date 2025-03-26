package org.example.Model;

import java.time.Instant;
import java.time.Duration;

public class Timer {
    private int countdownValue;

    public Timer(int countdownValue) {
        this.countdownValue = countdownValue;
    }

    public boolean countdown() {
        Instant start = Instant.now(); // salva l'ora corrente

        while (Duration.between(start, Instant.now()).getSeconds() < countdownValue) {
            try {
                Thread.sleep(1000); // Attende 1 secondo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
