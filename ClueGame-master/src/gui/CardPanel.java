/*
 * Authors: Calvin Ko and Michael Pineiro
 * Date: 16 April 2023
 * 
 * This class contains the GUI element for the right hand side of the GUI,
 * which contains panels for each seen card and card in the players hand.
 */

package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Utility;

public class CardPanel extends JPanel {
	JPanel peoplePanel;
	JPanel roomsPanel;
	JPanel weaponsPanel;
	HumanPlayer player;
	
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public CardPanel(HumanPlayer player)  {
		this.player = player;
		
		setLayout(new GridLayout(1, 0));
		add(generateMainPanel());
	}
	
	/*
	 * the primary panel that contains all other panels
	 */
	private JPanel generateMainPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3, 0));
		mainPanel.setBorder(BorderFactory.createTitledBorder("Known Cards"));
		mainPanel.add(peoplePanel());
		mainPanel.add(roomsPanel());
		mainPanel.add(weaponsPanel());
		return mainPanel;
	}
	
	/*
	 * added the panel to display people cards
	 */
	private JPanel peoplePanel() {
		peoplePanel = new JPanel();
		peoplePanel.setLayout(new GridLayout(0, 1));
		peoplePanel.setBorder(BorderFactory.createTitledBorder("People"));
		
		JPanel inHand = new JPanel();
		inHand.setBorder(BorderFactory.createTitledBorder("In Hand"));
		
		JPanel seen = new JPanel();
		seen.setBorder(BorderFactory.createTitledBorder("Seen"));
		
		peoplePanel.add(inHand);
		peoplePanel.add(seen);
		return peoplePanel;
	}
	
	/*
	 * added the panel to display rooms cards
	 */
	private JPanel roomsPanel() {
		roomsPanel = new JPanel();
		roomsPanel.setLayout(new GridLayout(0, 1));
		roomsPanel.setBorder(BorderFactory.createTitledBorder("Rooms"));
		
		JPanel inHand = new JPanel();
		//peoplePanel.setLayout(new GridLayout(2, 0));
		inHand.setBorder(BorderFactory.createTitledBorder("In Hand"));
		
		JPanel seen = new JPanel();
		seen.setBorder(BorderFactory.createTitledBorder("Seen"));
		
		roomsPanel.add(inHand);
		roomsPanel.add(seen);
		return roomsPanel;
	}
	
	/*
	 * added the panel to display weapons cards
	 */
	private JPanel weaponsPanel() {
		weaponsPanel = new JPanel();
		weaponsPanel.setLayout(new GridLayout(0, 1));
		weaponsPanel.setBorder(BorderFactory.createTitledBorder("Weapons"));
		
		JPanel inHand = new JPanel();
		//peoplePanel.setLayout(new GridLayout(2, 0));
		inHand.setBorder(BorderFactory.createTitledBorder("In Hand"));
		
		JPanel seen = new JPanel();
		seen.setBorder(BorderFactory.createTitledBorder("Seen"));
		
		weaponsPanel.add(inHand);
		weaponsPanel.add(seen);
		return weaponsPanel;
	}
	
	/*
	 * Reupdates some other panels
	 */
	private void updatePanel(JPanel panel, CardType type) {
		Set<Card> seen = player.getSeenCards();
		panel.removeAll();
		
		// build the cards in hand
		JPanel inHand = new JPanel();
		inHand.setBorder(BorderFactory.createTitledBorder("In Hand"));
		
		for (Card c : player.getHand()) {
			if (c.getCardType() == type) {
				JTextField cardName = new JTextField(c.getCardName());
				cardName.setEnabled(false);
				inHand.add(cardName);
			}
		}
		
		// build the seen cards
		JPanel seenPanel = new JPanel();
		seenPanel.setBorder(BorderFactory.createTitledBorder("Seen"));
		
		for (Card c : seen) {
			if (c.getCardType() == type) {
				JTextField text = new JTextField(c.getCardName());
				text.setEnabled(false);
				Color color = Utility.colorStringToObject(c.getColor());
				text.setBackground(color);
				seenPanel.add(text);
			}
		}
		panel.add(inHand);
		panel.add(seenPanel);
	}
	
	private Color getColor(String color) {
		switch(color) {
		case("Red"):
			return Color.red;
		case("Pink"):
			return Color.pink;
		case ("Cyan"):
			return Color.cyan;
		case ("Blue"):
			return Color.blue;
		case ("Yellow"):
			return Color.yellow;
		case ("Orange"):
			return Color.orange;
		default:
			return Color.white;
			
		}
	}
	
	public void updatePanels() {
		updatePanel(peoplePanel, CardType.PERSON);
		updatePanel(roomsPanel, CardType.ROOM);
		updatePanel(weaponsPanel, CardType.WEAPON);
		revalidate();
	}
	
}
