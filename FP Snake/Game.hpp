/* CSCI 261: Final Project
 *
 * Author: Calvin Ko
 * Resources used (Office Hours, Tutoring, Other Students, etc & in what capacity):
 *
 * This source code manages all of the game logic and how the game runs
 */

#ifndef GAME_HPP
#define GAME_HPP

#include <SFML/Graphics.hpp>
#include "Snake.hpp"
#include <cstdlib>
#include <ctime>

using namespace sf;
using namespace std;

class Game{
private:
    Color headColor;
    Color bodyColor;
    int xStart;
    int yStart;
    Vector2f foodPos;

public:
    Snake *snake;
    Game();
    Game(RenderWindow& window);
    ~Game();
    Snake getSnake();
    bool checkCollision(const sf::RectangleShape& a, const sf::RectangleShape& b);
    void snakeRectangle(Vector2f pos, Color color);
    void drawGame(RenderWindow& window);
    void snakeSize();
    RectangleShape food;
    Color getBodyColor();

};
//-----------------------------------------------------------------------------------------------------------------
Game::Game(){

}

/*constructor, sets starting point to half of window size, color of snake to be yellow and green
 *create initial snake head and food
 */
Game::Game(RenderWindow& window){
    srand(time(0));
    rand();
    headColor = Color::Yellow;
    bodyColor = Color::Green;
    xStart = window.getSize().x / 2;
    yStart = window.getSize().y / 2;
    snake = new Snake();
    snakeRectangle(Vector2f(xStart, yStart), headColor);

    //create food rectangle
    food.setSize(Vector2f(20,20));
    food.setPosition(Vector2f((rand() % window.getSize().x), (rand() % window.getSize().y)));
    food.setFillColor(Color(Color::Red));
    
}

//destructor
Game::~Game(){
    delete snake;
}

//check if the hitboxes of two rectangles intersect
bool Game::checkCollision(const sf::RectangleShape& a, const sf::RectangleShape& b) {
	return a.getGlobalBounds().intersects(b.getGlobalBounds());
		
}

//create rectangle and add to the snake list
void Game::snakeRectangle(Vector2f pos, Color color){
    RectangleShape rect;
    rect.setSize(Vector2f(20,20));
    rect.setPosition(pos);
    rect.setFillColor(color);
    snake->addBody(rect);

}

//draw the game components
void Game::drawGame(RenderWindow& window){
    snake->drawSnake(window);
    window.draw(food);
    
}

//return the body color of the snake
Color Game::getBodyColor(){
    return bodyColor;
}

#endif