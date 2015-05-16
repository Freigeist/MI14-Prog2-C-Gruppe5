package net.white_it.ships.models;

public class UBoot extends Schiff {

    public final static int size = 2;

    public UBoot(boolean ausrichtung, int coordX, int coordY) {
        super(UBoot.size, 1, 1, ausrichtung, coordX, coordY);
    }
}
