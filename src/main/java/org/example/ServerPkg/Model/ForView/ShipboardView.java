package org.example.ServerPkg.Model.ForView;

import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.ShipBoard;

import java.io.Serializable;

public class ShipboardView implements Serializable {
    private final ComponentsView[][] componentMatrixView = new ComponentsView[5][7];
    private final ComponentsView[] bookedComponents= new ComponentsView[2];

    public ShipboardView(ShipBoard shipBoard) {
        for(int i = 0; i < shipBoard.getComponentMatrix().length; i++){
            for(int j = 0; j < shipBoard.getComponentMatrix()[0].length; j++){
                if(shipBoard.getAvailablePositionMatrix()[i][j]) {
                    Components c = shipBoard.getComponent(j, i);
                    if(c != null) {
                        componentMatrixView[i][j] = c.createView();
                    }
                }
            }
        }
        for(int i = 0; i < shipBoard.getBookedComponents().length; i++){
            if(shipBoard.getBookedComponents()[i] != null) {
                bookedComponents[i] = shipBoard.getBookedComponents()[i].createView();
            }
        }

    }

    public ComponentsView[][] getComponentsView() {
        return componentMatrixView;
    }

    public ComponentsView[] getBookedComponents() {
        return bookedComponents;
    }
}
