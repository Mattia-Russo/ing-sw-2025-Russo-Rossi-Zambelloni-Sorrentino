package org.example.ServerPkg.Model.ComponentsPack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tubes extends Components{
    @JsonCreator
    public Tubes(
            @JsonProperty("direction") Direction direction,
            @JsonProperty("connectors") Connector[] connectors){
        super(direction, connectors);
    }
}
