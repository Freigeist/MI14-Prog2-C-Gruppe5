package net.white_it.ships.models;

public class Zerstoerer extends Schiff {

    public final static int size = 5;

    public Zerstoerer(boolean isVertical, int coordX, int coordY) {
        super(Zerstoerer.size, 3, 3, isVertical, coordX, coordY);
    }
}
