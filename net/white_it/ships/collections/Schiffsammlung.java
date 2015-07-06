package net.white_it.ships.collections;

import java.io.Serializable;

import net.white_it.ships.models.Schiff;

public class Schiffsammlung implements Serializable {
    /**
     * Array aller Schiffe
     */
    private Schiff[] schiffe;

    /**
     * Konstruktor. Erstellt ein Array des Typs @see Schiff der Länge Null.
     */
    public Schiffsammlung() {
        this.schiffe = new Schiff[0];
    }

    /**
     * Gibt das gesammte Schiffsarray zurück
     *
     * @return Schiff[]
     */
    public Schiff[] getSchiffe() {
        return schiffe;
    }

    /**
     * Fügt dem Schiff[] ein neues Schiff hinzu
     *
     * @param schiff
     */
    public void push(Schiff schiff) {
        Schiff[] s = new Schiff[schiffe.length + 1];
        for (int i = 0; i < schiffe.length; i++) {
            s[i] = this.schiffe[i];
        }
        s[schiffe.length] = schiff;
        this.schiffe = s;
    }

    /**
     * Entfernt alle Schiffe aus dem Array
     */
    public void clear() {
        this.schiffe = new Schiff[0];
    }

    public Schiff getShipByKey(int key) {
        return this.schiffe[key];
    }

    public void printShipList() {
        for (int i = 0; i < this.schiffe.length; i++) {
            System.out.println(i + " - " + this.schiffe[i].getIdentifier());
        }
    }

    public int[] getSchiffIDs(boolean excludeInactiveShips, boolean excludeDeadShips) {
        int tmp = 0;
        for (Schiff S : this.schiffe) {
            if (excludeInactiveShips && !S.isActive())
                continue;

            if (excludeDeadShips && !S.isAlive())
                continue;

            tmp++;
        }
        int[] ret = new int[tmp];
        tmp = 0;

        for (int i = 0; i < this.schiffe.length; i++) {
            if (excludeInactiveShips && !this.schiffe[i].isActive())
                continue;

            if (excludeDeadShips && !this.schiffe[i].isAlive())
                continue;

            ret[tmp] = i;
            tmp++;
        }
        return ret;
    }

    public int size(){
        return this.schiffe.length;
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
        String ret = "[Schiffsammlung=";
        int len = this.schiffe.length;

        for (int i = 0; i < len - 1; i++) {
            ret += this.schiffe[i].toString() + ",";
        }

        if (len > 0)
            ret += this.schiffe[len - 1].toString();

        ret += "]";

        return ret;
    }
}
