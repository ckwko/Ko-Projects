/*
 * Authors: Michael Pineiro and Calvin Ko
 * Date: March 4
 * This is a custom exception for when a SetupFile is improperly setup. It is thrown
 * when the board is not square (i.e. there are not the same number of columns for 
 * every row).
 */

package clueGame;

public class BadConfigFormatException extends Exception {

	/*
	 * Default constructor
	 */
	public BadConfigFormatException() {
		super("File isn't formatted correctly");
	}
	
	/*
	 * If we want to pass a message in
	 */
	public BadConfigFormatException(String message) {
		super(message);
	}
}
