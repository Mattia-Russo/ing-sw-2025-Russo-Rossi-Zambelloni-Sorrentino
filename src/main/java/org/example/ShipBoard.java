package org.example;

import java.util.ArrayList;
import java.util.List;

public class ShipBoard {
    private int dimension;
    private int deletedComponentsCounter;
    private Components[][] componentMatrix;
    private boolean[] shieldedDirections;

    public ShipBoard(int dimension){
        this.dimension = dimension;
        this.deletedComponentsCounter = 0;
        this.componentMatrix = new Components[dimension][dimension];

    }

    public boolean validPosition(int posX, int posY){
        if(posX < 0 || posX >= dimension || posY < 0 || posY >= dimension){
            return false;
        } else if (componentMatrix[posX][posY] != null) {
            return false;
        }else{
            return true;
        }

    }

    public int getCounter(){
        return deletedComponentsCounter;
    }
    public void bookComponents(int x, int y, Components component){
        if(validPosition(x, y)){
            componentMatrix[x][y] = component;
        }
    }

    public int getTotalBattery(){
        int totalBattery = 0;
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(componentMatrix[i][j]!=null && componentMatrix[i][j].getTileType()==TileType.BATTERYCOMPONENTS){
                    ContainersComponent container=(ContainersComponent) componentMatrix[i][j];
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
                    ContainersComponent container=(ContainersComponent) componentMatrix[i][j];
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
                    ContainersComponent container=(ContainersComponent) componentMatrix[i][j];
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
