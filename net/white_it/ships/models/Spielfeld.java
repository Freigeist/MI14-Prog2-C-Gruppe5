package net.white_it.ships.models;

import net.white_it.ships.collections.Schiffsammlung;
import net.white_it.ships.exceptions.NoValidCoordinateException;

public class Spielfeld {
    /**
     * Koordinatensystem des Spielfeldes
     *
     * @since 1.0
     */
    private boolean[][] coords;

    /**
     * Die Schiffssammlung des Spielers, dem das Feld gehört
     */
    private Schiffsammlung schiffe;

    /**
     * Konstruktor. Erstellt eines zweidimensionales Array und setzt alle Werte auf false.
     *
     * @param size    Größe des Spielfelds
     * @param schiffe Schiffssammlung des Spielers
     */
    public Spielfeld(int size, Schiffsammlung schiffe) {
        this.coords = new boolean[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                this.coords[x][y] = false;
            }
        }
        this.schiffe = schiffe;
    }

    /**
     * Gibt das Feld auf der Kommandozeile aus
     *
     * @param own Gibt an ob das Feld aus der Sicht des Spielers oder eines Gegners angezeigt wird
     */
    public void print(boolean own) {
        int length = coords.length;
        int xCounter = 1;
        char c;

        for (int y = 0; y <= length; y++) {
            if (y == 0) {
                System.out.print((length > 9 ? " " : "") + " |");
                for (int x = 1; x <= length; x++) {
                    c = (char) (64 + x); //ASCII A = 65, B = 66, usw.
                    if (x == length)
                        System.out.print(c + "\n");
                    else
                        System.out.print(c + "|");
                }
            } else {
                System.out.print((length > 9 && y < 10 ? " " : "") + (xCounter++) + "|");

                for (int x = 0; x < length; x++) {
                    System.out.print(getPos(x, y - 1, own));

                    if (x < length - 1)
                        System.out.print("|");
                }

                System.out.print("\n");
            }
        }
    }

    /**
     * Prüft den aktuellen Status einer Koordinate
     * <p/>
     * -2 - Treffer ins Wasser
     * -1 - Nichts
     * 0  - Ein Schiff, ein S wird nur dem Spieler dem das Fled gehört gezeigt
     * 1  - ein getroffenes Schiff
     *
     * @param x Zu prüfende X Koordinate
     * @param y Zu prüfende Y Koordinate
     * @return Integer aus obiger Definition
     * @throws IndexOutOfBoundsException
     */
    public int checkCoord(int x, int y) throws IndexOutOfBoundsException {
        int ret, check;
        if (this.coords[x][y]) {
            // Koordinate wurde getroffen, es wird erstmal Wasser angenommen,
            // außer ein Schiff gibt in seiner checkCoord Methode einen
            // Treffer zurück
            ret = -2;
        } else {
            ret = -1;
        }
        for (Schiff s : this.schiffe.getSchiffe()) {
            check = s.checkCoord(x, y);
            if(ret == -2 && check == -1)
                continue;
            if (ret < check)
                ret = check;
        }
        return ret;
    }

    /**
     * Ruft die @see checkCoord Methode auf und wandelt den Wert in einen String um:
     * -2 - "x" -> Treffer ins Wasser
     * -1 - " " -> Nichts
     * 0  - " " oder "S" -> Ein Schiff, ein S wird nur dem Spieler dem das Fled gehört gezeigt
     * 1  - "*" -> ein getroffenes Schiff
     *
     * @param x   X Koordinate
     * @param y   Y Koordinate
     * @param own Eigenes Spielfeld?
     * @return String aus obiger Definition
     */
    public String getPos(int x, int y, boolean own) {
        int check = checkCoord(x, y);
        switch (check) {
            case -2:
                return "x";
            case -1:
                return " ";
            case 0:
                return (own ? "S" : " ");
            case 1:
                return "*";
            default:
                return "";
        }
    }

    public boolean coordIsInField(int x, int y){
        return (x >= 0 && y >= 0 && x < this.coords.length && y < this.coords.length);
    }

    public void fire(int x, int y, int schussbreite, boolean isVertical) {
        boolean hitShip = false;
        if(!isVertical){
            for(int i = y; i < y + schussbreite; i++){
                if(this.coordIsInField(x,y)){
                    this.coords[x][y] = true;
                    for(Schiff s : this.schiffe.getSchiffe()){
                        if(s.wasHit(x,y)){
                            hitShip = true;
                            break;
                        }
                    }
                }
            }
        } else {
            for(int i = x; i < x + schussbreite; i++){
                if(this.coordIsInField(x,y)){
                    this.coords[x][y] = true;
                    for(Schiff s : this.schiffe.getSchiffe()){
                        if(s.wasHit(x,y)){
                            hitShip = true;
                            break;
                        }
                    }
                }
            }
        }
        if(hitShip)
            System.out.println("Ein Schiff wurde auf "+xyToCoord(x,y)+" getroffen!");
        else
            System.out.println("Der Schuss auf "+xyToCoord(x,y)+" ging ins Wasser.");
    }

    /**
     * Wandelt aus Buchstaben und Zahlen bestehende Koordinaten in Zahlen um,
     * die weiter genutzt werden können. Das Format spielt keine Rolle "A1", "1A", "1a" oder
     * aber auch "A,1" oder "1,A" o.ä. sind valide Angaben, wichtig ist das eine Zahl
     * vorhanden ist und genau ein Buchstabe von A-Z
     *
     * @param coord String der Koordinaten
     * @return int[] -> Index 0 = x; Index 1 = y
     * @throws NoValidCoordinateException
     */
    public static int[] coordToXY(String coord) throws NoValidCoordinateException {
        // Bestimmung x Koordinate
        String tmp = coord.toUpperCase().replaceAll("[^A-Z]+", ""); // Alles was keine Grossbuchstaben sind ersetzen
        if (tmp.length() != 1) // Länge nicht = 1 -> Keine gültige Koordinate -> Exception werfen
            throw new NoValidCoordinateException();
        int x = (int) tmp.charAt(0) - 65; // 65 = ASCII A -> A = x0, B = x1, usw

        // Bestimmung y Koordinate
        if (!coord.matches(".*[1-9][0-9]?.*") || coord.matches(".*[0-9][^0-9]+[0-9].*")) {
            // Wenn die Zahl nicht 1-99 ist oder Zahlen enthalten sind die durch beliebige
            // Zeichen voneinander getrennt sind, gehe von einer ungültigen Koordinate aus
            // z.B. 1a2 (uneindeutig)
            throw new NoValidCoordinateException();
        }
        int y;
        // Entferne alle Zeichen die keine Zahlen sind, parse den Integer
        // und ziehe 1 ab, da intern ab 0 begonnen wird
        y = Integer.parseInt(coord.replaceAll("[^0-9]+", "")) - 1;
        if (y < 0)
            throw new NoValidCoordinateException();
        return new int[]{x, y};
    }

    public static String xyToCoord(int x, int y){
        char c = (char) (65 + x);
        return c + "" + (y+1);
    }

    public boolean tryPlaceShip(boolean isVertical, int size, int x, int y){
        boolean ret = true;
        if(!isVertical){// Schiff ist horizontal
            if(x < 0 || x >= this.coords.length || x+size-1 >= this.coords.length) {
                //Schiff würde auf der X Achse über den Feldrand gehen, werfe IndexOutOfBoundsException
                throw new IndexOutOfBoundsException();
            }
            if(y < 0 || y >= this.coords.length){
                //Schiff würde auf der Y Achse über den Feldrand gehen, werfe IndexOutOfBoundsException
                throw new IndexOutOfBoundsException();
            }
            // Gehe alle möglichen Koordinaten des neuen Schiffs durch
            for(int i = x; i < x+size && ret; i++){
                // Prüfe jedes bislang vorhandene Schiff ob es blockiert
                for(Schiff s : this.schiffe.getSchiffe()){
                    if(s.checkCollision(x,y)){
                        // Wenn ein Schiff blockiert, setze ret auf false
                        // und breche die Schleife mit break ab
                        ret = false;
                        break;
                    }
                }
            }
        } else {// Schiff ist vertikal
            if(y < 0 || y >= this.coords.length || y+size-1 >= this.coords.length) {
                //Schiff würde auf der Y Achse über den Feldrand gehen, werfe IndexOutOfBoundsException
                throw new IndexOutOfBoundsException();
            }
            if(x < 0 || x >= this.coords.length){
                //Schiff würde auf der X Achse über den Feldrand gehen, werfe IndexOutOfBoundsException
                throw new IndexOutOfBoundsException();
            }
            // Gehe alle möglichen Koordinaten des neuen Schiffs durch
            for(int i = y; i < y+size && ret; i++){
                // Prüfe jedes bislang vorhandene Schiff ob es blockiert
                for(Schiff s : this.schiffe.getSchiffe()){
                    if(s.checkCollision(x,y)){
                        // Wenn ein Schiff blockiert, setze ret auf false
                        // und breche die Schleife mit break ab
                        ret = false;
                        break;
                    }
                }
            }
        }
        return ret;
    }
}
