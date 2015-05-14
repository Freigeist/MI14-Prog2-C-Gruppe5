package net.white_it.ships.collections;

import net.white_it.ships.exceptions.NoPlayerException;
import net.white_it.ships.models.Spieler;

/**
 * @author Florian Tenhaken <admin at white-it dot net>
 */
public class Spielersammlung {
    private Spieler[] spieler;
    private int activePlayer = -1;

    public Spielersammlung() {
        this.spieler = new Spieler[0];
    }

    public Spieler[] getSpieler() {
        return spieler;
    }

    public void push(Spieler spieler) {
        Spieler[] s = new Spieler[this.spieler.length + 1];
        for (int i = 0; i < this.spieler.length; i++) {
            s[i] = this.spieler[i];
        }
        s[this.spieler.length] = spieler;
        this.spieler = s;
    }

    public Spieler getNext(){
        if(this.spieler.length < 1)
            throw new NoPlayerException();
        activePlayer++;
        if(activePlayer >= spieler.length)
            activePlayer = 0;
        return this.spieler[activePlayer];
    }

    public void printPlayerList(boolean excludeActivePlayer){
        for(int i = 0; i < this.spieler.length; i++){
            if(excludeActivePlayer && i == this.activePlayer)
                continue;

            System.out.println(i + " - " + this.spieler[i].getName());
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
        String ret = "[Spielersammlung=";
        int len = this.spieler.length;

        for(int i = 0; i < len-1;i++){
            ret += this.spieler[i].toString() + ",";
        }

        if(len > 0)
            ret += this.spieler[len-1].toString();

        ret += "]";

        return ret;
    }
}
