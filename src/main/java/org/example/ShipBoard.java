package org.example;

import org.example.ComponentsPack.*;

import java.util.ArrayList;
import java.util.List;

public class ShipBoard {

    private int deletedComponentsCounter;
    private boolean[][] availablePositionMatrix;
    private Components[][] componentMatrix;
    private Components[] bookedComponents;  // da 2 elementi
    private boolean[] shieldedDirections;

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
                if(componentMatrix[i][j] instanceof BatteryStorage){
                    Cabin container=(Cabin) componentMatrix[i][j];
                    totalBattery+=container.getQuantity();
                }
            }

        }
        return totalBattery;
    }

    public List<Goods> getTotalGoods(){
        int totalGoods = 0;
        List<Goods> goodsList = new ArrayList<>();
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(componentMatrix[i][j]!=null && componentMatrix[i][j].getTileType()==TileType.CARGOHOLDS || componentMatrix[i][j].getTileType()==TileType.SPECIALCARGOHOLDS ){
                    Cabin container=(Cabin) componentMatrix[i][j];
                    totalGoods+=container.getQuantity();


                }
            }
        }
        return goodsList;
    }

    public int getTotalAstronauts(){
        int totalAstronauts = 0;
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if (componentMatrix[i][j] != null && componentMatrix[i][j].getTileType()==TileType.CABINS) {
                    Cabin container=(Cabin) componentMatrix[i][j];
                    totalAstronauts+=container.getQuantity();
                }
            }
        }
        return totalAstronauts;
    }

    public int getTotalCannonPower(){
        int totalCannonPower = 0;
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(componentMatrix!=null && componentMatrix[i][j].getTileType()==TileType.CANNON || componentMatrix[i][j].getTileType()==TileType.DOUBLECANNON){
                    PowerComponent power=(PowerComponent) componentMatrix[i][j];
                    totalCannonPower+=power.getPower();

                }
            }
        }
        return totalCannonPower;
    }

    public int getTotalEngineStrenght(){
        int totalEngineStrenght = 0;
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(componentMatrix[i][j]!=null && componentMatrix[i][j].getTileType()==TileType.ENGINE || componentMatrix[i][j].getTileType()==TileType.DOUBLEENGINE){
                    PowerComponent power=(PowerComponent) componentMatrix[i][j];
                    totalEngineStrenght+=power.getPower();
                }
            }
        }
        return totalEngineStrenght;
    }

    public int getNumDoubleCannon(){
        int totalDoubleCannon = 0;
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(componentMatrix[i][j]!=null && componentMatrix[i][j].getTileType()==TileType.DOUBLECANNON){
                    totalDoubleCannon++;

                }
            }
        }
        return totalDoubleCannon;
    }

    // da riscrivere
    public int getNumDoubleEngine(){
        int totalDoubleEngine = 0;
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(componentMatrix[i][j]!=null && componentMatrix[i][j].getTileType()==TileType.DOUBLEENGINE){
                    totalDoubleEngine++;
                }
            }
        }
        return totalDoubleEngine;
    }

    public boolean getIfShielded(int direction){
        return shieldedDirections[direction];
    }

    public void removeComponent(int x, int y){
        if(x<0 || y<0 || x>=componentMatrix[0].length || y>=componentMatrix.length){
            return;
        }
        if(componentMatrix[x][y]==null){
            return;
        }
        componentMatrix[x][y]=null;
        deletedComponentsCounter++;

        // CONTROLLARE SE ALTRI PEZZI DELLA NAVE SALTANO,
        // SE CI SONO DUE PEZZI PLAYER DEVE DECIDERE QUALE DEI DUE SCEGLIERE
        // SE RIMUOVO UNO SHIELD DEVO MODIFICARE L'ARRAY CHE SALVA I LATI PROTETTI

    }

    public Components[][] getComponentMatrix(){
        if(componentMatrix==null){
            return null;
        }
        int numRows=componentMatrix.length;
        Components[][] copyMatrix = new Components[numRows][];
        for(int i = 0; i<numRows; i++){
            int numCols=componentMatrix[i].length;
            copyMatrix[i]=new Components[numCols];

            for(int j = 0; j<numCols; j++){
                copyMatrix[i][j]=componentMatrix[i][j];
            }
        }
        return copyMatrix;
    }

    public Components getFirstComponent(int direction, int rowOrCol){
        switch (direction) {
            case 0:
                for (int i = 0; i < dimension; i++) {
                    if (validPosition(i, rowOrCol)) {
                        return componentMatrix[i][rowOrCol];
                    }
                }

            case 1:
                for (int i = dimension-1; i >= 0 ; i--) {
                    if (validPosition(rowOrCol, i)) {
                        return componentMatrix[rowOrCol][i];
                    }
                }
            case 2:
                for (int i = dimension-1; i >= 0 ; i--) {
                    if (validPosition(i, rowOrCol)) {
                        return componentMatrix[i][rowOrCol];
                    }
                }

            case 3:
                for (int i = 0; i < dimension; i++) {
                    if (validPosition(rowOrCol, i)) {
                        return componentMatrix[rowOrCol][i];
                    }
                }
            default:
                return null;
        }
    }
}
