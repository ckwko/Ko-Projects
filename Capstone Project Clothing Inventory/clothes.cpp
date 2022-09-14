/*
 * Author: Calvin Ko
 * Title: Inventory Tracker - Capstone Project
 * Description: Implementation file for clothes.h
 */
#include "clothes.h"
#include <string>
#include <iostream>

using namespace std;

//constructor
clothes::clothes(string size, string color, string style) {
	setSize(size);
	setColor(color);
	setStyle(style);
}

//check if parameter passed is valid in sizeList then set
void clothes::setSize(string s) {
	for (int i = 0; i < 5; i++) {
		if (s == sizeList[i]) {
			size = s;
		}
	}
}

string clothes::getSize() {
	return size;
}

//check if parameter passed is valid in colorList then set
void clothes::setColor(string c) {
	for (int i = 0; i < 9; i++) {
		if (c == colorList[i]) {
			color = c;
		}
	}
}

//return color value of object
string clothes::getColor() {
	return color;
}

//check if parameter passed is valid in sizeList then set
void clothes::setStyle(string ss) {
	for (int i = 0; i < 5; i++) {
		if (ss == styleList[i]) {
			style = ss;
		}
	}
}

string clothes::getStyle() {
	return style;
}




