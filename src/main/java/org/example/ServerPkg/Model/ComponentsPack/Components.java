package org.example.ServerPkg.Model.ComponentsPack;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.ShipBoard;

import java.util.ArrayList;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BatteryStorage.class, name = "BATTERYSTORAGE"),
        @JsonSubTypes.Type(value = Cabin.class, name = "CABIN"),
        @JsonSubTypes.Type(value = Cannon.class, name = "CANNON"),
        @JsonSubTypes.Type(value = Engine.class, name = "ENGINE"),
        @JsonSubTypes.Type(value = LifeSupportSystem.class, name = "LIFESUPPORTSYSTEM"),
        @JsonSubTypes.Type(value = Shield.class, name = "SHIELD"),
        @JsonSubTypes.Type(value = Storage.class, name = "STORAGE"),
        @JsonSubTypes.Type(value = Tubes.class, name = "TUBES")
})
public class Components {
    private Direction direction;
    private boolean covered;
    private boolean isPositoned;
    private final Connector[] connectors;
    private int posX;
    private int posY;

    public Components(Direction direction, Connector[] connectors) {
        this.direction = direction;
        this.connectors = connectors;
        this.covered = true;
        this.isPositoned = false;
        this.posX = 0;
        this.posY = 0;
    }

    public void uncover(){
        this.covered = false;
    }

    public boolean getIfCovered() {
        return this.covered;
    }

    public ComponentsView createView(){
        return new ComponentsView(getDirection(), getConnectors(), 0,null,0 ,0, null, null);
    }

    public boolean getIfPositioned() {
        return this.isPositoned;
    }

    public void setPosition(int x, int y) { // controllo se posizioni valide va fatto prima di chiamare questo metodo
        this.isPositoned = true;
        this.posX = x;
        this.posY = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void leftRotate(){
        this.direction = Direction.values()[(this.direction.ordinal()+3)%4];
    }

    public void rightRotate() {
        this.direction = Direction.values()[(this.direction.ordinal()+1)%4];
    }

    public Connector[] getConnectors(){
        return connectors;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Connector getDirConnector(Direction dir){ //restituisce il connettore che c'è nella direzione passata in modo assoluto
        switch(this.direction){
            case NORTH:
                return connectors[dir.ordinal()];
            case EAST:
                return connectors[(dir.ordinal()+3)%4];
            case SOUTH:
                return connectors[(dir.ordinal()+2)%4];
            case WEST:
                return connectors[(dir.ordinal()+1)%4];
            default:
                return null;
        }
    }

    public void remove(ShipBoard ship){}

    public void place(ShipBoard ship){}

    public void addLifeSupport(Cabin cabin) {}

    public void addCabin(LifeSupportSystem life){}

    public void removeCabin(LifeSupportSystem life, ShipBoard ship) {}

    public Cannon isDoubleCannon(){
        return null;
    }

    public Cannon isSingleCannon(){
        return null;
    }

    public Engine isDoubleEngine(){
        return null;
    }

    public Storage isStorage() {
        return null;
    }

    public Shield isShield(){return null;}

    public Alien hasAlien(){
        return null;
    }

    public void manageEpidemic(boolean[][] visited, int dimX, int dimY, ShipBoard ship){}

    public void addEpidemicCabin(int x, int y, ArrayList<Cabin> cabins, boolean[][] visited, int dimX, int dimY, ShipBoard ship){}

    public void addStorage(ArrayList<Goods> list){}

    public boolean checkRightCannon(ShipBoard ship){
        return false;
    }

    public boolean checkRightEngine(ShipBoard ship){
        return false;
    }

    public BatteryStorage isBatteryStorage(){
        return null;
    }

    public Cabin isCabin(){return null;}

}