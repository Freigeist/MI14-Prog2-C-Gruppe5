package net.white_it.ships.models;

/**
 * @author Florian Tenhaken <admin at white-it dot net>
 */
public abstract class Schiff {

    /**
     * Die Länge des Schiffes
     *
     * @since 1.0
     */
    private int laenge;

    /**
     * Ausrichtung des Schiffes, wobei false für horizontal und true für vertikal steht
     *
     * @since 1.0
     */
    private boolean ausrichtung;

    /**
     * Koordinate der X Achse
     *
     * @since 1.0
     */
    private int coordX;

    /**
     * Koordinate der Y Achse
     *
     * @since 1.0
     */
    private int coordY;

    /**
     * Array aus booleans in denen gespeichert wird wo das Schiff getroffen wurde
     *
     * @since 1.0
     */
    private boolean[] treffer;

    /**
     * Wie viele Züge ist das Schiff noch inaktiv?
     *
     * @since 1.0
     */
    private int inaktiveRunden;

    public Schiff(int laenge, boolean ausrichtung, int coordX, int coordY) {
        this.laenge = laenge;
        this.ausrichtung = ausrichtung;
        this.coordX = coordX;
        this.coordY = coordY;
        this.inaktiveRunden = 0;
    }

    public void preTurn() {
        this.inaktiveRunden--;
    }
}
