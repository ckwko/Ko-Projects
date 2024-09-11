package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

public class FileInitTest {
	// Constants that I will use to test whether the file was loaded correctly
	// public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 20;
	public static final int NUM_COLUMNS = 25;

	// NOTE: I made Board static because I only want to set it up one
	// time (using @BeforeAll), no need to do setup before each test.
	private static Board board;

	@BeforeAll
	public static void setUp() throws BadConfigFormatException {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}
	
	@Test
	public void testDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	
	@Test
	public void testRoomLabels() {
		// To ensure data is correctly loaded, test retrieving a few rooms
		// from the hash, including the first and last in the file and a few others
		assertEquals("Medbay", board.getRoom('M').getName() );
		assertEquals("Cafeteria", board.getRoom('C').getName() );
		assertEquals("Storage", board.getRoom('T').getName() );
		assertEquals("Reactor", board.getRoom('R').getName() );
		assertEquals("Navigation", board.getRoom('N').getName() );
		assertEquals("Security", board.getRoom('S').getName() );
		assertEquals("Electrical", board.getRoom('E').getName() );
		assertEquals("Communications", board.getRoom('O').getName() );
		assertEquals("Admin", board.getRoom('A').getName() );
	}
	
	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus
		// two cells that are not a doorway.
		// These cells are white on the planning spreadsheet
		@Test
		public void FourDoorDirections() {
			BoardCell cell = board.getCell(3, 6);
			assertTrue(cell.isDoorway());
			assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
			cell = board.getCell(4, 11);
			assertTrue(cell.isDoorway());
			assertEquals(DoorDirection.UP, cell.getDoorDirection());
			cell = board.getCell(15, 7);
			assertTrue(cell.isDoorway());
			assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
			cell = board.getCell(15, 3);
			assertTrue(cell.isDoorway());
			assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
			// Test that walkways are not doors
			cell = board.getCell(0, 7);
			assertFalse(cell.isDoorway());
		}
	
		// Test that we have the correct number of doors
		@Test
		public void testNumberOfDoorways() {
			int numDoors = 0;
			for (int row = 0; row < board.getNumRows(); row++)
				for (int col = 0; col < board.getNumColumns(); col++) {
					BoardCell cell = board.getCell(row, col);
					if (cell.isDoorway())
						numDoors++;
				}
			Assert.assertEquals(15, numDoors);
		}
		
		// Test a few room cells to ensure the room initial is correct.
		@Test
		public void testRooms() {
			// just test a standard room location
			BoardCell cell = board.getCell(0, 1);
			Room room = board.getRoom( cell ) ;
			assertTrue( room != null );
			assertEquals( room.getName(), "Medbay" ) ;
			assertFalse( cell.isLabel() );
			assertFalse( cell.isRoomCenter() ) ;
			assertFalse( cell.isDoorway()) ;

			// this is a label cell to test
			cell = board.getCell(3, 2);
			room = board.getRoom( cell ) ;
			assertTrue( room != null );
			assertEquals( room.getName(), "Medbay" ) ;
			assertTrue( cell.isLabel() );
			assertTrue( room.getLabelCell() == cell );
			
			// this is a room center cell to test
			cell = board.getCell(2, 11);
			room = board.getRoom( cell ) ;
			assertTrue( room != null );
			assertEquals( room.getName(), "Navigation" ) ;
			assertTrue( cell.isRoomCenter() );
			assertTrue( room.getCenterCell() == cell );
			
			// this is a secret passage test
			cell = board.getCell(0, 0);
			room = board.getRoom( cell ) ;
			assertTrue( room != null );
			assertEquals( room.getName(), "Medbay" ) ;
			assertTrue( cell.getSecretPassage() == 'A' );
			
			// test a walkway
			cell = board.getCell(5, 7);
			room = board.getRoom( cell ) ;
			// Note for our purposes, walkways and closets are rooms
			assertTrue( room != null );
			assertEquals( room.getName(), "Walkway" ) ;
			assertFalse( cell.isRoomCenter() );
			assertFalse( cell.isLabel() );
			
			// test a closet
			cell = board.getCell(5, 0);
			room = board.getRoom( cell ) ;
			assertTrue( room != null );
			assertEquals( room.getName(), "Unused" ) ;
			assertFalse( cell.isRoomCenter() );
			assertFalse( cell.isLabel() );
			
		}
		
}
