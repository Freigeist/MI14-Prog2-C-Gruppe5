package net.white_it.ships.helper;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.scene.control.ComboBox;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameFrame{
		JFrame hauptfenster; //////////////////////
		JButton neuesSpiel;
		JButton spielLaden;
		JButton introduction;
		
		JFrame inNeuesSpiel; //////////////////////
		JLabel anzahlSpielerText;
		JComboBox listeAnzahlSpieler;
		JPanel anzahlPlusText;
		
		public GameFrame(){
			/*JFRAME1*/
			hauptfenster = new JFrame();
			hauptfenster.setSize(800, 600); // bestimmt größe
			hauptfenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// jframe startet neue threads, das fenster geht bei klick auf x nicht ganz zu
			hauptfenster.setLocationRelativeTo(null); // fenster wird mittig positioniert
			hauptfenster.setTitle("MENU");
			hauptfenster.setResizable(true); // fenstergröße bleibt gleich
			hauptfenster.setLayout(new FlowLayout()); // macht die seite responsive // flowlayout
			
			/*JFRAME2*/
			inNeuesSpiel = new JFrame();
			inNeuesSpiel.setSize(800, 600); // bestimmt größe
			inNeuesSpiel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// jframe startet neue threads, das fenster geht bei klick auf x nicht ganz zu
			inNeuesSpiel.setLocationRelativeTo(null); // fenster wird mittig positioniert
			inNeuesSpiel.setTitle("MENU");
			inNeuesSpiel.setResizable(true); // fenstergröße bleibt gleich
			inNeuesSpiel.setLayout(new FlowLayout()); // macht die seite responsive // flowlayout
			
			//alles für FRAME1//
			/*jbutton*/
			neuesSpiel = new JButton("Neues Spiel");
			neuesSpiel.addActionListener(new ListenerAction());
			neuesSpiel.setEnabled(true);
			neuesSpiel.setVisible(true);
			hauptfenster.add(neuesSpiel);
			
			spielLaden = new JButton("Neues Spiel");
			spielLaden.addActionListener(new ListenerAction());
			spielLaden.setEnabled(true);
			spielLaden.setVisible(true);
			hauptfenster.add(spielLaden);
		
			introduction = new JButton("Neues Spiel");
			introduction.addActionListener(new ListenerAction());
			introduction.setEnabled(true);
			introduction.setVisible(true);
			hauptfenster.add(introduction);
			
			//alles für frame2//
			String[] listeComboBox = {"1", "2", "3", "4"};
			
			anzahlSpielerText = new JLabel("Anzahl Spieler:"); // überschrift
			listeAnzahlSpieler = new JComboBox(listeComboBox);
			
			anzahlPlusText = new JPanel();
			anzahlPlusText.add(anzahlSpielerText);
			anzahlPlusText.add(listeAnzahlSpieler);
			inNeuesSpiel.add(anzahlPlusText);
			anzahlPlusText.setVisible(true);
			
			
			
			
			
			
			
			
			
			hauptfenster.setVisible(true);  // müssen beide ganz am ende stehen 
			inNeuesSpiel.setVisible(false); // wird durch actionlistener bei klick auf neues spiel auf true gesetzt
		}
		
		public class ListenerAction implements ActionListener { // listener klasse ist in der our frame klasse. so haben wir zugriff auf alle attribute
			@Override
			// dieser methoden rumpf wird ausgeführt sobald der aktion listener was erfasst
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == neuesSpiel){ //standard methode die verschiedene buttons unterscheiden lässt
					inNeuesSpiel.setVisible(true);
				} else if(e.getSource() == spielLaden){
					
				} else{

				}
			}
		}
}
