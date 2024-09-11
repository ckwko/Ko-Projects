// Michael Pineiro, Calvin Ko

package experiment;

import java.util.*;

/*
 * A basic board class for testing
 */
public class TestBoard {
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	final static int COLS = 4;
	final static int ROWS = 4;
	private BoardCell[][] grid;
	
	
	/*
	 * empty constructor
	 */
	public TestBoard() {
		super();
		grid = new BoardCell[ROWS][COLS];
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		generateGrid();
		calcAdjacencyMatrix();
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
	 * Create grid
	 */
	private void generateGrid() {
		for (int i = 0; i < COLS; i++) {
			for (int j = 0; j < ROWS; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
	}
	
	/*
	 * Calculate adjacency matrix
	 */
	private void calcAdjacencyMatrix() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				BoardCell cell = grid[i][j];
				if (i >= 1) { // if not on left side
					cell.addAdjacency(grid[i-1][j]);
				}
				if (i < COLS - 1) { // if not on right side
					cell.addAdjacency(grid[i+1][j]);
				}
				if (j >= 1) { // if not on the top
					cell.addAdjacency(grid[i][j-1]);
				}
				if (j < ROWS - 1) { // if not bottom
					cell.addAdjacency(grid[i][j+1]);
				}
				adjMatrix.put(cell, cell.getAdjList()); // add to matrix
			}
		}
	}
	
}
