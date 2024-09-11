/* Authors: Michael Pineiro and Calvin Ko


 * Date: 7 April 2023
 * 
 *  This class is the main gui compoenent, with the JFrame for the two other
 *  panels and the boardpanel
 */

package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import javax.swing.JFrame;
import javax.swing.JPanel;

import clueGame.Board;
import clueGame.Card;
import clueGame.HumanPlayer;

public class ClueGame extends JFrame {
	private static Board board;
	private CardPanel cardPanel;
	private GameControlPanel controlPanel;
	
	public ClueGame() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		HumanPlayer player = (HumanPlayer) board.getPlayer(0);
		
		// initialize window size and panels
		setSize(1200, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cardPanel = new CardPanel(player);
		cardPanel.updatePanels();
		board.setCardPanel(cardPanel);
		controlPanel = new GameControlPanel();
		add(board, BorderLayout.CENTER);
		add(cardPanel, BorderLayout.EAST); 
		add(controlPanel, BorderLayout.SOUTH);
	}
	
	private static void displayMessageDialog(ClueGame gui) {
		String name = board.getPlayer(0).getName();
		JOptionPane.showMessageDialog(gui, "You are " + name + ".\n"
				+ "Can you find the solution \n"
				+ "before the computer players?");		
		
	}
	
	/**
	 * Main entrypoint to the game
	 */
	public static void main(String[] args) {
		ClueGame gui = new ClueGame();
		gui.setVisible(true);
		displayMessageDialog(gui);

	}
	
}
