package net.white_it.ships.helper;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.white_it.ships.helper.GameFrame.ListenerAction;


//singleton//
public class Hauptfenster extends JFrame{
	private static Hauptfenster instance = null;
	
	private HashMap<String, JComponent> mainWindowObjects, gameSettingsObjects, battlefieldObjects;
	
	private Hauptfenster(){
		mainWindowObjects = new HashMap<>();
		gameSettingsObjects = new HashMap<>();
		battlefieldObjects = new HashMap<>();
		
		JButton button = new JButton();
		button.addActionListener(new Listener());
		button.setEnabled(true);
		button.setBounds(10, 10, 100, 50);
		button.setVisible(true);
	}
	
	public static Hauptfenster getInstance(){
		if(instance == null){
			instance = new Hauptfenster();
		}
		return instance;
	}
	
	public void showMainWindow(){
		
	}
	
	public void showGameSettings(){
		
	}
	
	public void showBattlefield(){
		
	}
	
	private class Listener implements ActionListener { // listener klasse ist in der our frame klasse. so haben wir zugriff auf alle attribute
		@Override
		// dieser methoden rumpf wird ausgeführt sobald der aktion listener was erfasst
		public void actionPerformed(ActionEvent e) {
			/*if(e.getSource() == ){ //standard methode die verschiedene buttons unterscheiden lässt
			} else if(e.getSource() == ){
			}*/
		}
	}
}
