/*
 * Authors: Michael Pineiro and Calvin Ko
 * Date: March 1
 * This class contains the information that is specific to groups of cells that are
 * considered 'rooms.' It stores the location of a vent cell (if any), the center cell,
 * the label cell, a list of the doorways which lead to the room, and the name of the
 * room. These are stored in a list in the Board class to be referenced for general
 * room specific information.
 */

package clueGame;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private BoardCell ventCell;
	private ArrayList<BoardCell> doorWays;
	
	/*
	 * Constructor
	 */
	public Room(String name) {
		this.name = name;
		doorWays = new ArrayList<BoardCell>();
	}
	
	/*
	 * Adds a cell to the list of doorways associated to the room
	 */
	public void addDoorway(BoardCell doorway) {
		doorWays.add(doorway);
	}
	
	/*
	 * Getter for name
	 */
	public String getName() {
		return name;
	}
	
	/*
	 * Getter for the label cell
	 */
	public BoardCell getLabelCell() {
		return labelCell;
	}

	/*
	 * Getter for the center cell
	 */
	public BoardCell getCenterCell() {
		return centerCell;
	}
	
	/*
	 * Getter for the vent cell with the room
	 */
	public BoardCell getVentCell() {
		return ventCell;
	}
	
	/*
	 * Getter for the list of doorway cells
	 */
	public ArrayList<BoardCell> getDoorways() {
		return doorWays;
	}
	
	/*
	 * Setter for center cell
	 */
	public void setCenterCell(BoardCell cell) { 
		centerCell = cell;
	}
	
	/*
	 * Setter for label cell
	 */
	public void setLabelCell(BoardCell cell) {
		labelCell = cell;
	}

	/*
	 * Setter for the vent cell
	 */
	public void setVentCell(BoardCell ventCell) {
		this.ventCell = ventCell;
	}
	
	/*
	 * Draw labels for the rooms
	 */
	public void drawLabel(Graphics g, int cellWidth, int cellHeight) {
		g.setColor(Color.black);
		g.drawString(name, labelCell.getCol()*cellWidth, labelCell.getRow()*cellHeight);
	}
}

