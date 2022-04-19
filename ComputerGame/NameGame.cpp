/*
 * NameGame.cpp
 */



#include "NameGame.h"

void NameGame::computerMove()
{
    //Randomly determine the number of marbles to reduce
   int n=(marbles==1)?1:(((rand()/(double)RAND_MAX)*marbles/2+1)); 
   marbles-=n;

   std::cout<<"The machine takes "<<n<<" marbles, you have "
            <<marbles<<" marbles left.\n\n";
}

void NameGame::makeMove()
{
   int x;
   std::cout<<"How many marbles would you like to take away? \n";
   std::cin>>x;
   marbles-=x;
   std::cout<<"You have taken "<<x<<" marbles away!\n"
            <<"There are "<<marbles<<" marbles left.\n\n";
}

