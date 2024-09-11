// Authors Michael Pineiro, Calvin Ko
// Test suite for the cluesetup file (loading players, weapon types, rooms)

package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;

public class GameSetupTests {
	
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	
	@Test
	public void testPlayersLoaded() {
		BoardCell cell = board.getCell(0, 7);
		assertEquals(cell.isOccupied(), true);
		cell = board.getCell(0, 14);
		assertEquals(cell.isOccupied(), true);
		cell = board.getCell(0, 20);
		assertEquals(cell.isOccupied(), true);
		cell = board.getCell(19, 6);
		assertEquals(cell.isOccupied(), true);
		cell = board.getCell(19, 15);
		assertEquals(cell.isOccupied(), true);
		cell = board.getCell(9, 24);
		assertEquals(cell.isOccupied(), true);
	}
	
	@Test
	public void testPlayersInitialized() {
		ArrayList<Player> players = board.getPlayerList();
		assertEquals(players.size(), 6);
		boolean humanPlayer = false;
		for (Player p: players) {
			if (p.isHuman()) {
				humanPlayer = true;
				break;
			}
		}
		assert(humanPlayer);
	}
	
	@Test
	public void confirmAllCardsAreLoaded() {
		ArrayList<Card> deck = board.getDeck();
		assertEquals(deck.size(), 21); // ensures there is a card for every character, room, weapon
		int roomCount = 0, weaponCount = 0, playerCount = 0;
		for (Card c : deck) {
			CardType type = c.getCardType();
			if (type == CardType.WEAPON) {
				weaponCount++;
			} else if (type == CardType.ROOM) {
				roomCount++;
			} else if (type == CardType.PERSON) {
				playerCount++;
			} else {
				throw new AssertionError("There was a card with an undefined type");
			}
		}
		assertEquals(roomCount, 9);
		assertEquals(weaponCount, 6);
		assertEquals(playerCount, 6);
	}
	
	@Test
	public void confirmSolutionDealt() {
		Solution sol = board.getSolution();
		assertNotNull(sol.getRoom());
		assertNotNull(sol.getWeapon());
		assertNotNull(sol.getPlayer());
	}

	@Test
	public void confirmEachPlayerHasDealtCards() {
		ArrayList<Player> players = board.getPlayerList();
		for (Player p: players) {
			assertNotNull(p.getHand());
			assertEquals(p.getHand().size(), 3);
		}
	}
}
