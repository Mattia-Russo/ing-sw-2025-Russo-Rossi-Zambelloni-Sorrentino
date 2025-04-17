package org.example.ServerPkg.Model;

import org.example.ServerPkg.Model.Exceptions.InvalidMethodCallException;

import java.util.Timer;
import java.util.TimerTask;

public class TimerGenerator {
    private final int countdownValue;
    private boolean isAvailable;
    private int flipCounter;
    private boolean finished;

    public TimerGenerator() {
        this.countdownValue = 60;
        this.isAvailable = true;
        this.flipCounter = 0;
        this.finished = false;
    }

    public boolean start() {
        if(isAvailable) {
            isAvailable = false;
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
            int remainingTime = countdownValue;
            public void run() {
                if (remainingTime > 0) {
                    remainingTime--;
                } else {
                    timer.cancel();
                    isAvailable = true;
                    flipCounter++;
                    if(flipCounter == 3)
                        finished = true;
                }
            }
            };
            timer.scheduleAtFixedRate(task, 0, 1000);
            return finished;
        }else throw new InvalidMethodCallException("Timer is already running");
    }
}
