package net.white_it.ships.models;

public class Korvette extends Schiff {
    public final static int size = 3;

    public Korvette(boolean ausrichtung, int coordX, int coordY) {
        super(Korvette.size, 1, 1, ausrichtung, coordX, coordY);
    }
}
