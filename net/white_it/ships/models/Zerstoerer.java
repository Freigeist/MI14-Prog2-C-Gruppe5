package net.white_it.ships.models;

/**
 * @author Florian Tenhaken <admin at white-it dot net>
 */
public class Zerstoerer extends Schiff {
    public Zerstoerer(boolean ausrichtung, int coordX, int coordY) {
        super(5, 3, 3, ausrichtung, coordX, coordY);
    }
}
