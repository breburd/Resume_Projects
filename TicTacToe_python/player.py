# Import libraries
from board import Board


# Represents an abstract tic-tac-toe player
class Player:

    # Initialize the player
    def __init__(self, number):
        self.number = number
        self.mark = "X" if number == 1 else "O"
        self.opponent_mark = "O" if number == 1 else "X"

    # Get the player's next move
    # Note: This is an abstract method to be implemented in the player subclass
    def get_next_move(self, board: Board) -> int:
        pass

    def is_line_empty(self, board: Board, line) -> bool:
        return line.count('-') == 3

    def is_line_full(self, board: Board, line) -> bool:
        return line.count('-') == 0

    # Gets the score. Positive if the player is winning. Negative if the player is losing
    def get_score(self, board: Board) -> int:
        if board.has_win(self.mark):
            return 10
        elif board.has_win(self.opponent_mark):
            return -10
        return 0
