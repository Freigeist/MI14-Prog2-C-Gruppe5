package net.white_it.ships.gui;

import net.white_it.ships.collections.Schiffsammlung;
import net.white_it.ships.collections.Spielersammlung;
import net.white_it.ships.models.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

//singleton//
public class Hauptfenster extends JFrame {
    private static Hauptfenster instance = null;
    private HashMap<String, JComponent> mainWindowObjects, gameSettingsObjects, battlefieldObjects;

    private Integer intReturnValue = null;
    private String stringReturnValue = null;

    private boolean coordButtonsActive = true;

    private Hauptfenster() {
        // Alle Buttons, Textfelder, etc aus Fenstern werden hier drin gespeichert, zur Vereinfachung
        // des Ein-/ und Ausblendens
        mainWindowObjects = new HashMap<>();
        gameSettingsObjects = new HashMap<>();
        battlefieldObjects = new HashMap<>();

        // Eine neue Instanz für jeden Button/jedes Textfeld würde einen sehr großen Overhead erzeugen
        WindowActionListener windowActionListenerInstance = new WindowActionListener();
        WindowDocumentListener windowDocumentListenerInstance = new WindowDocumentListener();


        // Arten an Elementen die Benötigt werden
        JButton button;
        JTextField textField;
        JEditorPane textArea;
        JLabel label;
        JCheckBox checkBox;
        JRadioButton radioButton;

        /*
         * BEGINN HAUPTMENUE
         */
        button = new JButton();
        button.addActionListener(windowActionListenerInstance);
        button.setBounds(290, 10, 220, 50);
        button.setText("Neues Spiel");
        button.setName("newGameButton"); // Die Namen werden im WindowActionListener benötigt
        this.add(button);
        mainWindowObjects.put("newGameButton", button);

        // Wiederverwendung der Variable button, da diese nicht nach hinzufügen zum Fenster
        // und zum Vector nicht mehr benötigt wird. Die spätere Verwendung folgt aus dem Vector
        // heraus
        button = new JButton();
        button.addActionListener(windowActionListenerInstance);
        button.setBounds(290, 70, 220, 50);
        button.setText("Letztes Spiel laden");
        button.setName("loadGameButton");
        this.add(button);
        mainWindowObjects.put("loadGameButton", button);

        button = new JButton();
        button.addActionListener(windowActionListenerInstance);
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
            radioButton.setBounds(50, 30 * i + 20, 180, 20);
            radioButton.addActionListener(windowActionListenerInstance);
            if (i == 6)
                radioButton.setSelected(true);
            this.add(radioButton);
            group.add(radioButton);
            this.gameSettingsObjects.put("radio" + i + "Player", radioButton);
        }

        label = new JLabel("Namen der Spieler (3-20 Zeichen):");
        label.setBounds(300, 20, 200, 20);
        this.add(label);
        this.gameSettingsObjects.put("labelNames", label);

        label = new JLabel("Is KI?");
        label.setBounds(520, 20, 50, 20);
        this.add(label);
        this.gameSettingsObjects.put("labelIsKI", label);

        for (int i = 1; i <= 6; i++) {
            label = new JLabel("Spieler " + i + ":");
            label.setBounds(230, 30 * i + 20, 70, 20);
            this.add(label);
            this.gameSettingsObjects.put("labelS" + i, label);

            textField = new JTextField();
            textField.setName("nameP" + i);
            textField.setBounds(300, 30 * i + 20, 200, 20);
            textField.setText("Spieler " + i);
            textField.getDocument().addDocumentListener(windowDocumentListenerInstance);
            this.add(textField);
            this.gameSettingsObjects.put("nameP" + i, textField);

            checkBox = new JCheckBox();
            checkBox.setBounds(520, 30 * i + 20, 50, 20);
            this.add(checkBox);
            this.gameSettingsObjects.put("isKIP" + i, checkBox);
        }

        label = new JLabel("Spielfeldgr\u00F6\u00DFe");
        label.setBounds(600, 20, 200, 20);
        this.add(label);
        this.gameSettingsObjects.put("labelFieldSize", label);

        textField = new JTextField();
        textField.setName("fieldSize");
        textField.setBounds(600, 40, 35, 20);
        textField.setText("10");
        textField.getDocument().addDocumentListener(windowDocumentListenerInstance);
        this.add(textField);
        this.gameSettingsObjects.put("fieldSize", textField);

        label = new JLabel("(min. 3, max. 20)");
        label.setBounds(645, 40, 155, 20);
        this.add(label);
        this.gameSettingsObjects.put("labelFieldSizeDesc", label);

        label = new JLabel("(Max. 3 Schiffe)");
        label.setForeground(Color.gray);
        label.setBounds(600, 60, 200, 20);
        this.add(label);
        this.gameSettingsObjects.put("labelMaxShips", label);


        label = new JLabel("Anzahl UBoote");
        label.setBounds(600, 90, 200, 20);
        this.add(label);
        this.gameSettingsObjects.put("labelCountUBoote", label);

        textField = new JTextField();
        textField.setName("countUBoote");
        textField.setBounds(600, 110, 35, 20);
        textField.setText("0");
        textField.getDocument().addDocumentListener(windowDocumentListenerInstance);
        this.add(textField);
        this.gameSettingsObjects.put("countUBoote", textField);

        label = new JLabel("(max. 3)");
        label.setBounds(645, 110, 155, 20);
        this.add(label);
        this.gameSettingsObjects.put("labelCountUBooteDesc", label);


        label = new JLabel("Anzahl Korvetten");
        label.setBounds(600, 140, 200, 20);
        this.add(label);
        this.gameSettingsObjects.put("labelCountKorvetten", label);

        textField = new JTextField();
        textField.setName("countKorvetten");
        textField.setBounds(600, 160, 35, 20);
        textField.setText("0");
        textField.getDocument().addDocumentListener(windowDocumentListenerInstance);
        this.add(textField);
        this.gameSettingsObjects.put("countKorvetten", textField);

        label = new JLabel("(max. 3)");
        label.setBounds(645, 160, 155, 20);
        this.add(label);
        this.gameSettingsObjects.put("labelCountKorvettenDesc", label);


        label = new JLabel("Anzahl Fregatten");
        label.setBounds(600, 190, 200, 20);
        this.add(label);
        this.gameSettingsObjects.put("labelCountFregatten", label);

        textField = new JTextField();
        textField.setName("countFregatten");
        textField.setBounds(600, 210, 35, 20);
        textField.setText("0");
        textField.getDocument().addDocumentListener(windowDocumentListenerInstance);
        this.add(textField);
        this.gameSettingsObjects.put("countFregatten", textField);

        label = new JLabel("(max. 2)");
        label.setBounds(645, 210, 155, 20);
        this.add(label);
        this.gameSettingsObjects.put("labelCountFregattenDesc", label);


        label = new JLabel("Anzahl Zerst\u00F6rer");
        label.setBounds(600, 240, 200, 20);
        this.add(label);
        this.gameSettingsObjects.put("labelCountZerstoerer", label);

        textField = new JTextField();
        textField.setName("countZerstoerer");
        textField.setBounds(600, 260, 35, 20);
        textField.setText("0");
        textField.getDocument().addDocumentListener(windowDocumentListenerInstance);
        this.add(textField);
        this.gameSettingsObjects.put("countZerstoerer", textField);

        label = new JLabel("(max. 2)");
        label.setBounds(645, 260, 155, 20);
        this.add(label);
        this.gameSettingsObjects.put("labelCountZerstoererDesc", label);

        // Feld zur Fehleranzeige
        label = new JLabel("Es wird min. 1 Schiff benötigt!"); // Per Default stehen alle Arten auf 0
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setForeground(Color.red);
        label.setBounds(10, 300, 590, 240);
        this.add(label);
        this.gameSettingsObjects.put("labelErrorMsg", label);

        button = new JButton();
        button.addActionListener(windowActionListenerInstance);
        button.setBounds(10, 550, 200, 40);
        button.setText("< Zur\u00FCck");
        button.setName("backToMainButton");
        this.add(button);
        gameSettingsObjects.put("backToMainButton", button);

        button = new JButton();
        button.addActionListener(windowActionListenerInstance);
        button.setBounds(590, 550, 200, 40);
        button.setText("Spiel starten >");
        button.setName("startGameButton");
        button.setEnabled(false);
        this.add(button);
        gameSettingsObjects.put("startGameButton", button);
        /*
         * BEGINN SETTINGS
         */

        /*
         * BEGINN SPIEL
         */
        label = new JLabel("<html>Spielinformationen:<br />" +
                "Aktueller Spieler: <font color=\"#009900\">NAME</font><br />" +
                "Gegnerischer Spieler: <font color=\"#990000\">NAME</font><br />" +
                "<br />" +
                "Nächste Aktion:<br />" +
                "<font color=\"#000099\">Bla bla bla</font></html>");
        label.setBounds(10, 10, 290, 170);
        label.setVerticalAlignment(SwingConstants.TOP);
        this.add(label);
        battlefieldObjects.put("labelStatus", label);

        // Player-/Shipselects
        for (int i = 0; i < 6; i++) {
            button = new JButton();
            button.addActionListener(windowActionListenerInstance);
            button.setBounds(50, ((i + 1) * 30) + 200, 200, 20);
            button.setText("Element " + i);
            button.setName("buttonSelectI" + i);
            button.setVisible(false);
            this.add(button);
            battlefieldObjects.put("buttonSelectI" + i, button);
        }

        char c;
        for (int x = 1; x <= 20; x++) {
            c = (char) (64 + x);
            for (int y = 1; y <= 20; y++) {
                button = new JButton();
                button.addActionListener(windowActionListenerInstance);
                button.setBounds((x * 30) + 340, (y * 30) - 10, 30, 30);
                button.setText("x");
                button.setToolTipText(c + "" + y);
                button.setName("coordButton");
                button.setMargin(new Insets(0, 0, 0, 0));
                button.setVisible(false);
                this.add(button);
                battlefieldObjects.put("coordButton" + c + y, button);
            }
        }

        /*
         * ENDE SPIEL
         */

        this.setResizable(false);
        this.setSize(810, 630);
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
        this.clearWindow();
        for (Map.Entry<String, JComponent> e : battlefieldObjects.entrySet()) {
            if (!e.getKey().matches("^(coordButton|buttonSelect).*$"))
                e.getValue().setVisible(true);
            else
                e.getValue().setVisible(false);
        }
        this.revalidate();
        this.repaint();
        this.setSize(1010, 670);
    }

    public void buildPlayerSelect(Spielersammlung spieler) {
        for (Map.Entry<String, JComponent> e : battlefieldObjects.entrySet()) {
            if (!e.getKey().matches("^buttonSelect[NJVH]"))
                e.getValue().setVisible(false);
        }
        JButton button;
        boolean alive;
        for (int i = 0; i < 6; i++) {
            button = (JButton) battlefieldObjects.get("buttonSelectI" + i);
            if (i < spieler.size()) {
                button.setVisible(true);
                alive = spieler.getSpielerByKey(i).isAlive();
                button.setEnabled(alive);
                button.setForeground((alive ? Color.BLACK : Color.lightGray));
            } else {
                button.setVisible(false);
            }
        }
        this.repaint();
        this.revalidate();
    }

    public void buildShipSelect(Schiffsammlung schiffe) {
        for (Map.Entry<String, JComponent> e : battlefieldObjects.entrySet()) {
            if (!e.getKey().matches("^buttonSelect[NJVH]"))
                e.getValue().setVisible(false);
        }
        JButton button;
        boolean alive, active;
        for (int i = 0; i < 6; i++) {
            button = (JButton) battlefieldObjects.get("buttonSelectI" + i);
            if (i < schiffe.size()) {
                button.setVisible(true);
                alive = schiffe.getShipByKey(i).isAlive();
                active = schiffe.getShipByKey(i).isActive();
                button.setEnabled(alive && active);
                if (!alive) {
                    button.setForeground(Color.RED);
                } else if (!active) {
                    button.setForeground(Color.LIGHT_GRAY);
                } else {
                    button.setForeground(Color.BLACK);
                }
            } else {
                button.setVisible(false);
            }
        }
        this.repaint();
        this.revalidate();
    }

    public void showVHSelect() {
        for (Map.Entry<String, JComponent> e : battlefieldObjects.entrySet()) {
            if (!e.getKey().matches("^buttonSelect[NJI]"))
                e.getValue().setVisible(false);
            if (!e.getKey().matches("^buttonSelect[VH]"))
                e.getValue().setVisible(true);
        }
        this.revalidate();
        this.repaint();
    }

    public void showJNSelect() {
        for (Map.Entry<String, JComponent> e : battlefieldObjects.entrySet()) {
            if (!e.getKey().matches("^buttonSelect[VHI]"))
                e.getValue().setVisible(false);
            if (!e.getKey().matches("^buttonSelect[JN]"))
                e.getValue().setVisible(true);
        }
        this.revalidate();
        this.repaint();
    }

    public void hideSelects() {
        for (Map.Entry<String, JComponent> e : battlefieldObjects.entrySet()) {
            if (!e.getKey().matches("^buttonSelect[VHSPJN]"))
                e.getValue().setVisible(false);
        }
        this.revalidate();
        this.repaint();
    }

    public void buildBattlefieldFromSpielfeld(Spielfeld spielfeld, boolean own) {
        int size = spielfeld.size();
        char c;
        JButton button;
        for (int x = 0; x < 20; x++) {
            c = (char) (65 + x);
            for (int y = 0; y < 20; y++) {
                button = (JButton) battlefieldObjects.get("coordButton" + c + (y + 1));
                if (x < size && y < size) {
                    button.setVisible(true);
                    button.setText(spielfeld.getPos(x, y, own));
                } else {
                    button.setVisible(false);
                }
            }
        }
    }

    public void updateInformationLabel(String current, String enemy, String nextJob) {
        JLabel label = (JLabel) battlefieldObjects.get("labelStatus");
        label.setText("<html>Spielinformationen:<br />" +
                "Aktueller Spieler: <font color=\"#009900\">" + current + "</font><br />" +
                "Gegnerischer Spieler: <font color=\"#990000\">" + enemy + "</font><br />" +
                "<br />" +
                "Nächste Aktion:<br />" +
                "<font color=\"#000099\">" + nextJob + "</font></html>");
    }

    public Integer getIntReturnValue() {
        Integer retVal = this.intReturnValue;
        this.intReturnValue = null;
        return retVal;
    }

    private void setIntReturnValue(Integer intReturnValue) {
        this.intReturnValue = intReturnValue;
    }

    public String getStringReturnValue() {
        String retVal = this.stringReturnValue;
        this.stringReturnValue = null;
        return retVal;
    }

    private void setStringReturnValue(String stringReturnValue) {
        this.stringReturnValue = stringReturnValue;
    }

    private class WindowActionListener implements ActionListener { // listener klasse ist in der our frame klasse. so haben wir zugriff auf alle attribute
        @Override
        // dieser methoden rumpf wird ausgeführt sobald der aktion listener was erfasst
        public void actionPerformed(ActionEvent event) {
            JComponent el = (JComponent) event.getSource();
            int p;
            switch (el.getName()) {
                case "newGameButton": // Hier kommt der Name des Elements ins Spiel
                    showGameSettings();
                    break;
                case "loadGameButton":
                    // Do Stuff
                    break;
                case "buttonSelectI0":
                case "buttonSelectI1":
                case "buttonSelectI2":
                case "buttonSelectI3":
                case "buttonSelectI4":
                case "buttonSelectI5":
                    p = Integer.parseInt(el.getName().substring(13, 14));
                    setIntReturnValue(p);
                    hideSelects();
                    break;
                case "radio2Player":
                case "radio3Player":
                case "radio4Player":
                case "radio5Player":
                case "radio6Player":
                    p = Integer.parseInt(el.getName().substring(5, 6));
                    for (int i = 1; i <= 6; i++) {
                        if (i <= p) {
                            if (((JTextField) gameSettingsObjects.get("nameP" + i)).getText().isEmpty())
                                ((JTextField) gameSettingsObjects.get("nameP" + i)).setText("Spieler " + i);
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
                case "backToMainButton":
                    showMainWindow();
                    break;
                case "startGameButton":
                    showBattlefield();
                    break;
                case "coordButton":
                    JButton button = (JButton) el;

                    // 'Missbrauch' des ToolTips zur Koordinatenübergabe
                    int[] coords = Spielfeld.coordToXY(button.getToolTipText());

                    // Do Stuff
                    break;
                default:
                    System.err.println("(Button ohne Handler geklickt)");
            }
        }
    }

    private class WindowDocumentListener implements DocumentListener {
        private final int E_OK = 0;
        private final int E_NONUMBER = 1;
        private final int E_LESS = 2;
        private final int E_MORE = 3;

        public void insertUpdate(DocumentEvent e) {
            validateTextFields();
        }

        public void removeUpdate(DocumentEvent e) {
            validateTextFields();
        }

        public void changedUpdate(DocumentEvent e) {
            //Plain text components do not fire these events
        }

        public void validateTextFields() {
            int error = 0, check = 0, fieldSize = -1, anzahlUBoote = 0, anzahlFregatten = 0, anzahlKorvetten = 0, anzahlZerstoerer = 0;
            JTextField field;
            String errorText = "<html>";

            /*
             * Prüfung der Namenslängen
             */
            for (int i = 1; i <= 6; i++) {
                field = (JTextField) gameSettingsObjects.get("nameP" + i);
                check = field.getText().length();
                if (field.isEnabled() && check < 3) {
                    error = 1;
                    errorText += "Name von Spieler " + i + " ist zu kurz!<br />";
                    field.setForeground(Color.red);
                } else if (field.isEnabled() && check > 20) {
                    error = 1;
                    errorText += "Name von Spieler " + i + " ist zu lang!<br />";
                    field.setForeground(Color.red);
                } else {
                    field.setForeground(Color.black);
                }
            }

            /*
             * Prüfung ob Feldgröße valide ist
             */
            field = (JTextField) gameSettingsObjects.get("fieldSize");
            try {
                fieldSize = Integer.parseInt(field.getText());
                if (fieldSize < 5) {
                    error = 1;
                    errorText += "Feldgr\u00F6\u00DFe muss min. 5x5 sein!<br />";
                    field.setForeground(Color.red);
                } else if (fieldSize > 20) {
                    error = 1;
                    errorText += "Feldgr\u00F6\u00DFe darf max. 20x20 sein!<br />";
                    field.setForeground(Color.red);
                } else {
                    field.setForeground(Color.black);
                }
            } catch (NumberFormatException nbe) {
                error = 1;
                errorText += "Feldgr\u00F6\u00DFe ist keine Zahl!<br />";
                field.setForeground(Color.red);
            }

            /*
             * Wenn die Feldgröße über 3 und unter 20 liegt bedeutet dies, dass die
             * Feldgröße valide ist und wir weiter machen können.
             */
            if (fieldSize >= 5 && fieldSize <= 20) {
                // Feldgröße passt, aktiviere alle abhängigen Felder (wieder)
                gameSettingsObjects.get("countUBoote").setEnabled(true);
                gameSettingsObjects.get("countKorvetten").setEnabled(true);
                gameSettingsObjects.get("countFregatten").setEnabled(true);
                gameSettingsObjects.get("countZerstoerer").setEnabled(true);

                int maxShips = Math.round(fieldSize / 3);

                int maxUBoot = (int) Math.floor(fieldSize / UBoot.size);
                maxUBoot = (maxShips < maxUBoot ? maxShips : maxUBoot);

                int maxKorvette = (int) Math.floor(fieldSize / Korvette.size);
                maxKorvette = (maxShips < maxKorvette ? maxShips : maxKorvette);

                int maxFregatte = (int) Math.floor(fieldSize / Fregatte.size);
                maxFregatte = (maxShips < maxFregatte ? maxShips : maxFregatte);

                int maxZerstoerer = (int) Math.floor(fieldSize / Zerstoerer.size);
                maxZerstoerer = (maxShips < maxZerstoerer ? maxShips : maxZerstoerer);

                // Updaten der Lables
                ((JLabel) gameSettingsObjects.get("labelMaxShips")).setText(
                        "(Max. " + maxShips + " Schiff" + (maxShips == 1 ? "" : "e") + ")");
                ((JLabel) gameSettingsObjects.get("labelCountUBooteDesc")).setText("(max. " + maxUBoot + ")");
                ((JLabel) gameSettingsObjects.get("labelCountKorvettenDesc")).setText("(max. " + maxKorvette + ")");
                ((JLabel) gameSettingsObjects.get("labelCountFregattenDesc")).setText("(max. " + maxFregatte + ")");
                ((JLabel) gameSettingsObjects.get("labelCountZerstoererDesc")).setText("(max. " + maxZerstoerer + ")");

                // Prüfung der UBoot-Anzahl
                field = (JTextField) gameSettingsObjects.get("countUBoote");
                try {
                    anzahlUBoote = Integer.parseInt(field.getText());
                    if (anzahlUBoote < 0) {
                        error = 1;
                        errorText += "UBoot-Anahl kann nicht kleiner 0 sein!<br />";
                        field.setForeground(Color.red);
                    } else if (anzahlUBoote > maxUBoot) {
                        error = 1;
                        errorText += "UBoot-Anzahl kann nicht gr\u00F6\u00DFer " + maxUBoot + " sein!<br />";
                        field.setForeground(Color.red);
                    } else {
                        field.setForeground(Color.black);
                    }
                } catch (NumberFormatException nbe) {
                    error = 1;
                    errorText += "UBoot-Anzahl ist keine Zahl!<br />";
                    field.setForeground(Color.red);
                }

                // Prüfung der Korvetten-Anzahl
                field = (JTextField) gameSettingsObjects.get("countKorvetten");
                try {
                    anzahlKorvetten = Integer.parseInt(field.getText());
                    if (anzahlKorvetten < 0) {
                        error = 1;
                        errorText += "Korvetten-Anahl kann nicht kleiner 0 sein!<br />";
                        field.setForeground(Color.red);
                    } else if (anzahlKorvetten > maxKorvette) {
                        error = 1;
                        errorText += "Korvetten-Anzahl kann nicht gr\u00F6\u00DFer " + maxUBoot + " sein!<br />";
                        field.setForeground(Color.red);
                    } else {
                        field.setForeground(Color.black);
                    }
                } catch (NumberFormatException nbe) {
                    error = 1;
                    errorText += "Korvetten-Anzahl ist keine Zahl!<br />";
                    field.setForeground(Color.red);
                }

                // Prüfung der Fregatten-Anzahl
                field = (JTextField) gameSettingsObjects.get("countFregatten");
                try {
                    anzahlFregatten = Integer.parseInt(field.getText());
                    if (anzahlFregatten < 0) {
                        error = 1;
                        errorText += "Fregatten-Anahl kann nicht kleiner 0 sein!<br />";
                        field.setForeground(Color.red);
                    } else if (anzahlFregatten > maxFregatte) {
                        error = 1;
                        errorText += "Fregatten-Anzahl kann nicht gr\u00F6\u00DFer " + maxUBoot + " sein!<br />";
                        field.setForeground(Color.red);
                    } else {
                        field.setForeground(Color.black);
                    }
                } catch (NumberFormatException nbe) {
                    error = 1;
                    errorText += "Fregatten-Anzahl ist keine Zahl!<br />";
                    field.setForeground(Color.red);
                }

                // Prüfung der Zerstörer-Anzahl
                field = (JTextField) gameSettingsObjects.get("countZerstoerer");
                try {
                    anzahlZerstoerer = Integer.parseInt(field.getText());
                    if (anzahlZerstoerer < 0) {
                        error = 1;
                        errorText += "Zerstoerer-Anahl kann nicht kleiner 0 sein!<br />";
                        field.setForeground(Color.red);
                    } else if (anzahlZerstoerer > maxZerstoerer) {
                        error = 1;
                        errorText += "Zerstoerer-Anzahl kann nicht gr\u00F6\u00DFer " + maxUBoot + " sein!<br />";
                        field.setForeground(Color.red);
                    } else {
                        field.setForeground(Color.black);
                    }
                } catch (NumberFormatException nbe) {
                    error = 1;
                    errorText += "Zerstoerer-Anzahl ist keine Zahl!<br />";
                    field.setForeground(Color.red);
                }

                if ((anzahlUBoote + anzahlFregatten + anzahlKorvetten + anzahlZerstoerer) > maxShips) {
                    error = 1;
                    errorText += "Die Gesamtanzahl der Schiffe ist gr\u00F6\u00DFer als " + maxShips + "!<br />";
                } else if ((anzahlUBoote + anzahlFregatten + anzahlKorvetten + anzahlZerstoerer) <= 0) {
                    error = 1;
                    errorText += "Es wird min. 1 Schiff benötigt!<br />";
                }

            } else {
                // Feldgröße passt nicht, deaktiviere alle abhängigen Felder
                gameSettingsObjects.get("countUBoote").setEnabled(false);
                gameSettingsObjects.get("countKorvetten").setEnabled(false);
                gameSettingsObjects.get("countFregatten").setEnabled(false);
                gameSettingsObjects.get("countZerstoerer").setEnabled(false);

                ((JLabel) gameSettingsObjects.get("labelMaxShips")).setText("(Max. ??? Schiffe)");
            }

            if (error == 1) {
                ((JLabel) gameSettingsObjects.get("labelErrorMsg")).setText(errorText + "</html>");
                gameSettingsObjects.get("startGameButton").setEnabled(false);
            } else {
                ((JLabel) gameSettingsObjects.get("labelErrorMsg")).setText("");
                gameSettingsObjects.get("startGameButton").setEnabled(true);
            }
        }
    }
}
