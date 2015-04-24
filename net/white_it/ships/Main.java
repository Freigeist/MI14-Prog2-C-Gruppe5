package net.white_it.ships;

import net.white_it.ships.models.Spielfeld;

/**
 * @author Florian Tenhaken <admin at white-it dot net>
 */
public class Main {
	public static void main(String[] args) {

		Spielfeld test = new Spielfeld(5);
		test.feldErstellen(5);
		test.print();
		
		
		test.setzeSchiff(2,4);
		test.setzeSchiff(0,1);
		
		test.beschiesseFeld(2,4);
		test.beschiesseFeld(1,4);
		
		System.out.println("");
		test.print();

		/**
    	   if(test.istSchiffDa(1,2)){


    		System.out.println("ist da");
    	} else {
    		System.out.println("nicht da");

    	}
		 */
	}
}
