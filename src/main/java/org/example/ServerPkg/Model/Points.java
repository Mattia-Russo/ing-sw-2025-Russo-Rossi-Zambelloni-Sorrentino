package org.example.ServerPkg.Model;

import java.io.Serializable;

public class Points implements Serializable {
    private int x;
    private int y;

    public Points(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
