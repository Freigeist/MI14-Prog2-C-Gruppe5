package net.white_it.ships.models;

/**
 * @author Florian Tenhaken <admin at white-it dot net>
 */
public class Spielfeld {
    /**
     * Koordinatensystem des Spielfeldes
     *
     * @since 1.0
     */
    private int[][] coords;

    public Spielfeld(int size) {
        this.coords = new int[size][size];
    }

    /**
     * Gibt das Feld auf der Kommandozeile aus
     */
    public void print() {
        int length = coords.length;
        int yCounter = 1;
        char c;

        for (int x = 0; x <= length; x++) {
            if (x == 0) {
                System.out.print((length > 9 ? " " : "") + " |");
                for (int y = 1; y <= length; y++) {
                    c = (char) (64 + y);
                    if (y == length)
                        System.out.print(c + "\n");
                    else
                        System.out.print(c + "|");
                }
            } else {
                //System.out.print((length > 9 ? "-" : "") + "-+");
                //for (int y = 1; y < length; y++)
                //    System.out.print("-+");
                System.out.print((length > 9 && x < 10 ? " " : "") + (yCounter++) + "|");

                for (int y = 1; y < length; y++)
                    System.out.print(" |");

                System.out.print("\n");
            }
        }
    }
}
