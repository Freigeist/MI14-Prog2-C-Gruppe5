package net.white_it.ships;

import net.white_it.ships.collections.Schiffsammlung;
import net.white_it.ships.models.Spielfeld;
import net.white_it.ships.models.UBoot;

/**
 * @author Florian Tenhaken <admin at white-it dot net>
 */
public class Main {
    public static void main(String[] args) {
        Schiffsammlung S = new Schiffsammlung();
        Spielfeld s = new Spielfeld(4,S);
        S.push(new UBoot(false,1,1));
        S.push(new UBoot(true,3,2));
        S.push(new UBoot(true,0,2));
        s.print(true);
    }
}
