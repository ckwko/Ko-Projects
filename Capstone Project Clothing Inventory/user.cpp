/*
 * Author: Calvin Ko
 * Title: Inventory Tracker - Capstone Project
 * Description: Implementation of user.h
 */
#include "user.h"
#include <iostream>
#include <algorithm>

using namespace std;

void sizeInput(string &size) {
    cout << "Input size (XS, S, M, L, XL): ";
    cin >> size;
    transform(size.begin(), size.end(), size.begin(), ::toupper);
}

void colorInput(string &color) {
    cout << "Input color (WHITE, BLACK, BROWN, RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE): ";
    cin >> color;
    transform(color.begin(), color.end(), color.begin(), ::toupper);
}

void styleInput(string &style) {
    cout << "Input style (SHIRT, PANTS, SOCKS, HEADWEAR, UNDERGARMENT): ";
    cin >> style;
    transform(style.begin(), style.end(), style.begin(), ::toupper);
}

bool sizeValid(string size) {
    string sizeList[5] = { "XS", "S", "M", "L", "XL" };	

    for(int i = 0; i < 5; i++) {
        if(size == sizeList[i]) {
            return true;
        }
    }

    return false;
}

bool colorValid(string color) {
    string colorList[9] = { "WHITE", "BLACK", "BROWN", "RED", "ORANGE", "YELLOW", "GREEN", "BLUE", "PURPLE" };

    for(int i = 0; i < 9; i++) {
        if(color == colorList[i]) {
            return true;
        }
    }

    return false;
}

bool styleValid(string style) {
    string styleList[5] = { "SHIRT", "PANTS", "SOCKS", "HEADWEAR", "UNDERGARMENT"};

    for(int i = 0; i < 5; i++) {
        if(style == styleList[i]) {
            return true;
        }
    }

    return false;
}

