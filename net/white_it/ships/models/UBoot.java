package net.white_it.ships.models;

/**
 * @author Florian Tenhaken <admin at white-it dot net>
 */
public class UBoot extends Schiff {
    public UBoot(boolean ausrichtung, int coordX, int coordY) {
        super(2, 1, 1, ausrichtung, coordX, coordY);
    }
}
