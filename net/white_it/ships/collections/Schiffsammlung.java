package net.white_it.ships.collections;

import net.white_it.ships.models.Schiff;

/**
 * @author Florian Tenhaken <admin at white-it dot net>
 */
public class Schiffsammlung {
    private Schiff[] schiffe;

    public Schiffsammlung() {
        this.schiffe = new Schiff[0];
    }

    public Schiff[] getSchiffe() {
        return schiffe;
    }

    public void push(Schiff schiff) {
        Schiff[] s = new Schiff[schiffe.length + 1];
        for (int i = 0; i < schiffe.length; i++) {
            s[i] = this.schiffe[i];
        }
        s[schiffe.length] = schiff;
        this.schiffe = s;
    }

    @Override
    public String toString() {
        String ret = "[Schiffsammlung=";
        int len = this.schiffe.length;

        for(int i = 0; i < len-1;i++){
            ret += this.schiffe[i].toString() + ",";
        }

        if(len > 0)
            ret += this.schiffe[len-1].toString();

        ret += "]";

        return ret;
    }
}
