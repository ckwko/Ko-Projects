/*
 * Authors: Michael Pineiro, Calvin Ko
 * A class that contains all required methods ONLY for initialization of the class
 */

package clueGame;

import java.awt.Color;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import java.util.Random;

public class BoardInitializer {
	Board board;
	ArrayList<Card> players = new ArrayList<Card>();
	ArrayList<Card> weapons = new ArrayList<Card>();
	ArrayList<Card> rooms = new ArrayList<Card>();
	ArrayList<Card> remainingCards = new ArrayList<Card>();
	
	public BoardInitializer(Board board) {
		super();
		this.board = board;
		}
	
	/*
	 * Calculate adjacency matrix
	 */
	protected void calcAdjacencyMatrix() {
		board.adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		for (int row = 0; row < board.grid.length; row++) {
			for (int col = 0; col < board.grid[0].length; col++) {
				BoardCell cell = board.grid[row][col];
				if (cell.isRoom()) { // add the vent if there is one
					addVentsToAdjList(cell);
					addDoorsToAdjList(cell);
					continue;
				}
				
				if (cell.isDoorway()) {
					addDoorwayToRoomAdjacency(cell, row, col);
				}
				
				checkBounds(cell, row, col);
				
				board.adjMatrix.put(cell, cell.getAdjList()); // add to matrix
			}
		}
	}

	private void checkBounds(BoardCell cell, int row, int col) {
		if (col >= 1) { // if not on left side
			addAdjToCell(cell, row, col - 1);
		}
		if (col < board.grid[0].length - 1) { // if not on right side
			addAdjToCell(cell, row, col + 1);
		}
		if (row >= 1) { // if not on the top
			addAdjToCell(cell, row - 1, col);
		}
		if (row < board.grid.length - 1) { // if not bottom
			addAdjToCell(cell, row + 1, col);
		}
	}
	
	/*
	 * If the current cell is a door, then add the center cell to the room that
	 * the door is facing to the doors adjacency matrix.
	 */
	private void addDoorwayToRoomAdjacency(BoardCell cell, int row, int col) {
		DoorDirection direction = cell.getDoorDirection();
		BoardCell roomCell;
		Room room;
		switch (direction) {
		case UP: 
			roomCell = board.grid[row - 1][col];
			room = board.roomMap.get(roomCell.getInitial());
			roomCell = room.getCenterCell();
			cell.addAdjacency(roomCell);
			break;
		case DOWN: 
			roomCell = board.grid[row + 1][col];
			room = board.roomMap.get(roomCell.getInitial());
			roomCell = room.getCenterCell();
			cell.addAdjacency(roomCell);
			break;
		case LEFT: 
			roomCell = board.grid[row][col - 1];
			room = board.roomMap.get(roomCell.getInitial());
			roomCell = room.getCenterCell();
			cell.addAdjacency(roomCell);
			break;
		case RIGHT: 
			roomCell = board.grid[row][col+1];
			room = board.roomMap.get(roomCell.getInitial());
			roomCell = room.getCenterCell();
			cell.addAdjacency(roomCell);
			break;
		}
		
	}
	
	/*
	 * Helper for adjacency matrix to add to a certain cell depending on the new location
	 */
	private void addAdjToCell(BoardCell cell, int row, int col) {
		if (!board.grid[row][col].isRoom() && board.grid[row][col].getInitial() != 'X') { // if the new spot is not a room
			cell.addAdjacency(board.grid[row][col]);
		} 
	}
	
	/*
	 * If a vent cell exists in the room of the current cell, add it to the adjList
	 * of the current cell
	 */
	private void addVentsToAdjList(BoardCell cell) {
		char initial = cell.getInitial();
		Room room = board.roomMap.get(initial);
		BoardCell ventCell = room.getVentCell();
		if (ventCell != null) {
			Room newRoom = board.roomMap.get(ventCell.getSecretPassage());
			ventCell = newRoom.getCenterCell();
			cell.addAdjacency(ventCell);
		}
	}
	
	/*
	 * If the current cell is in a room and this is called, add all doors associated
	 * with the room to the adjacency list.
	 */
	private void addDoorsToAdjList(BoardCell cell) {
		char initial = cell.getInitial();
		Room room = board.roomMap.get(initial);
		ArrayList<BoardCell> doorways = room.getDoorways();
		for (BoardCell door : doorways) {
			cell.addAdjacency(door);
		}
	}
	
	/*
	 * loads the setup files and creates cards accordingly
	 */
	public void loadSetupConfig() {
		board.roomMap = new HashMap<Character, Room>();
		try {
			FileReader fileReader = new FileReader("data/" + board.setupConfigFile);
			Scanner in = new Scanner(fileReader);
			String contents = "";
			String line = "";
			while (in.hasNextLine()) {
				contents = in.nextLine();
				String split[] = contents.split(", ");
				String type = split[0];
				Card card = null;
				
				if (type.charAt(0) == '/') {
					continue;
					
				} else if (type.equals("Space")) {
					board.roomMap.put(split[2].charAt(0), new Room(split[1]));
				} else if (type.equals("Room")) {
					board.roomMap.put(split[2].charAt(0), new Room(split[1]));
					card = new Card(split[1], CardType.valueOf(type.toUpperCase()));
					rooms.add(card);
				} else if (type.equals("Human")) { // is a player
					HumanPlayer player = new HumanPlayer(split[1], split[1], split[2], split[3]);
					board.addPlayer(player);
					card = new Card(split[1], CardType.valueOf("PERSON"));
					players.add(card);
				} else if (type.equals("Computer")) {
					ComputerPlayer player = new ComputerPlayer(split[1], split[1], split[2], split[3]);
					board.addPlayer(player);
					card = new Card(split[1], CardType.valueOf("PERSON"));
					players.add(card);
				} else if (type.equals("Weapon")) {
					card = new Card(split[1], CardType.valueOf(type.toUpperCase()));
					weapons.add(card);
				}
				if (card != null) {
					board.addCardToDeck(card);
					remainingCards.add(card);
				}
				
			}
		} catch(FileNotFoundException e) {
			System.out.println(e.toString());
			System.out.println("Setup Config file not found.");
		}
	}
	
	
	/*
	 * load layout files
	 */
	public void loadLayoutConfig() throws BadConfigFormatException{
		try {
			// Get the number of rows and columns and initialize the grid
			FileReader fileReader = new FileReader("data/" + board.layoutConfigFile); // read in the file
			Scanner in = new Scanner(fileReader);
			int rowAndColumn[] = getNumRowsAndColsFromInputFile(in);
			board.numRows = rowAndColumn[0];
			board.numColumns = rowAndColumn[1];
			
			generateGrid(board.numRows, board.numColumns);
			String stringData[][] = new String[board.numRows][board.numColumns];
			
			// Actually load the data from the config file
			fileReader = new FileReader("data/" + board.layoutConfigFile); // read in the file
			in = new Scanner(fileReader);
			int currentRow = 0; // keep track of location within the grid
			while (in.hasNext()) { // iterate through each line
				String rowContent[] = in.next().split(","); // split each line into elements
				// check for incorrectly formatted files
				if (rowContent.length != board.numColumns) { 
					throw new BadConfigFormatException("File has null spaces"); 
				}
				for (int i = 0; i < rowContent.length; i++) { // each element
					char initial = rowContent[i].charAt(0);
					if(!board.roomMap.containsKey(initial)) { // check that the symbol exists
						throw new BadConfigFormatException("Symbol not found");
					}
					
				board.grid[currentRow][i].setInitial(initial); // add to grid
				stringData[currentRow][i] = rowContent[i]; // save string data
				}
				currentRow++;
			}
			
			// finish loading information
			for (int row = 0; row < board.numRows; row++) {
				for (int col = 0; col < board.numColumns; col++) {
					board.grid[row][col] = loadCellInformation(row, col, stringData[row][col]);
				}
			}
			
		} catch(FileNotFoundException e) {
			System.out.println(e.toString());
			System.out.println("Layout Config file not found.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
			System.out.println("The config file is not formatted correctly.");
			throw new BadConfigFormatException();
		}
	}
	
	/*
	 * Instantiate the 2D array of cells that make up the board.
	 */
	private void generateGrid(int rows, int columns) {
		board.grid = new BoardCell[rows][columns]; // set the grid up
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				BoardCell cell = new BoardCell(row, col);
				board.grid[row][col] = cell;
			}
		}
	}
	
	/*
	 * Helper function for loadLayoutConfig to get the number of rows and columns in config file
	 */
	private int[] getNumRowsAndColsFromInputFile(Scanner in) {
		int rowAndColumn[] = new int[2];
		rowAndColumn[0] = 0;
		String line = "";
		while (in.hasNextLine()) { // iterate through to get number of rows
			rowAndColumn[0]++;
			line = in.nextLine();
		}
		rowAndColumn[1] = line.split(",").length; // record number of columns
		return rowAndColumn;
	}
	
	/*
	 * Helper function to loadLayoutConfig(). Creates the cell and gets the special
	 * characters
	 */
	private BoardCell loadCellInformation(int row, int col, String cellElement) {
		DoorDirection direction = DoorDirection.NONE;
		
		BoardCell cell = board.grid[row][col];
		if (cellElement.length() > 1) {
			char special = cellElement.charAt(1);
			checkCellInfo(special, cell, row, col);
			
		} else if (board.roomMap.get(cell.getInitial()) != null && cell.getInitial() != 'W' && cell.getInitial() != 'X') { // if the cell is a room
			cell.setIsRoom(true);
		}
		setRoomInformation(cell);
		return cell;
	}
	
	/*
	 * Helper function for loadCellInformation
	 * Checks characters in cell for what info it has
	 */
	private void checkCellInfo(char special, BoardCell cell, int row, int col) {
		switch (special) {
		case '>':
			cell.setDoorDirection(DoorDirection.RIGHT);
			addDoorwaysToRoom(cell, row, col + 1);
			break;
			
		case '<':
			cell.setDoorDirection(DoorDirection.LEFT);
			addDoorwaysToRoom(cell, row, col - 1);
			break;
			
		case '^':
			cell.setDoorDirection(DoorDirection.UP);
			addDoorwaysToRoom(cell, row - 1, col);
			break;
			
		case 'v':
			cell.setDoorDirection(DoorDirection.DOWN);
			addDoorwaysToRoom(cell, row + 1, col);
			break;
			
		case '*':
			cell.setRoomCenter(true);
			cell.setIsRoom(true);
			break;
			
		case '#':
			cell.setIsLabel(true);
			cell.setIsRoom(true);
			break;
			
		default:
			cell.setPassageChar(special); // the cell must be a vent room!
			cell.setPassage(true);
			cell.setIsRoom(true);
			break;
			
		}
	}
	
	/*
	 * A helper function to loadCellInformation. Used to keep track of which
	 * doors are associated with which rooms.
	 */
	private void addDoorwaysToRoom(BoardCell currentCell, int row, int col) {
		BoardCell cell = board.grid[row][col];
		if (cell != null) {
			Room room = board.roomMap.get(cell.getInitial());
			room.addDoorway(currentCell);
		}
	}
	
	/*
	 * Helper function to loadLayoutConfig for setting individual cell info for rooms 
	 */
	private void setRoomInformation(BoardCell cell) {
		char initial = cell.getInitial();
		if (cell.isRoomCenter()) { // check if cell is a center
			board.roomMap.get(initial).setCenterCell(cell);
		}
		
		if (cell.isLabel()) {
			board.roomMap.get(initial).setLabelCell(cell);
		}
		
		if (cell.isPassage()) {
			board.roomMap.get(initial).setVentCell(cell);
			
		}
		
	}
	
	/*
	 * Sets cells on the board where players exist isOccupied = true
	 */
	public void addOccupiedSpaces() {
		ArrayList<Player> players = board.getPlayerList();
		for (Player p: players) {
			int row = p.getRow();
			int col = p.getCol();
			board.grid[row][col].setOccupied(true, p.getColor());
		}
	}
	
	/*
	 * Generates a solution to the game!
	 */
	public void createSolution() {
		if (players.size() == 0) {
			return;
		}
		Random rand = new Random();
		int randomIndex =  rand.nextInt(players.size());
		Card player = players.get(randomIndex);
		randomIndex = rand.nextInt(weapons.size());
		Card weapon = weapons.get(randomIndex);
		randomIndex = rand.nextInt(rooms.size());
		Card room = rooms.get(randomIndex);
		Solution sol = new Solution(player, weapon, room);
		board.solution = sol;
		remainingCards.remove(player);
		remainingCards.remove(weapon);
		remainingCards.remove(room);
	}
	
	public void dealHandsToPlayers() {
		for (Player p : board.getPlayerList()) {
			Random rand = new Random();
			String color = p.getColor();
			int randomIndex = 0;
			randomIndex =  rand.nextInt(remainingCards.size());
			Card cardOne = remainingCards.get(randomIndex);
			cardOne.setColor(color);
			remainingCards.remove(cardOne);
			randomIndex = rand.nextInt(remainingCards.size());
			Card cardTwo = remainingCards.get(randomIndex);
			cardTwo.setColor(color);
			remainingCards.remove(cardTwo);
			randomIndex = rand.nextInt(remainingCards.size());
			Card cardThree = remainingCards.get(randomIndex);
			cardThree.setColor(color);
			remainingCards.remove(cardThree);
			p.updateHand(cardOne);
			p.updateHand(cardTwo);
			p.updateHand(cardThree);
		}
	}
	
}
