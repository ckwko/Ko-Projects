/* Authors: Calvin Ko and Michael Pineiro
 * Date: March 1
 * This class is for an individual cell, which stores the individual information
 * for each. The cells are stored within the grid in the board. Each cell contains
 * basic information (location, label, etc), an adjacency matrix, and identifiers (
 * isRoom, isOccupied).
 */

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

/*
 * Test class for an individual cell
 */
public class BoardCell {
	private final int row, col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel, roomCenter, isRoom, isOccupied, isPassage, isDoorway = false;
	private char passageChar; 	
	private Set<BoardCell> adjList;
	Color color;
	
	/*
	 * Basic constructor
	 */
	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.doorDirection = DoorDirection.NONE;
		
		adjList = new HashSet<BoardCell>();
	}
	
	/*
	 * Params width and height for overall panel size
	 */
	public void draw(int width, int height, int colSize, Graphics g) {
		int recSize = width / colSize;
		g.drawRect(row * recSize, col * recSize, recSize, recSize);
	}
	
	/*
	 * method for cell to draw itself
	 */
	public void drawCell(Graphics g, int cellWidth, int cellHeight, Color color) {
		// calculate the position of the cells
		int rowPos = row * cellHeight;
		int colPos = col * cellWidth;
		
		// if cell is a room, don't draw borders, if cell is occupied draw player that is occupying cell
		
		if(isRoom) {
			g.setColor(color);
			g.fillRect(colPos, rowPos, cellWidth, cellHeight);
			
		} else {
			g.setColor(color);
			g.fillRect(colPos, rowPos, cellWidth, cellHeight);
			g.setColor(Color.black);
			g.drawRect(colPos, rowPos, cellWidth, cellHeight);
			
		}
	}
	
	/*
	 * Draws only the door objects
	 */
	public void drawDoor(Graphics g, int cellWidth, int cellHeight) {
		// calculate the position of the cells
		int rowPos = row * cellHeight;
		int colPos = col * cellWidth;
		
		g.setColor(Color.blue);
		// draw door based on direction
		switch (doorDirection) {
			case LEFT:
				g.fillRect(colPos - (cellWidth / 5), rowPos, (cellWidth / 5), cellHeight);
				break;
				
			case RIGHT:
				g.fillRect(colPos + cellWidth, rowPos, (cellWidth / 5), cellHeight);
				break;
				
			case DOWN:
				g.fillRect(colPos, rowPos + cellHeight, cellWidth, (cellHeight / 5));
				break;
				
			case UP:
				g.fillRect(colPos, rowPos - (cellHeight / 5), cellWidth, (cellHeight / 5));
				break;
		}
	}
	
	/*
	 * Getter for isRoom
	 */
	public boolean isRoom() {
		return isRoom;
	}

	/*
	 * Setter for isRoom
	 */
	public void setIsRoom(boolean isRoomPart) {
		this.isRoom = isRoomPart;
	}

	/*
	 * Getter for isOccupied
	 */
	public boolean isOccupied() {
		return isOccupied;
	}

	/*
	 * Setter for isOccupied with color
	 */
	public void setOccupied(boolean isOccupied, String color) {
		this.isOccupied = isOccupied;
		setColor(color);
	}

	/*
	 * Setter for isOccupied without color
	 */
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	/*
	 * Setter for the color of a spot (mainly used for players when occupying spots)
	 */
	public void setColor(String color) {
		switch(color) {
		case("Red"):
			this.color = Color.red;
			break;
		case("Pink"):
			this.color = Color.pink;
		break;
		case ("Cyan"):
			this.color = Color.cyan;
		break;
		case ("Blue"):
			this.color = Color.blue;
		break;
		case ("Yellow"):
			this.color = Color.yellow;
		break;
		case ("Orange"):
			this.color = Color.orange;
		break;
		default:
			this.color = Color.white;
			break;
			
		}
	}
	
	/*
	 * Getter for adjList
	 */
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	
	/*
	 * Setter for adding element to adjList
	 */
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	
	/*
	 * Getter for isDoorway
	 */
	public boolean isDoorway() {
		return doorDirection != DoorDirection.NONE;
	}
	
	/*
	 * get door direction
	 */
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	/*
	 * getter for isLabel
	 */
	public boolean isLabel() {
		return roomLabel;
	}
	
	/*
	 * getter for isPassage
	 */
	public boolean isPassage() {
		return isPassage;
	}
	
	/*
	 * getter for isRoomCenter
	 */
	public boolean isRoomCenter() {
		return roomCenter;
	}
	
	/*
	 * getter for passageChar
	 */
	public char getSecretPassage() {
		return passageChar;
	}

	/*
	 * getter for initial
	 */
	public char getInitial() {
		return initial;
	}

	/*
	 * setter for initial
	 */
	public void setInitial(char initial) {
		this.initial = initial;
	}

	/*
	 * setter for the door direction
	 */
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
		if (this.doorDirection != DoorDirection.NONE) {
			isDoorway = true;
		} else {
			isDoorway = false;
		}
	}

	/*
	 * getter for setPassage
	 */
	public void setPassage(boolean isPassage) {
		this.isPassage = isPassage;
	}
	
	/*
	 * setter for secretePassage
	 */
	public void setPassageChar(char secretPassage) {
		this.passageChar = secretPassage;
		if (this.passageChar != ' ') {
			isPassage = true;
		} else {
			isPassage = false;
		}
		
	}
	
	/*
	 * Setter for room center
	 */
	public void setRoomCenter(boolean roomCenter) {
		this.roomCenter = roomCenter;
	}
	
	
	/*
	 * Setter for isLabel
	 */
	public void setIsLabel(boolean isLabel) {
		this.roomLabel = isLabel;
	}

	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
}
