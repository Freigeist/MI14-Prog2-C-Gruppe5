package net.white_it.ships.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;

//singleton//
public class Hauptfenster extends JFrame {
    private static Hauptfenster instance = null;

    private HashMap<String, JComponent> mainWindowObjects, gameSettingsObjects, battlefieldObjects;

    private Hauptfenster() {
        // Alle Buttons, Textfelder, etc aus Fenstern werden hier drin gespeichert, zur Vereinfachung
        // des Ein-/ und Ausblendens
        mainWindowObjects = new HashMap<>();
        gameSettingsObjects = new HashMap<>();
        battlefieldObjects = new HashMap<>();

        // Eine neue Instanz für jeden Button würde einen sehr großen Overhead erzeugen
        Listener listenerInstance = new Listener();

        // Arten an Elementen die Benötigt werden
        JButton button;
        JTextField textField;
        JLabel label;
        JCheckBox checkBox;
        JRadioButton radioButton;

        /*
         * BEGINN HAUPTMENUE
         */
        button = new JButton();
        button.addActionListener(listenerInstance);
        button.setBounds(290, 10, 220, 50);
        button.setText("Neues Spiel");
        button.setName("newGameButton"); // Die Namen werden im Listener benötigt
        this.add(button);
        mainWindowObjects.put("newGameButton", button);

        // Wiederverwendung der Variable button, da diese nicht nach hinzufügen zum Fenster
        // und zum Vector nicht mehr benötigt wird. Die spätere Verwendung folgt aus dem Vector
        // heraus
        button = new JButton();
        button.addActionListener(listenerInstance);
        button.setBounds(290, 70, 220, 50);
        button.setText("Letztes Spiel laden");
        button.setName("loadGameButton");
        this.add(button);
        mainWindowObjects.put("loadGameButton", button);

        button = new JButton();
        button.addActionListener(listenerInstance);
        button.setBounds(290, 130, 220, 50);
        button.setText("Anleitung");
        button.setName("manualButton");
        this.add(button);
        mainWindowObjects.put("manualButton", button);
        /*
         * ENDE HAUPTMENUE
         */

        /*
         * BEGINN SETTINGS
         */
        label = new JLabel("Anzahl der Spieler:");
        label.setBounds(50, 50, 180, 20);
        this.add(label);
        this.gameSettingsObjects.put("labelPlayerCount", label);

        ButtonGroup group = new ButtonGroup();
        for (int i = 2; i <= 6; i++) {
            radioButton = new JRadioButton(i + " Spieler");
            radioButton.setName("radio" + i + "Player");
            radioButton.setBounds(50, 30 * i + 50, 180, 20);
            radioButton.addActionListener(listenerInstance);
            if(i == 6)
                radioButton.setSelected(true);
            this.add(radioButton);
            group.add(radioButton);
            this.gameSettingsObjects.put("radio" + i + "Player", radioButton);
        }

        label = new JLabel("Namen der Spieler:");
        label.setBounds(300, 50, 200, 20);
        this.add(label);
        this.gameSettingsObjects.put("labelNames", label);

        label = new JLabel("Is KI?");
        label.setBounds(520, 50, 50, 20);
        this.add(label);
        this.gameSettingsObjects.put("labelIsKI", label);

        for (int i = 1; i <= 6; i++) {
            label = new JLabel("Spieler " + i + ":");
            label.setBounds(230, 30 * i + 50, 70, 20);
            this.add(label);
            this.gameSettingsObjects.put("labelS" + i, label);

            textField = new JTextField();
            textField.setName("nameP" + i);
            textField.setBounds(300, 30 * i + 50, 200, 20);
            textField.setText("Spieler " + i);
            this.add(textField);
            this.gameSettingsObjects.put("nameP" + i, textField);

            checkBox = new JCheckBox();
            checkBox.setBounds(520, 30 * i + 50, 50, 20);
            this.add(checkBox);
            this.gameSettingsObjects.put("isKIP" + i, checkBox);
        }


        this.setResizable(false);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setTitle("BattleShip");

        this.setVisible(true);
        this.showMainWindow();
    }

    private void clearWindow() {
        for (JComponent e : this.mainWindowObjects.values()) {
            e.setVisible(false);
        }
        for (JComponent e : this.gameSettingsObjects.values()) {
            e.setVisible(false);
        }
        for (JComponent e : this.battlefieldObjects.values()) {
            e.setVisible(false);
        }
        this.revalidate();
        this.repaint();
    }

    public static Hauptfenster getInstance() {
        if (instance == null) {
            instance = new Hauptfenster();
        }
        return instance;
    }

    public void showMainWindow() {
        this.clearWindow();
        for (JComponent e : mainWindowObjects.values()) {
            e.setVisible(true);
        }
        this.revalidate();
        this.repaint();
    }

    public void showGameSettings() {
        this.clearWindow();
        for (JComponent e : gameSettingsObjects.values()) {
            e.setVisible(true);
        }
        this.revalidate();
        this.repaint();
    }

    public void showBattlefield() {

    }

    private class Listener implements ActionListener { // listener klasse ist in der our frame klasse. so haben wir zugriff auf alle attribute
        @Override
        // dieser methoden rumpf wird ausgeführt sobald der aktion listener was erfasst
        public void actionPerformed(ActionEvent event) {
            JComponent el = (JComponent) event.getSource();
            switch (el.getName()) {
                case "newGameButton": // Hier kommt der Name des Elements ins Spiel
                    showGameSettings();
                    break;
                case "loadGameButton":
                    // Do Stuff
                    break;
                case "radio2Player":
                case "radio3Player":
                case "radio4Player":
                case "radio5Player":
                case "radio6Player":
                    int p = Integer.parseInt(el.getName().substring(5, 6));
                    for (int i = 1; i <= 6; i++) {
                        if (i <= p) {
                            if (((JTextField) gameSettingsObjects.get("nameP" + i)).getText().isEmpty())
                                ((JTextField) gameSettingsObjects.get("nameP" + i)).setText("Spieler "+i);
                            gameSettingsObjects.get("nameP" + i).setEnabled(true);
                            gameSettingsObjects.get("isKIP" + i).setEnabled(true);
                        } else {
                            gameSettingsObjects.get("nameP" + i).setEnabled(false);
                            ((JTextField) gameSettingsObjects.get("nameP" + i)).setText("");
                            gameSettingsObjects.get("isKIP" + i).setEnabled(false);
                            ((JCheckBox) gameSettingsObjects.get("isKIP" + i)).setSelected(false);
                        }
                    }
                    break;
                default:
                    System.err.println("(Button ohne Handler geklickt)");
            }
        }
    }
}
