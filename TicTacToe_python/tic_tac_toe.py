# Import libraries
from random_player import RandomPlayer
from conditional_player import ConditionalPlayer
from utility_player import UtilityPlayer
from minimax_player import MinimaxPlayer
from alpha_beta_player import AlphaBetaPlayer
from goal_player import GoalPlayer
from human_player import HumanPlayer
from game import Game
from enum import Enum


# class syntax
class Difficulty(Enum):
    Easy = RandomPlayer
    Medium = UtilityPlayer
    Hard_1 = MinimaxPlayer
    Hard_2 = AlphaBetaPlayer


# Set the players for the game
is_first_player = input("Do you want to be player 1? (y/n)") == "y"
opponent_difficulty = Difficulty[input("What difficulty do you want to play?\n"
                                       "Available options are:\n"
                                       "\tEasy\n"
                                       "\tMedium\n"
                                       "\tHard_1\n"
                                       "\tHard_2\n")]
if is_first_player:
    player1 = HumanPlayer(1)
    player2 = opponent_difficulty.value(2)
else:
    player1 = opponent_difficulty.value(1)
    player2 = HumanPlayer(2)

# Loop until the user chooses to exit the program
while True:

    # Create a new game using the two players
    game = Game(player1, player2)

    # Play the game to it's conclusion
    game.play()

    # Ask the user if they want to continue
    choice = input("Play another game? Y/N: ")

    # Exit the program if the user doesn't want to play anymore
    if choice != "Y":
        break
