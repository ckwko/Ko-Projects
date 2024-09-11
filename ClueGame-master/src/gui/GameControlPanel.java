/*
 * Authors: Michael Pineiro and Calvin Ko
 * Date: 4 April 2023
 * Resources: Help with borders https://www.tutorialspoint.com/swingexamples/add_title_to_border_panel.htm
 * This class contains the JPanel for the bottom part of the GUI, where the player can
 * see who's turn it is, what their roll was, accuse, move to next player, and see
 * results.
 */

package gui;

import java.awt.Color;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;


import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.Player;
import clueGame.Solution;
import clueGame.Utility;

public class GameControlPanel extends JPanel implements ActionListener{

	JTextField currentTurnText, rollNumber, currentGuess, resultGuess;
	JButton nextButton, accusationButton;
	Card curDisprove;

	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		setLayout(new GridLayout(2, 0));
		JPanel topPanel = createTopPanel();
		JPanel bottomPanel = createBottomPanel();

		add(topPanel);
		add(bottomPanel);
		Border border = BorderFactory.createTitledBorder("Control");
		setBorder(border);
	}

	/*
	 * The top panel contains who's turn it is, the roll, and buttons to accuse or move to next
	 */
	private JPanel createTopPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 4));

		// create two panels
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2, 1));
		turnPanel.add(new JLabel("Whos Turn?"));
		currentTurnText = new JTextField();
		currentTurnText.setEnabled(false);
		turnPanel.add(currentTurnText);

		// display roll info
		JPanel rollPanel = new JPanel();
		rollPanel.setLayout(new GridLayout(2, 2));
		rollPanel.add(new JLabel("Roll"));
		rollNumber = new JTextField();
		rollNumber.setEnabled(false);
		rollPanel.add(rollNumber);

		mainPanel.add(turnPanel);
		mainPanel.add(rollPanel);
		accusationButton = new JButton("Make Accusation");
		accusationButton.addActionListener(this);
		nextButton = new JButton("Next");
		nextButton.addActionListener(this);
		mainPanel.add(nextButton);
		mainPanel.add(accusationButton);
		return mainPanel;
	}

	/*
	 * The bottom panel contains the current guess and guess result
	 */
	private JPanel createBottomPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0, 2));

		// Guess panel
		JPanel guessPanel = new JPanel();
		Border guessBorder = BorderFactory.createTitledBorder("Guess");
		guessPanel.setBorder(guessBorder);
		guessPanel.setLayout(new GridLayout(1, 0));
		currentGuess = new JTextField();
		currentGuess.setEnabled(false);
		guessPanel.add(currentGuess);

		// Guess result panel
		JPanel resultPanel = new JPanel();
		Border resultBorder = BorderFactory.createTitledBorder("Guess Result");
		resultPanel.setBorder(resultBorder);
		resultPanel.setLayout(new GridLayout(1, 0));
		resultGuess = new JTextField();
		resultGuess.setEnabled(false);
		resultPanel.add(resultGuess);

		mainPanel.add(guessPanel);
		mainPanel.add(resultPanel);
		return mainPanel;
	}

	/*
	 * Sets the textbox for the guess
	 */
	private void setGuess(String guess, Color color) {
		currentGuess.setBackground(color);
		currentGuess.setForeground(color.black);
		currentGuess.setText(guess);
	}

	/*
	 * Sets the textbox for the result
	 */
	private void setGuessResult(String result, Color color) {
		resultGuess.setBackground(color);
		resultGuess.setText(result);
	}

	/*
	 * Sets the textbox for the current player
	 */
	private void setTurn(Player player, int roll) {
		Color color = Utility.colorStringToObject(player.getColor());
		currentTurnText.setBackground(color);
		currentTurnText.setForeground(Color.black);
		currentTurnText.setText(player.getName());
		rollNumber.setText(Integer.toString(roll));

	}	

	/*
	 * Update info in the control panel
	 */
	private void updateControlPanel(int roll) {
		Board board = Board.getInstance();
		rollNumber.setText(Integer.toString(roll));
		int currentPlayer = board.getCurrentPlayer();
		Player curPlayer = board.getPlayer(currentPlayer);
		setTurn(curPlayer, roll);
		
		if(board.getPlayerSuggestionFinished()) {
			if(board.getResultCard() != null) {
				String guess = board.getGuessString();
				Color guessColor = Utility.colorStringToObject(curPlayer.getColor());
				setGuess(guess, guessColor);
				Card result = board.getResultCard();
				setGuessResult(result.getCardName(), Utility.colorStringToObject(result.getColor()));
			} else {
				setGuessResult("Suggestion disproven!", Color.white);
			}
		}
	}

	/*
	 * Method to define what happens when next button is presseed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nextButton) {
			Board board = Board.getInstance();
			int currentPlayer = board.getCurrentPlayer();
			BoardCell playerPos = board.getCell(board.getPlayer(currentPlayer).getRow(), board.getPlayer(currentPlayer).getCol());

			int roll = board.nextTurn(); // does another turn!
			currentPlayer = board.getCurrentPlayer(); // the current player has gotten updated
			if (!board.currentPlayerIsHuman()) {
				// move player
				board.moveComputerPlayer();

			}
			// gets new roll and processes targets
			updateControlPanel(roll); // updates the text in the control panel
			board.repaint(); // repaints the board with changes
		} else if (e.getSource() == accusationButton) {
			MakeAccusationBox accusationBox = new MakeAccusationBox();
			accusationBox.setVisible(true);
			// all other accusation logic must be controlled from there. Async
		}
	}
}
