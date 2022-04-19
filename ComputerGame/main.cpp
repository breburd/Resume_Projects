/*
 * Defines a base class and then a specific computer game class.
 * 
 * Implement the traditional Name Game.
 */


#include <cstdlib>
#include "NameGame.h"

using namespace std;

int main(int argc, char** argv)
{
    Game *g=new NameGame(100);
    Game::who w=g->play();
    if(w==Game::COMPUTER)
        cout<<"Congratulations! You win!\n";
    else if (w==Game::HUMAN)
         cout<<"Sorry, you are lost!\n";
    return 0;
}


