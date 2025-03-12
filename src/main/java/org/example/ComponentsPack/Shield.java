package org.example.ComponentsPack;

public class Shield extends Components {
    private final int direction1;
    private final int direction2;

    public Shield(int direction1, int direction2) {
        this.direction1 = direction1;
        this.direction2 = direction2;
    }

    public int[] getDirection(){

        return new int[]{direction1, direction2};
    }
}
