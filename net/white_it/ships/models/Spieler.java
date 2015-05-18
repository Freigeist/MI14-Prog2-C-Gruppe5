package net.white_it.ships.models;

import net.white_it.ships.collections.Schiffsammlung;

public class Spieler {
    /**
     * Name des Spielers
     *
     * @since 1.0
     */
    private String name;

    /**
     * Das Spielfeld des Spielers
     *
     * @since 1.0
     */
    private Spielfeld spielfeld;

    /**
     * Die Schiffe des Spielers
     *
     * @since 1.0
     */
    private Schiffsammlung schiffe;

    public Spieler(String name, int spielfeldgroesse) {
        this.name = name;
        this.schiffe = new Schiffsammlung();
        this.spielfeld = new Spielfeld(spielfeldgroesse, schiffe);
    }

    /**
     * Funktion zur Vorbereitung eines Zugs des Spielers, hier werden zum Beispiel
     * die @see Schiff#inaktiveRunden Counter runtergezählt insofern sie nicht 0
     * sind.
     *
     * @since 1.0
     */
    public void preTurn(){
        for(Schiff s : this.schiffe.getSchiffe()){
            s.preTurn();
        }
    }

    public boolean isAlive(){
        for (Schiff s : this.schiffe.getSchiffe()){
            if(s.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasActiveShips(){
        for (Schiff s : this.schiffe.getSchiffe()){
            if(s.isActive() && s.isAlive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt den Namen des Spielers zurück
     * @return Name des Spielers
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt die @see Schiffssammlung des Spielers zurück
     * @return Schiffssammlung des Spielers
     */
    public Schiffsammlung getSchiffe() {
        return schiffe;
    }

    public Spielfeld getSpielfeld() {
        return spielfeld;
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
        return "[Spieler="+this.name+"]";
    }
}
