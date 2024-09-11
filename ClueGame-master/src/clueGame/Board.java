/* Authors: Calvin Ko and Michael Pineiro
 * Date: March 1
 * This class is the Board, which contains the number of rows, an adjacency
 * matrix, a grid of the board cell objects, and loads in the config files.
 * Follows a singleton design pattern
 */
package clueGame;

import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.CardPanel;
import gui.MakeSuggestionBox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener{
	static int numColumns, numRows; 

	// adjMatrix and Grid Setup
	private Set<BoardCell> targets, visited;
	protected Map<BoardCell, Set<BoardCell>> adjMatrix;
	protected BoardCell[][] grid;

	// configuration setup
	protected String layoutConfigFile, setupConfigFile;
	protected Map<Character, Room> roomMap;

	private static Board theInstance = new Board();
	BoardInitializer initial;

	// Player stuff
	private ArrayList<Player> players;
	private int currentPlayer;
	private boolean playerIsFinished, playerSuggestionFinished;
	private Solution currentSuggestion;
	private String resultMessage;

	// Card stuff
	private ArrayList<Card> deck;
	protected Solution solution;

	// GUI stuff
	public JPanel boardPanel;
	int cellWidth, cellHeight;
	private boolean hasMoved;
	private CardPanel cardPanel;
	private Card resultCard;
	private String guessString;

	/*
	 * Private Constructor
	 */
	private Board() {
		super();
	}

	// this method returns only the board
	public static Board getInstance() {
		return theInstance;
	}

	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize() {
		initial = new BoardInitializer(theInstance);
		PrintWriter out = null;
		players =  new ArrayList<Player>();
		playerIsFinished = true;
		deck = new ArrayList<Card>();
		solution = new Solution(null, null, null);
		this.boardPanel = new JPanel();
		addMouseListener(this);
		try {
			out = new PrintWriter("logfile.txt");
			loadSetupConfig();
			initial.loadLayoutConfig();
			initial.calcAdjacencyMatrix();
			initial.addOccupiedSpaces();
			initial.createSolution();
			initial.dealHandsToPlayers();
			currentPlayer = players.size()-1;
			nextTurn();
		} catch (FileNotFoundException e) {
			out.println("File not found");
			System.out.println("File not found");
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Public accessor to the BoardInitializer class
	 */
	public void loadSetupConfig() {
		initial.loadSetupConfig();
	}

	/*
	 * Public accessor to the BoardInitializer class
	 */
	public void loadLayoutConfig() throws BadConfigFormatException {
		initial.loadLayoutConfig();
	}

	/*
	 * Will calculate the target cells based on pathLength and origin
	 */
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		// add start location to visited list
		visited.add(startCell);
		findAllTargets(startCell, pathLength); // recursive function that does BFS
	}

	/*
	 * Recursive function doing BFS for target cells.
	 */
	private void findAllTargets(BoardCell startCell, int pathLength) {
		for (BoardCell cell : startCell.getAdjList()) {
			if (visited.contains(cell) || cell.isOccupied()) { // skip this cell
				if(cell.isRoom() && cell.isOccupied()) {
					targets.add(cell);
				}
				continue;
			}
			visited.add(cell); // add to visited
			if (pathLength == 1) {
				targets.add(cell);
			} else if (cell.isRoom()) {
				targets.add(cell);
			}
			else {
				findAllTargets(cell, pathLength - 1);
			}
			visited.remove(cell); // remove from visited if done with recursive call
		}
	}

	/*
	 * Checks if accusation matches solution
	 */
	public boolean checkAccusation(Solution sol) {
		if(sol.getPlayer().getCardName() == solution.getPlayer().getCardName()) { // put all in one if?
			if(sol.getRoom().getCardName() == solution.getRoom().getCardName()) {
				if(sol.getWeapon().getCardName() == solution.getWeapon().getCardName()) {
					return true;
				}
			}
		}

		return false;
	}

	/*
	 * Go through player list and ask each for disproval
	 */
	public Card handleSuggestion(Player player, Solution suggestion) {
		Card disproval = null;
		int playerIndex = players.indexOf(player);
		int viewedPlayer = playerIndex;

		for (int i = 0; i < players.size(); i++) {
			viewedPlayer = (viewedPlayer + 1) % players.size();
			disproval = players.get(viewedPlayer).disproveSuggestion(suggestion);
			if (disproval != null) {
				break;
			} else {
				player.setDisproved(false);
			}
		}

		return disproval;
	}

	public void moveSuggestedPlayer(Solution suggestion) {
		Card playerCard = suggestion.getPlayer();
		Card roomCard = suggestion.getRoom();
		Player suggestedPlayer = playerCard.getPlayerObject(players);
		Room suggestedRoom = roomCard.getRoomObject(roomMap);

	}

	/*
	 * Method to display board in the gui
	 */
	public void paintComponent(Graphics g) {
		cellWidth = getWidth()/numColumns;
		cellHeight = getHeight()/numRows;
		super.paintComponent(g);

		// draw and color cells
		for(int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				BoardCell currentCell = getCell(row,col);
				String curRoomName = getRoom(currentCell.getInitial()).getName();
				Color cellColor = Color.LIGHT_GRAY;

				// color walkways dark grey, unused cells as black, rooms as light grey
				switch(curRoomName) {
				case "Walkway":
					cellColor = Color.DARK_GRAY;
					break;

				case "Unused":
					cellColor = Color.black;
					break;

				default:
					cellColor = Color.LIGHT_GRAY;
					break;
				}

				getCell(row, col).drawCell(g, cellWidth, cellHeight, cellColor);
			}

		}

		// draw targets if turn is on human turn
		if(currentPlayerIsHuman()) {
			if(!hasMoved) {
				displayTargets(g);
			}
		}

		// draws the players
		drawPlayers(g);

		// draw and color doorways
		drawDoor(g);

		// label rooms
		for(Map.Entry<Character, Room> mapElement : roomMap.entrySet()) {
			Room room = mapElement.getValue();

			if(!(room.getName().equals("Unused")) && !(room.getName().equals("Walkway"))) {
				room.drawLabel(g, cellWidth, cellHeight);
			}
		}
	}

	/*
	 * Logic for drawing players. Draws players normally but also ensures to offset players
	 * that occupy rooms
	 */
	private void drawPlayers(Graphics g) {
		Map<String, Integer> roomOccupancy = new HashMap<String, Integer>();
		ArrayList<String> rooms = getRoomNames();
		for (String r : rooms) {
			roomOccupancy.put(r, 0);
		}
		int offset = 0;
		for (Player player : players) {
			String playerRoom = player.getRoom();
			if (playerRoom != null) {
				Integer occupants = roomOccupancy.get(playerRoom);
				occupants++;
				offset = 10 * occupants;
			}
			player.drawPlayer(g, cellWidth, cellHeight, offset);
			offset = 0;
		}
	}

	/*
	 * Draw doorways for each room
	 * signified by small rectangles
	 */
	private void drawDoor(Graphics g) {
		// draw and color doorways
		for(int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				BoardCell cell = getCell(row, col);
				if (cell.isDoorway()) {
					getCell(row, col).drawDoor(g, cellWidth, cellHeight);
				}
			}
		}
	}

	/*
	 * Contains the logic of what to do when a player presses the next button
	 */
	public int nextTurn() {
		if (!playerIsFinished) {
			// create dialog box notifying that the player must first finish their turn
			JOptionPane.showMessageDialog(boardPanel, "You must finish your turn first!");
			return -1;
		}

		currentPlayer = (currentPlayer + 1) % players.size();

		Player player = players.get(currentPlayer);
		int roll = player.rollDice();
		int row = player.getRow();
		int col = player.getCol();
		BoardCell playerCell = getCell(row, col);

		calcTargets(playerCell, roll);
		if (player.isHuman()) {
			playerIsFinished = false;	
			hasMoved = false;
		}
		return roll;
	}

	/*
	 * display targets on the gui as cyan colored
	 * rooms should be fully filled by color
	 */
	public void displayTargets(Graphics g) {
		for(BoardCell cell : targets) {
			if(cell.isRoom()) {
				Room targetRoom = getRoom(cell.getInitial());

				for(int row = 0; row < numRows; row++) {
					for (int col = 0; col < numColumns; col++) {
						BoardCell currentCell = getCell(row,col);
						Room currentRoom = getRoom(currentCell.getInitial());

						if(currentRoom.getName().equals(targetRoom.getName())) {
							currentCell.drawCell(g, cellWidth, cellHeight, Color.CYAN);
						}
					}
				}
			}

			cell.drawCell(g, cellWidth, cellHeight, Color.CYAN);
		}
	}


	/*
	 * If a player clicks on a cell, performs the logic on whether they can move there
	 * and displays a splash screen if they cannot.
	 */
	public void validateHumanCanMoveToCell(int mouseX, int mouseY) {
		if (hasMoved) {
			JOptionPane.showMessageDialog(boardPanel, "You have already moved!");
			return;
		}
		Player curPlayer = players.get(currentPlayer);
		int playerRow = curPlayer.getRow();
		int playerCol = curPlayer.getCol();
		int mouseCellX = mouseX/cellWidth;
		int mouseCellY = mouseY/cellHeight;
		BoardCell newCell = getCell(mouseCellY, mouseCellX);
		BoardCell oldCell = getCell(playerRow, playerCol);

		Set<Character> roomsMoveable = new HashSet<Character>();
		for (BoardCell cell : targets) {
			if(cell.isRoom()) {
				roomsMoveable.add(cell.getInitial());
			}
		}

		if(targets.contains(newCell)) { // if the cell is distinctly in the targets list
			changePlayerPos(curPlayer, oldCell, newCell);
			curPlayer.setRoom(null);

		} else if (newCell.isRoom()) { // if a cell is a room in the target list
			char initial = newCell.getInitial();
			if (roomsMoveable.contains(initial)) {
				Room room = roomMap.get(initial);
				BoardCell centerCell = room.getCenterCell();
				curPlayer.setRoom(room.getName());
				changePlayerPos(curPlayer, oldCell, centerCell);
				makeSuggestion(curPlayer, room.getName());
				// nothing under here will be executed - making the suggestion causes
				// weird async issues

			} else {
				showInvalidMoveDialog();
			}
		} else {
			showInvalidMoveDialog();
		}
	}

	/*
	 * Simple method that displays if the player selected an invalid target to move to
	 */
	private void showInvalidMoveDialog() {
		JOptionPane.showMessageDialog(boardPanel, "Please select a valid target");
	}

	/*
	 * Method to change the position of the player and reset board colors
	 */
	public void changePlayerPos(Player player, BoardCell oldCell, BoardCell newCell) {
		oldCell.setOccupied(false);
		newCell.setOccupied(true, player.getColor());
		player.setRow(newCell.getRow());
		player.setCol(newCell.getCol());
		hasMoved = true;
	}


	/*
	 * Logic that validates if a suggestion can be performed
	 */
	public void checkSuggestion() {
		Player curPlayer = players.get(currentPlayer);
		int playerRow = curPlayer.getRow();
		int playerCol = curPlayer.getCol();
		BoardCell playerCell = getCell(playerRow, playerCol);

		playerSuggestionFinished = true;

	}

	/*
	 * Method that contains logic to adjust whether or not to consider a player as finished with their turn
	 */
	public void updateIsPlayerFinished() {
		if(hasMoved == true && playerSuggestionFinished == true) {
			playerIsFinished = true;

		} else {
			playerIsFinished = false;
		}
	}

	public boolean getPlayerSuggestionFinished() {
		return playerSuggestionFinished;
	}

	/*
	 * Takes the current player, picks a random target, moves to that spot, and updates
	 * cell info 
	 */
	public void moveComputerPlayer() {
		//Set<BoardCell> targets = board.getTargets();
		// pick a random target to select
		ComputerPlayer player = (ComputerPlayer) getPlayer(currentPlayer);

		BoardCell targetCell = player.selectTarget(targets, deck, roomMap);
		int row = player.getRow();
		int col = player.getCol();
		BoardCell oldCell = getCell(row, col);
		targetCell.setOccupied(true, player.getColor());
		oldCell.setOccupied(false);
		player.setRow(targetCell.getRow());
		player.setCol(targetCell.getCol());

		if (targetCell.isRoom()) {
			Character initial = targetCell.getInitial();
			String room = roomMap.get(initial).getName();
			player.setRoom(room);
			makeSuggestion(player, room);

		} else {
			player.setRoom(null);
		}
	}

	/*
	 * check if player is human and display suggestion combo box
	 */
	public void makeSuggestion(Player player, String room) {
		if (!player.isHuman()) {
			// is a computer
			ComputerPlayer compPlayer = (ComputerPlayer) players.get(currentPlayer);
			Solution compSuggestion = compPlayer.createSuggestion(deck, room);

			String roomName = compSuggestion.getRoomName();
			String playerName = compSuggestion.getPlayerName();
			String weaponName = compSuggestion.getWeaponName();
			guessString = playerName + ", " + roomName + ", " + weaponName;
			if(checkCompAccusation(compPlayer, compSuggestion)) {
				JOptionPane.showMessageDialog(boardPanel, "You lost!"
						+ " The computer player guessed first."
						+ "The solution was " + solution.getPlayerName() + " in "
						+ solution.getRoomName() + " with " + solution.getWeaponName());
				System.exit(0);
			}

		} else {
			// is a human
			hasMoved = true; // end the players turn once they end up in the room
			repaint();
			// create dialog
			MakeSuggestionBox suggestionBox = new MakeSuggestionBox(room);
			suggestionBox.setVisible(true);
			// everything under here is handled ASYNCRONOUSLY. That means that this
			// code will be executed before getting any input from the suggestion box.
			// This means that the suggestion is handled via the sugg. box itself.
		}
	}

	/*
	 * check if computer should make accusation
	 * if can then make accusation
	 */
	public boolean checkCompAccusation(Player player, Solution suggestion) {
		Card roomCard = suggestion.getRoom();
		Card playerCard = suggestion.getPlayer();
		Card weaponCard = suggestion.getWeapon();

		if(handleSuggestion(player, suggestion) == null) {
			if(!player.checkIsInHand(roomCard) && !player.checkIsInHand(playerCard) && !player.checkIsInHand(weaponCard)) {
				return true;
			}
		}
		
		return false;
	}

	/*
	 * return values from the suggestion combo box
	 */
	public void setSuggestionValues(String room, String weapon, String playerName) {
		// Convert strings to cards
		Card roomCard = new Card(room, CardType.ROOM);
		Card weaponCard = new Card(weapon, CardType.WEAPON);
		Card playerCard = new Card(playerName, CardType.PERSON);

		currentSuggestion = new Solution(playerCard, weaponCard, roomCard);
		Player player = getPlayerFromName(playerName);
		resultCard = handleSuggestion(player, currentSuggestion);

		// move the other player
		Player humanPlayer = players.get(currentPlayer); // this setComboValues is only called for human player
		movePlayerForSuggestion(player, humanPlayer.getRow(), humanPlayer.getCol());

		// update seen cards for player
		players.get(currentPlayer).updateSeen(resultCard); 
		cardPanel.updatePanels();
		repaint();
		playerIsFinished = true;

	}

	/*
	 * move the player in gui to the room that suggestion was made in
	 */
	private void movePlayerForSuggestion(Player player, int row, int col) {
		int oldRow = player.getRow();
		int oldCol = player.getCol();
		BoardCell oldCell = getCell(oldRow, oldCol);
		oldCell.setOccupied(false);
		// new cell should already be occupied
		player.setRow(row);
		player.setCol(col);
		repaint();

	}

	/*
	 * get player from string value
	 */
	private Player getPlayerFromName(String player) {
		for (Player p : players) {
			if (p.getName() == player) {
				return p;
			}
		}
		return null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();

		validateHumanCanMoveToCell(mouseX, mouseY);
		repaint();
		checkSuggestion();
		updateIsPlayerFinished();
	}

	/*
	 * Cell getter
	 */
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

	/*
	 * Target getter
	 */
	public Set<BoardCell> getTargets() {
		return targets;
	}

	/*
	 * Setter for config files
	 */
	public void setConfigFiles(String layoutFile, String setupFile) {
		layoutConfigFile = layoutFile;
		setupConfigFile = setupFile;
	}

	/*
	 * Room getter on character
	 */
	public Room getRoom(char roomName) {
		Room room = roomMap.get(roomName);
		return room;
	}

	/*
	 * Room getter on cell
	 */
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}

	/*
	 * Number of rows getter
	 */
	public int getNumRows() {
		return numRows;
	}

	/*
	 * Number of columns getter
	 */
	public int getNumColumns() {
		return numColumns;
	}

	/*
	 * Getter for individual cells
	 */
	public Set<BoardCell> getAdjList(int row, int col) {
		BoardCell cell = grid[row][col];
		return cell.getAdjList();
	}

	public ArrayList<Player> getPlayerList() {
		return players;
	}

	public Player getPlayer(int index) {
		return players.get(index);
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public void addCardToDeck(Card card) {
		deck.add(card);
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Card player, Card weapon, Card room) {
		Solution sol = new Solution(player, weapon, room);
		this.solution = sol;
	}

	// made for testing
	public void setPlayers(ArrayList<Player> playerList) {
		this.players = playerList;
	}

	public Map<Character, Room> getRoomMap(){
		return roomMap;
	}

	// get current player
	public int getCurrentPlayer() {
		return currentPlayer;
	}

	// setter for player is finished
	public void setPlayerIsFinished(boolean result) {
		playerIsFinished = result;
	}

	// get playerIsFinished
	public boolean getPlayerIsFinished() {
		return playerIsFinished;
	}

	public Solution getCurrentSuggestion() {
		return currentSuggestion;
	}

	public Card getResultCard() {
		return resultCard;
	}

	public String getGuessString() {
		return guessString;
	}

	/*
	 * Gets if the current player is human
	 */
	public boolean currentPlayerIsHuman() {
		Player player = players.get(currentPlayer);
		return player.isHuman();
	}

	public ArrayList<Card> getPlayerCards() {
		ArrayList<Card> players = new ArrayList<Card>();
		for (Card c : deck) {
			if (c.getCardType() == CardType.PERSON) {
				players.add(c);
			}
		}
		return players;
	}

	public ArrayList<Card> getWeaponCards() {
		ArrayList<Card> weapons = new ArrayList<Card>();
		for (Card c : deck) {
			if (c.getCardType() == CardType.WEAPON) {
				weapons.add(c);
			}
		}
		return weapons;
	}

	public ArrayList<String> getRoomNames() {
		ArrayList<String> roomNames = new ArrayList<String>();
		for (Map.Entry<Character,Room> entry : roomMap.entrySet()) {
			Room room = entry.getValue();
			String roomName = room.getName();
			if (roomName != "Unused" && roomName != "Walkway") {
				roomNames.add(roomName);
			}
		}
		return roomNames;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void setCardPanel(CardPanel cardPanel) {
		this.cardPanel = cardPanel;
	}



}
