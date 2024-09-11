// Michael Pineiro, Calvin Ko

package experiment;

import java.util.*;

/*
 * Test class for an individual cell
 */
public class BoardCell {
	private int row;
	private int col;
	private Set<BoardCell> adjList;
	private boolean isRoom; // if it is a room
	private boolean isOccupied; // if the cell is occupied
	
	/*
	 * Empty constructor
	 */
	public BoardCell(int row, int col) {
		super();
		adjList = new HashSet<BoardCell>();
		this.row = row;
		this.col = col;
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
	public void setRoom(boolean isRoomPart) {
		this.isRoom = isRoomPart;
	}

	/*
	 * Getter for isOccupied
	 */
	public boolean isOccupied() {
		return isOccupied;
	}

	/*
	 * Setter for isOccupied
	 */
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
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
	
}
