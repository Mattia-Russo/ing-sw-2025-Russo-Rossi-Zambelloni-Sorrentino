package org.example.ServerPkg.Model;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Verifica se sono lo stesso oggetto
        if (o == null || getClass() != o.getClass()) return false; // Verifica la classe
        Points points = (Points) o; // Fai il cast a Points
        return x == points.x && y == points.y; // Confronta i valori di x e y
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y); // Usa un hash basato sui valori di x e y
    }

}
