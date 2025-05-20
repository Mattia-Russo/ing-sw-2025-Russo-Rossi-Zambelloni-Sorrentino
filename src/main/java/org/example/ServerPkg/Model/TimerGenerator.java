package org.example.ServerPkg.Model;

import org.example.ServerPkg.Model.Exceptions.InvalidMethodCallException;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

public class TimerGenerator implements Serializable {
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

    public int start(){
        if(isAvailable) {
            isAvailable = false;
            CountDownLatch latch = new CountDownLatch(1);

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
                    if(flipCounter == 3) {
                        finished = true;
                    }
                    latch.countDown();
                }
            }
            };
            timer.scheduleAtFixedRate(task, 0, 1000);
            try {
                latch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return flipCounter;
        }else throw new InvalidMethodCallException("Timer is already running");
    }
}
