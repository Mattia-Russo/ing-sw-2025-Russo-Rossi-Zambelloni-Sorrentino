package org.example.Model.CardPack;

import org.example.Model.ComponentsPack.Cabin;
import org.example.Model.ComponentsPack.Components;
import org.example.Model.ShipBoard;

public class Epidemic extends AdventureCard{
    public Epidemic(int CardLevel, int lostDays){
        super(CardLevel, lostDays);
    }

    public void checkAdjacentCabins(ShipBoard s){
        for(int i = 0; i < s.getComponentMatrix().length; i++){
            for(int j = 0; j < s.getComponentMatrix()[0].length; j++){
                if(s.getAvailablePositionMatrix()[i][j]){
                    Components c = s.getComponent(i,j);
                    if(c instanceof Cabin && (((Cabin)c).getAlien()!=null || ((Cabin)c).getNumAstronauts()!=0)){
                        if(s.getComponent(i+1,j) instanceof Cabin  && (((Cabin)s.getComponent(i+1,j)).getAlien()!=null || ((Cabin)s.getComponent(i+1,j)).getNumAstronauts()!=0)){
                            if(((Cabin) c).getAlien()!=null){
                                ((Cabin) c).removeAlien();
                            }else{
                                ((Cabin) c).changeNumAstronauts(-1);
                            }
                            if(((Cabin) s.getComponent(i+1,j)).getAlien()!=null){
                                ((Cabin) s.getComponent(i+1,j)).removeAlien();
                            }else{
                                ((Cabin) s.getComponent(i+1,j)).changeNumAstronauts(-1);
                            }
                        }
                        if(s.getComponent(i-1,j) instanceof Cabin  && (((Cabin)s.getComponent(i-1,j)).getAlien()!=null || ((Cabin)s.getComponent(i-1,j)).getNumAstronauts()!=0)){
                            if(((Cabin) c).getAlien()!=null){
                                ((Cabin) c).removeAlien();
                            }else{
                                ((Cabin) c).changeNumAstronauts(-1);
                            }
                            if(((Cabin) s.getComponent(i-1,j)).getAlien()!=null){
                                ((Cabin) s.getComponent(i-1,j)).removeAlien();
                            }else{
                                ((Cabin) s.getComponent(i-1,j)).changeNumAstronauts(-1);
                            }
                        }
                        if(s.getComponent(i,j-1) instanceof Cabin  && (((Cabin)s.getComponent(i,j-1)).getAlien()!=null || ((Cabin)s.getComponent(i,j-1)).getNumAstronauts()!=0)){
                            if(((Cabin) c).getAlien()!=null){
                                ((Cabin) c).removeAlien();
                            }else{
                                ((Cabin) c).changeNumAstronauts(-1);
                            }
                            if(((Cabin) s.getComponent(i,j-1)).getAlien()!=null){
                                ((Cabin) s.getComponent(i,j-1)).removeAlien();
                            }else{
                                ((Cabin) s.getComponent(i,j-1)).changeNumAstronauts(-1);
                            }
                        }
                        if(s.getComponent(i,j+1) instanceof Cabin  && (((Cabin)s.getComponent(i,j+1)).getAlien()!=null || ((Cabin)s.getComponent(i,j+1)).getNumAstronauts()!=0)){
                            if(((Cabin) c).getAlien()!=null){
                                ((Cabin) c).removeAlien();
                            }else{
                                ((Cabin) c).changeNumAstronauts(-1);
                            }
                            if(((Cabin) s.getComponent(i,j+1)).getAlien()!=null){
                                ((Cabin) s.getComponent(i,j+1)).removeAlien();
                            }else{
                                ((Cabin) s.getComponent(i,j+1)).changeNumAstronauts(-1);
                            }

                        }
                    }
                }

            }

        }
    }
}
