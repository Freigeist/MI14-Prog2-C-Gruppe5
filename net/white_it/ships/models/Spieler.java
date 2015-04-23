package net.white_it.ships.models;

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
    private Schiff[] schiffe;


    /**
     * Funktion zur Vorbereitung eines Zugs des Spielers, hier werden zum Beispiel
     * die @see Schiff::inaktiveRunden Counter runtergezählt insofern sie nicht 0
     * sind.
     *
     * @since 1.0
     */
    public void preTurn(){
        for(Schiff s : schiffe){
            s.preTurn();
        }
    }
}
