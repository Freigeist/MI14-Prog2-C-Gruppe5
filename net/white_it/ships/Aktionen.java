package net.white_it.ships;

import java.io.Serializable;

import net.white_it.ships.collections.GameObjects;
import net.white_it.ships.collections.Schiffsammlung;
import net.white_it.ships.collections.Spielersammlung;
import net.white_it.ships.exceptions.AbortedByUserException;
import net.white_it.ships.exceptions.NoValidCoordinateException;
import net.white_it.ships.gui.Hauptfenster;
import net.white_it.ships.helper.IO;
import net.white_it.ships.helper.SaveLoad;
import net.white_it.ships.helper.SettingsHelper;
import net.white_it.ships.models.*;

public class Aktionen implements Serializable {
    /**
     * hier werden spieleinstellungen vorgenommen
     *
     * @since 1.0
     */

    private static Hauptfenster fenster;

    public static void prepareGame() {
        fenster = Hauptfenster.getInstance();

        String check;
        check = _getStringFromGUI();
        int spielerzahl, anzahlUBoote, anzahlFregatten, anzahlKorvetten, anzahlZerstoerer;

        if (check.equalsIgnoreCase("j")) {
            SaveLoad.load();
            fenster.showBattlefield();
            return;
        } else {
            fenster.showGameSettings();

            SettingsHelper s;
            do {
                s = fenster.getSettingsReturnValue();
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (s == null);

            fenster.showBattlefield();

            spielerzahl = s.getPlayerCount();
            anzahlUBoote = s.getUboote();
            anzahlKorvetten = s.getKorvetten();
            anzahlFregatten = s.getFregatten();
            anzahlZerstoerer = s.getZerstoerer();

            for (int i = 0; i < spielerzahl; i++) {
                GameObjects.getSpieler().push(new Spieler(s.getNames()[i], s.getFieldSize(), s.getIsBot()[i]));
            }
        }

        System.out.println(fenster);
        fenster.updateInformationLabel("","","Es folgt nun die Platzierung der Schiffe");
        _waitForContinue(false);

        Spieler spieler;
        Schiffsammlung schiffe;
        boolean accepted, isComputer, isValid, isVertical;
        int[] coord = new int[0];
        for (int i = 0; i < spielerzahl; i++) {
            spieler = GameObjects.getSpieler().getSpielerByKey(i);
            schiffe = spieler.getSchiffe();
            isComputer = spieler.getIsComputer();
            fenster.updateInformationLabel(spieler.getName(),"","Es werden nun die Schiffe plaziert");
            _waitForContinue(isComputer);

            do {
                accepted = true;
                schiffe.clear();
                ZerstoererLoop:
                for (int x = 0; x < anzahlZerstoerer; x++) {
                    fenster.buildBattlefieldFromSpielfeld(spieler.getSpielfeld(), true);
                    fenster.updateInformationLabel(spieler.getName(), "", "An welcher Stelle soll ein Zerstoerer plaziert werdern?");
                    do {
                        isValid = false;
                        try {
                            coord = _retriveCoordsFromGui(true, spieler.getIsComputer());
                            isVertical = _retriveAusrichtungFromGui(true, spieler.getIsComputer());
                        } catch (AbortedByUserException ignored) {
                            accepted = false;
                            break ZerstoererLoop;
                        }
                        try {
                            if (spieler.getSpielfeld().tryPlaceShip(isVertical, Zerstoerer.size, coord[0], coord[1])) {
                                schiffe.push(new Zerstoerer(isVertical, coord[0], coord[1]));
                                isValid = true;
                            } else {
                                fenster.updateInformationLabel(spieler.getName(), "", "Das Schiff kann nicht direkt an ein anderes grenzen! An welcher Stelle soll ein Zerstoerer plaziert werdern?");
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                            fenster.updateInformationLabel(spieler.getName(), "", "Das Schiff kann nicht aus dem Feld herausragen! An welcher Stelle soll ein Zerstoerer plaziert werdern?");
                        }
                    } while (!isValid);
                }

                FregattenLoop:
                for (int x = 0; x < anzahlFregatten && accepted; x++) {
                    fenster.buildBattlefieldFromSpielfeld(spieler.getSpielfeld(), true);
                    fenster.updateInformationLabel(spieler.getName(), "", "An welcher Stelle soll eine Fregatte plaziert werdern?");
                    do {
                        isValid = false;
                        try {
                            coord = _retriveCoordsFromGui(true, spieler.getIsComputer());
                            isVertical = _retriveAusrichtungFromGui(true, spieler.getIsComputer());
                        } catch (AbortedByUserException ignored) {
                            accepted = false;
                            break FregattenLoop;
                        }
                        try {
                            if (spieler.getSpielfeld().tryPlaceShip(isVertical, Fregatte.size, coord[0], coord[1])) {
                                schiffe.push(new Fregatte(isVertical, coord[0], coord[1]));
                                isValid = true;
                            } else {
                                fenster.updateInformationLabel(spieler.getName(), "", "Das Schiff kann nicht direkt an ein anderes grenzen! An welcher Stelle soll eine Fregatte plaziert werdern?");
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                            fenster.updateInformationLabel(spieler.getName(), "", "Das Schiff kann nicht aus dem Feld herausragen! An welcher Stelle soll eine Fregatte plaziert werdern?");
                        }
                    } while (!isValid);
                }

                KorvettenLoop:
                for (int x = 0; x < anzahlKorvetten && accepted; x++) {
                    fenster.buildBattlefieldFromSpielfeld(spieler.getSpielfeld(), true);
                    fenster.updateInformationLabel(spieler.getName(), "", "An welcher Stelle soll eine Korvette plaziert werdern?");
                    do {
                        isValid = false;
                        try {
                            coord = _retriveCoordsFromGui(true, spieler.getIsComputer());
                            isVertical = _retriveAusrichtungFromGui(true, spieler.getIsComputer());
                        } catch (AbortedByUserException ignored) {
                            accepted = false;
                            break KorvettenLoop;
                        }
                        try {
                            if (spieler.getSpielfeld().tryPlaceShip(isVertical, Korvette.size, coord[0], coord[1])) {
                                schiffe.push(new Korvette(isVertical, coord[0], coord[1]));
                                isValid = true;
                            } else {
                                fenster.updateInformationLabel(spieler.getName(), "", "Das Schiff kann nicht direkt an ein anderes grenzen! An welcher Stelle soll eine Korvette plaziert werdern?");
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                            fenster.updateInformationLabel(spieler.getName(), "", "Das Schiff kann nicht aus dem Feld herausragen! An welcher Stelle soll eine Korvette plaziert werdern?");
                        }
                    } while (!isValid);
                }

                UBootLoop:
                for (int x = 0; x < anzahlUBoote && accepted; x++) {
                    fenster.buildBattlefieldFromSpielfeld(spieler.getSpielfeld(), true);
                    fenster.updateInformationLabel(spieler.getName(), "", "An welcher Stelle soll ein Uboot plaziert werdern?");
                    do {
                        isValid = false;
                        try {
                            coord = _retriveCoordsFromGui(true, spieler.getIsComputer());
                            isVertical = _retriveAusrichtungFromGui(true, spieler.getIsComputer());
                        } catch (AbortedByUserException ignored) {
                            accepted = false;
                            break UBootLoop;
                        }
                        try {
                            if (spieler.getSpielfeld().tryPlaceShip(isVertical, UBoot.size, coord[0], coord[1])) {
                                schiffe.push(new UBoot(isVertical, coord[0], coord[1]));
                                isValid = true;
                            } else {
                                fenster.updateInformationLabel(spieler.getName(), "", "Das Schiff kann nicht direkt an ein anderes grenzen! An welcher Stelle soll ein Uboot plaziert werdern?");
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                            fenster.updateInformationLabel(spieler.getName(), "", "Das Schiff kann nicht aus dem Feld herausragen! An welcher Stelle soll ein Uboot plaziert werdern?");
                        }
                    } while (!isValid);
                }

                if (accepted) {
                    fenster.buildBattlefieldFromSpielfeld(spieler.getSpielfeld(), true);

                    if (isComputer) {
                        isValid = true;
                        accepted = true;
                    } else {
                        fenster.updateInformationLabel(spieler.getName(), "", "Dies w\u00E4re ihr Spielfeld, m\u00F6chten sie das setzen wiederholen?");
                        do {
                            isValid = false;
                            fenster.showJNSelect();
                            check = _getStringFromGUI();
                            if (check.matches("[jJnN]")) {
                                isValid = true;
                                accepted = check.equalsIgnoreCase("n");
                            }
                        } while (!isValid);

                    }
                }
            } while (!accepted);
        }

        spielstandGui(); // nachdem das feld eingerichtet ist, wird gespeichert
    }

    public static void gameLoop() {
        fenster = Hauptfenster.getInstance();
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
                fenster.updateInformationLabel("", "", "'" + activePlayer.getName() + "' ist der letzte lebende Spieler und hat damit gewonnen!");
                break;
            }

            activePlayer.preTurn();

            if (activePlayer.hasActiveShips()) {
                fenster.updateInformationLabel(activePlayer.getName(), "", "'" + activePlayer.getName() + "' ist nun am Zug!");

                _waitForContinue(activePlayer.getIsComputer());

                fenster.updateInformationLabel(activePlayer.getName(), "", "Bitte w\u00E4hlen sie den Spieler den sie angreifen wollen");
                while (true) {
                    if (activePlayer.getIsComputer()) {
                        int[] IDs = spieler.getPlayerIDs(true, true);
                        inputI = IDs[(int) (Math.random() * IDs.length)];    //random
                        System.out.println(inputI);

                    } else {
                        fenster.buildPlayerSelect(spieler);
                        inputI = _getIntFromGUI();
                    }
                    try {
                        enemyPlayer = spieler.getSpielerByKey(inputI);
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                        enemyPlayer = null;
                    }
                    if (enemyPlayer == null) {
                        fenster.updateInformationLabel(activePlayer.getName(), "", "Keinen g\u00FCltigen Spieler gew\u00E4hlt! Bitte wählen sie den Spieler den sie angreifen wollen");
                    } else if (enemyPlayer == activePlayer) {
                        fenster.updateInformationLabel(activePlayer.getName(), "", "Sie haben sich selbst gew\u00E4hlt! Bitte wählen sie den Spieler den sie angreifen wollen");
                    } else if (!enemyPlayer.isAlive()) {
                        fenster.updateInformationLabel(activePlayer.getName(), "", "Dieser Spieler ist bereits ausgeschieden! Bitte wählen sie den Spieler den sie angreifen wollen");
                    } else {
                        break;
                    }

                }

                while (true) {
                    fenster.updateInformationLabel(activePlayer.getName(), enemyPlayer.getName(), "Bitte w\u00E4hlen sie das Schiff mit dem sie schie\u00DFen wollen");

                    if (activePlayer.getIsComputer()) {
                        int[] IDs = activePlayer.getSchiffe().getSchiffIDs(true, true);
                        inputI = IDs[(int) (Math.random() * IDs.length)];    //random
                    } else {
                        fenster.buildShipSelect(activePlayer.getSchiffe());
                        inputI = _getIntFromGUI();
                    }
                    try {
                        activeShip = activePlayer.getSchiffe().getShipByKey(inputI);
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                        activeShip = null;
                    }
                    if (activeShip == null) {
                        fenster.updateInformationLabel(activePlayer.getName(), enemyPlayer.getName(), "Keinen g\u00FCltiges Schiff gew\u00E4hlt! Bitte wählen sie das Schiff mit dem sie schießen wollen");
                    } else if (!activeShip.isActive()) {
                        fenster.updateInformationLabel(activePlayer.getName(), enemyPlayer.getName(), "Dieses Schiff ist noch inaktiv! Bitte wählen sie das Schiff mit dem sie schießen wollen");
                    } else if (!activeShip.isAlive()) {
                        fenster.updateInformationLabel(activePlayer.getName(), enemyPlayer.getName(), "Dieses Schiff ist gesunken! Bitte wählen sie das Schiff mit dem sie schießen wollen");
                    } else {
                        break;
                    }
                }

                fenster.buildBattlefieldFromSpielfeld(enemyPlayer.getSpielfeld(),false);
                fenster.updateInformationLabel(activePlayer.getName(), enemyPlayer.getName(), "Wohin m\u00F6chten sie schie\u00DFen ? ");
                        coords = _retriveCoordsFromGui(false, activePlayer.getIsComputer());

                if (activeShip.getSchussbreite() > 1) {
                    fenster.updateInformationLabel(activePlayer.getName(), enemyPlayer.getName(), "M\u00F6chten sie horizontal oder vertikal schie\u00DFen?");
                    isVertical = _retriveAusrichtungFromGui(false, activePlayer.getIsComputer());
                } else {
                    isVertical = false;
                }

                enemyPlayer.getSpielfeld().fire(coords[0], coords[1], activeShip.getSchussbreite(), isVertical, true);
                fenster.buildBattlefieldFromSpielfeld(enemyPlayer.getSpielfeld(),false);
                activeShip.hasFired();
            } else {
                System.out.println("\n'" + activePlayer.getName() + "' muss diese Runde aussetzen, da keine aktiven\n" +
                        "Schiffe vorhanden sind und nicht geschossen werden kann!\n");
            }
            if (!activePlayer.getIsComputer())
                spielstandGui();
        }
    }

    /**
     * Hilfsfunktion zur Vermeidung von unn\u00F6tigen doppelten Code
     *
     * @return
     * @throws AbortedByUserException
     */
    private static int[] _retriveCoordsFromGui(boolean allowReset, boolean isComputer) throws AbortedByUserException {
        boolean isValid;
        int fieldSize = GameObjects.getSpieler().getSpielerByKey(0).getSpielfeld().size();

        if (!isComputer) fenster.enableCoordButtons();
        if (allowReset) fenster.showResetButton();

        String check = (isComputer ? getCoordsForKI(fieldSize) : _getStringFromGUI());

        fenster.hideResetButton();

        if (allowReset && check.equalsIgnoreCase("reset")) {
            throw new AbortedByUserException();
        }

        return Spielfeld.coordToXY(check);
    }

    /**
     * Weitere Funktion zur Vermeidung von unn\u00F6tigen doppelten Code
     *
     * @param isComputer
     * @return
     * @throws AbortedByUserException
     */
    private static boolean _retriveAusrichtungFromGui(boolean allowReset, boolean isComputer) throws AbortedByUserException {
        boolean isValid, ret = false;

        if (!isComputer) fenster.showVHSelect();
        if (allowReset) fenster.showResetButton();

        String check = (isComputer ? getAusrichtungForKI() : _getStringFromGUI());

        fenster.hideResetButton();

        if (allowReset && check.equalsIgnoreCase("reset")) {
            throw new AbortedByUserException();
        }

        return check.equalsIgnoreCase("v");
    }

    public static String _getStringFromGUI() {
        String ret;
        do {
            ret = fenster.getStringReturnValue();
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (ret == null);
        return ret;
    }

    public static int _getIntFromGUI() {
        Integer ret;
        do {
            ret = fenster.getIntReturnValue();
            try {
                Thread.sleep(200L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (ret == null);
        return ret;
    }


    public static String getCoordsForKI(int max) {
        String[] abc = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u"};
        String letter = abc[(int) Math.round(Math.random() * max)];
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

    public static void spielstandGui() {
        fenster.updateInformationLabel("","","Möchten sie an dieser Stelle speichern?");
        String check;
        boolean accepted;

        do {
            fenster.showJNSelect();
            check = _getStringFromGUI();
        } while (!check.matches("^[jJnN]$"));
        accepted = check.equalsIgnoreCase("j");

        if (accepted) {
            SaveLoad.save(GameObjects.getSpieler());
            fenster.updateInformationLabel("", "", "Ihr Spielstand wurde abgespeichert");
            _waitForContinue(false);
        }

    }

    private static void _waitForContinue(boolean isKI) {
        if (isKI) return;
        fenster.showContinueButton();
        _getStringFromGUI();
    }
}