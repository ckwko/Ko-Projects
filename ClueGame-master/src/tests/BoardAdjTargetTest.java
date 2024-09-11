/* Authors: Michael Pineiro and Calvin Ko
 * 
 */

package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {

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
	public void testAdjacenciesRooms()
	{
		// First test medbay with 2 doors and 1 vent
		Set<BoardCell> testList = board.getAdjList(2, 2); // center cell
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(13, 23))); // vent
		assertTrue(testList.contains(board.getCell(3, 6)));
		assertTrue(testList.contains(board.getCell(5, 1))); // door 
		
		// test storage
		testList = board.getAdjList(17, 2);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(3, 17))); // vent
		assertTrue(testList.contains(board.getCell(15, 3))); // door
		
		// one more room, the reactor
		testList = board.getAdjList(16, 11);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(15, 7))); // left door
		assertTrue(testList.contains(board.getCell(15, 15))); // right door
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(3, 6); // doorway to medbay
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2, 2))); // room center
		assertTrue(testList.contains(board.getCell(3, 7))); // another walkway
		assertTrue(testList.contains(board.getCell(2, 6))); // another walkway
		assertTrue(testList.contains(board.getCell(4, 6))); // another walkway

		testList = board.getAdjList(5, 1);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(2, 2))); // room center
		assertTrue(testList.contains(board.getCell(6, 1))); // walkway
		assertTrue(testList.contains(board.getCell(5, 2))); // walkway
		
		testList = board.getAdjList(4, 11); 
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2, 11))); // center
		assertTrue(testList.contains(board.getCell(4, 12)));
		assertTrue(testList.contains(board.getCell(4, 10)));
		assertTrue(testList.contains(board.getCell(5, 11)));
	}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(19, 6);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(18, 6)));
		
		// Test near a door but not adjacent
		testList = board.getAdjList(16, 18);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(16, 17)));
		assertTrue(testList.contains(board.getCell(16, 19)));
		assertTrue(testList.contains(board.getCell(15, 18)));

		// Test adjacent to walkways
		testList = board.getAdjList(10, 17);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(9, 17)));
		assertTrue(testList.contains(board.getCell(11, 17)));
		assertTrue(testList.contains(board.getCell(10, 16)));
		assertTrue(testList.contains(board.getCell(10, 18)));

		// Test next to closet
		testList = board.getAdjList(7, 13);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(6, 13)));
		assertTrue(testList.contains(board.getCell(7, 14)));
	
	}
	
	
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInMedbay() {
		// test a roll of 1
		board.calcTargets(board.getCell(2, 2), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(5, 1))); // doorway
		assertTrue(targets.contains(board.getCell(3, 6)));
		assertTrue(targets.contains(board.getCell(13, 23))); // center cell of other room (vented 1)
		
		// test a roll of 3
		board.calcTargets(board.getCell(2, 2), 3);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(13, 23))); // center cell of other room (vented 1)
		assertTrue(targets.contains(board.getCell(1, 6)));
		assertTrue(targets.contains(board.getCell(3, 8)));	
		assertTrue(targets.contains(board.getCell(6, 0)));
		assertTrue(targets.contains(board.getCell(7, 1)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(2, 2), 4);
		targets= board.getTargets();
		assertEquals(16, targets.size());
		assertTrue(targets.contains(board.getCell(5, 2)));
		assertTrue(targets.contains(board.getCell(7, 0)));	
		assertTrue(targets.contains(board.getCell(1, 5)));
		assertTrue(targets.contains(board.getCell(1, 7)));	
	}
	
	@Test
	public void testTargetsInReactor() {
		// test a roll of 1
		board.calcTargets(board.getCell(16, 11), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(15, 7)));
		assertTrue(targets.contains(board.getCell(15, 15)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(16, 11), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(15, 5)));
		assertTrue(targets.contains(board.getCell(17, 7)));	
		assertTrue(targets.contains(board.getCell(13, 15)));
		assertTrue(targets.contains(board.getCell(14, 16)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(16, 11), 4);
		targets= board.getTargets();
		assertEquals(20, targets.size());
		assertTrue(targets.contains(board.getCell(12, 7)));
		assertTrue(targets.contains(board.getCell(12, 15)));	
		assertTrue(targets.contains(board.getCell(17, 16)));
		assertTrue(targets.contains(board.getCell(15, 6)));	
	}

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(6, 17), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(3, 17)));
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(6, 16)));	
		assertTrue(targets.contains(board.getCell(6, 18)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(12, 21), 3);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(12, 18)));
		assertTrue(targets.contains(board.getCell(11, 19)));
		assertTrue(targets.contains(board.getCell(13, 19)));	
		assertTrue(targets.contains(board.getCell(10, 20)));
		assertTrue(targets.contains(board.getCell(14, 20)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(18, 16), 4);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(17, 15)));
		assertTrue(targets.contains(board.getCell(15, 15)));
		assertTrue(targets.contains(board.getCell(16, 16)));	
		assertTrue(targets.contains(board.getCell(14, 16)));
		assertTrue(targets.contains(board.getCell(16, 18)));	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(0, 7), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(1, 7)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(0, 7), 3);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(1, 5)));
		assertTrue(targets.contains(board.getCell(2, 6)));
		assertTrue(targets.contains(board.getCell(3, 7)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(0, 7), 4);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(1, 6)));
		assertTrue(targets.contains(board.getCell(3, 6)));
		assertTrue(targets.contains(board.getCell(4, 7)));	
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(11, 6), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(11, 5)));
		assertTrue(targets.contains(board.getCell(11, 7)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(11, 6), 3);
		targets= board.getTargets();
		assertEquals(14, targets.size());
		assertTrue(targets.contains(board.getCell(12, 2)));
		assertTrue(targets.contains(board.getCell(8, 6)));
		assertTrue(targets.contains(board.getCell(11, 5)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(11, 6), 4);
		targets= board.getTargets();
		assertEquals(19, targets.size());
		assertTrue(targets.contains(board.getCell(12, 2)));
		assertTrue(targets.contains(board.getCell(7, 6)));
		assertTrue(targets.contains(board.getCell(11, 10)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 1
		board.getCell(5, 1).setOccupied(true, "blue");
		board.calcTargets(board.getCell(7, 2), 4);
		board.getCell(5, 1).setOccupied(false, "blue");
		Set<BoardCell> targets = board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(5, 2)));
		assertTrue(targets.contains(board.getCell(9, 2)));
		assertTrue(targets.contains(board.getCell(7, 4)));	
		assertFalse( targets.contains( board.getCell(5, 1))) ;
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(3, 6).setOccupied(true, "blue");
		board.calcTargets(board.getCell(3, 7), 2);
		board.getCell(3, 6).setOccupied(false, "blue");
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(2, 6)));	
		assertTrue(targets.contains(board.getCell(1, 7)));	
		assertTrue(targets.contains(board.getCell(5, 7)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(12, 21).setOccupied(true, "blue");
		board.calcTargets(board.getCell(13, 23), 2);
		board.getCell(12, 21).setOccupied(false, "blue");
		targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(2, 2)));

	}
}