package net.white_it.ships.models;

import net.white_it.ships.collections.Schiffsammlung;

/**
 * @author Florian Tenhaken <admin at white-it dot net>
 */
public class Spielfeld {
    /**
     * Koordinatensystem des Spielfeldes
     *
     * @since 1.0
     */
    private boolean[][] coords;

    private Schiffsammlung schiffe;

    public Spielfeld(int size, Schiffsammlung schiffe) {
        this.coords = new boolean[size][size];
        this.schiffe = schiffe;
    }

    /**
     * Gibt das Feld auf der Kommandozeile aus
     */
    public void print(boolean own) {
        int length = coords.length;
        int yCounter = 1;
        char c;

        for (int x = 0; x <= length; x++) {
            if (x == 0) {
                System.out.print((length > 9 ? " " : "") + " |");
                for (int y = 1; y <= length; y++) {
                    c = (char) (64 + y);
                    if (y == length)
                        System.out.print(c + "\n");
                    else
                        System.out.print(c + "|");
                }
            } else {
                System.out.print((length > 9 && x < 10 ? " " : "") + (yCounter++) + "|");

                for (int y = 0; y < length; y++) {
                    System.out.print(getPos(x-1,y,own));

                    if(y < length-1)
                        System.out.print("|");
                }

                System.out.print("\n");
            }
        }
    }

    public int checkCoord(int x, int y){
        int ret = Integer.MIN_VALUE, check;
        for(Schiff s : this.schiffe.getSchiffe()){
            check = s.checkCoord(x,y);
            if(ret < check)
                ret = check;
        }
        return ret;
    }

    public String getPos(int x, int y, boolean own){
        int check = checkCoord(x,y);
        switch (check){
            case -1:
                return " ";
            case 0:
                return (own ? "S" : " ");
            case 1:
                return "x";
            default:
                return "";
        }
    }
}
