/*
 * Author: Calvin Ko
 * Title: Inventory Tracker - Capstone Project
 * Description: This file contains the linkedlist class and its implementation
 */
#ifndef LINKEDLIST_HPP
#define LINKEDLIST_HPP
#include "Node.hpp"

template<typename T>
class LinkedList {
private:
    Node<T> *mPHead;
    Node<T> *mPTail;
    int unsigned mListSize;
    Node<T> *mMakeNodeForValue(const T input);

public:
    LinkedList();
    void pushFront(T input);
    void pushBack(T input);
    T popFront();
    T front();
    T back();
    const int unsigned size();
    ~LinkedList();
    void copyConstructor(LinkedList &list);
    LinkedList<T>& operator=(LinkedList&);
    T at(const int pos);
    void deleteNodes();
};

#endif

//---------------------------------------------------------------------
//methods for LinkedList

template<typename T>
//constructor
LinkedList<T>::LinkedList(){
    mPHead = nullptr;
    mPTail = nullptr;
    mListSize = 0;
}

template<typename T>
//destructor
LinkedList<T>::~LinkedList(){
    Node<T>* temp = mPHead;
    while(temp != nullptr) {
        temp = temp -> pNext;
        delete mPHead;
        mPHead = temp;
    }
    mPHead = nullptr;
    mPTail = nullptr;
}

template<typename T>
//returns a node pointer to new node with value of parameter
Node<T>* LinkedList<T>::mMakeNodeForValue(const T input){
    Node<T> *pNode = new Node<T>;
    pNode->value = input;
    pNode->pNext = nullptr;
    return pNode;
}

template<typename T>
//add node to the head of list
void LinkedList<T>::pushFront(T input){
    Node<T> *pNode = new Node<T>;
    if(mPHead == nullptr || mPTail == nullptr){
        pNode->value = input;
        pNode->pNext = mPHead;
        mPHead = pNode;
        mPTail = mPHead;
        return;
    }
    pNode->value = input;
    pNode->pNext=mPHead;
    mPHead = pNode;
    return;
}

template<typename T>
//adds node to the tail of list
void LinkedList<T>::pushBack(T input){
    Node<T> *pNode = new Node<T>;
    if(mPHead == nullptr || mPTail == nullptr){
        pNode->value = input;
        pNode->pNext = nullptr;
        mPTail = pNode;
        mPHead = pNode;
        return;
    }
    
    pNode->value = input;
    pNode->pNext = nullptr;
    mPTail->pNext = pNode;
    mPTail = pNode;
    return;
}

template<typename T>
//deletes the head of list
T LinkedList<T>::popFront(){
    T value;
    if(mPHead != nullptr){
        value = mPHead->value;
        Node<T> *temp = mPHead;
        temp = temp->pNext;
        delete mPHead;
        mPHead = temp;
        return value;
    }
    return T();
}

template<typename T>
//returns value of the head of the list
T LinkedList<T>::front(){
    if(mPHead != nullptr){
        return mPHead->value;
    }
    return T();
}

template<typename T>
//returns value of the tail of the list
T LinkedList<T>::back(){
    if(mPHead != nullptr){
        return mPTail->value;
    }
    return T();
}

template<typename T>
//returns current size of list
const int unsigned LinkedList<T>::size(){
    int count;
    for(Node<T> *temp = mPHead; temp!= nullptr; temp = temp->pNext){
        count++;
    }

    return count;
}

template<typename T>
//create a deep copy of the object passed in
void LinkedList<T>::copyConstructor(LinkedList<T> &list){
    //start a new linked list to copy to
    Node<T> *temp = mPHead;
    Node<T> *copyHead = new Node<T>;

    //copy data to new linked list
    copyHead->value = temp->value;
    Node<T> *copyCurrent = copyHead;
    temp = temp->pNext;

    while(temp != nullptr){
        copyCurrent->pNext = new Node<T>;
        copyCurrent = copyCurrent->pNext;
        copyCurrent->value = temp->value;
        copyCurrent->pNext = nullptr;
        temp = temp->pNext;

    }
    
    list.mPHead = copyHead;
    return;
}

template<typename T>
//Deallocate the current list and create deep copy of passed object
LinkedList<T>& LinkedList<T>::operator=(LinkedList<T>& list){
    //clear current list
    Node<T>* current = mPHead;
    while(current != nullptr){
        current = current->pNext;
        delete mPHead;
        mPHead = current;
    }

    //start a new linked list to copy to
    Node<T> *copyFromCurrent = list.mPHead;
    mPHead = new Node<T>;

    //copy data to new linked list
    mPHead->value = copyFromCurrent->value;
    Node<T> *copyCurrent = mPHead;
    copyFromCurrent = copyFromCurrent->pNext;

    while(copyFromCurrent != nullptr){
        copyCurrent->pNext = new Node<T>;
        copyCurrent = copyCurrent->pNext;
        copyCurrent->value = copyFromCurrent->value;
        copyCurrent->pNext = nullptr;
        copyFromCurrent = copyFromCurrent->pNext;
    }
    
    return *this;
}

template<typename T>
//returns value at the provided position
T LinkedList<T>::at(const int pos){
    Node<T> *temp = mPHead;
    if(temp == nullptr){
        cout << "empty" << endl;
    }
    
    for(int i = 0; i < pos; i++){
        if(temp == nullptr){
            cout << i << endl;
            return T();
        }
        temp = temp->pNext;
    }
    return temp->value;
}

template<typename T>
void LinkedList<T>::deleteNodes(){
    Node<T>* current = mPHead;
    while(current != nullptr){
        current = current->pNext;
        delete mPHead;
        mPHead = current;
        mPTail = mPHead;
    }
}