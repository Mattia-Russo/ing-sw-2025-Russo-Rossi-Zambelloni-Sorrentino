package org.example.ServerPkg.Model;

import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.example.ServerPkg.Model.Exceptions.*;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class ShipBoard implements Serializable {

    private int deletedComponentsCounter;
    private final boolean[][] availablePositionMatrix;
    private final Components[][] componentMatrix;
    private final Components[] bookedComponents;
    private final int[] shieldedDirections;
    private float singleCannonPower;
    private int singleEnginePower;
    private int totalBattery;
    private int totalAstronauts;
    private int numDoubleCannons;
    private int numDoubleEngines;


    public ShipBoard( boolean[][] availablePositionMatrix, int matrixWidth, int matrixHeight) {
        this.deletedComponentsCounter = 0;
        this.availablePositionMatrix = availablePositionMatrix;
        this.componentMatrix = new Components[matrixHeight][matrixWidth];
        this.bookedComponents = new Components[2];
        this.shieldedDirections = new int[4];
        this.singleCannonPower = 0;
        this.singleEnginePower = 0;
        this.totalBattery = 0;
        this.totalAstronauts = 0;
        this.numDoubleCannons = 0;
        this.numDoubleEngines = 0;
    }

    public int[] getShieldedDirections() {
        return shieldedDirections;
    }

    public void setNumAstronauts(int numAstronauts) {
        this.totalAstronauts += numAstronauts;
    }

    public int getSingleEnginePower() {
        return singleEnginePower;
    }

    public void setSingleEnginePower(int singleEnginePower) {
        this.singleEnginePower += singleEnginePower;
    }

    public int getNumDoubleEngines(){
        return numDoubleEngines;
    }

    public void setNumDoubleEngines(int numDoubleEngines) {
        this.numDoubleEngines += numDoubleEngines;
    }

    public float getSingleCannonPower() {
        return singleCannonPower;
    }

    public void setSingleCannonPower(float singleCannonPower) {
        this.singleCannonPower += singleCannonPower;
    }

    public int getNumDoubleCannon() {
        return numDoubleCannons;
    }

    public void setNumDoubleCannon(int numDoubleCannon) {
        this.numDoubleCannons += numDoubleCannon;
    }

    public Components[][] getComponentMatrix() {
        return componentMatrix;
    }

    public boolean[][] getAvailablePositionMatrix(){
        return availablePositionMatrix;
    }

    public boolean validPosition(int y, int x){
        return (y >= 5 && y < componentMatrix.length + 5 && x >= 4 && x < componentMatrix[0].length + 4) && availablePositionMatrix[y - 5][x - 4];
    }

    public Components getComponent(int posY, int posX){
        return componentMatrix[posY - 5][posX - 4];
    }

    public int getDeletedComponentsCounter(){
        return deletedComponentsCounter;
    }

    public void bookComponents(Components component){
        for (int i = 0; i < bookedComponents.length; i++) {
            if (bookedComponents[i] == null) {
                bookedComponents[i] = component;
                return;
            }
        }
        throw new FullBookedSlotsException("All booked component slots are full!");
    }

    public Components[] getBookedComponents(){
        return bookedComponents;
    }

    public int getTotalBattery(){
        return totalBattery;
    }

    public void setTotalBattery(int totalBattery){
        this.totalBattery += totalBattery;
    }

    public ArrayList<Goods> getTotalGoods(){
        ArrayList<Goods> totalGoodsList = new ArrayList<>();
        for(int i = 5; i < componentMatrix.length + 5; i++){
            for(int j = 4; j < componentMatrix.length + 4; j++){
                if(availablePositionMatrix[i - 5][j - 4]) {
                    Components c = getComponent(i, j);
                    if(c!=null) {
                        c.addStorage(totalGoodsList);
                    }
                }
            }
        }
        return totalGoodsList;
    }

    public int getTotalAstronauts(){
        return totalAstronauts;
    }

    private ArrayList<BatteryStorage> getBatteryStorageFromPosition(ArrayList<Points> pos){
        ArrayList<BatteryStorage> batteryStorages = new ArrayList<>();
        for(Points p : pos){
            if(!availablePositionMatrix[p.getY() - 5][p.getX() - 4]) {
                throw new InvalidPositionException("Position is invalid");
            }
            if(getComponent(p.getY(),p.getX()).isBatteryStorage()==null) {
                throw new InvalidParameterException("Is not a Battery Storage");
            }
            batteryStorages.add(getComponent(p.getY(),p.getX()).isBatteryStorage());
        }
        return batteryStorages;
    }

    public float getTotalCannonPower(ArrayList<Points> cannonPos, ArrayList<Points> batteriesPos){
        float totalCannonPower = 0;
        if(cannonPos!=null && batteriesPos!=null) {
            ArrayList<Cannon> cannons = new ArrayList<>();
            ArrayList<BatteryStorage> batteryStorages;
            for (Points p : cannonPos) {
                if (!availablePositionMatrix[p.getY() - 5][p.getX() - 4]) {
                    throw new InvalidPositionException("Position is invalid");
                }
                if (getComponent(p.getY(), p.getX()).isDoubleCannon() == null) {
                    throw new InvalidParameterException("Is not a Double Cannon");
                }
                cannons.add(getComponent(p.getY(), p.getX()).isDoubleCannon());
            }

            if(cannonPos.size()!=cannonPos.stream().distinct().count()){
                throw new CannonSelectedTwiceException("You selected twice the same cannon");
            }

            batteryStorages = getBatteryStorageFromPosition(batteriesPos);
            if (batteryStorages.size() < cannons.size()) {
                throw new BatteriesLessThenCannonException("Not enough batteries onboard to activate double cannons!");
            }

            for (int i = 0; i < cannons.size(); i++) {
                if (cannons.get(i).getDirection() == Direction.NORTH) {
                    totalCannonPower += 2;
                } else {
                    totalCannonPower += 1;
                }
                batteryStorages.get(i).setQuantity(-1, this);
            }
        }

        int alienPower=0;
        if(totalCannonPower + this.singleCannonPower > 0){
            for(int i = 5; i < componentMatrix.length + 5; i++){
                for(int j = 4; j < componentMatrix[0].length + 4; j++){
                    if(availablePositionMatrix[i - 5][j - 4]) {
                        Components c = getComponent(i, j);
                        if(c!=null && c.hasAlien() != null && c.hasAlien().colour() == AlienColour.PURPLE){
                            alienPower+=2;
                        }
                    }
                }
            }
        }
        return alienPower + totalCannonPower + this.singleCannonPower;
    }

    public int getTotalEnginePower(ArrayList<Points> enginesPos, ArrayList<Points> batteriesPos){
        int totalEnginePower = 0;
        if(enginesPos!=null && batteriesPos!=null) {
            ArrayList<BatteryStorage> batteryStorages;
            ArrayList<Engine> engines = new ArrayList<>();
            for (Points p : enginesPos) {
                if (!availablePositionMatrix[p.getY() - 5][p.getX() - 4]) {
                    throw new InvalidPositionException("Position is invalid");
                }
                if (getComponent(p.getY(), p.getX()).isDoubleEngine() == null) {
                    throw new InvalidParameterException("Is not a Double Engine");
                }
                engines.add(getComponent(p.getY(), p.getX()).isDoubleEngine());
            }

            batteryStorages = getBatteryStorageFromPosition(batteriesPos);
            if (batteryStorages.size() < engines.size()) {
                throw new BatteriesLessThenCannonException("Not enough batteries onboard to activate double cannons!");
            }

            for (int i = 0; i < engines.size(); i++) {
                totalEnginePower += 2;
                batteryStorages.get(i).setQuantity(-1, this);
            }
        }

        int alienPower = 0;
        if(totalEnginePower + this.singleEnginePower > 0){
            for(int i = 5; i < componentMatrix.length + 5; i++){
                for(int j = 4; j < componentMatrix[0].length + 4; j++){
                    if(availablePositionMatrix[i - 5][j - 4]) {
                        Components c = getComponent(i, j);
                        if (c!=null && c.hasAlien() != null && c.hasAlien().colour()==AlienColour.BROWN){
                            alienPower+=2;
                        }
                    }
                }
            }
        }
        return alienPower + totalEnginePower + this.singleEnginePower;
    }

    public boolean shieldsNotProtects(Direction dir, ArrayList<Points> shield, ArrayList<Points> batteriesPos) {
        ArrayList<BatteryStorage> batteryStorages;
        ArrayList<Shield> userShields = new ArrayList<>();
        for(Points p : shield){
            if(!availablePositionMatrix[p.getY() - 5][p.getX() - 4]) {
                throw new InvalidPositionException("Position is invalid");
            }
            if(getComponent(p.getY(),p.getX()).isShield()==null) {
                throw new InvalidParameterException("Is not a Shield");
            }
            userShields.add(getComponent(p.getY(),p.getX()).isShield());
        }

        batteryStorages = getBatteryStorageFromPosition(batteriesPos);
        if(batteryStorages.size()< userShields.size()) {
            throw new BatteriesLessThenCannonException("Not enough batteries onboard to activate Shield protection!");
        }

        for (int i = 0; i< userShields.size(); i++) {
            batteryStorages.get(i).setQuantity(-1, this);
            if(userShields.get(i).getDirection1()==dir||userShields.get(i).getDirection2()==dir) {
                return true;
            }
        }
        return false;
    }

    public boolean cannonProtects(Direction dir, int rowOrCol, ArrayList<Points> cannon, ArrayList<Points> batteriesPos) {
        ArrayList<Cannon> cannons = new ArrayList<>();
        ArrayList<BatteryStorage> batteryStorages;
        for(Points p : cannon){
            if(!availablePositionMatrix[p.getY() - 5][p.getX() - 4]) {
                throw new InvalidPositionException("Position is invalid");
            }
            if(getComponent(p.getY(),p.getX()).isDoubleCannon()==null) {
                throw new InvalidParameterException("Is not a Double Cannon");
            }
            cannons.add(getComponent(p.getY(),p.getX()).isDoubleCannon());
        }

        batteryStorages = getBatteryStorageFromPosition(batteriesPos);
        if(batteryStorages.size()<cannons.size()) {
            throw new BatteriesLessThenCannonException("Not enough batteries onboard to activate double cannons!");
        }

        for (int i = 0; i < cannons.size(); i++) {
            batteryStorages.get(i).setQuantity(-1, this);
            Cannon c=cannons.get(i);
            switch(dir){
                case NORTH:
                    if(c.getDirection()==Direction.NORTH && c.getPosX()==rowOrCol) {
                        return true;
                    }
                    break;
                case EAST:
                    if(c.getDirection()==Direction.EAST && (c.getPosY()==rowOrCol||c.getPosY()-1==rowOrCol||c.getPosY()+1==rowOrCol)){
                        return true;
                    }
                    break;
                case SOUTH:
                    if(c.getDirection()==Direction.SOUTH && (c.getPosX()==rowOrCol||c.getPosX()-1==rowOrCol||c.getPosX()+1==rowOrCol)){
                        return true;
                    }
                    break;
                case WEST:
                    if(c.getDirection()==Direction.WEST && (c.getPosY()==rowOrCol||c.getPosY()-1==rowOrCol||c.getPosY()+1==rowOrCol)){
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    public boolean getIfShielded(Direction direction){
        return switch (direction) {
            case NORTH -> getShieldedDirections()[0] > 0;
            case EAST -> getShieldedDirections()[1] > 0;
            case SOUTH -> getShieldedDirections()[2] > 0;
            case WEST -> getShieldedDirections()[3] > 0;
        };
    }

    public void addShieldInDirection(Direction direction) {
        switch (direction) {
            case NORTH:
                shieldedDirections[0] += 1;
                break;
            case EAST:
                shieldedDirections[1] += 1;
                break;
            case SOUTH:
                shieldedDirections[2] += 1;
                break;
            case WEST:
                shieldedDirections[3] += 1;
        }
    }

    public void decreaseShieldInDirection(Direction direction) {
        switch (direction) {
            case NORTH:
                shieldedDirections[0] -= 1;
                break;
            case EAST:
                shieldedDirections[1] -= 1;
                break;
            case SOUTH:
                shieldedDirections[2] -= 1;
                break;
            case WEST:
                shieldedDirections[3] -= 1;
        }
    }

    public void removeComponent(int x, int y) {
        if (!validPosition(y, x)) {
            throw new InvalidPositionException("Position is invalid");
        }

        if (componentMatrix[y - 5][x - 4] == null) {
            throw new AlreadyEmptyPositionException("Position already empty");
        }
        componentMatrix[y - 5][x - 4].remove(this);
        componentMatrix[y - 5][x - 4] = null;
        deletedComponentsCounter++;
    }

    public void removeBookedComponents(){
        for(int i=0; i<bookedComponents.length; i++){
            if(bookedComponents[i] != null){
                bookedComponents[i] = null;
                deletedComponentsCounter++;
            }
        }
    }

    public boolean checkIfSplit(int x, int y){
        ArrayList<Components> connectedComponents = findConnectedComponents(x, y);
        for(int i = 5; i < componentMatrix.length + 5; i++){
            for(int j = 4; j < componentMatrix[0].length + 4; j++){
                if(availablePositionMatrix[i - 5][j - 4] && componentMatrix[i - 5][j - 4] != null && !connectedComponents.contains(componentMatrix[i - 5][j - 4])){
                    return true;
                }
            }
        }
        return false;
    }

    public void removeWreck(int x, int y){
        ArrayList<Components> connectedComponents = findConnectedComponents(x, y);
        for(int i = 5; i < componentMatrix.length + 5; i++){
            for(int j = 4; j < componentMatrix[0].length + 4; j++){
                if(availablePositionMatrix[i - 5][j - 4] && componentMatrix[i - 5][j - 4]!=null && !connectedComponents.contains(componentMatrix[i - 5][j - 4])){
                    removeComponent(j,i);
                }
            }
        }
    }

    private ArrayList<Components> findConnectedComponents(int x, int y) {
        ArrayList<Components> connectedComponents = new ArrayList<>();
        boolean[][] visited = new boolean[componentMatrix.length][componentMatrix[0].length];
        dfs(y, x, visited, connectedComponents);
        return connectedComponents;
    }

    private void dfs(int row, int col, boolean[][] visited, ArrayList<Components> result) {

        if (row < 0 || row >= componentMatrix.length || col < 0 || col >= componentMatrix[0].length || visited[row][col] || componentMatrix[row][col] == null) {
            return;
        }

        visited[row][col] = true;
        result.add(componentMatrix[row][col]);

        exploreNear(row, col, row - 1, col, Direction.NORTH, visited, result);
        exploreNear(row, col, row + 1, col, Direction.SOUTH, visited, result);
        exploreNear(row, col, row, col - 1, Direction.WEST, visited, result);
        exploreNear(row, col, row, col + 1, Direction.EAST, visited, result);
    }

    private void exploreNear(int row, int col, int newRow, int newCol, Direction dir, boolean[][] visited, ArrayList<Components> result) {

        if (newRow >= 0 && newRow < componentMatrix.length && newCol >= 0 && newCol < componentMatrix[0].length && !visited[newRow][newCol] && componentMatrix[newRow][newCol] != null) {
            if (getIfConnected(componentMatrix[row][col], componentMatrix[newRow][newCol], dir)) {
                dfs(newRow, newCol, visited, result);
            }
        }
    }

    private boolean getIfConnected(Components c1, Components c2, Direction dir) {
        Connector conn1 = c1.getDirConnector(dir);
        Connector conn2 = c2.getDirConnector(Direction.values()[(dir.ordinal()+2)%4]);

        return (conn1 == conn2 && conn1 != Connector.EMPTY) || (conn1 == Connector.UNIVERSAL && conn2 != Connector.EMPTY) || (conn2 == Connector.UNIVERSAL && conn1 != Connector.EMPTY);
    }

    public Components getFirstComponent(Direction direction, int rowOrCol){
        switch (direction) {
            case EAST:
                for (int i = componentMatrix[0].length + 3 /* + 4 - 1*/; i > 3; i--) {
                    if (availablePositionMatrix[rowOrCol - 5][i - 4] && componentMatrix[rowOrCol - 5][i - 4]!=null) {
                        return componentMatrix[rowOrCol - 5][i - 4];
                    }
                }
                break;

            case SOUTH:
                for (int i = componentMatrix.length + 4; i > 4 ; i--) {
                    if (availablePositionMatrix[i - 5][rowOrCol - 4] && componentMatrix[i - 5][rowOrCol - 4]!=null) {
                        return componentMatrix[i - 5][rowOrCol - 4];
                    }
                }
                break;
            case WEST:
                for (int i = 4; i < componentMatrix[0].length + 4; i++) {
                    if (availablePositionMatrix[rowOrCol - 5][i - 4] && componentMatrix[rowOrCol - 5][i - 4]!=null) {
                        return componentMatrix[rowOrCol - 5][i - 4];
                    }
                }
                break;

            case NORTH:
                for (int i = 5; i < componentMatrix.length + 5; i++) {
                    if (availablePositionMatrix[i - 5][rowOrCol - 4] && componentMatrix[i - 5][rowOrCol - 4]!=null) {
                        return componentMatrix[i - 5][rowOrCol - 4];
                    }
                }
                break;
        }
        return null;
    }

    public boolean getIfSingleCannon(Direction dir, int rowOrCol){
        if (dir.ordinal()%2 == 0 && rowOrCol < 11 && rowOrCol > 3) {
            for(int i = 5; i < componentMatrix.length + 5; i++){
                if(availablePositionMatrix[i - 5][rowOrCol - 4]){
                    Components c = getComponent(i, rowOrCol);
                    if(c!=null && c.isSingleCannon()!=null){
                        if (((c.getDirection().ordinal()+2)%4) == dir.ordinal()){
                            return true;
                        }
                    }
                }
            }
        } else if(rowOrCol < 10 && rowOrCol > 4){
            for(int i = 4; i < componentMatrix[0].length + 4; i++){
                if(availablePositionMatrix[rowOrCol - 5][i - 4]){
                    Components c = getComponent(rowOrCol, i);
                    if(c!=null && c.isSingleCannon()!=null){
                        if (((c.getDirection().ordinal()+2)%4) == dir.ordinal()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean getIfDoubleCannon(Direction dir, int rowOrCol){
        if (dir.ordinal()%2 == 0 && rowOrCol < 11 && rowOrCol > 3) {
            for(int i = 5; i < componentMatrix.length + 5; i++){
                if(availablePositionMatrix[i - 5][rowOrCol - 4]){
                    Components c = getComponent(i, rowOrCol);
                    if(c!=null && c.isDoubleCannon()!=null){
                        if (((c.getDirection().ordinal()+2)%4) == dir.ordinal()){
                            return true;
                        }
                    }
                }
            }
        } else if(rowOrCol < 10 && rowOrCol > 4){
            for(int i = 4; i < componentMatrix[0].length + 4; i++){
                if(availablePositionMatrix[rowOrCol - 5][i - 4]){
                    Components c = getComponent(rowOrCol, i);
                    if(c!=null && c.isDoubleCannon()!=null){
                        if (((c.getDirection().ordinal()+2)%4) == dir.ordinal()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void placeComponent(int x, int y, Components component){
        if (validPosition(y, x)){
            if (componentMatrix[y - 5][x - 4] == null) {
                componentMatrix[y - 5][x - 4] = component;
                component.setPosition(x, y);
                component.place(this);
            } else {
                throw new OccupiedPositionException("Position already occupied!");
            }

        } else {
            throw new InvalidPositionException("x: " + x + " y: " + y + " is an invalid position");
        }
    }

    public boolean getIfExposed(Direction dir, Components c){
        switch(dir){
            case NORTH:
                if(!validPosition(c.getPosY()-1, c.getPosX()) && c.getDirConnector(Direction.NORTH) != Connector.EMPTY)
                    return true;
                else
                    return c.getDirConnector(Direction.NORTH) != Connector.EMPTY && getComponent(c.getPosY()-1, c.getPosX())== null;
            case EAST:
                if(!validPosition(c.getPosY(), c.getPosX()+1) && c.getDirConnector(Direction.EAST) != Connector.EMPTY)
                    return true;
                else
                    return c.getDirConnector(Direction.EAST) != Connector.EMPTY && getComponent(c.getPosY(), c.getPosX()+1) == null;
            case SOUTH:
                if(!validPosition(c.getPosY()+1, c.getPosX()) && c.getDirConnector(Direction.SOUTH) != Connector.EMPTY)
                    return true;
                else
                    return c.getDirConnector(Direction.SOUTH) != Connector.EMPTY && getComponent(c.getPosY()+1, c.getPosX()) == null;
            case WEST:
                if(!validPosition(c.getPosY(), c.getPosX()-1) && c.getDirConnector(Direction.WEST) != Connector.EMPTY)
                    return true;
                else
                    return c.getDirConnector(Direction.WEST) != Connector.EMPTY && getComponent(c.getPosY(), c.getPosX()-1) == null;
            default:
                return false;
        }
    }

    public int getTotalExposedConnectors() {
        int totalExposedConnectors = 0;
        for (int i=0; i < componentMatrix.length; i++) {
            for (int j=0; j < componentMatrix[0].length; j++) {
                if (availablePositionMatrix[i][j] && componentMatrix[i][j] != null) {
                    for(Direction d : Direction.values()) {     // in alcuni casi controllo la connessione 2 volte, può essere ottimizzato
                        if (getIfExposed(d, componentMatrix[i][j])) {
                            totalExposedConnectors++;
                        }
                    }
                }
            }
        }
        return totalExposedConnectors;
    }
}
