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

    private int schusszahl;

    private int wiederNutzbarNach;

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

    public Schiff(int laenge, int schusszahl, int wiederNutzbarNach, boolean ausrichtung, int coordX, int coordY) {
        this.laenge = laenge;
        this.schusszahl = schusszahl;
        this.wiederNutzbarNach = wiederNutzbarNach;
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

    public boolean checkCollision(int x, int y) {
        if (!this.ausrichtung) {
            if (y < this.coordY - 1 || y > this.coordY + 1) {
                return false;
            }

            if (x >= this.coordX - 1 && x <= this.coordX + this.laenge + 1) {
                return true;
            } else {
                return false;
            }
        } else {
            if (x < this.coordX - 1 || x > this.coordX + 1) {
                return false;
            }

            if (y >= this.coordY - 1 && y <= this.coordY + this.laenge + 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p/>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "[" + this.getClass().getSimpleName() + "=" + coordX + "," + coordY + "]";
    }

    public void hasFired() {
        this.inaktiveRunden = this.wiederNutzbarNach + 1; //aktive Runde wird mitgezählt
    }
}
