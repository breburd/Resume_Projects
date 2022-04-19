/*
 * Game.cpp
 */


#include "Game.h"

Game::who Game::play()
 {
  //determines who starts first
     srand((unsigned)time(NULL));
  int turn=(int)(0.5+rand()/(double)RAND_MAX);
   displayMessage();
   std::cout<<((turn==0)?"The machine goes ":"You go ")
            <<" first: \n";
   while(true)
   {
       if(turn==0)//Machine's turn
           computerMove();
       else
          makeMove();
       //Determines if any one wins yet
       if(isGameOver() && turn==0)
           return COMPUTER;
       else if(isGameOver() && turn==1)
           return HUMAN;
       //Switch player
       turn=(1+turn)%2;
   }
 }
   void Game::displayMessage()const
   {
       //Don't know what the game is
       std::cout<<"Welcome to our great game: "; 
   }
