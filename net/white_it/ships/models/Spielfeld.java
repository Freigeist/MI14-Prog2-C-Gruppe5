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
    public void print(){

    }
}
