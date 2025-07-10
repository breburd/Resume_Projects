# Import libraries
from player import Player
from board import Board
from argmax import argmax
import math


# Represents a minimax agent with alpha-beta pruning
class AlphaBetaPlayer(Player):

    # Gets the next move given the current board state
    def get_next_move(self, board: Board) -> int:
        value, move = self.get_minimax(board, True, -math.inf, math.inf)
        return move

    def get_minimax(self, board: Board, is_max: bool, alpha: float, beta: float) -> (int, int):
        # Check if the game is over
        if board.is_terminal():
            return self.get_score(board), None
        # Initialize v to negative infinity when maximizing, and positive infinity when minimizing
        v = -math.inf if is_max else math.inf
        available_actions = board.get_open_spaces()
        move = available_actions[0]
        # Iterate over all the open board slots to determine the best move
        for a in available_actions:
            action_board = board.copy()
            # This player is the maximizer and the minimizer is the opponent
            action_board.mark_space(a, self.mark if is_max else self.opponent_mark)
            v2, a2 = self.get_minimax(action_board, not is_max, alpha, beta)
            if is_max and v2 > v:
                v, move = v2, a
                alpha = max(alpha, v)
            elif not is_max and v2 < v:
                v, move = v2, a
                beta = min(beta, v)
            if (is_max and v2 >= beta) or (not is_max and v2 <= alpha):
                return v, move
        return v, move