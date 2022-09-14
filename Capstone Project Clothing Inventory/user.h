/*
 * Author: Calvin Ko
 * Title: Inventory Tracker - Capstone Project
 * Description: These functions help to get user input and validate their input
 */
#ifndef USER_H
#define USER_H

#include <string>

using namespace std;

//menu for getting size from user
void sizeInput(string &size);

//menu for getting color from user
void colorInput(string &size);

//menu for getting style from user
void styleInput(string &style);

//validate if size type exists
bool sizeValid(string size);

//validate if color type exists
bool colorValid(string color);

//validate if style type exists
bool styleValid(string style);

#endif // !USER_H