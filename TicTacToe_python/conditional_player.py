# Import libraries
from player import Player
from board import Board


# Represents a tic-tac-toe agent that evaluates moves using conditional logic
class ConditionalPlayer(Player):

    # Returns the next move given the current board state
    def get_next_move(self, board: Board) -> int:
        move = self.get_decisive_move(board)
        if move is not None:
            return move
        return self.get_non_decisive_move(board)

    def get_decisive_move(self, board: Board) -> int:
        """Check if any of the spots are possible winning locations,

        Returns
        -------
        int : the int of the index where a win is possible or -1
        """
        other_win = None
        # Rearrange the winning lines to check diagonals first
        rearranged_lines = []
        rearranged_lines.extend(board.lines[6:])
        rearranged_lines.extend(board.lines[:6])
        for winning_location in rearranged_lines:
            values = [board.spaces[j] for j in winning_location]
            # Check if there is an empty space in the diagonal, row, or column
            if '-' in values:
                # Check if the current location results in our win
                if values.count(self.mark) == 2:
                    return winning_location[values.index('-')]
                # Check if the current location results in opponent win
                if values.count(self.opponent_mark) == 2:
                    other_win = winning_location[values.index('-')]
        # Block opponent from winning or return -1 to make a non-decisive move
        return other_win

    def get_non_decisive_move(self, board: Board) -> int:
        # Rearrange the lines to check diagonals first
        rearranged_lines = []
        rearranged_lines.extend(board.lines[6:])
        rearranged_lines.extend(board.lines[:6])
        for winning_location in rearranged_lines:
            values = [board.spaces[j] for j in winning_location]
            # Check if there is an empty space in the diagonal, row, or column
            if '-' in values:
                return winning_location[values.index('-')]
        # Otherwise, return the first open space
        return board.spaces.index('-')
