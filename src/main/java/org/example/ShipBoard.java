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

    public ShipBoard(boolean[][] availablePositionMatrix, int matrixDimension) {
        this.deletedComponentsCounter = 0;
        this.availablePositionMatrix = availablePositionMatrix;
        this.componentMatrix = new Components[matrixDimension][matrixDimension];
        this.bookedComponents = new Components[2];
        this.shieldedDirections = new boolean[4];

    }

    public Components[][] getComponentMatrix() {
        return componentMatrix;
    }

    public boolean[][] getAvailablePositionMatrix(){
        return availablePositionMatrix;
    }

    public boolean validPosition(int posX, int posY){
        if(posX < 0 || posX >= componentMatrix.length || posY < 0 || posY >= componentMatrix[0].length){
            return false;
        } else if (!availablePositionMatrix[posX][posY]) {
            return false;
        } else return componentMatrix[posX][posY] == null;
    }

    public Components getComponent(int posX, int posY){
        return componentMatrix[posX][posY];
    }

    public int getCounter(){
        return deletedComponentsCounter;
    }

    public void changeCounter(int deletedComponentsCounter){
        this.deletedComponentsCounter+= deletedComponentsCounter;
    }

    public void bookComponents(Components component){
        if(bookedComponents[0] == null){
            bookedComponents[0] = component;
        } else if(bookedComponents[1] == null){
            bookedComponents[1] = component;
        }
    }

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

    public void addBatteries(int amount, BatteryStorage batteryStorage){
        batteryStorage.setQuantity(amount);
    }

    public void removeBatteries(int amount, BatteryStorage batteryStorage){
        batteryStorage.setQuantity(-amount);
    }

    public ArrayList<Goods> getTotalGoods(){
        ArrayList<Goods> totalGoodsList = new ArrayList<>();
        for(int i = 0; i < componentMatrix.length; i++){
            for(int j = 0; j < componentMatrix[0].length; j++){
                if(availablePositionMatrix[i][j]) {
                    Components c = getComponent(i, j);
                    if (c instanceof Storage) {
                        totalGoodsList.addAll(Arrays.asList(((Storage) c).getGoods()));
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

    public float getTotalCannonPower(int NumDoubleCannon){
        int doubleCannonCounter = NumDoubleCannon;
        float totalCannonPower = 0;
        for(int i = 0; i < componentMatrix.length; i++){
            for(int j = 0; j < componentMatrix[0].length; j++){
                if(availablePositionMatrix[i][j]){
                    Components c = getComponent(i, j);
                    if(c instanceof Cannon){
                        if (((Cannon) c).getPower() == 2 && doubleCannonCounter > 0){
                            if (c.getDirection() != Direction.NORTH){
                                totalCannonPower += 1;
                            } else {
                                totalCannonPower += 2;
                            }
                            doubleCannonCounter -= 1;   // compito del controller di ridurre l'energia
                        } else if (((Cannon) c).getPower() == 1){
                            if (c.getDirection() != Direction.NORTH){
                                totalCannonPower += 0.5;
                            } else {
                                totalCannonPower += 1;
                            }
                        }
                    }
                }
            }
        }
        return totalCannonPower;
    }

    public int getTotalEngineStrenght(int NumDoubleEngine){
        int doubleEngineCounter = NumDoubleEngine;
        int totalEnginePower = 0;
        for(int i = 0; i < componentMatrix.length; i++){
            for(int j = 0; j < componentMatrix[0].length; j++){
                if(availablePositionMatrix[i][j]){
                    Components c = getComponent(i, j);
                    if(c instanceof Engine){
                        if (((Engine) c).getPower() == 2 && doubleEngineCounter > 0){
                            totalEnginePower += 2;
                            doubleEngineCounter -= 1;   // compito del controller di ridurre l'energia
                        } else if (((Cannon) c).getPower() == 1){
                            totalEnginePower += 1;
                        }
                    }
                }
            }
        }
        return totalEnginePower;
    }

    public int getNumDoubleCannon(){
        int totalDoubleCannon = 0;
        for(int i = 0; i < componentMatrix.length; i++){
            for(int j = 0; j < componentMatrix[0].length; j++){
                if(availablePositionMatrix[i][j]){
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
                if(availablePositionMatrix[i][j]){
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

    public void removeComponent(int x, int y) {
        if (availablePositionMatrix[x][y]) {
            if (x < 0 || y < 0 || x >= componentMatrix.length || y >= componentMatrix[0].length) {
                return;
            }
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



            // CONTROLLARE SE ALTRI PEZZI DELLA NAVE SALTANO,
            // SE CI SONO DUE PEZZI PLAYER DEVE DECIDERE QUALE DEI DUE SCEGLIERE
            // SE RIMUOVO UNO SHIELD DEVO MODIFICARE L'ARRAY CHE SALVA I LATI PROTETTI
            
            componentMatrix[x][y] = null;
            deletedComponentsCounter++;
        }
    }

    public Components getFirstComponent(int direction, int rowOrCol){
        switch (direction) {
            case 0:
                for (int i = 0; i < componentMatrix[0].length; i++) {
                    if (validPosition(i, rowOrCol)) {
                        return componentMatrix[i][rowOrCol];
                    }
                }

            case 1:
                for (int i = componentMatrix.length; i > 0 ; i--) {
                    if (validPosition(rowOrCol, i)) {
                        return componentMatrix[rowOrCol][i];
                    }
                }
            case 2:
                for (int i = componentMatrix[0].length; i > 0 ; i--) {
                    if (validPosition(i, rowOrCol)) {
                        return componentMatrix[i][rowOrCol];
                    }
                }

            case 3:
                for (int i = 0; i < componentMatrix.length; i++) {
                    if (validPosition(rowOrCol, i)) {
                        return componentMatrix[rowOrCol][i];
                    }
                }
            default:
                return null;
        }
    }

    public boolean getIfSingleCannon(Direction dir, int rowOrCol){
        if (dir.ordinal()%2 == 0){
            for(int i = 0; i < componentMatrix[0].length; i++){
                if(availablePositionMatrix[i][rowOrCol]){
                    Components c = getComponent(i, rowOrCol);
                    if(c instanceof Cannon){
                        if ((c.getDirection().ordinal()+2) == dir.ordinal() && ((Cannon) c).getPower() == 1){
                            return true;
                        }
                    }
                }
            }
        } else {
            for(int i = 0; i < componentMatrix[0].length; i++){
                if(availablePositionMatrix[rowOrCol][i]){
                    Components c = getComponent(rowOrCol, i);
                    if(c instanceof Cannon){
                        if ((c.getDirection().ordinal()+2) == dir.ordinal() && ((Cannon) c).getPower() == 1){
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
                if(availablePositionMatrix[i][rowOrCol]){
                    Components c = getComponent(i, rowOrCol);
                    if(c instanceof Cannon){
                        if ((c.getDirection().ordinal()+2) == dir.ordinal() && ((Cannon) c).getPower() == 2){
                            return true;
                        }
                    }
                }
            }
        } else {
            for(int i = 0; i < componentMatrix[0].length; i++){
                if(availablePositionMatrix[rowOrCol][i]){
                    Components c = getComponent(rowOrCol, i);
                    if(c instanceof Cannon){
                        if ((c.getDirection().ordinal()+2) == dir.ordinal() && ((Cannon) c).getPower() == 2){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void placeComponent(int x, int y, Components component){
        if (availablePositionMatrix[x][y]){
            if(componentMatrix[x][y]==null){
                componentMatrix[x][y] = component;
            }
        }
    }

    public ArrayList<LifeSupportSystem> getIfAlienSupported(Cabin cabin){
        int x = cabin.getPosX();
        int y = cabin.getPosY();
        ArrayList<LifeSupportSystem> lifeSupportList = new ArrayList<>();
        if(validPosition(x+1, y) && componentMatrix[x+1][y] instanceof LifeSupportSystem){
            lifeSupportList.add((LifeSupportSystem) componentMatrix[x+1][y]);
        }
        if(validPosition(x-1, y) && componentMatrix[x-1][y] instanceof LifeSupportSystem){
            lifeSupportList.add((LifeSupportSystem) componentMatrix[x-1][y]);
        }
        if(validPosition(x, y+1) && componentMatrix[x][y+1] instanceof LifeSupportSystem){
            lifeSupportList.add((LifeSupportSystem) componentMatrix[x][y+1]);
        }
        if(validPosition(x, y-1) && componentMatrix[x][y-1] instanceof LifeSupportSystem){
            lifeSupportList.add((LifeSupportSystem) componentMatrix[x][y-1]);
        }
        return lifeSupportList;
    }

}
