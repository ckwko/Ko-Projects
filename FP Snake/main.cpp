/* CSCI 261: Final Project
 *
 * Author: Calvin Ko
 * Resources used (Office Hours, Tutoring, Other Students, etc & in what capacity):
 *
 * This source code runs the game and checks for inputs from the user, at the end of the game
 * the code records the scores in a text file and tells the player if they beat the hiscore
 */

#include <SFML/Graphics.hpp>
#include <iostream>
#include <list>
#include <string>
#include <ctime>
#include <fstream>
#include "Snake.hpp"
#include "Game.hpp"

using namespace std;
using namespace sf;

bool isNumber(const string& str); //function to help go through scores file

int main(){
    
    // create a window
    RenderWindow window( VideoMode(1000, 800), "Snake" );
    
    //create random positions for food to spawn
    srand(time(0));
    rand();
    Vector2f foodPos; 
    int framerate = 10;

    //score to keep track of how many food is eaten
    int score = 0;
    int hiscore = 0;
    
    //create an event object once to store future events
    Event event;

    //create intial game objects and conditions
    Game *game = new Game(window);
    bool gameRunning = true;

    //open file to get past scores
    string filename = "Scores.txt";
    fstream file;
    file.open(filename, fstream::in | fstream::out | fstream::app);
    
    // If file does not exist, Create new file
    if (file.fail()){
        file.open(filename,  fstream::in | fstream::out | fstream::trunc);
        file <<"\n";
        file.close();

    }else{ // use existing file
        while(!file.eof()){
            string name;
            int temp;
            file >> name >> temp;
            if(temp > hiscore){
                hiscore = temp;
            }
        }
        file.close();
    }


    // while the window is open
    while(gameRunning) {
        // clear any existing contents
        window.clear();

        //set starting framerate of game
        window.setFramerateLimit(framerate);

        //draw the initial head
        game->drawGame(window);
        game->snake->updatePos();

        //check if food and snakehead collide, move the food object and add to score, increase speed over time;
        if(game->checkCollision(game->food, game->snake->returnHead())){
            foodPos = Vector2f(labs((rand() % window.getSize().x)) - 40, labs((rand() % window.getSize().y) - 40));
            game->food.setPosition(foodPos); 
            window.draw(game->food);
            game->snakeRectangle(game->snake->getTailPos(), game->getBodyColor());
            score++;
            if(score % 3 == 0){
                framerate++;
            }
        }

        //check if snake collides with body
        if(game->snake->checkSnakeCollision()){
            gameRunning = false;
        }

        if(game->snake->getHeadPos().x <= 0 || game->snake->getHeadPos().x >= window.getSize().x - 20){
            gameRunning = false;
        }

        if(game->snake->getHeadPos().y <= 0 || game->snake->getHeadPos().y >= window.getSize().y - 20){
            gameRunning = false;
        }


        //check for keypresses to change the direction of the snake
        if(event.type == Event::KeyPressed){
            if(event.key.code == (Keyboard::Key::Up)){
                if(game->snake->getDirectionY() != 1){
                    game->snake->updateDir(0,-1);

                }

            }else if(event.key.code == (Keyboard::Key::Left)){
                if(game->snake->getDirectionX() != 1){
                    game->snake->updateDir(-1,0);
                }

            }else if(event.key.code == (Keyboard::Key::Right)){
                if(game->snake->getDirectionX() != -1){
                    game->snake->updateDir(1,0);
                }

            }else if(event.key.code == (Keyboard::Key::Down)){
                if(game->snake->getDirectionY() != -1){
                    game->snake->updateDir(0,1);
                }
            }
        }

        // display the current contents of the window
        window.display();

        /////////////////////////////////////
        // BEGIN EVENT HANDLING HERE
        // check if any events happened since the last time checked
        while( window.pollEvent(event) ) {
            // if event type corresponds to pressing window X
            if(event.type == Event::Closed) {
                // tell the window to close
                window.close();
            }
            // check addition event types to handle additional events
        }
        //  END  EVENT HANDLING HERE
        /////////////////////////////////////
    }

    //create and store scores in text file
    string name;
    cout << "Enter your name: ";
    cin >> name;

    //check if new hiscore
    if(score > hiscore){
        cout << "New highscore! - " << score << endl;
        cout << "Previous highscore - " << hiscore << endl;

    }else{
        cout << "Your score - " << score << endl;
        cout << "Hiscore - " << hiscore << endl;
        cout << "Better luck next time." << endl;
    }

    ofstream scores(filename, ios::app);
    scores << name << ": " << score << "\n";
    scores.close(); 

    //clear memory
    game->~Game();
    delete game;

    return 0;
}
