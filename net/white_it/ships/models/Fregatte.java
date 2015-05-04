package net.white_it.ships.models;

/**
 * @author Florian Tenhaken <admin at white-it dot net>
 */
public class Fregatte extends Schiff {

    public Fregatte(boolean ausrichtung, int coordX, int coordY) {
        super(4, 2, 2, ausrichtung, coordX, coordY);
    }
}
