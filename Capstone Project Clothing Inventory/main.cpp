/*
 * Author: Calvin Ko
 * Title: Inventory Tracker - Capstone Project
 * Description: This program generates a virtual clothing store inventory and allows the user to add to
 * it if they are an employee, otherwise the user can search the inventory for a specific clothing item
 */

#include <iostream>
#include <string>
#include "clothes.h"
#include "LinkedList.hpp"
#include <ctime>
#include <algorithm>
#include "user.h"

using namespace std;

//randomly generate a clothing object
clothes generateClothing();

//fill passed in rack with int rackSize amount of clothes objects
void populateRack(LinkedList<clothes> &rack, int rackSize);

//function to run search function
void findNumItems(string &userSize, string &userColor, string &userStyle, LinkedList<LinkedList<clothes>> &rackList);

//add items to inventory
void addItems(LinkedList<LinkedList<clothes>> &rackList);

//print out inventory
void printInventory(LinkedList<LinkedList<clothes>> &rackList);

int main() {
    //initialize customer choice variables
    string userSize = "", userColor = "", userStyle = "";

    //create 2d matrix of clothes
    int rackSize = 15;
    LinkedList<LinkedList<clothes>> rackList;
    LinkedList<clothes> rack1;
    LinkedList<clothes> rack2;
    LinkedList<clothes> rack3;

    //fill up rack lists with clothes of rackSize
    populateRack(rack1, rackSize);
    populateRack(rack2, rackSize);
    populateRack(rack3, rackSize);

    rackList.pushBack(rack1);
    rackList.pushBack(rack2);
    rackList.pushBack(rack3); 

    //ask if user is employee or customer
    int userType = 0;
    int employeeChoice = 0;
    cout << "Are you a customer or employee?" << endl;
    cout << "1: Employee" << endl << "2: Customer" << endl;
    cin >> userType;

    //run employee actions if 1 customer actions if 2
    switch(userType) {
        case 1:
            cout << "What would you like to do?" << endl;
            cout << "1: Add items to inventory" << endl << "2: Search for items" << endl;
            cin >> employeeChoice;
            
            switch(employeeChoice) {
                case 1:
                    addItems(rackList);
                    break;

                case 2:
                    findNumItems(userSize, userColor, userStyle, rackList);
                    break;
            }
            break;

        case 2:
            findNumItems(userSize, userColor, userStyle, rackList);
            break;
    }

	return 0;
}

clothes generateClothing(){
    //initialize variables for choosing clothing details
    int sizeNum = 1, colorNum = 1, styleNum = 1;
    string sizeChoice = "", colorChoice = "", styleChoice = "";

    sizeNum = rand() % 5 + 1;
    colorNum = rand() % 9 + 1;
    styleNum = rand() % 5 + 1;

    switch(sizeNum){
        case 1:
            sizeChoice = "XS";
            break;
        
        case 2:
            sizeChoice = "S";
            break;

        case 3:
            sizeChoice = "M";
            break;

        case 4:
            sizeChoice = "L";
            break;

        case 5:
            sizeChoice = "XL";
            break;

    }

    switch(colorNum){
        case 1:
            colorChoice = "RED";
            break;
        
        case 2:
            colorChoice = "ORANGE";
            break;

        case 3:
            colorChoice = "YELLOW";
            break;

        case 4:
            colorChoice = "GREEN";
            break;

        case 5:
            colorChoice = "BLUE";
            break;

        case 6:
            colorChoice = "PURPLE";
            break;

        case 7:
            colorChoice = "BLACK";
            break;

        case 8:
            colorChoice = "WHITE";
            break;

        case 9:
            colorChoice = "BROWN";
            break;

    }

   switch(styleNum){
        case 1:
            styleChoice = "SHIRT";
            break;
        
        case 2:
            styleChoice = "PANTS";
            break;

        case 3:
            styleChoice = "SOCKS";
            break;

        case 4:
            styleChoice = "HEADWEAR";
            break;

        case 5:
            styleChoice = "UNDERGARMENT";
            break;

    }

    clothes clothing(sizeChoice, colorChoice, styleChoice);

    return clothing;
}

void populateRack(LinkedList<clothes> &rack, int rackSize){
    for(int i = 0; i < rackSize; i++){
        rack.pushBack(generateClothing());
    }
}

void findNumItems(string &userSize, string &userColor, string &userStyle, LinkedList<LinkedList<clothes>> &rackList) { 


    cout << "----------------------------------------------" << endl;
    cout << "Input clothing details:" << endl;

    sizeInput(userSize);

    while(!sizeValid(userSize)) {
        cout << endl << "Please enter a valid size: " << endl;
        sizeInput(userSize);
    }

    cout << userSize << endl;

    cout << endl;
    colorInput(userColor);

    while(!colorValid(userColor)) {
        cout << endl << "Please enter a valid color: " << endl;
        colorInput(userColor);
    }

    cout << endl;
    styleInput(userStyle);

    while(!styleValid(userStyle)) { 
        cout << endl << "Please enter a valid style: " << endl;
        styleInput(userStyle);
    }

    int clothingCount = 0;

    //search for the clothing user inputted
    for(int i = 0; i < rackList.size(); i++) {
        for(int j = 0; j < rackList.at(i).size(); j++) {
            if(rackList.at(i).at(j).getSize() == userSize) {
                if(rackList.at(i).at(j).getColor() == userColor) {
                    if(rackList.at(i).at(j).getStyle() == userStyle) {
                        clothingCount++;
                    }
                }
            }
        }
    }
    
    cout << endl << "Matching Items Found: " << clothingCount << endl;

    printInventory(rackList);

    return;
}

void addItems(LinkedList<LinkedList<clothes>> &rackList) {
    int rackNum;
    string size = "", color = "", style = "";

    cout << "Which rack to add to? (1-3): ";
    cin >> rackNum;

    while(rackNum > rackList.size()) {
        cout << "Please enter valid rack number: ";
        cin >> rackNum;
    }
    
    cout << "Input clothing details:" << endl;

    sizeInput(size);

    while(!sizeValid(size)) {
        cout << endl << "Please enter a valid size: " << endl;
        sizeInput(size);
    }

    cout << endl;
    colorInput(color);

    while(!colorValid(color)) {
        cout << endl << "Please enter a valid color: " << endl;
        colorInput(color);
    }

    cout << endl;
    styleInput(style);

    while(!styleValid(style)) { 
        cout << endl << "Please enter a valid style: " << endl;
        styleInput(style);
    }

    clothes clothing(size, color, style);

    rackList.at(rackNum - 1).pushBack(clothing);

    printInventory(rackList);

    return;
}

void printInventory(LinkedList<LinkedList<clothes>> &rackList) {
    cout << "----------------------------------------------" << endl;
    int printInv = 0;
    cout << "Would you like to print inventory contents?" << endl;
    cout << "1: Yes" << endl << "2: No" << endl;
    cin >> printInv;

    switch(printInv) {
        case 1:
            for(int i = 0; i < rackList.size(); i++) {
                cout << endl << "Rack " << i + 1 << ": " << endl;
                for(int j = 0; j < rackList.at(i).size(); j++) {
                    cout << rackList.at(i).at(j).getSize() << " " << rackList.at(i).at(j).getColor() << " " << rackList.at(i).at(j).getStyle() << endl;
                }
            }
            break;

        case 2:
            break;
    }
}