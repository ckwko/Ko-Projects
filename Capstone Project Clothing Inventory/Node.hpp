/*
 * Author: Calvin Ko
 * Title: Inventory Tracker - Capstone Project
 * Description: This file contains the node class for linkedlists
 */
#ifndef NODE_H
#define NODE_H
template<typename T>

struct Node {
    /**
     * @brief the value of this Node
     * 
     */
    T value;
    /**
     * @brief pointer to the next element of the linked list
     * 
     */
    Node<T> *pNext;
};

#endif