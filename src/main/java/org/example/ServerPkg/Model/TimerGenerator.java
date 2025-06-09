package org.example.ServerPkg.Model;

import org.example.ServerPkg.Model.Exceptions.InvalidMethodCallException;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class TimerGenerator implements Serializable {
    private final int countdownValue;
    private boolean isAvailable;

    public TimerGenerator() {
        this.countdownValue = 60;
        this.isAvailable = true;
    }

    public void start(){
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
                }
            }
            };
            timer.scheduleAtFixedRate(task, 0, 1000);
        }else throw new InvalidMethodCallException("Timer is already running");
    }
}
