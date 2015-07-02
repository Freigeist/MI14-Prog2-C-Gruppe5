package net.white_it.ships;

import java.io.Serializable;

public class Main {
    public static void main(String[] args) {
        Aktionen.prepareGame();
        Aktionen.gameLoop();
    }
}