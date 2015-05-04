package net.white_it.ships;

import net.white_it.ships.collections.Schiffsammlung;
import net.white_it.ships.models.*;

/**
 * @author Florian Tenhaken <admin at white-it dot net>
 */
public class Main {
    public static void main(String[] args) {
        Schiffsammlung S = new Schiffsammlung();
        Spielfeld s = new Spielfeld(10,S);
        S.push(new UBoot(false,1,1));
        S.push(new Korvette(true, 4, 0));
        S.push(new UBoot(false,0,4));
        System.out.println(S);
        s.print(true);
    }
}
