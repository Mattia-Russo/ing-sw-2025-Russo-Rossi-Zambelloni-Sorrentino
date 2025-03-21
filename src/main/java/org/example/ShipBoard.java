package org.example;

import org.example.ComponentsPack.*;
import org.example.ComponentsPack.Direction;

import java.util.ArrayList;
import java.util.Arrays;

public class ShipBoard {

    private int deletedComponentsCounter;
    private boolean[][] availablePositionMatrix;
    private Components[][] componentMatrix;
    private Components[] bookedComponents;
    private boolean[] shieldedDirections;
    private float singleCannonPower;
    private int singleEnginePower;

    public ShipBoard(boolean[][] availablePositionMatrix, int matrixWidth, int matrixHeight) {
        this.deletedComponentsCounter = 0;
        this.availablePositionMatrix = availablePositionMatrix;
        this.componentMatrix = new Components[matrixWidth][matrixHeight];
        this.bookedComponents = new Components[2];
        this.shieldedDirections = new boolean[4];
        this.singleCannonPower = 0;
        this.singleEnginePower = 0;

    }

    public Components[][] getComponentMatrix() {
        return componentMatrix;
    }

    public boolean[][] getAvailablePositionMatrix(){
        return availablePositionMatrix;
    }

    public boolean validPosition(int posX, int posY){
        if (posX < 0 || posX >= componentMatrix.length || posY < 0 || posY >= componentMatrix[0].length) {
            return false;
        }
        if (!availablePositionMatrix[posX][posY]) {
            return false;
        }
        return true;
    }

    public Components getComponent(int posX, int posY){
        return componentMatrix[posX][posY];
    }

    public int getCounter(){
        return deletedComponentsCounter;
    }


    public void bookComponents(Components component){
        if(bookedComponents[0] == null){
            bookedComponents[0] = component;
        } else if(bookedComponents[1] == null){
            bookedComponents[1] = component;
        }
    }

    public Components[] getBookedComponents(){return bookedComponents;}

    public int getTotalBattery(){
        int totalBattery = 0;
        for(int i = 0; i < componentMatrix.length; i++){
            for(int j = 0; j < componentMatrix[0].length; j++){
                if(availablePositionMatrix[i][j]){
                    Components c = getComponent(i, j);
                    if(c instanceof BatteryStorage){
                        totalBattery += ((BatteryStorage) c).getQuantity();
                    }
                }

            }

        }
        return totalBattery;
    }

    public ArrayList<Goods> getTotalGoods(){
        ArrayList<Goods> totalGoodsList = new ArrayList<>();
        for(int i = 0; i < componentMatrix.length; i++){
            for(int j = 0; j < componentMatrix[0].length; j++){
                if(availablePositionMatrix[i][j]) {
                    Components c = getComponent(i, j);
                    if (c instanceof Storage) {
                        int z=0;
                        while(z<((Storage) c).getCapacity() && ((Storage) c).getGoods()[z]!=null) {
                            totalGoodsList.add((((Storage) c).getGoods()[z]));
                            z++;
                        }
                    }
                }
            }
        }
        return totalGoodsList;
    }

    public int getTotalAstronauts(){
        int totalAstronauts = 0;
        for(int i = 0; i < componentMatrix.length; i++){
            for(int j = 0; j < componentMatrix[0].length; j++){
                if(availablePositionMatrix[i][j]) {
                    Components c = getComponent(i, j);
                    if (c instanceof Cabin) {
                        totalAstronauts += ((Cabin) c).getNumAstronauts();
                    }
                }
            }
        }
        return totalAstronauts;
    }

    public float getTotalCannonPower(ArrayList<Cannon> cannons){
        float totalCannonPower = 0;
        for (Cannon c : cannons) {
            if (c.getDirection()==Direction.NORTH){
                totalCannonPower += 2;
            } else {
                totalCannonPower += 1;
            }
        }
        return totalCannonPower + this.singleCannonPower;
    }

    public int getTotalEngineStrenght(ArrayList<Engine> engines){
        int totalEnginePower = 0;
        for (Engine c : engines) {
            totalEnginePower += 2;
        }
        return totalEnginePower + this.singleEnginePower;
    }

    public int getNumDoubleCannon(){
        int totalDoubleCannon = 0;
        for(int i = 0; i < componentMatrix.length; i++){
            for(int j = 0; j < componentMatrix[0].length; j++){
                if(availablePositionMatrix[i][j]) {
                    Components c = getComponent(i, j);
                    if (c instanceof Cannon && ((Cannon) c).getPower() == 2){
                        totalDoubleCannon += 1;
                    }
                }
            }
        }
        return totalDoubleCannon;
    }

    public int getNumDoubleEngine(){
        int totalDoubleEngine = 0;
        for(int i = 0; i < componentMatrix.length; i++){
            for(int j = 0; j < componentMatrix[0].length; j++){
                if(availablePositionMatrix[i][j]) {
                    Components c = getComponent(i, j);
                    if (c instanceof Engine && ((Engine) c).getPower() == 2){
                        totalDoubleEngine += 1;
                    }
                }
            }
        }
        return totalDoubleEngine;
    }

    public boolean getIfShielded(int direction){
        return shieldedDirections[direction];
    }

    public void addShieldedDirections(Direction direction){
        switch(direction){
            case NORTH:
                shieldedDirections[0] = true;
                break;
            case EAST:
                shieldedDirections[1] = true;
                break;
            case SOUTH:
                shieldedDirections[2] = true;
                break;
            case WEST:
                shieldedDirections[3] = true;
        }

    }

    public void removeComponent(int x, int y) {
        if (x < 0 || y < 0 || x >= componentMatrix.length || y >= componentMatrix[0].length) {
            return;
        }
        if (availablePositionMatrix[x][y]) {
            if (componentMatrix[x][y] == null) {
                return;
            }
            if (componentMatrix[x][y] instanceof LifeSupportSystem) {
                if (componentMatrix[x][y + 1] instanceof Cabin) {
                    if (componentMatrix[x][y + 2] instanceof LifeSupportSystem) {
                        if (((LifeSupportSystem) componentMatrix[x][y + 2]).getColour() != ((LifeSupportSystem) componentMatrix[x][y]).getColour() && ((Cabin) componentMatrix[x][y + 1]).getAlien().getColour() == ((LifeSupportSystem) componentMatrix[x][y]).getColour()) {
                            ((Cabin) componentMatrix[x][y + 1]).addAlien(null);
                        }
                    } else if (componentMatrix[x + 1][y + 1] instanceof LifeSupportSystem) {
                        if (((LifeSupportSystem) componentMatrix[x + 1][y + 1]).getColour() != ((LifeSupportSystem) componentMatrix[x][y]).getColour() && ((Cabin) componentMatrix[x][y + 1]).getAlien().getColour() == ((LifeSupportSystem) componentMatrix[x][y]).getColour()) {
                            ((Cabin) componentMatrix[x][y + 1]).addAlien(null);
                        }
                    } else if (componentMatrix[x - 1][y + 1] instanceof LifeSupportSystem) {
                        if (((LifeSupportSystem) componentMatrix[x - 1][y + 1]).getColour() != ((LifeSupportSystem) componentMatrix[x][y]).getColour() && ((Cabin) componentMatrix[x][y + 1]).getAlien().getColour() == ((LifeSupportSystem) componentMatrix[x][y]).getColour()) {
                            ((Cabin) componentMatrix[x][y + 1]).addAlien(null);
                        }
                    } else {
                        ((Cabin) componentMatrix[x][y + 1]).addAlien(null);
                        ((Cabin) componentMatrix[x][y + 1]).changeWithLifeSupport(false);
                    }
                }
                if (componentMatrix[x - 1][y] instanceof Cabin) {
                    if (componentMatrix[x - 1][y + 1] instanceof LifeSupportSystem) {
                        if (((LifeSupportSystem) componentMatrix[x - 1][y + 1]).getColour() != ((LifeSupportSystem) componentMatrix[x][y]).getColour() && ((Cabin) componentMatrix[x - 1][y]).getAlien().getColour() == ((LifeSupportSystem) componentMatrix[x][y]).getColour()) {
                            ((Cabin) componentMatrix[x - 1][y]).addAlien(null);
                        }
                    } else if (componentMatrix[x - 1][y - 1] instanceof LifeSupportSystem) {
                        if (((LifeSupportSystem) componentMatrix[x + 1][y - 1]).getColour() != ((LifeSupportSystem) componentMatrix[x][y]).getColour() && ((Cabin) componentMatrix[x - 1][y]).getAlien().getColour() == ((LifeSupportSystem) componentMatrix[x][y]).getColour()) {
                            ((Cabin) componentMatrix[x - 1][y]).addAlien(null);
                        }
                    } else if (componentMatrix[x - 2][y] instanceof LifeSupportSystem) {
                        if (((LifeSupportSystem) componentMatrix[x - 2][y]).getColour() != ((LifeSupportSystem) componentMatrix[x][y]).getColour() && ((Cabin) componentMatrix[x - 1][y]).getAlien().getColour() == ((LifeSupportSystem) componentMatrix[x][y]).getColour()) {
                            ((Cabin) componentMatrix[x - 1][y]).addAlien(null);
                        }
                    } else {
                        ((Cabin) componentMatrix[x - 1][y]).addAlien(null);
                        ((Cabin) componentMatrix[x - 1][y]).changeWithLifeSupport(false);
                    }
                }
                if (componentMatrix[x][y - 1] instanceof Cabin) {
                    if (componentMatrix[x - 1][y - 1] instanceof LifeSupportSystem) {
                        if (((LifeSupportSystem) componentMatrix[x - 1][y - 1]).getColour() != ((LifeSupportSystem) componentMatrix[x][y]).getColour() && ((Cabin) componentMatrix[x][y - 1]).getAlien().getColour() == ((LifeSupportSystem) componentMatrix[x][y]).getColour()) {
                            ((Cabin) componentMatrix[x][y - 1]).addAlien(null);
                        }
                    } else if (componentMatrix[x][y - 2] instanceof LifeSupportSystem) {
                        if (((LifeSupportSystem) componentMatrix[x][y - 2]).getColour() != ((LifeSupportSystem) componentMatrix[x][y]).getColour() && ((Cabin) componentMatrix[x][y - 1]).getAlien().getColour() == ((LifeSupportSystem) componentMatrix[x][y]).getColour()) {
                            ((Cabin) componentMatrix[x][y - 1]).addAlien(null);
                        }
                    } else if (componentMatrix[x + 1][y - 1] instanceof LifeSupportSystem) {
                        if (((LifeSupportSystem) componentMatrix[x + 1][y - 1]).getColour() != ((LifeSupportSystem) componentMatrix[x][y]).getColour() && ((Cabin) componentMatrix[x][y - 1]).getAlien().getColour() == ((LifeSupportSystem) componentMatrix[x][y]).getColour()) {
                            ((Cabin) componentMatrix[x][y - 1]).addAlien(null);
                        }
                    } else {
                        ((Cabin) componentMatrix[x][y - 1]).addAlien(null);
                        ((Cabin) componentMatrix[x][y - 1]).changeWithLifeSupport(false);
                    }
                }
                if (componentMatrix[x + 1][y] instanceof Cabin) {
                    if (componentMatrix[x + 1][y + 1] instanceof LifeSupportSystem) {
                        if (((LifeSupportSystem) componentMatrix[x + 1][y + 1]).getColour() != ((LifeSupportSystem) componentMatrix[x][y]).getColour() && ((Cabin) componentMatrix[x + 1][y]).getAlien().getColour() == ((LifeSupportSystem) componentMatrix[x][y]).getColour()) {
                            ((Cabin) componentMatrix[x + 1][y]).addAlien(null);
                        }
                    } else if (componentMatrix[x + 1][y - 1] instanceof LifeSupportSystem) {
                        if (((LifeSupportSystem) componentMatrix[x + 1][y - 1]).getColour() != ((LifeSupportSystem) componentMatrix[x][y]).getColour() && ((Cabin) componentMatrix[x + 1][y]).getAlien().getColour() == ((LifeSupportSystem) componentMatrix[x][y]).getColour()) {
                            ((Cabin) componentMatrix[x + 1][y]).addAlien(null);
                        }
                    } else if (componentMatrix[x + 2][y] instanceof LifeSupportSystem) {
                        if (((LifeSupportSystem) componentMatrix[x + 2][y]).getColour() != ((LifeSupportSystem) componentMatrix[x][y]).getColour() && ((Cabin) componentMatrix[x + 1][y]).getAlien().getColour() == ((LifeSupportSystem) componentMatrix[x][y]).getColour()) {
                            ((Cabin) componentMatrix[x + 1][y]).addAlien(null);
                        }
                    } else {
                        ((Cabin) componentMatrix[x + 1][y]).addAlien(null);
                        ((Cabin) componentMatrix[x + 1][y]).changeWithLifeSupport(false);
                    }
                }
            } else if (componentMatrix[x][y] instanceof Shield){
                shieldedDirections[((Shield) componentMatrix[x][y]).getDirection1().ordinal()] = false;
                shieldedDirections[((Shield) componentMatrix[x][y]).getDirection2().ordinal()] = false;
            }

            if(componentMatrix[x][y] instanceof Cannon && ((Cannon) componentMatrix[x][y]).getPower()==1){
                if(componentMatrix[x][y].getDirection()==Direction.NORTH||componentMatrix[x][y].getDirection()==Direction.SOUTH){
                    singleCannonPower -=1;
                }else
                    singleCannonPower -= 0.5F;
            }

            if(componentMatrix[x][y] instanceof Engine && ((Engine) componentMatrix[x][y]).getPower()==1){
                singleEnginePower-=1;
            }

            componentMatrix[x][y] = null;
            deletedComponentsCounter++;
        }
    }

    public boolean checkIfSplitted(int row,int col){
        ArrayList<Components> connectedComponents = findConnectedComponents(row,col);
        for(int i = 0; i < componentMatrix.length; i++){
            for(int j = 0; j < componentMatrix[0].length; j++){
                if(availablePositionMatrix[i][j]) {
                    if(!connectedComponents.contains(componentMatrix[i][j])){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void removeWreck(int row,int col){
        ArrayList<Components> connectedComponents = findConnectedComponents(row,col);
        for(int i = 0; i < componentMatrix.length; i++){
            for(int j = 0; j < componentMatrix[0].length; j++){
                if(availablePositionMatrix[i][j]) {
                    if(!connectedComponents.contains(componentMatrix[i][j])){
                        removeComponent(i,j);
                    }
                }
            }
        }

    }

    private ArrayList<Components> findConnectedComponents(int row, int col) {
        ArrayList<Components> connectedComponents = new ArrayList<>();
        boolean[][] visited = new boolean[componentMatrix.length][componentMatrix[0].length];
        dfs(row, col, visited, connectedComponents);
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
            case WEST:
                for (int i = 0; i < componentMatrix.length; i++) {
                    if (availablePositionMatrix[i][rowOrCol]&&componentMatrix[i][rowOrCol]!=null) {
                        return componentMatrix[i][rowOrCol];
                    }
                }
                break;

            case NORTH:
                for (int i = componentMatrix[0].length-1; i < 0 ; i--) {
                    if (availablePositionMatrix[rowOrCol][i]&&componentMatrix[i][rowOrCol]!=null) {
                        return componentMatrix[rowOrCol][i];
                    }
                }
                break;
            case EAST:
                for (int i = componentMatrix.length-1; i < 0 ; i--) {
                    if (availablePositionMatrix[i][rowOrCol]&&componentMatrix[i][rowOrCol]!=null) {
                        return componentMatrix[i][rowOrCol];
                    }
                }
                break;

            case SOUTH:
                for (int i = 0; i < componentMatrix[0].length; i++) {
                    if (availablePositionMatrix[rowOrCol][i]&&componentMatrix[i][rowOrCol]!=null) {
                        return componentMatrix[rowOrCol][i];
                    }
                }
                break;
            default:
                return null;
        }
        return null;
    }

    public boolean getIfSingleCannon(Direction dir, int rowOrCol){
        if (dir.ordinal()%2 == 0){
            for(int i = 0; i < componentMatrix[0].length; i++){
                if(availablePositionMatrix[rowOrCol][i]){
                    Components c = getComponent(rowOrCol, i);
                    if(c instanceof Cannon){
                        if (((c.getDirection().ordinal()+2)%4) == dir.ordinal() && ((Cannon) c).getPower() == 1){
                            return true;
                        }
                    }
                }
            }
        } else {
            for(int i = 0; i < componentMatrix.length; i++){
                if(availablePositionMatrix[i][rowOrCol]){
                    Components c = getComponent(i, rowOrCol);
                    if(c instanceof Cannon){
                        if (((c.getDirection().ordinal()+2)%4) == dir.ordinal() && ((Cannon) c).getPower() == 1){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }   // come fa a capire l'utente se è  meteor swarm o stray big meteor (l'immagine è la stessa, la gestione è diversa)

    public boolean getIfDoubleCannon(Direction dir, int rowOrCol){
        if (dir.ordinal()%2 == 0){
            for(int i = 0; i < componentMatrix[0].length; i++){
                if(availablePositionMatrix[rowOrCol][i]){
                    Components c = getComponent(rowOrCol, i);
                    if(c instanceof Cannon){
                        if (((c.getDirection().ordinal()+2)%4) == dir.ordinal() && ((Cannon) c).getPower() == 2){
                            return true;
                        }
                    }
                }
            }
        } else {
            for(int i = 0; i < componentMatrix.length; i++){
                if(availablePositionMatrix[i][rowOrCol]){
                    Components c = getComponent(i, rowOrCol);
                    if(c instanceof Cannon){
                        if (((c.getDirection().ordinal()+2)%4) == dir.ordinal() && ((Cannon) c).getPower() == 2){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void placeComponent(int row, int col, Components component){
        if (validPosition(row, col)){
            componentMatrix[row][col] = component;
            component.setPosition(row, col);
            if (component instanceof Cannon){
                if (((Cannon) component).getPower() == 1){
                    switch (((Cannon) component).getDirection()){
                        case NORTH:
                            this.singleCannonPower += 1;
                            break;
                        case SOUTH:
                            this.singleCannonPower += 1;
                            break;
                        default:
                            this.singleCannonPower += 0.5F;
                    }
                }
            } else if (component instanceof Engine){
                if (((Engine) component).getPower() == 1){
                    this.singleEnginePower += 1;
                }
            } else if (component instanceof LifeSupportSystem){
                if (validPosition(row+1,col) && componentMatrix[row+1][col] instanceof Cabin){
                    ((Cabin) componentMatrix[row+1][col]).changeWithLifeSupport(true);
                }
                if (validPosition(row-1,col) && componentMatrix[row-1][col] instanceof Cabin){
                    ((Cabin) componentMatrix[row-1][col]).changeWithLifeSupport(true);
                }
                if (validPosition(row,col+1) && componentMatrix[row][col+1] instanceof Cabin){
                    ((Cabin) componentMatrix[row][col+1]).changeWithLifeSupport(true);
                }
                if (validPosition(row,col-1) && componentMatrix[row][col-1] instanceof Cabin){
                    ((Cabin) componentMatrix[row][col-1]).changeWithLifeSupport(true);
                }
            } else if (component instanceof Cabin){
                if (validPosition(row+1,col) && componentMatrix[row+1][col] instanceof LifeSupportSystem){
                    ((Cabin) component).changeWithLifeSupport(true);
                } else if (validPosition(row-1,col) && componentMatrix[row-1][col] instanceof LifeSupportSystem){
                    ((Cabin) component).changeWithLifeSupport(true);
                } else if (validPosition(row,col+1) && componentMatrix[row][col+1] instanceof LifeSupportSystem){
                    ((Cabin) component).changeWithLifeSupport(true);
                } else if (validPosition(row,col-1) && componentMatrix[row][col-1] instanceof LifeSupportSystem){
                    ((Cabin) component).changeWithLifeSupport(true);
                }
            }else if(component instanceof Shield){
                addShieldedDirections(((Shield) component).getDirection1());
                addShieldedDirections(((Shield) component).getDirection2());
            }
        }
    }

    public ArrayList<LifeSupportSystem> getIfAlienSupported(Cabin cabin){
        int x = cabin.getPosX();
        int y = cabin.getPosY();
        ArrayList<LifeSupportSystem> lifeSupportList = new ArrayList<>();
        if(availablePositionMatrix[x+1][y] && componentMatrix[x+1][y] instanceof LifeSupportSystem){
            lifeSupportList.add((LifeSupportSystem) componentMatrix[x+1][y]);
        }
        if(availablePositionMatrix[x-1][y] && componentMatrix[x-1][y] instanceof LifeSupportSystem){
            lifeSupportList.add((LifeSupportSystem) componentMatrix[x-1][y]);
        }
        if(availablePositionMatrix[x][y+1] && componentMatrix[x][y+1] instanceof LifeSupportSystem){
            lifeSupportList.add((LifeSupportSystem) componentMatrix[x][y+1]);
        }
        if(availablePositionMatrix[x][y-1] && componentMatrix[x][y-1] instanceof LifeSupportSystem){
            lifeSupportList.add((LifeSupportSystem) componentMatrix[x][y-1]);
        }
        return lifeSupportList;
    }
}
