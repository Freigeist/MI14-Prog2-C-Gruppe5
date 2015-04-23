package net.white_it.ships;

import net.white_it.ships.models.Spieler;

public class Aktionen {
	/**
	 * hier werden spieleinstellungen vorgenommen
	 * 
	 * @since 1.0
	 */
	public static void prepareGame(){
		createPlayer();
		
	}
	
	/**
	 * Methode zum erstellen von Spielern
	 * @since 2
	 */
	public static void createPlayer(int zahl){
		for(int i = 1; i < zahl; i++){
			Spieler s = new Spieler();
		}
	}
	
	/**
	 * Methode zum erstellen der Schiffe
	 * @param zahl1
	 */
	public static createShips(int zahl1){
		for(int i = 0; i < zahl1; i++){
			Spieler s = new Spieler();
		}
	}
	
	
}
