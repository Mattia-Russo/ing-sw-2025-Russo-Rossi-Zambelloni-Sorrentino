package org.example.ServerPkg.Model.ForView;

import org.example.ServerPkg.Model.ComponentsPack.Components;
import org.example.ServerPkg.Model.ShipBoard;

import java.io.Serializable;

public class ShipboardView implements Serializable {
    private final ComponentsView[][] componentMatrixView = new ComponentsView[5][7];
    private final Components[] bookedComponents;

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
        bookedComponents = shipBoard.getBookedComponents();
    }

    public ComponentsView[][] getComponentsView() {
        return componentMatrixView;
    }

    public Components[] getBookedComponents() {
        return bookedComponents;
    }
}
