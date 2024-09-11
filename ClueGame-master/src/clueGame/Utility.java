/* Authors: Michael Pineiro and Calvin Ko
 * 
 * 
 * This class contains etc. methods that are used as helpers in multiple parts of the
 * codebase!
 */

package clueGame;

import java.awt.Color;

public class Utility {
	
	/*
	 * Setter for the color of a spot (mainly used for players when occupying spots)
	 */
	public static Color colorStringToObject(String color) {
		switch(color) {
		case("Red"):
			return Color.red;
		case("Pink"):
			return Color.pink;
		case ("Cyan"):
			return Color.cyan;
		case ("Blue"):
			return Color.blue;
		case ("Yellow"):
			return Color.yellow;
		case ("Orange"):
			return Color.orange;
		default:
			return Color.white;
		}
	}
}
