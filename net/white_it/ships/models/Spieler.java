package net.white_it.ships.models;

import net.white_it.ships.collections.Schiffsammlung;

/**
 * @author Florian Tenhaken <admin at white-it dot net>
 */
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
     * die @see Schiff::inaktiveRunden Counter runtergezählt insofern sie nicht 0
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

    public String getName() {
        return name;
    }

    public Schiffsammlung getSchiffe() {
        return schiffe;
    }

    public void printSpielfeld(boolean own){
        this.spielfeld.print(own);
    }
}
