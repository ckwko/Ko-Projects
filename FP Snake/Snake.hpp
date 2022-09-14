/* CSCI 261: Final Project
 *
 * Author: Calvin Ko
 * Resources used (Office Hours, Tutoring, Other Students, etc & in what capacity):
 *
 * This source code manages everything that happens with the snake such as the direction and the collision
 */

#ifndef SNAKE_HPP
#define SNAKE_HPP

#include <SFML/Graphics.hpp>
#include <list>
// #include "Game.hpp"

using namespace std;
using namespace sf;

class Snake{
private:
    float speed;
    int direction[2];
    vector<RectangleShape> body;

public:
    Snake();
    ~Snake();
    void addBody(RectangleShape& newSegment);
    void updatePos();
    void updateDir(const int xD, const int yD);
    Vector2f getTailPos();
    Vector2f getHeadPos();
    void drawSnake(RenderWindow& window);
    RectangleShape returnHead();
    int getDirectionX();
    int getDirectionY();
    bool checkSnakeCollision();
};
//-----------------------------------------------------------------------------------------------------------------
//set speed of the snake and initial direction
Snake::Snake(){    
    speed = 22;
    direction[0] = 0;
    direction[1] = 0;
    
}

Snake::~Snake(){
    
}

//add rectangle to the body list
void Snake::addBody(RectangleShape& newSegment){
    body.push_back(newSegment);
    return;
}

//move the snake by updating positions of the body segments
void Snake::updatePos(){
    Vector2f nextPos = body[0].getPosition();
    body[0].move(Vector2f(speed*direction[0], speed*direction[1]));

    for(int i = 1; i < body.size(); i++){
        Vector2f temp = body[i].getPosition();
        body[i].setPosition(nextPos);
        nextPos = temp;
    }

    return;
}

//chamge the direction of the snake
void Snake::updateDir(const int xD, const int yD){
    direction[0] = xD; 
    direction[1] = yD;
}

//return the position of the tail
Vector2f Snake::getTailPos(){
    RectangleShape tail = body.back();
    return tail.getPosition();
    
}

//get the position of the head
Vector2f Snake::getHeadPos(){
    RectangleShape head = body.front();
    return head.getPosition();
}

//draw snake using the body list
void Snake::drawSnake(RenderWindow& window){
    for(int i = 0; i < body.size(); i++){
        window.draw(body[i]);
    }

    return;
}

//return the head rectangle of the snake from the body list
RectangleShape Snake::returnHead(){
    return body[0];
}

//return the x direction snake is travelling
int Snake::getDirectionX(){
    return direction[0];
}

//return the y direction snake is travelling
int Snake::getDirectionY(){
    return direction[1];
}

//check if the snake collides with itself
bool Snake::checkSnakeCollision(){
    bool collision;
    for(int i = 2; i < body.size(); i++){
        collision = body[0].getGlobalBounds().intersects(body[i].getGlobalBounds());
        if(collision == true){
            return true;
        }
    }
}

#endif