// Michael Pineiro, Calvin Ko

package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.BoardCell;

public class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	void setUp() {
		board = new TestBoard();
	}
	
	/*
	 * Tests whether the adjacency list properly finds adjacent cells
	 */
	@Test
	void testAdjacency() {
		BoardCell cell = board.getCell(0, 0); // origin
		Set<BoardCell> testAdjList = cell.getAdjList();
		assert(testAdjList.contains(board.getCell(1, 0)));
		assert(testAdjList.contains(board.getCell(0,  1)));
		assertEquals(testAdjList.size(), 2);
		
		// Repeat with different cells
		cell = board.getCell(1, 0);
		testAdjList = cell.getAdjList();
		assert(testAdjList.contains(board.getCell(0, 0)));
		assert(testAdjList.contains(board.getCell(1,  1)));
		assert(testAdjList.contains(board.getCell(2,  0)));
		assertEquals(testAdjList.size(), 3);
		
		// Another along edge by origin
		cell = board.getCell(0, 1);
		testAdjList = cell.getAdjList();
		assert(testAdjList.contains(board.getCell(0, 0)));
		assert(testAdjList.contains(board.getCell(1,  1)));
		assert(testAdjList.contains(board.getCell(0,  2)));
		assertEquals(testAdjList.size(), 3);
		
		// not by edge
		cell = board.getCell(1, 1);
		testAdjList = cell.getAdjList();
		assert(testAdjList.contains(board.getCell(0, 1)));
		assert(testAdjList.contains(board.getCell(1,  0)));
		assert(testAdjList.contains(board.getCell(1,  2)));
		assert(testAdjList.contains(board.getCell(2,  1)));
		assertEquals(testAdjList.size(), 4);
		
		// in opposite corner to origin
		cell = board.getCell(3, 3);
		testAdjList = cell.getAdjList();
		assert(testAdjList.contains(board.getCell(3, 2)));
		assert(testAdjList.contains(board.getCell(2,  3)));
		assertEquals(testAdjList.size(), 2);
		
	}
	
	/*
	 * Test the calcTargets() function with an empty board
	 */
	@Test
	void testTargetsNormal() {
		BoardCell cell = board.getCell(0, 0); // origin
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(2, 0)));
	}
	
	/*
	 * More testing of calcTargets function with not empty board.
	 */
	@Test
	void testTargetsNormal2() {
		BoardCell cell = board.getCell(3, 3); // starting point
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		
	}
	
	/*
	 * more calcTargets() testing
	 */
	@Test
	void testTargetsNormal3() {
		BoardCell cell = board.getCell(1, 3); // starting point
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		
	}
	
	@Test
	void testTargetsNormal4() {
		BoardCell cell = board.getCell(0, 1); // starting point
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		
	}
	
	@Test
	void testTargetsNormal5() {
		BoardCell cell = board.getCell(2, 1); // starting point
		board.calcTargets(cell, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		
	}
	
	@Test
	void testTargetsNormal6() {
		BoardCell cell = board.getCell(3, 1); // starting point
		board.calcTargets(cell, 4);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 3)));
		
	}
	
	@Test
	void testTargetsNormal7() {
		BoardCell cell = board.getCell(3, 0); // starting point
		board.calcTargets(cell, 5);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
		
	}
	
	/*
	 * Tests if a room is a target
	 */
	@Test
	void testTargetsRoom1() {
		board.getCell(0, 3).setRoom(true); // set spots in room
		board.getCell(1, 2).setRoom(true);
		board.getCell(2, 1).setRoom(true);
		board.getCell(3, 0).setRoom(true);
		BoardCell cell = board.getCell(0, 0); //origin
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		int cellsInRooms = 0;
		for (BoardCell currentCell : targets) {
			if (currentCell.isRoom()) {
				cellsInRooms++;
			}
		}
		assertEquals(4, cellsInRooms); // will make testboard with 9 spots that are rooms within a path of 3 of 0,0		
	}
	
	@Test
	void testTargetsRoom2() {
		board.getCell(0, 3).setRoom(true); // set spots in room
		board.getCell(1, 2).setRoom(true);
		board.getCell(2, 1).setRoom(true);
		board.getCell(3, 0).setRoom(true);
		BoardCell cell = board.getCell(3, 3); // now set cell to opposite corner
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets();
		int cellsInRooms = 0;
		for (BoardCell currentCell : targets) {
			if (currentCell.isRoom()) {
				cellsInRooms++;
			}
		}
		assertEquals(0, cellsInRooms);
	}
	
	/*
	 * Special test case for testing to see if it stops finding targets after a room.
	 */
	@Test
	void testTargetsRoom3() {
		board.getCell(0, 3).setRoom(true); // set spots in room
		board.getCell(1, 2).setRoom(true);
		board.getCell(2, 1).setRoom(true);
		board.getCell(3, 0).setRoom(true);
		board.getCell(1, 3).setRoom(true); // it should not hit this one!
		BoardCell cell = board.getCell(0, 0); // now set cell to opposite corner
		board.calcTargets(cell, 4);
		Set<BoardCell> targets = board.getTargets();
		int cellsInRooms = 0;
		
		// special test case to test that finding targets stops once a room is found!
		for (BoardCell currentCell : targets) {
			if (currentCell.isRoom()) {
				cellsInRooms++;
			}
		}
		assertEquals(cellsInRooms, 4);
	}
	
	/*
	 * Test the calcTargets() function with occupied spaces
	 */
	@Test
	void testTargetsOccupied() {
		board.getCell(1,1).setOccupied(true);
		BoardCell cell = board.getCell(0,0); // origin
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
			
	}
	
	@Test
	void testTargetsOccupied2() {
		board.getCell(2,1).setOccupied(true);
		board.getCell(2,3).setOccupied(true);
		BoardCell cell = board.getCell(2,2); //starting point
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(3, 0)));
			
	}
	
	/*
	 * Test the calcTargets() function with both occupied and room spaces
	 */
	@Test
	void testTargetsMixed() {
		board.getCell(1, 3).setOccupied(true);
		board.getCell(1, 1).setRoom(true);
		BoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(0,0)));
		assertTrue(targets.contains(board.getCell(1,1)));
		assertTrue(targets.contains(board.getCell(2,2)));
		
	}
	
	/*
	 * Tests a mix of different kinds of cells
	 */
	@Test
	void testTargetsMixed2() {
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setRoom(true);
		BoardCell cell = board.getCell(0, 3);
		board.calcTargets(cell, 3);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(1,2)));
		assertTrue(targets.contains(board.getCell(2,2)));
		assertTrue(targets.contains(board.getCell(3,3)));
		
	}
	
}
