package net.white_it.ships.models;

/**
 * @author Florian Tenhaken <admin at white-it dot net>
 */
public class SpielFeld {
    /**
     * Koordinatensystem des Spielfeldes
     *
     * @since 1.0
     */
    private int[][] coords;

    public SpielFeld(int size) {
        this.coords = new int[size][size];
        
        for(int i = 0; i < size; i ++){
        	for(int j = 0; j < size; j++){
        		coords = new int[i][j];
        	}
        }
    }

    /**
     * Gibt das Feld auf der Kommandozeile aus
     */
    public void print(){

    }
}
