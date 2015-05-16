package net.white_it.ships;

import net.white_it.ships.collections.GameObjects;
import net.white_it.ships.helper.IO;
import net.white_it.ships.models.Spieler;

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

        System.out.print("Wie groß soll das Spielfeld sein? [5-20]");
        int spielfeldgroesse;
        do {
            spielfeldgroesse = IO.getInt();
        } while (spielfeldgroesse < 5 || spielfeldgroesse > 20);

        int maxShips = Math.round(spielfeldgroesse / 3);
        int maxUBoot = (int) Math.floor(spielfeldgroesse / 2);
        int maxKorvette = (int) Math.floor(spielfeldgroesse / 3);
        int maxFregatte = (int) Math.floor(spielfeldgroesse / 4);
        int maxZerstoerer = (int) Math.floor(spielfeldgroesse / 5);

        System.out.println("Es können max. " + maxShips + " gesetzt werden, bitte entscheiden sie welche:");

        int Counter, UBoote, Korvetten, Fregatten, Zerstoerer;
        String check;
        boolean accepted = false;
        do {
            Counter = maxShips;
            UBoote = 0;
            Korvetten = 0;
            Fregatten = 0;
            Zerstoerer = 0;
            if (Counter > 0) {
                System.out.println("Ein UBoot ist 2 Felder lang, trifft ein Feld\n" +
                        "und muss dann 1 Runde aussetzen.");
                System.out.print("Wie viele UBoote sollen teilnehmen? [0-" + (Counter < maxUBoot ? Counter : maxUBoot) + "]: ");
                do {
                    UBoote = IO.getInt();
                } while (UBoote <= Counter || UBoote <= maxUBoot || UBoote >= 0);
                Counter -= UBoote;
            }

            if (Counter > 0) {
                System.out.println("Eine Korvette ist 3 Felder lang, trifft ein Feld\n" +
                        "und muss dann 1 Runde aussetzen.");
                System.out.print("Wie viele Korvetten sollen teilnehmen? [0-" + (Counter < maxKorvette ? Counter : maxKorvette) + "]: ");
                do {
                    Korvetten = IO.getInt();
                } while (Korvetten <= Counter || Korvetten <= maxKorvette || Korvetten >= 0);
                Counter -= Korvetten;
            }

            if (Counter > 0) {
                System.out.println("Eine Fregatte ist 4 Felder lang, trifft zwei Felder nebeinander\n" +
                        "und muss dann 2 Runde aussetzen.");
                System.out.print("Wie viele Fregatten sollen teilnehmen? [0-" + (Counter < maxFregatte ? Counter : maxFregatte) + "]: ");
                do {
                    Fregatten = IO.getInt();
                } while (Fregatten <= Counter || Fregatten <= maxFregatte || Fregatten >= 0);
                Counter -= Fregatten;
            }

            if (Counter > 0) {
                System.out.println("Ein Zerstoerer ist 5 Felder lang, trifft drei Felder nebeinander\n" +
                        "und muss dann 3 Runde aussetzen.");
                System.out.print("Wie viele Zerstoerer sollen teilnehmen? [0-" + (Counter < maxZerstoerer ? Counter : maxZerstoerer) + "]: ");
                do {
                    Zerstoerer = IO.getInt();
                } while (Zerstoerer <= Counter || Zerstoerer <= maxZerstoerer || Zerstoerer >= 0);
                Counter -= Zerstoerer;
            }

            if(Counter > 0) {
                System.out.println("Sie haben noch " + Counter + " von " + maxShips + "Schiffen übrig,\n" +
                        "deren Typen sie nicht gewaehlt haben, moechten sie wirklich fortfahren? [j/n]: ");
                do {
                    check = IO.getString();
                } while (!check.matches("^[jJnN]$"));
                accepted = check.equalsIgnoreCase("j");
            } else if (Counter == maxShips) {
                System.out.println("Es wurde keine Schiffe gewaehlt, die Auswahl wird wiederholt!");
                accepted = false;
            }
        } while (!accepted);


        System.out.println("Es werden " + spielerzahl + " Spieler auf Feldern von " +
                spielfeldgroesse + "x" + spielfeldgroesse + " spielen.");
        System.out.println("\nDabei werden Folgende Schiffe zum Einsatz kommen: ");
        System.out.println("\tUBoote: " + UBoote);
        System.out.println("\tKorvetten: " + Korvetten);
        System.out.println("\tFregatten: " + Fregatten);
        System.out.println("\tZerstoerer: " + Zerstoerer);

        String name;
        for(int i = 1; i <= spielerzahl; i++){
            System.out.println("Bitte geben sie den Namen für Spieler" + i + " ein.");
            do {
                System.out.print("Name (min. 3 & max. 20 Zeichen): ");
                name = IO.getString();
            } while (name.length() < 3 || name.length() > 20);
            GameObjects.getSpieler().push(new Spieler(name,spielfeldgroesse));
        }
    }
}
