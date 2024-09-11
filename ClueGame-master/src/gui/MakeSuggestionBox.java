/*
 * Authors: Calvin Ko and Michael Pineiro
 * Date: 20 April 2023
 * This class contains the dialog box that pops up to make a suggestion
 */

package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;


import clueGame.Board;
import clueGame.Card;

public class MakeSuggestionBox extends JFrame implements ActionListener {

	private JPanel mainPanel;
	private Board board = Board.getInstance();
	private String room;
	
	JButton submit, cancel;
	JComboBox<String> weaponOptions;
	JComboBox<String> playerOptions;

	
	// constructor
	public MakeSuggestionBox(String room) {
		this.room = room;
		setSize(200, 300);
		setLocationRelativeTo(null);
		generateMainPanel();
		add(mainPanel);
	}
	
	private void generateMainPanel() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4, 0));
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(1, 2));
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(new GridLayout(1, 2));
		JPanel weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(1, 2));
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 2));
		
		// room panel
		JTextField roomLabel = new JTextField("Room");
		JTextField roomName = new JTextField(room);
		roomLabel.setEnabled(false);
		roomName.setEnabled(false);
		roomPanel.add(roomLabel);
		roomPanel.add(roomName);
		
		// player panel
		JTextField playerLabel = new JTextField("Players");
		playerLabel.setEnabled(false);
		playerOptions = new JComboBox<String>();
		ArrayList<Card> players = board.getPlayerCards();
		for (Card p : players) {
			playerOptions.addItem(p.getCardName());
		}
		playerPanel.add(playerLabel);
		playerPanel.add(playerOptions);
		
		// weapon panel
		JTextField weaponLabel = new JTextField("Weapons");
		weaponLabel.setEnabled(false);
		weaponOptions =  new JComboBox<String>();
		ArrayList<Card> weapons = board.getWeaponCards();
		for (Card r : weapons) {
			weaponOptions.addItem(r.getCardName());
		}
		weaponPanel.add(weaponLabel);
		weaponPanel.add(weaponOptions);
		
		// buttons panel
		submit = new JButton("Submit");
		submit.addActionListener(this);
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		buttonsPanel.add(submit);
		buttonsPanel.add(cancel);
		
		mainPanel.add(roomPanel);
		mainPanel.add(playerPanel);
		mainPanel.add(weaponPanel);
		mainPanel.add(buttonsPanel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cancel) {
			this.dispose();
		} else {
			String weapon = String.valueOf(weaponOptions.getSelectedItem());
			String player = String.valueOf(playerOptions.getSelectedItem());
			board.setSuggestionValues(room, weapon, player);
			this.dispose();
		}
	}
}
