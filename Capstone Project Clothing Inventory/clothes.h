#ifndef CLOTHES_H
/*
 * Author: Calvin Ko
 * Title: Inventory Tracker - Capstone Project
 * Description: This file contains the clothes class
 */
#define CLOTHES_H

#include <string>

using namespace std;

class clothes
{
	private:
		string size;
		string color;
		string style;

		//lists of valid descriptors for clothes
		string sizeList[5] = { "XS", "S", "M", "L", "XL" };
		string colorList[9] = { "WHITE", "BLACK", "BROWN", "RED", "ORANGE", "YELLOW", "GREEN", "BLUE", "PURPLE" };
		string styleList[5] = { "SHIRT", "PANTS", "SOCKS", "HEADWEAR", "UNDERGARMENT"};

	public:
		//constructor
		clothes(string size = "M", string color = "WHITE", string style = "SHIRT");

		//check if parameter passed is valid in sizeList then set
		void setSize(string s);

		//return size value of object
		string getSize();

		//check if parameter passed is valid in colorList then set
		void setColor(string c);

		//return color value of object
		string getColor();

		//check if parameter passed is valid in styleList then set
		void setStyle(string s);

		//return style value of object
		string getStyle();

};

#endif // !CLOTHES_H