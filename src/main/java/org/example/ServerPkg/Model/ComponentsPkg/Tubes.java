package org.example.ServerPkg.Model.ComponentsPkg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.ServerPkg.Model.ForView.ComponentsView;

import java.io.Serializable;

public class Tubes extends Components implements Serializable {
    private final int id;

    @JsonCreator
    public Tubes(
            @JsonProperty("id") int id,
            @JsonProperty("direction") Direction direction,
            @JsonProperty("connectors") Connector[] connectors){
        super(direction, connectors);
        this.id = id;
    }

    @Override
    public ComponentsView createView(){
        return new ComponentsView(getDirection(), getConnectors(), id,"Tubes", 0 ,0, null, null, null);
    }
}
