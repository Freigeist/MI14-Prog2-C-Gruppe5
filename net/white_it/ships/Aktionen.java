package net.white_it.ships;

import java.io.Serializable;

import net.white_it.ships.collections.GameObjects;
import net.white_it.ships.collections.Schiffsammlung;
import net.white_it.ships.collections.Spielersammlung;
import net.white_it.ships.exceptions.AbortedByUserException;
import net.white_it.ships.exceptions.NoValidCoordinateException;
import net.white_it.ships.helper.IO;
import net.white_it.ships.helper.SaveLoad;
import net.white_it.ships.models.*;

public class Aktionen implements Serializable {
    /**
     * hier werden spieleinstellungen vorgenommen
     *
     * @since 1.0
     */
    private static int spielfeldgroesse;
    private static int spielerzahl;
    private static boolean isComputer;
    private static boolean accepted = false;
    private static int Counter, anzahlUBoote, anzahlKorvetten, anzahlFregatten, anzahlZerstoerer;
    private static String check;

    public static void prepareGame() {
        System.out.println("M\u00F6chten Sie den letzten Spielstand laden? [j/n]");
        String check1;
        boolean accepted1 = false;
        do {
            check1 = IO.getString();
        } while (!check1.matches("^[jJnN]$"));
        accepted1 = check1.equalsIgnoreCase("j");

        if (accepted1) {
            SaveLoad.load();
        } else {

        }

        if (!accepted1) {
            System.out.print("Wie viele Spieler sollen teilnehmen? [2-6]: ");
            do {
                spielerzahl = IO.getInt();
            } while (spielerzahl < 2 || spielerzahl > 6);

            do {
                System.out.print("Wie gro\u00DF soll das Spielfeld sein? [5-20]: ");
                spielfeldgroesse = IO.getInt();
            } while (spielfeldgroesse < 5 || spielfeldgroesse > 20);

            int maxShips = Math.round(spielfeldgroesse / 3);
            int maxUBoot = (int) Math.floor(spielfeldgroesse / UBoot.size);
            int maxKorvette = (int) Math.floor(spielfeldgroesse / Korvette.size);
            int maxFregatte = (int) Math.floor(spielfeldgroesse / Fregatte.size);
            int maxZerstoerer = (int) Math.floor(spielfeldgroesse / Zerstoerer.size);

            System.out.println("Es k\u00F6nnen max. " + maxShips + " gesetzt werden, bitte entscheiden sie welche:\n");
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
                    System.out.println("\n\nEs wurde keine Schiffe gew\u00E4hlt, die Auswahl wird wiederholt!\n");
                    accepted = false;
                } else if (Counter > 0) {
                    System.out.print("Sie haben noch " + Counter + " von " + maxShips + "Schiffen \u00FCbrig,\n" +
                            "deren Typen sie nicht gew\u00E4hlt haben, m\u00F6chten sie wirklich fortfahren? [j/n]: ");
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
            String isPcAbfrage;
            boolean isValid_test;
            for (int i = 1; i <= spielerzahl; i++) {
                System.out.println("Bitte geben sie den Namen f\u00FCr Spieler" + i + " ein.");
                do {
                    System.out.print("Name (min. 3 & max. 20 Zeichen): ");
                    name = IO.getString();
                } while (name.length() < 3 || name.length() > 20);

                System.out.println("Soll " + name + " ein Computer sein?");

                do {
                    System.out.print("J/N");

                    isPcAbfrage = IO.getString();

                    if (isPcAbfrage.equalsIgnoreCase("j")) {
                        System.out.println("Der Spieler " + name + " ist ein Computer.");
                        isValid_test = true;
                        isComputer = true;
                    } else if (isPcAbfrage.equalsIgnoreCase("n")) {
                        System.out.println("Der Spieler " + name + " ist kein Computer.");
                        isValid_test = true;
                        isComputer = false;
                    } else {
                        isValid_test = false;
                        isComputer = false;
                    }

                } while (!isValid_test);


                GameObjects.getSpieler().push(new Spieler(name, spielfeldgroesse, isComputer));
            }
        }

        System.out.println("\n\nNun folgt die Plazierung der Schiffe, bei der die Spieler nacheinander\n" +
                "ihre Schiffe auf dem Spielfeld platzieren k\u00F6nnen. Der Spieler kann jederzeit\n" +
                "durch die Eingabe von 'reset' mit der Platzierung von vorne beginnen.\n");

        Spieler spieler;
        Schiffsammlung schiffe;
        boolean isValid, isVertical;
        int[] coord = new int[0];
        System.out.println(spielerzahl);
        for (int i = 0; i < spielerzahl; i++) {
            spieler = GameObjects.getSpieler().getSpielerByKey(i);
            schiffe = spieler.getSchiffe();
            isComputer = spieler.getIsComputer();
            System.out.println("Spieler " + (i + 1) + " ist nun dran seine Schiffe zu setzen.");
            IO.waitForReturn(isComputer);

            do {
                accepted = true;
                schiffe.clear();
                ZerstoererLoop:
                for (int x = 0; x < anzahlZerstoerer; x++) {
                    spieler.getSpielfeld().print(true);
                    System.out.println("An welcher Stelle soll ein Zerstoerer plaziert werdern?");
                    do {
                        isValid = false;
                        try {
                            coord = _retriveCoords(true, spieler.getIsComputer());
                            isVertical = _retriveAusrichtung(true, spieler.getIsComputer());
                        } catch (AbortedByUserException ignored) {
                            accepted = false;
                            break ZerstoererLoop;
                        }
                        try {
                            if (spieler.getSpielfeld().tryPlaceShip(isVertical, Zerstoerer.size, coord[0], coord[1])) {
                                schiffe.push(new Zerstoerer(isVertical, coord[0], coord[1]));
                                isValid = true;
                            } else {
                                System.out.println("Das Schiff kann nicht direkt an ein anderes grenzen!");
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                            System.out.println("Das Schiff kann nicht aus dem Feld herausragen!");
                        }
                    } while (!isValid);
                }

                FregattenLoop:
                for (int x = 0; x < anzahlFregatten && accepted; x++) {
                    spieler.getSpielfeld().print(true);
                    System.out.println("An welcher Stelle soll eine Fregatte plaziert werdern?");
                    do {
                        isValid = false;
                        try {
                            coord = _retriveCoords(true, spieler.getIsComputer());
                            isVertical = _retriveAusrichtung(true, spieler.getIsComputer());
                        } catch (AbortedByUserException ignored) {
                            accepted = false;
                            break FregattenLoop;
                        }
                        try {
                            if (spieler.getSpielfeld().tryPlaceShip(isVertical, Fregatte.size, coord[0], coord[1])) {
                                schiffe.push(new Fregatte(isVertical, coord[0], coord[1]));
                                isValid = true;
                            } else {
                                System.out.println("Das Schiff kann nicht direkt an ein anderes grenzen!");
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                            System.out.println("Das Schiff kann nicht aus dem Feld herausragen!");
                        }
                    } while (!isValid);
                }

                KorvettenLoop:
                for (int x = 0; x < anzahlKorvetten && accepted; x++) {
                    spieler.getSpielfeld().print(true);
                    System.out.println("An welcher Stelle soll eine Korvette plaziert werdern?");
                    do {
                        isValid = false;
                        try {
                            coord = _retriveCoords(true, spieler.getIsComputer());
                            isVertical = _retriveAusrichtung(true, spieler.getIsComputer());
                        } catch (AbortedByUserException ignored) {
                            accepted = false;
                            break KorvettenLoop;
                        }
                        try {
                            if (spieler.getSpielfeld().tryPlaceShip(isVertical, Korvette.size, coord[0], coord[1])) {
                                schiffe.push(new Korvette(isVertical, coord[0], coord[1]));
                                isValid = true;
                            } else {
                                System.out.println("Das Schiff kann nicht direkt an ein anderes grenzen!");
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                            System.out.println("Das Schiff kann nicht aus dem Feld herausragen!");
                        }
                    } while (!isValid);
                }

                UBootLoop:
                for (int x = 0; x < anzahlUBoote && accepted; x++) {
                    spieler.getSpielfeld().print(true);
                    System.out.println("An welcher Stelle soll ein Uboot plaziert werdern?");
                    do {
                        isValid = false;
                        try {
                            coord = _retriveCoords(true, spieler.getIsComputer());
                            isVertical = _retriveAusrichtung(true, spieler.getIsComputer());
                        } catch (AbortedByUserException ignored) {
                            accepted = false;
                            break UBootLoop;
                        }
                        try {
                            if (spieler.getSpielfeld().tryPlaceShip(isVertical, UBoot.size, coord[0], coord[1])) {
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

                if (accepted) {
                    spieler.getSpielfeld().print(true);

                    if (isComputer) {
                        isValid = true;
                        accepted = true;
                    } else {
                        System.out.print("\nDies w\u00E4re ihr Spielfeld, m\u00F6chten sie das setzen wiederholen? [j/n]: ");
                        do {
                            isValid = false;
                            check = IO.getString();
                            if (check.matches("[jJnN]")) {
                                isValid = true;
                                accepted = check.equalsIgnoreCase("n");
                            }
                        } while (!isValid);

                    }
                }
            } while (!accepted);

        }

        if (!accepted1) {
            spielstand(); // nachdem das feld eingerichtet ist, wird gespeichert
        }
    }

    public static void gameLoop() {
        Spielersammlung spieler = GameObjects.getSpieler();

        Spieler prevPlayer;
        Spieler activePlayer = null;
        Spieler enemyPlayer;

        Schiff activeShip;

        boolean isVertical;

        int inputI;
        int[] coords;

        while (true) {
            prevPlayer = activePlayer;

            do {
                activePlayer = spieler.getNext();
            } while (!activePlayer.isAlive() && activePlayer != prevPlayer);

            if (activePlayer == prevPlayer) {
                System.out.println("'" + activePlayer.getName() + "' ist der letzte lebende Spieler und hat damit gewonnen!");
                break;
            }

            activePlayer.preTurn();

            if (activePlayer.hasActiveShips()) {
                System.out.println("'" + activePlayer.getName() + "' ist nun am Zug!");

                IO.waitForReturn(activePlayer.getIsComputer());

                while (true) {
                    spieler.printPlayerList(true, true);
                    System.out.print("\nBitte w\u00E4hlen sie den Spieler den sie angreifen wollen: ");

                    if (activePlayer.getIsComputer()) {
                        int[] IDs = spieler.getPlayerIDs(true,true);
                        inputI = IDs[(int)(Math.random() * IDs.length)];    //random
                        System.out.println(inputI);

                    } else {
                        inputI = IO.getInt();
                    }
                    try {
                        enemyPlayer = spieler.getSpielerByKey(inputI);
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                        enemyPlayer = null;
                    }
                    if (enemyPlayer == null) {
                        System.out.println("Keinen g\u00FCltigen Spieler gew\u00E4hlt!\n");
                    } else if (enemyPlayer == activePlayer) {
                        System.out.println("Sie haben sich selbst gew\u00E4hlt!\n");
                    } else if (!enemyPlayer.isAlive()) {
                        System.out.println("Dieser Spieler ist bereits ausgeschieden!\n");
                    } else {
                        System.out.println("");
                        break;
                    }

                }

                while (true) {
                    activePlayer.getSchiffe().printShipList();
                    System.out.print("Bitte w\u00E4hlen sie das Schiff mit dem sie schie\u00DFen wollen: ");

                    if (activePlayer.getIsComputer()) {
                        int[] IDs = activePlayer.getSchiffe().getSchiffIDs(true,true);
                        inputI = IDs[(int)(Math.random() * IDs.length)];    //random
                        System.out.println(inputI);
                    } else {
                        inputI = IO.getInt();
                    }
                    try {
                        activeShip = activePlayer.getSchiffe().getShipByKey(inputI);
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                        activeShip = null;
                    }
                    if (activeShip == null) {
                        System.out.println("Keinen g\u00FCltiges Schiff gew\u00E4hlt!\n");
                    } else if (!activeShip.isActive()) {
                        System.out.println("Dieses Schiff ist noch inaktiv!\n");
                    } else if (!activeShip.isAlive()) {
                        System.out.println("Dieses Schiff ist gesunken!\n");
                    } else {
                        System.out.println("");
                        break;
                    }
                }

                enemyPlayer.getSpielfeld().print(false);
                System.out.println("Wohin m\u00F6chten sie schie\u00DFen?");
                coords = _retriveCoords(false, activePlayer.getIsComputer());

                if (activeShip.getSchussbreite() > 1) {
                    System.out.println("M\u00F6chten sie horizontal oder vertikal schie\u00DFen?");
                    isVertical = _retriveAusrichtung(false, activePlayer.getIsComputer());
                } else {
                    isVertical = false;
                }

                enemyPlayer.getSpielfeld().fire(coords[0], coords[1], activeShip.getSchussbreite(), isVertical);
                activeShip.hasFired();
            } else {
                System.out.println("\n'" + activePlayer.getName() + "' muss diese Runde aussetzen, da keine aktiven\n" +
                        "Schiffe vorhanden sind und nicht geschossen werden kann!\n");
            }
        }
    }

    /**
     * Hilfsfunktion zur Vermeidung von unn\u00F6tigen doppelten Code
     *
     * @return
     * @throws AbortedByUserException
     */
    private static int[] _retriveCoords(boolean allowReset, boolean isComputer) throws AbortedByUserException {
        boolean isValid;
        String check;
        int[] coord = new int[0];

        do {
            isValid = false;

            if (isComputer) {
                check = getCoordsForKI();
                System.out.println(check);
            } else {
                System.out.print("Position [z.B. A1 oder auch 1A" + (allowReset ? " oder 'reset'" : "") + "]: ");
                check = IO.getString();
            }
            if (allowReset && check.equalsIgnoreCase("reset")) {
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

    /**
     * Weitere Funktion zur Vermeidung von unn\u00F6tigen doppelten Code
     *
     * @param isComputer
     * @return
     * @throws AbortedByUserException
     */
    private static boolean _retriveAusrichtung(boolean allowReset, boolean isComputer) throws AbortedByUserException {
        boolean isValid, ret = false;
        String check;

        do {
            isValid = false;
            if (isComputer) {
                check = getAusrichtungForKI();
                System.out.println(check);
            } else {
                System.out.print("Ausrichtung [h/v" + (allowReset ? " oder 'reset'" : "") + "]: ");
                check = IO.getString();
            }
            if (allowReset && check.equalsIgnoreCase("reset")) {
                throw new AbortedByUserException();
            }
            if (check.matches("[hHvV]")) {
                isValid = true;
                ret = check.equalsIgnoreCase("v");
            }

        } while (!isValid);

        return ret;
    }


    public static String getCoordsForKI() {
        String[] abc = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u"};
        int max = getSpielfeldgroesse();
        int min = 0;
        String letter = abc[(int) Math.round(Math.random() * (max - min) + min)];
        String number = "" + (int) (Math.random() * max + 1);

        return number + letter;
    }

    public static String getAusrichtungForKI() {
        String ausrichtung;
        int number = (int) (Math.random() * 100);

        if (number % 2 == 0) {
            ausrichtung = "v";
        } else {
            ausrichtung = "h";
        }

        return ausrichtung;
    }

    static int getSpielfeldgroesse() {
        return spielfeldgroesse;
    }

    public static void spielstand() {
        System.out.println("Möchten sie an dieser Stelle speichern? [j/n]");
        String check;
        boolean accepted;

        do {
            check = IO.getString();
        } while (!check.matches("^[jJnN]$"));
        accepted = check.equalsIgnoreCase("j");

        if (accepted) {
            SaveLoad.save(GameObjects.getSpieler());
            System.out.println("Ihr Spielstand wurde abgespeichert");
        }

    }
}