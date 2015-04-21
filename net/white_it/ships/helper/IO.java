package net.white_it.ships.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Florian Tenhaken <admin at white-it dot net>
 */
public class IO {
    /**
     * BufferedReader Object zum Einlesen von Eingaben aus der Kommandozeile
     */
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Liest einen String von der Kommandozeile ein
     *
     * @return String
     */
    public static String getString() {
        String line = "";
        try {
            line = in.readLine();
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }

        return line;
    }

    /**
     * Liest einen Integer von der Kommandozeile ein
     *
     * @return int
     */
    public static int getInt() {
        int num = 0;
        int err = 1;
        do {
            try {
                String zeile = in.readLine();
                num = Integer.parseInt(zeile);
                err = 0;
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
        while (err == 1);

        return num;
    }
}
