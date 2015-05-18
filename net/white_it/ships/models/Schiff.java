package net.white_it.ships.models;

public abstract class Schiff {

    /**
     * Die Länge des Schiffes
     *
     * @since 1.0
     */
    private int laenge;

    private int schussbreite;

    private int wiederNutzbarNach;

    /**
     * Ausrichtung des Schiffes, wobei false für horizontal und true für vertikal steht
     *
     * @since 1.0
     */
    private boolean isVertical;

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

    public Schiff(int laenge, int schussbreite, int wiederNutzbarNach, boolean isVertical, int coordX, int coordY) {
        this.laenge = laenge;
        this.schussbreite = schussbreite;
        this.wiederNutzbarNach = wiederNutzbarNach;
        this.isVertical = isVertical;
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

    public boolean wasHit(int x, int y) {
        if (this.checkCoord(x, y) >= 0) {
            if (!this.isVertical) {
                this.treffer[x - this.coordX] = true;
            } else {
                this.treffer[y - this.coordY] = true;
            }
            return true;
        }
        return false;
    }

    public int checkCoord(int x, int y) {
        if (!this.isVertical) { // Schiff ist horizontal
            if (y != this.coordY) {
                // Wenn das Schiff horizontal ist und die übergebene Y Koordinate nicht der
                // Y Koordinate des Schiffs entspricht, ist eine weitere Überprüfung hinfällig
                return -2;
            }

            if (x >= this.coordX && x < this.coordX + this.laenge) {
                // Ist das Schiff an der ursprünglichen X Koordinate
                return (this.treffer[x - this.coordX] ? 1 : 0);
            } else {
                return -2;
            }
        } else { // Schiff ist vertikal
            if (x != this.coordX) {
                return -1;
            }

            if (y >= this.coordY && y < this.coordY + this.laenge) {
                return (this.treffer[y - this.coordY] ? 1 : 0);
            } else {
                return -2;
            }
        }
    }

    public boolean checkCollision(int x, int y) {
        if (!this.isVertical) { // Schiff ist horizontal
            if (y < this.coordY - 1 || y > this.coordY + 1) {
                // Belegt das Schiff auf dieser Y Koordinate bzw. 1 darüber oder darunter Platz?
                // Wenn nicht ist eine weitere Prüfung sinnlos.
                return false;
            }

            // Wenn die Y Koordinate zutrifft, prüfe ob das Schiff auf der X Koordinate
            // den übergebenen Platz einnimmt, prüfe den Bereich von X-1 bis X+Schifflänge
            // Blockiert das Schiff gib true zurück, anderenfalls false
            return (x >= this.coordX - 1 && x <= this.coordX + this.laenge);
        } else { // Schiff ist vertikal, Code hier ist nur Achsentechnisch vertauscht
            if (x < this.coordX - 1 || x > this.coordX + 1) {
                return false;
            }

            return (y >= this.coordY - 1 && y <= this.coordY + this.laenge);
        }
    }

    public String getIdentifier() {
        String status = (this.isAlive() ? (this.isActive() ? "Aktiv" : "Inaktiv") : "Gesunken");
        return this.getClass().getSimpleName() + " auf " + coordX + "," + coordY + " (" + status + ")";
    }

    public int getSchussbreite() {
        return schussbreite;
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
