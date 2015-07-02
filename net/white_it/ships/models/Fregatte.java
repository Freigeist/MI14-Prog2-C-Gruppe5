package net.white_it.ships.models;

import java.io.Serializable;

public class Fregatte extends Schiff implements Serializable {

    public final static int size = 4;

    public Fregatte(boolean isVertical, int coordX, int coordY) {
        super(Fregatte.size, 2, 2, isVertical, coordX, coordY);
    }
}
