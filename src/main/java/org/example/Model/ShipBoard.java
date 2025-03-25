package org.example.Model;

import org.example.ComponentsPack.*;
import org.example.Model.ComponentsPack.*;
import org.example.Model.Exceptions.AlreadyEmptyPositionException;
import org.example.Model.Exceptions.FullBookedSlotsException;
import org.example.Model.Exceptions.InvalidPositionException;

import java.util.ArrayList;
import java.util.Objects;

public class ShipBoard {

    private int deletedComponentsCounter;
    private boolean[][] availablePositionMatrix;
    private Components[][] componentMatrix;
    private Components[] bookedComponents;
    private boolean[] shieldedDirections;
    private float singleCannonPower;
    private int numDoubleCannons;
    private int singleEnginePower;
    private int numDoubleEngines;
    private int totalBattery;
    private int totalAstronauts;
    private ArrayList<Alien> aliens;

    public ShipBoard(boolean[][] availablePositionMatrix, int matrixWidth, int matrixHeight) {
        this.deletedComponentsCounter = 0;
        this.availablePositionMatrix = availablePositionMatrix;
        this.componentMatrix = new Components[matrixWidth][matrixHeight];
        this.bookedComponents = new Components[2];
        this.shieldedDirections = new boolean[4];
        this.singleCannonPower = 0;
        this.singleEnginePower = 0;
        this.numDoubleCannons = 0;
        this.numDoubleEngines = 0;
        this.totalBattery = 0;
        this.totalAstronauts = 0;
        this.aliens = new ArrayList<>();
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
        return totalAstronauts;
    }

    public float getTotalCannonPower(ArrayList<Cannon> cannons){
        float totalCannonPower = 0;
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
                        if (c instanceof Cabin && ((Cabin)c).getAlien().getColour()==AlienColour.PURPLE){
                            alienPower+=2;
                        }
                    }
                }
            }
        }
        return alienPower + totalCannonPower + this.singleCannonPower;
    }

    public int getTotalEngineStrenght(ArrayList<Engine> engines){
        int totalEnginePower = 0;
        for (int i=0; i<engines.size(); i++) {
            totalEnginePower += 2;
        }
        int alienPower = 0;
        if(totalEnginePower + this.singleEnginePower > 0){
            for(int i = 0; i < componentMatrix.length; i++){
                for(int j = 0; j < componentMatrix[0].length; j++){
                    if(availablePositionMatrix[i][j]) {
                        Components c = getComponent(i, j);
                        if (c instanceof Cabin && ((Cabin)c).getAlien().getColour()==AlienColour.BROWN){
                            alienPower+=2;
                        }
                    }
                }
            }
        }
        return alienPower + totalEnginePower + this.singleEnginePower;
    }

    public int getNumDoubleCannon(){
        return this.numDoubleCannons;
    }

    public int getNumDoubleEngine(){
        return this.numDoubleEngines;
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
        if (componentMatrix[x][y] instanceof LifeSupportSystem) {
            boolean check = false;
            if (componentMatrix[x][y + 1] instanceof Cabin) {
                for(int i=0; i<((Cabin)componentMatrix[x][y + 1]).getLifeSupportSystemArrayList().size() && !check; i++){
                    if(((Cabin)componentMatrix[x][y + 1]).getLifeSupportSystemArrayList().get(i) != componentMatrix[x][y]) {
                        if (((Cabin) componentMatrix[x][y + 1]).getLifeSupportSystemArrayList().get(i).getColour() == (((LifeSupportSystem) componentMatrix[x][y]).getColour())) {
                            check=true;
                        }
                    }
                }
                if(!check) {
                    if (((Cabin) componentMatrix[x][y + 1]).getAlien().getColour() == (((LifeSupportSystem) componentMatrix[x][y]).getColour())) {
                        ((Cabin) componentMatrix[x][y + 1]).removeAlien();
                    }
                    if(((Cabin) componentMatrix[x][y + 1]).getLifeSupportSystemArrayList().isEmpty()){
                        ((Cabin) componentMatrix[x][y + 1]).changeWithLifeSupport(false);
                    }
                }
                ((Cabin) componentMatrix[x][y + 1]).removeLifeSupport((LifeSupportSystem) componentMatrix[x][y]);
            }
            if (componentMatrix[x - 1][y] instanceof Cabin) {
                for(int i=0; i<((Cabin)componentMatrix[x-1][y]).getLifeSupportSystemArrayList().size() && !check; i++){
                    if(((Cabin)componentMatrix[x-1][y]).getLifeSupportSystemArrayList().get(i) != componentMatrix[x][y]) {
                        if (((Cabin) componentMatrix[x-1][y]).getLifeSupportSystemArrayList().get(i).getColour() == (((LifeSupportSystem) componentMatrix[x][y]).getColour())) {
                            check=true;
                        }
                    }
                }
                if(!check){
                    if (((Cabin) componentMatrix[x-1][y]).getAlien().getColour() == (((LifeSupportSystem) componentMatrix[x][y]).getColour())) {
                        ((Cabin) componentMatrix[x-1][y]).removeAlien();
                    }
                    if(((Cabin) componentMatrix[x-1][y]).getLifeSupportSystemArrayList().isEmpty()){
                        ((Cabin) componentMatrix[x-1][y]).changeWithLifeSupport(false);
                    }
                }
                ((Cabin) componentMatrix[x-1][y]).removeLifeSupport((LifeSupportSystem) componentMatrix[x][y]);
            }
            if (componentMatrix[x][y - 1] instanceof Cabin) {
                for(int i=0; i<((Cabin)componentMatrix[x][y - 1]).getLifeSupportSystemArrayList().size() && !check; i++){
                    if(((Cabin)componentMatrix[x][y - 1]).getLifeSupportSystemArrayList().get(i) != componentMatrix[x][y]) {
                        if (((Cabin) componentMatrix[x][y - 1]).getLifeSupportSystemArrayList().get(i).getColour() == (((LifeSupportSystem) componentMatrix[x][y]).getColour())) {
                            check=true;
                        }
                    }
                }
                if(!check) {
                    if (((Cabin) componentMatrix[x][y - 1]).getAlien().getColour() == (((LifeSupportSystem) componentMatrix[x][y]).getColour())) {
                        ((Cabin) componentMatrix[x][y - 1]).removeAlien();
                    }
                    if(((Cabin) componentMatrix[x][y - 1]).getLifeSupportSystemArrayList().isEmpty()){
                        ((Cabin) componentMatrix[x][y - 1]).changeWithLifeSupport(false);
                    }
                }
                ((Cabin) componentMatrix[x][y - 1]).removeLifeSupport((LifeSupportSystem) componentMatrix[x][y]);
            }
            if (componentMatrix[x + 1][y] instanceof Cabin) {
                for(int i=0; i<((Cabin)componentMatrix[x+1][y]).getLifeSupportSystemArrayList().size() && !check; i++){
                    if(((Cabin)componentMatrix[x+1][y]).getLifeSupportSystemArrayList().get(i) != componentMatrix[x][y]) {
                        if (((Cabin) componentMatrix[x+1][y]).getLifeSupportSystemArrayList().get(i).getColour() == (((LifeSupportSystem) componentMatrix[x][y]).getColour())) {
                            check=true;
                        }
                    }
                }
                if(!check){
                    if (((Cabin) componentMatrix[x+1][y]).getAlien().getColour() == (((LifeSupportSystem) componentMatrix[x][y]).getColour())) {
                        ((Cabin) componentMatrix[x+1][y]).removeAlien();
                    }
                    if(((Cabin) componentMatrix[x+1][y]).getLifeSupportSystemArrayList().isEmpty()){
                        ((Cabin) componentMatrix[x+1][y]).changeWithLifeSupport(false);
                    }
                }
                ((Cabin) componentMatrix[x-1][y]).removeLifeSupport((LifeSupportSystem) componentMatrix[x][y]);
            }
        } else if (componentMatrix[x][y] instanceof Shield){
            shieldedDirections[((Shield) componentMatrix[x][y]).getDirection1().ordinal()] = false;
            shieldedDirections[((Shield) componentMatrix[x][y]).getDirection2().ordinal()] = false;
        } else if (componentMatrix[x][y] instanceof Cannon){
            if(((Cannon) componentMatrix[x][y]).getPower()==1){
                if(componentMatrix[x][y].getDirection()==Direction.NORTH||componentMatrix[x][y].getDirection()==Direction.SOUTH){
                    singleCannonPower -=1;
                }else {
                    singleCannonPower -= 0.5F;
                }
            } else {
                numDoubleCannons--;
            }

        } else if(componentMatrix[x][y] instanceof Engine){
            if (((Engine) componentMatrix[x][y]).getPower()==1){
                singleEnginePower-=1;
            } else {
                numDoubleEngines--;
            }
        } else if (componentMatrix[x][y] instanceof Cabin) {
            this.setNumAstronauts(((Cabin) componentMatrix[x][y]).getNumAstronauts());
            this.aliens.remove(((Cabin) componentMatrix[x][y]).getAlien());
        }

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
            if (componentMatrix[row][col] == null) {
                componentMatrix[row][col] = component;
                component.setPosition(row, col);
                if (component instanceof Cannon){
                    if (((Cannon) component).getPower() == 1){
                        if (component.getDirection() == Direction.NORTH) {
                            this.singleCannonPower += 1;
                        } else {
                            this.singleCannonPower += 0.5F;
                        }
                    } else {
                        this.numDoubleCannons++;
                    }

                } else if (component instanceof Engine){
                    if (((Engine) component).getPower() == 1){
                        this.singleEnginePower += 1;
                    } else {
                        this.numDoubleEngines++;
                    }
                } else if (component instanceof LifeSupportSystem){
                    if (validPosition(row+1,col) && componentMatrix[row+1][col] instanceof Cabin && !((Cabin) componentMatrix[row+1][col]).getIsCentral()){
                        ((Cabin) componentMatrix[row+1][col]).changeWithLifeSupport(true);
                        ((Cabin) componentMatrix[row+1][col]).addLifeSupport(((LifeSupportSystem) component));
                    }
                    if (validPosition(row-1,col) && componentMatrix[row-1][col] instanceof Cabin && !((Cabin) componentMatrix[row-1][col]).getIsCentral()){
                        ((Cabin) componentMatrix[row-1][col]).changeWithLifeSupport(true);
                        ((Cabin) componentMatrix[row-1][col]).addLifeSupport(((LifeSupportSystem) component));
                    }
                    if (validPosition(row,col+1) && componentMatrix[row][col+1] instanceof Cabin && !((Cabin) componentMatrix[row][col+1]).getIsCentral()){
                        ((Cabin) componentMatrix[row][col+1]).changeWithLifeSupport(true);
                        ((Cabin) componentMatrix[row][col+1]).addLifeSupport(((LifeSupportSystem) component));
                    }
                    if (validPosition(row,col-1) && componentMatrix[row][col-1] instanceof Cabin && !((Cabin) componentMatrix[row][col-1]).getIsCentral()){
                        ((Cabin) componentMatrix[row][col-1]).changeWithLifeSupport(true);
                        ((Cabin) componentMatrix[row][col-1]).addLifeSupport(((LifeSupportSystem) component));
                    }
                } else if (component instanceof Cabin){
                    this.totalAstronauts +=2;
                    if (validPosition(row+1,col) && componentMatrix[row+1][col] instanceof LifeSupportSystem){
                        ((Cabin) component).changeWithLifeSupport(true);
                        ((Cabin) component).addLifeSupport(((LifeSupportSystem) componentMatrix[row+1][col]));

                    } else if (validPosition(row-1,col) && componentMatrix[row-1][col] instanceof LifeSupportSystem){
                        ((Cabin) component).changeWithLifeSupport(true);
                        ((Cabin) component).addLifeSupport(((LifeSupportSystem) componentMatrix[row-1][col]));
                    } else if (validPosition(row,col+1) && componentMatrix[row][col+1] instanceof LifeSupportSystem){
                        ((Cabin) component).changeWithLifeSupport(true);
                        ((Cabin) component).addLifeSupport(((LifeSupportSystem) componentMatrix[row][col+1]));
                    } else if (validPosition(row,col-1) && componentMatrix[row][col-1] instanceof LifeSupportSystem){
                        ((Cabin) component).changeWithLifeSupport(true);
                        ((Cabin) component).addLifeSupport(((LifeSupportSystem) componentMatrix[row][col-1]));
                    }
                }else if(component instanceof Shield){
                    addShieldedDirections(((Shield) component).getDirection1());
                    addShieldedDirections(((Shield) component).getDirection2());
                }
            } else {
                throw new IllegalStateException("Position already occupied!");
            }

        } else {
            throw new IllegalArgumentException("Invalid position");
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

    public void addAlien(Cabin cabin, Alien alien){
        cabin.addAlien(alien);
        this.totalAstronauts -= cabin.getNumAstronauts();
        aliens.add(alien);
    }
}
