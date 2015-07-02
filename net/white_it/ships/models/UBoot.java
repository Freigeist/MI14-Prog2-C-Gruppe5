package net.white_it.ships.models;

import java.io.Serializable;

public class UBoot extends Schiff implements Serializable {

    public final static int size = 2;

    public UBoot(boolean isVertical, int coordX, int coordY) {
        super(UBoot.size, 1, 1, isVertical, coordX, coordY);
    }
}
