package org.example.Model;

import org.example.Model.ComponentsPack.*;
import org.example.Model.Exceptions.AlreadyEmptyPositionException;
import org.example.Model.Exceptions.FullBookedSlotsException;
import org.example.Model.Exceptions.InvalidPositionException;
import org.example.Model.Exceptions.OccupiedPositionException;

import java.awt.*;
import java.security.InvalidParameterException;

import java.util.ArrayList;

public class ShipBoard {

    private int deletedComponentsCounter;
    private boolean[][] availablePositionMatrix;
    private Components[][] componentMatrix;
    private Components[] bookedComponents;
    private int[] shieldedDirections;
    private float singleCannonPower;
    private int singleEnginePower;
    private int totalBattery;
    private int totalAstronauts;

    public ShipBoard(boolean[][] availablePositionMatrix, int matrixWidth, int matrixHeight) {
        this.deletedComponentsCounter = 0;
        this.availablePositionMatrix = availablePositionMatrix;
        this.componentMatrix = new Components[matrixWidth][matrixHeight];
        this.bookedComponents = new Components[2];
        this.shieldedDirections = new int[4];
        this.singleCannonPower = 0;
        this.singleEnginePower = 0;
        this.totalBattery = 0;
        this.totalAstronauts = 0;
    }

    public float getSingleCannonPower() {
        return singleCannonPower;
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

    public void setSingleCannonPower(float singleCannonPower) {
        this.singleCannonPower += singleCannonPower;
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

    public ArrayList<Goods> getTotalGoods(){
        ArrayList<Goods> totalGoodsList = new ArrayList<>();
        for(int i = 0; i < componentMatrix.length; i++){
            for(int j = 0; j < componentMatrix[0].length; j++){
                if(availablePositionMatrix[i][j]) {
                    Components c = getComponent(i, j);
                    c.addStorage(totalGoodsList);
                }
            }
        }
        return totalGoodsList;
    }

    public int getTotalAstronauts(){
        return totalAstronauts;
    }

    public float getTotalCannonPower(ArrayList<Point> pos){
        float totalCannonPower = 0;
        ArrayList<Cannon> cannons = new ArrayList<>();
        for(Point p : pos){
            if(!availablePositionMatrix[p.x][p.y]) {
                throw new InvalidPositionException("Position is invalid");
            }
            if(getComponent(p.x,p.y).isDoubleCannon()==null) {
                throw new InvalidParameterException("Is not a Double Cannon");
            }
            cannons.add(getComponent(p.x,p.y).isDoubleCannon());
        }

        for (Cannon c : cannons) {
            if (c.getDirection()== Direction.NORTH){
                totalCannonPower += 2;
            } else {
                totalCannonPower += 1;
            }
        }
        int alienPower=0;
        if(totalCannonPower + this.singleCannonPower > 0){
            for(int i = 0; i < componentMatrix.length; i++){
                for(int j = 0; j < componentMatrix[0].length; j++){
                    if(availablePositionMatrix[i][j]) {
                        Components c = getComponent(i, j);
                        if(c.hasAlien() != null && c.hasAlien().getColour() == AlienColour.PURPLE){
                            alienPower+=2;
                        }
                    }
                }
            }
        }
        return alienPower + totalCannonPower + this.singleCannonPower;
    }

    public int getTotalEnginePower(ArrayList<Point> pos){
        int totalEnginePower = 0;
        ArrayList<Engine> engines = new ArrayList<>();
        for(Point p : pos){
            if(!availablePositionMatrix[p.x][p.y]) {
                throw new InvalidPositionException("Position is invalid");
            }
            if(getComponent(p.x,p.y).isDoubleEngine()==null) {
                throw new InvalidParameterException("Is not a Double engine");
            }
            engines.add(getComponent(p.x,p.y).isDoubleEngine());
        }

        for (Engine e: engines) {
            totalEnginePower += 2;
        }

        int alienPower = 0;
        if(totalEnginePower + this.singleEnginePower > 0){
            for(int i = 0; i < componentMatrix.length; i++){
                for(int j = 0; j < componentMatrix[0].length; j++){
                    if(availablePositionMatrix[i][j]) {
                        Components c = getComponent(i, j);
                        if (c.hasAlien() != null && c.hasAlien().getColour()==AlienColour.BROWN){
                            alienPower+=2;
                        }
                    }
                }
            }
        }
        return alienPower + totalEnginePower + this.singleEnginePower;
    }

    public boolean getIfShielded(int direction){
        return shieldedDirections[direction] > 0;
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

    /*
    nel controller:
    * try {
        removeComponent(x, y);
    } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
    }

    * */
    public void removeComponent(int x, int y) {
        if (!validPosition(x, y)) {
            throw new InvalidPositionException("Position is invalid");
        }

        if (componentMatrix[x][y] == null) {
            throw new AlreadyEmptyPositionException("Position already empty");
        }
        componentMatrix[x][y].remove(this);
        componentMatrix[x][y] = null;
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
                for (int i = componentMatrix[0].length; i > 0 ; i--) {
                    if (availablePositionMatrix[rowOrCol][i]&&componentMatrix[i][rowOrCol]!=null) {
                        return componentMatrix[rowOrCol][i];
                    }
                }
                break;
            case EAST:
                for (int i = componentMatrix.length; i > 0 ; i--) {
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
                    if(c.isCannon()!=null){
                        if (((c.getDirection().ordinal()+2)%4) == dir.ordinal()){
                            return true;
                        }
                    }
                }
            }
        } else {
            for(int i = 0; i < componentMatrix.length; i++){
                if(availablePositionMatrix[i][rowOrCol]){
                    Components c = getComponent(i, rowOrCol);
                    if(c.isCannon()!=null){
                        if (((c.getDirection().ordinal()+2)%4) == dir.ordinal()){
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
                    if(c.isDoubleCannon()!=null){
                        if (((c.getDirection().ordinal()+2)%4) == dir.ordinal()){
                            return true;
                        }
                    }
                }
            }
        } else {
            for(int i = 0; i < componentMatrix.length; i++){
                if(availablePositionMatrix[i][rowOrCol]){
                    Components c = getComponent(i, rowOrCol);
                    if(c.isDoubleCannon()!=null){
                        if (((c.getDirection().ordinal()+2)%4) == dir.ordinal()){
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
            if (componentMatrix[row][col] == null) {
                componentMatrix[row][col] = component;
                component.setPosition(row, col);
                component.place(this);
            } else {
                throw new OccupiedPositionException("Position already occupied!");
            }

        } else {
            throw new InvalidPositionException("Invalid position");
        }
    }

    public boolean getIfExposed(Direction dir, Components c){
        switch(dir){
            case NORTH:
                return c.getDirConnector(Direction.NORTH) != Connector.EMPTY && validPosition(c.getPosX(), c.getPosY()-1) && componentMatrix[c.getPosX()][c.getPosY()-1] == null;
            case EAST:
                return c.getDirConnector(Direction.EAST) != Connector.EMPTY && validPosition(c.getPosX() +1, c.getPosY()) && componentMatrix[c.getPosX()+1][c.getPosY()] == null;
            case SOUTH:
                return c.getDirConnector(Direction.SOUTH) != Connector.EMPTY && validPosition(c.getPosX(), c.getPosY()+1) && componentMatrix[c.getPosX()][c.getPosY()+1] == null;
            case WEST:
                return c.getDirConnector(Direction.WEST) != Connector.EMPTY && validPosition(c.getPosX()-1, c.getPosY()) && componentMatrix[c.getPosX()-1][c.getPosY()] == null;
            default:
                return false;
        }
    }

    public int getTotalExposedConnectors() {
        int totalExposedConnectors = 0;
        for (int i=0; i < componentMatrix.length; i++) {
            for (int j=0; j < componentMatrix[i].length; j++) {
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
