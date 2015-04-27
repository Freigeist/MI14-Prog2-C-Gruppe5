package net.white_it.ships;

import net.white_it.ships.helper.IO;

public class Aktionen {
    /**
     * hier werden spieleinstellungen vorgenommen
     *
     * @since 1.0
     */
    public static void prepareGame() {
        System.out.print("Wie viele Spieler sollen teilnehmen? [2-6]");
        int spielerzahl;
        do {
            spielerzahl = IO.getInt();
        } while (spielerzahl < 2 || spielerzahl > 6);

        System.out.print("Wie groﬂ soll das Spielfeld sein? [5-20]");
        int spielfeldgroesse;
        do {
            spielfeldgroesse = IO.getInt();
        } while (spielfeldgroesse < 5 || spielfeldgroesse > 20);

        System.out.println("Es werden " + spielerzahl + " Spieler auf Feldern von " + spielfeldgroesse + "x" + spielfeldgroesse + " spielen.");
    }
}
