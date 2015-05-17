package net.white_it.ships;

import net.white_it.ships.collections.GameObjects;
import net.white_it.ships.collections.Schiffsammlung;
import net.white_it.ships.exceptions.AbortedByUserException;
import net.white_it.ships.exceptions.NoValidCoordinateException;
import net.white_it.ships.helper.IO;
import net.white_it.ships.models.*;

public class Aktionen {
    /**
     * hier werden spieleinstellungen vorgenommen
     *
     * @since 1.0
     */
    public static void prepareGame() {
        System.out.print("Wie viele Spieler sollen teilnehmen? [2-6]: ");
        int spielerzahl;
        do {
            spielerzahl = IO.getInt();
        } while (spielerzahl < 2 || spielerzahl > 6);

        System.out.print("Wie gro� soll das Spielfeld sein? [5-20]: ");
        int spielfeldgroesse;
        do {
            spielfeldgroesse = IO.getInt();
        } while (spielfeldgroesse < 5 || spielfeldgroesse > 20);

        int maxShips = Math.round(spielfeldgroesse / 3);
        int maxUBoot = (int) Math.floor(spielfeldgroesse / 2);
        int maxKorvette = (int) Math.floor(spielfeldgroesse / 3);
        int maxFregatte = (int) Math.floor(spielfeldgroesse / 4);
        int maxZerstoerer = (int) Math.floor(spielfeldgroesse / 5);

        System.out.println("Es k�nnen max. " + maxShips + " gesetzt werden, bitte entscheiden sie welche:\n");

        int Counter, anzahlUBoote, anzahlKorvetten, anzahlFregatten, anzahlZerstoerer;
        String check;
        boolean accepted = false;
        do {
            Counter = maxShips;
            anzahlUBoote = 0;
            anzahlKorvetten = 0;
            anzahlFregatten = 0;
            anzahlZerstoerer = 0;
            if (Counter > 0) {
                System.out.println("Ein UBoot ist 2 Felder lang, trifft ein Feld\n" +
                        "und muss dann 1 Runde aussetzen.");
                System.out.print("Wie viele UBoote sollen teilnehmen? [0-" + (Counter < maxUBoot ? Counter : maxUBoot) + "]: ");
                do {
                    anzahlUBoote = IO.getInt();
                } while (anzahlUBoote > Counter || anzahlUBoote > maxUBoot || anzahlUBoote < 0);
                Counter -= anzahlUBoote;
            }

            if (Counter > 0) {
                System.out.println("\nEine Korvette ist 3 Felder lang, trifft ein Feld\n" +
                        "und muss dann 1 Runde aussetzen.");
                System.out.print("Wie viele Korvetten sollen teilnehmen? [0-" + (Counter < maxKorvette ? Counter : maxKorvette) + "]: ");
                do {
                    anzahlKorvetten = IO.getInt();
                } while (anzahlKorvetten > Counter || anzahlKorvetten > maxKorvette || anzahlKorvetten < 0);
                Counter -= anzahlKorvetten;
            }

            if (Counter > 0) {
                System.out.println("\nEine Fregatte ist 4 Felder lang, trifft zwei Felder\n" +
                        "nebeinander und muss dann 2 Runde aussetzen.");
                System.out.print("Wie viele Fregatten sollen teilnehmen? [0-" + (Counter < maxFregatte ? Counter : maxFregatte) + "]: ");
                do {
                    anzahlFregatten = IO.getInt();
                } while (anzahlFregatten > Counter || anzahlFregatten > maxFregatte || anzahlFregatten < 0);
                Counter -= anzahlFregatten;
            }

            if (Counter > 0) {
                System.out.println("\nEin Zerstoerer ist 5 Felder lang, trifft drei Felder\n" +
                        "nebeinander und muss dann 3 Runde aussetzen.");
                System.out.print("Wie viele Zerstoerer sollen teilnehmen? [0-" + (Counter < maxZerstoerer ? Counter : maxZerstoerer) + "]: ");
                do {
                    anzahlZerstoerer = IO.getInt();
                } while (anzahlZerstoerer > Counter || anzahlZerstoerer > maxZerstoerer || anzahlZerstoerer < 0);
                Counter -= anzahlZerstoerer;
            }

            if (Counter == maxShips) {
                System.out.println("\n\nEs wurde keine Schiffe gewaehlt, die Auswahl wird wiederholt!\n");
                accepted = false;
            } else if (Counter > 0) {
                System.out.println("Sie haben noch " + Counter + " von " + maxShips + "Schiffen �brig,\n" +
                        "deren Typen sie nicht gewaehlt haben, moechten sie wirklich fortfahren? [j/n]: ");
                do {
                    check = IO.getString();
                } while (!check.matches("^[jJnN]$"));
                accepted = check.equalsIgnoreCase("j");
            } else {
                accepted = true;
            }
        } while (!accepted);


        System.out.println("Es werden " + spielerzahl + " Spieler auf Feldern von " +
                spielfeldgroesse + "x" + spielfeldgroesse + " spielen.");
        System.out.println("\nDabei werden Folgende Schiffe zum Einsatz kommen: ");
        System.out.println("\tUBoote: " + anzahlUBoote);
        System.out.println("\tKorvetten: " + anzahlKorvetten);
        System.out.println("\tFregatten: " + anzahlFregatten);
        System.out.println("\tZerstoerer: " + anzahlZerstoerer);

        String name;
        for (int i = 1; i <= spielerzahl; i++) {
            System.out.println("Bitte geben sie den Namen f�r Spieler" + i + " ein.");
            do {
                System.out.print("Name (min. 3 & max. 20 Zeichen): ");
                name = IO.getString();
            } while (name.length() < 3 || name.length() > 20);
            GameObjects.getSpieler().push(new Spieler(name, spielfeldgroesse));
        }

        System.out.println("\n\nNun folgt die Plazierung der Schiffe, bei der die Spieler nacheinander\n" +
                "ihre Schiffe auf dem Spielfeld platzieren k�nnen. Der Spieler kann jederzeit durch die\n" +
                "Eingabe von 'reset' mit der Platzierung von vorne beginnen.\n");

        Spieler spieler;
        Schiffsammlung schiffe;
        boolean isValid, isVertical;
        int[] coord = new int[0];
        for (int i = 0; i < spielerzahl; i++) {
            spieler = GameObjects.getSpieler().getSpielerByKey(i);
            schiffe = spieler.getSchiffe();
            System.out.println("Spieler " + (i + 1) + " ist nun dran seine Schiffe zu setzen.");
            IO.waitForReturn();

            do {
                accepted = true;
                schiffe.clear();
                ZerstoererLoop:
                for (int x = 0; x < anzahlZerstoerer; x++) {
                    spieler.printSpielfeld(true);
                    System.out.println("An welcher Stelle soll ein Zerstoerer plaziert werdern?");
                    do {
                        isValid = false;
                        try {
                            coord = _retriveCoords();
                            isVertical = _retriveAusrichtung();
                        } catch (AbortedByUserException ignored) {
                            accepted = false;
                            break ZerstoererLoop;
                        }
                        try {
                            if (spieler.tryPlaceShip(isVertical, Zerstoerer.size, coord[0], coord[1])) {
                                schiffe.push(new Zerstoerer(isVertical, coord[0], coord[1]));
                                isValid = true;
                            }else {
                                System.out.println("Das Schiff kann nicht direkt an ein anderes grenzen!");
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                            System.out.println("Das Schiff kann nicht aus dem Feld herausragen!");
                        }
                    } while (!isValid);
                }

                FregattenLoop:
                for (int x = 0; x < anzahlFregatten && accepted; x++) {
                    spieler.printSpielfeld(true);
                    System.out.println("An welcher Stelle soll eine Fregatte plaziert werdern?");
                    do {
                        isValid = false;
                        try {
                            coord = _retriveCoords();
                            isVertical = _retriveAusrichtung();
                        } catch (AbortedByUserException ignored) {
                            accepted = false;
                            break FregattenLoop;
                        }
                        try {
                            if (spieler.tryPlaceShip(isVertical, Fregatte.size, coord[0], coord[1])) {
                                schiffe.push(new Fregatte(isVertical, coord[0], coord[1]));
                                isValid = true;
                            }else {
                                System.out.println("Das Schiff kann nicht direkt an ein anderes grenzen!");
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                            System.out.println("Das Schiff kann nicht aus dem Feld herausragen!");
                        }
                    } while (!isValid);
                }

                KorvettenLoop:
                for (int x = 0; x < anzahlKorvetten && accepted; x++) {
                    spieler.printSpielfeld(true);
                    System.out.println("An welcher Stelle soll eine Korvette plaziert werdern?");
                    do {
                        isValid = false;
                        try {
                            coord = _retriveCoords();
                            isVertical = _retriveAusrichtung();
                        } catch (AbortedByUserException ignored) {
                            accepted = false;
                            break KorvettenLoop;
                        }
                        try {
                            if (spieler.tryPlaceShip(isVertical, Korvette.size, coord[0], coord[1])) {
                                schiffe.push(new Korvette(isVertical, coord[0], coord[1]));
                                isValid = true;
                            }else {
                                System.out.println("Das Schiff kann nicht direkt an ein anderes grenzen!");
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                            System.out.println("Das Schiff kann nicht aus dem Feld herausragen!");
                        }
                    } while (!isValid);
                }

                UBootLoop:
                for (int x = 0; x < anzahlUBoote && accepted; x++) {
                    spieler.printSpielfeld(true);
                    System.out.println("An welcher Stelle soll ein Uboot plaziert werdern?");
                    do {
                        isValid = false;
                        try {
                            coord = _retriveCoords();
                            isVertical = _retriveAusrichtung();
                        } catch (AbortedByUserException ignored) {
                            accepted = false;
                            break UBootLoop;
                        }
                        try {
                            if (spieler.tryPlaceShip(isVertical, UBoot.size, coord[0], coord[1])) {
                                schiffe.push(new UBoot(isVertical, coord[0], coord[1]));
                                isValid = true;
                            } else {
                                System.out.println("Das Schiff kann nicht direkt an ein anderes grenzen!");
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                            System.out.println("Das Schiff kann nicht aus dem Feld herausragen!");
                        }
                    } while (!isValid);
                }

                if(accepted) {
                    spieler.printSpielfeld(true);
                    System.out.print("\nDies waere ihr Spielfeld, m�chten sie das setzen wiederholen? [j/n]: ");
                    do {
                        isValid = false;
                        check = IO.getString();
                        if (check.matches("[jJnN]")) {
                            isValid = true;
                            accepted = check.equalsIgnoreCase("n");
                        }
                    } while (!isValid);
                }
            } while (!accepted);
        }


    }

    /**
     * Hilfsfunktion zur Vermeidung von unn�tigen doppelten Code
     *
     * @return
     * @throws AbortedByUserException
     */
    private static int[] _retriveCoords() throws AbortedByUserException {
        boolean isValid;
        String check;
        int[] coord = new int[0];

        System.out.print("Position [z.B. A1 oder auch 1A oder 'reset']: ");

        do {
            isValid = false;
            check = IO.getString();
            if (check.equalsIgnoreCase("reset")) {
                throw new AbortedByUserException();
            }
            try {
                coord = Spielfeld.coordToXY(check);
                isValid = true;
            } catch (NoValidCoordinateException e) {
                System.out.println("'" + check + "' ist keine valide Koordinate!");
            }
        } while (!isValid);

        return coord;
    }

    private static boolean _retriveAusrichtung() throws AbortedByUserException {
        boolean isValid, ret = false;
        String check;

        System.out.print("Ausrichtung [h/v oder 'reset']: ");

        do {
            isValid = false;
            check = IO.getString();
            if (check.equalsIgnoreCase("reset")) {
                throw new AbortedByUserException();
            }
            if (check.matches("[hHvV]")) {
                isValid = true;
                ret = check.equalsIgnoreCase("v");
            }
        } while (!isValid);

        return ret;
    }
}
