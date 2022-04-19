/*
 * Name.h
 * 
 * The traditional Name Game: It has a pile of marbles and two players
 *  take turns to take away marbles from the pile: each time a player
 *  needs to takes away at least one marble and at most half of the 
 *  marbles (1 if there is only 1 marble). The one who takes away the 
 *  last marble will be losing the game.
 * 
 * If you take away marbles such that the left over is 1 less than a 
 * power of 2, you will certainly win the game.
 *  Ex: 1, 3, 7, 15, 31, 63, 127,...
 */



#ifndef NAMEGAME_H
#define NAMEGAME_H

#include "Game.h"

class NameGame:public Game
{
public:    
    NameGame(int m){marbles=m;}
    virtual void displayMessage()const
    {
        Game::displayMessage();
        std::cout<<"Nim!\n"
                <<"There are "<<marbles<<" marbles in the pile.\n"
                <<"You need to take at least one marble away \n"
                <<"and at most half of them or 1 if there is\n"
                <<" only one each time. The one who takes the \n"
                <<"last marble away loses the game.\n\n";
    }
    virtual void computerMove();
    virtual void makeMove();
    virtual bool isGameOver()const{return marbles==0;}
  private:
     int marbles;
};

#endif /* NAMEGAME_H */

