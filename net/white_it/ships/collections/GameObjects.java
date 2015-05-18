package net.white_it.ships.collections;

public class GameObjects {
    private static Spielersammlung spieler = new Spielersammlung();

    public static Spielersammlung getSpieler() {
        return spieler;
    }

    public static void setSpieler(Spielersammlung spieler) {
        GameObjects.spieler = spieler;
    }

}
