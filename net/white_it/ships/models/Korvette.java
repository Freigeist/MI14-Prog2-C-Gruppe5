package net.white_it.ships.models;

/**
 * @author Florian Tenhaken <admin at white-it dot net>
 */
public class Korvette extends Schiff {
    public Korvette(boolean ausrichtung, int coordX, int coordY) {
        super(3, 1, 1, ausrichtung, coordX, coordY);
    }
}
