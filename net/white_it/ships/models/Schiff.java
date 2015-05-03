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

        this.treffer = new boolean[laenge];
        for (int i = 0; i < laenge; i++) {
            this.treffer[i] = false;
        }
    }

    /**
     * Zugvorbereitung
     *
     * @since 1.0
     */
    public void preTurn() {
        if (this.inaktiveRunden > 0)
            this.inaktiveRunden--;
    }

    public boolean isActive() {
        return this.inaktiveRunden == 0;
    }

    public boolean isAlive() {
        for (boolean b : this.treffer) {
            if (!b) {
                return true;
            }
        }
        return false;
    }

    public int checkCoord(int x, int y) {
        if (!this.ausrichtung) {
            if (y != this.coordY) {
                return -1;
            }

            if (x >= this.coordX && x < this.coordX + this.laenge) {
                return (this.treffer[x - this.coordX] ? 1 : 0);
            } else {
                return -1;
            }
        } else {
            if (x != this.coordX) {
                return -1;
            }

            if (y >= this.coordY && y < this.coordY + this.laenge) {
                return (this.treffer[y - this.coordY] ? 1 : 0);
            } else {
                return -1;
            }
        }
    }
}
