package org.example.ComponentsPack;

public class Shield extends Components {
    private int direction1;
    private int direction2;

    public int[] getDirection(){
        return new int[]{direction1, direction2};
    }
}
