package net.white_it.ships.models;

import java.io.Serializable;

public class Zerstoerer extends Schiff implements Serializable {

    public final static int size = 5;

    public Zerstoerer(boolean isVertical, int coordX, int coordY) {
        super(Zerstoerer.size, 3, 3, isVertical, coordX, coordY);
    }
}
