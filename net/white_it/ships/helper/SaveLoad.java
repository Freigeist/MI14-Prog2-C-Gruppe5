package net.white_it.ships.helper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import net.white_it.ships.collections.GameObjects;
import net.white_it.ships.collections.Spielersammlung;

public class SaveLoad implements Serializable {

    public static void save(Spielersammlung spielersammlung) {
        try {
            FileOutputStream fs = new FileOutputStream("spielstand.ser"); // was folgt soll in "serialisierungTest1.ser" gespeichert werden
            ObjectOutputStream os = new ObjectOutputStream(fs); // outputstream um in die datei reinschreiben zu können
            os.writeObject(spielersammlung);
            os.close();
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    public static void load() {

        Spielersammlung spielersammlung = null;
        try {
            FileInputStream fs = new FileInputStream("spielstand.ser"); // einlesen der datei festgelegt
            ObjectInputStream is = new ObjectInputStream(fs);
            // objekte werden nacheinander eingelesen. reihenfolge muss gleich sein, wie wie bei Test
            spielersammlung = (Spielersammlung) is.readObject();
            System.out.println(spielersammlung);
            is.close();
        } catch (ClassNotFoundException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
        GameObjects.setSpieler(spielersammlung); // wenn das spiel geladen wird, wird die abgespeicherte spielersammlung verwendet
    }
}
