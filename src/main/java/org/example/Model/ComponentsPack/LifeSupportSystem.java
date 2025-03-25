package org.example.Model.ComponentsPack;

import org.example.Model.ShipBoard;

public class LifeSupportSystem extends Components {
    private final AlienColour colour;

    public LifeSupportSystem(AlienColour colour, Direction direction, Connector[] connectors) {
        super(direction, connectors);
        this.colour = colour;
    }

    public AlienColour getColour() {
        return colour;
    }


    @Override
    public void remove(ShipBoard ship) {
        boolean check = false;
        if (ship.getComponent(getPosX(), getPosY() + 1) instanceof Cabin) {
            for (int i = 0; i < ((Cabin) ship.getComponent(getPosX(), getPosY() + 1)).getLifeSupportSystemArrayList().size() && !check; i++) {
                if (((Cabin) ship.getComponent(getPosX(), getPosY() + 1)).getLifeSupportSystemArrayList().get(i) != this) {
                    if (((Cabin) ship.getComponent(getPosX(), getPosY() + 1)).getLifeSupportSystemArrayList().get(i).getColour() == getColour()) {
                        check = true;
                    }
                }
            }
            if (!check) {
                if (((Cabin) ship.getComponent(getPosX(), getPosY() + 1)).getAlien().getColour() == getColour()) {
                    ((Cabin) ship.getComponent(getPosX(), getPosY() + 1)).removeAlien();
                }
                if (((Cabin) ship.getComponent(getPosX(), getPosY() + 1)).getLifeSupportSystemArrayList().isEmpty()) {
                    ((Cabin) ship.getComponent(getPosX(), getPosY() + 1)).changeWithLifeSupport(false);
                }
            }
            ((Cabin) ship.getComponent(getPosX(), getPosY() + 1)).removeLifeSupport(this);
        }
        if (ship.getComponent(getPosX() - 1, getPosY()) instanceof Cabin) {
            for (int i = 0; i < ((Cabin) ship.getComponent(getPosX() - 1, getPosY())).getLifeSupportSystemArrayList().size() && !check; i++) {
                if (((Cabin) ship.getComponent(getPosX() - 1, getPosY())).getLifeSupportSystemArrayList().get(i) != this) {
                    if (((Cabin) ship.getComponent(getPosX() - 1, getPosY())).getLifeSupportSystemArrayList().get(i).getColour() == getColour()) {
                        check = true;
                    }
                }
            }
            if (!check) {
                if (((Cabin) ship.getComponent(getPosX() - 1, getPosY())).getAlien().getColour() == getColour()) {
                    ((Cabin) ship.getComponent(getPosX() - 1, getPosY())).removeAlien();
                }
                if (((Cabin) ship.getComponent(getPosX() - 1, getPosY())).getLifeSupportSystemArrayList().isEmpty()) {
                    ((Cabin) ship.getComponent(getPosX() - 1, getPosY())).changeWithLifeSupport(false);
                }
            }
            ((Cabin) ship.getComponent(getPosX() - 1, getPosY())).removeLifeSupport(this);
        }

        if (ship.getComponent(getPosX(), getPosY() - 1) instanceof Cabin) {
            for (int i = 0; i < ((Cabin) ship.getComponent(getPosX(), getPosY() - 1)).getLifeSupportSystemArrayList().size() && !check; i++) {
                if (((Cabin) ship.getComponent(getPosX(), getPosY() - 1)).getLifeSupportSystemArrayList().get(i) != this) {
                    if (((Cabin) ship.getComponent(getPosX(), getPosY() - 1)).getLifeSupportSystemArrayList().get(i).getColour() == getColour()) {
                        check = true;
                    }
                }
            }
            if (!check) {
                if (((Cabin) ship.getComponent(getPosX(), getPosY() - 1)).getAlien().getColour() == getColour()) {
                    ((Cabin) ship.getComponent(getPosX(), getPosY() - 1)).removeAlien();
                }
                if (((Cabin) ship.getComponent(getPosX(), getPosY() - 1)).getLifeSupportSystemArrayList().isEmpty()) {
                    ((Cabin) ship.getComponent(getPosX(), getPosY() - 1)).changeWithLifeSupport(false);
                }
            }
            ((Cabin) ship.getComponent(getPosX(), getPosY() - 1)).removeLifeSupport(this);
        }
        if (ship.getComponent(getPosX() + 1, getPosY()) instanceof Cabin) {
            for (int i = 0; i < ((Cabin) ship.getComponent(getPosX() + 1, getPosY())).getLifeSupportSystemArrayList().size() && !check; i++) {
                if (((Cabin) ship.getComponent(getPosX() + 1, getPosY())).getLifeSupportSystemArrayList().get(i) != this) {
                    if (((Cabin) ship.getComponent(getPosX() + 1, getPosY())).getLifeSupportSystemArrayList().get(i).getColour() == getColour()) {
                        check = true;
                    }
                }
            }
            if (!check) {
                if (((Cabin) ship.getComponent(getPosX() + 1, getPosY())).getAlien().getColour() == getColour()) {
                    ((Cabin) ship.getComponent(getPosX() + 1, getPosY())).removeAlien();
                }
                if (((Cabin) ship.getComponent(getPosX() + 1, getPosY())).getLifeSupportSystemArrayList().isEmpty()) {
                    ((Cabin) ship.getComponent(getPosX() + 1, getPosY())).changeWithLifeSupport(false);
                }
            }
            ((Cabin) ship.getComponent(getPosX() + 1, getPosY())).removeLifeSupport(this);
        }
    }

    @Override
    public void place(ShipBoard ship) {
        if (ship.validPosition(getPosX()+1,getPosY()) && ship.getComponent(getPosX()+1,getPosY()) instanceof Cabin && !((Cabin) componentMatrix[row + 1][col]).getIsCentral()) {
            ((Cabin) componentMatrix[row + 1][col]).changeWithLifeSupport(true);
            ((Cabin) componentMatrix[row + 1][col]).addLifeSupport(((LifeSupportSystem) component));
        }
        if (validPosition(row - 1, col) && componentMatrix[row - 1][col] instanceof Cabin && !((Cabin) componentMatrix[row - 1][col]).getIsCentral()) {
            ((Cabin) componentMatrix[row - 1][col]).changeWithLifeSupport(true);
            ((Cabin) componentMatrix[row - 1][col]).addLifeSupport(((LifeSupportSystem) component));
        }
        if (validPosition(row, col + 1) && componentMatrix[row][col + 1] instanceof Cabin && !((Cabin) componentMatrix[row][col + 1]).getIsCentral()) {
            ((Cabin) componentMatrix[row][col + 1]).changeWithLifeSupport(true);
            ((Cabin) componentMatrix[row][col + 1]).addLifeSupport(((LifeSupportSystem) component));
        }
        if (validPosition(row, col - 1) && componentMatrix[row][col - 1] instanceof Cabin && !((Cabin) componentMatrix[row][col - 1]).getIsCentral()) {
            ((Cabin) componentMatrix[row][col - 1]).changeWithLifeSupport(true);
            ((Cabin) componentMatrix[row][col - 1]).addLifeSupport(((LifeSupportSystem) component));
        }
    }
}
