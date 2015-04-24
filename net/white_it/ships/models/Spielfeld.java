package net.white_it.ships.models;



/**

 * @author Florian Tenhaken <admin at white-it dot net>

 */

public class Spielfeld {

	/**

	 * Koordinatensystem des Spielfeldes

	 *

	 * @since 1.0

	 */

	private static int[][] feld;



	public Spielfeld() {



	}



	public Spielfeld(int groesse) {

		this.feld = new int[groesse][groesse];

	}





	public void feldErstellen(int groesse){



		int[][] feld = new int[groesse][groesse];

		for (int y=0;y<feld.length;y++){ // senkrecht

			for (int x=0;x<feld.length;x++){ //waagerecht

				feld[x][y] = 0;

			}





		} 

	}



	/*
	 * Hiermit werden alle erstellten Felder mit "0" gefüllt
	 */
	public static void belegeKoordinate(int x, int y) {
		getMatrix();
		feld[x][y] = 0;
	}
	
	/*
	 * Hiermit werden Schiffe gesetzt -> Feld wird mit 1 gefüllt
	 */
	public void setzeSchiff(int x, int y) {

		getMatrix();

		feld[x][y] = 1;

	}
	
	/*
	 * Hiermit wird ein Feld beschossen. 
	 * Wenn sich hier kein Schiff befindet, wird in das Feld "2" eingetragen -> Getroffenes Wasser
	 * Wenn sich hier ein Schiff befindet, wird in das Feld "3" eingetragen -> Getroffenes Schiff
	 */
	public void beschiesseFeld(int x, int y) {

		getMatrix();

		if(feld[x][y] == 0) {

			feld[x][y] = 2;

		} else if(feld[x][y] == 1) {
			feld[x][y] = 3;
		}

	}


	/*
	 * Hier wird geprüft ob sich ein Schiff auf dem Feld befindet. Wenn "1" -> return true
	 */
	public boolean istSchiffDa(int x, int y) {

		getMatrix();

		if(feld[x][y] == 1) {

			return true;

		} else {

			return false;

		}



	}

	/**
	 * Hiermit können wir auf unser Feld zugreifen
	 */
	public static int[][] getMatrix(){
		return feld;
	}










	/**

	 * Gibt das Feld auf der Kommandozeile aus

	 */

	public void print(){

		System.out.println("    0  1  2  3  4");

		for(int y = 0; y < feld.length; y++){

			System.out.print(" "+y+" ");

			for(int x = 0; x < feld.length; x++){





				if(feld[x][y] == 0){

					System.out.print("[ ]");

				} else if(feld[x][y] == 1){

					System.out.print("[1]");

				} else if(feld[x][y] == 2){

					System.out.print("[2]");

				} else if(feld[x][y] == 3){

					System.out.print("[3]");

				}

			}

			System.out.println("");

		}

	}

}

