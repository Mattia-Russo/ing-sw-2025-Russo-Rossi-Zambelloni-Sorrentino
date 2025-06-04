package org.example.ServerPkg.Model.ComponentsPkg;

import org.example.ServerPkg.Model.ForView.ComponentsView;
import java.io.Serializable;

public class Tubes extends Components implements Serializable {

    public Tubes(int id, Direction direction, Connector[] connectors){
        super(direction, connectors, id);
    }

    @Override
    public ComponentsView createView(){
        return new ComponentsView(getDirection(), getConnectors(), getId(),"Tubes", 0 ,0, null, null, null);
    }
}
