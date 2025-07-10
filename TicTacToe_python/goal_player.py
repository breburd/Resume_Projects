# Import libraries
from board import Board
from player import Player
from argmax import argmax


# Represents a tic-tac-toe agent evaluating moves with a goal-based function
class GoalPlayer(Player):

    def __init__(self, number):
        super().__init__(number)
        # Set the initial goal line to the diagonal from top-left to bottom-right
        self.goal_line_index = 6

    # Gets the next move using a utility function
    # and conditional logic for decisive moves
    def get_next_move(self, board: Board) -> int:
        # if there is a win for either player, go there (choose the spot that results in own win first)
        possible_win = self.check_for_win(board)
        if possible_win is not None:
            return possible_win

        # Check if the opponent has blocked the line for the current goal and pick a new goal if so
        line = board.lines[self.goal_line_index]
        values = [board.spaces[j] for j in line]
        if self.opponent_mark in values:
            self.goal_line_index = self.get_new_goal(board)
            # Reset the line after the new goal is set
            line = board.lines[self.goal_line_index]

        return line[self.get_best_index(board, line)]

    def check_for_win(self, board: Board):
        other_win = None
        for winning_location in board.lines:
            values = [board.spaces[j] for j in winning_location]
            # Check if there is an empty space in the diagonal, row, or column
            if '-' in values:
                # Check if the current location results in our win
                if values.count(self.mark) == 2:
                    return winning_location[values.index('-')]
                # Check if the current location results in opponent win
                if values.count(self.opponent_mark) == 2:
                    other_win = winning_location[values.index('-')]
        return other_win

    def get_new_goal(self, board: Board):
        # Calculate which line would be the best chance at winning based on the current board state
        rearranged_lines = []
        rearranged_lines.extend(board.lines[6:])
        rearranged_lines.extend(board.lines[:6])
        self.goal_line_index = -1
        # First check to see if there is a line that already has our mark and could be set up to win
        # Lines with 2 marks have already been checked prior to this
        for i in range(0, len(rearranged_lines)):
            line = rearranged_lines[i]
            if not self.is_line_full(board, line):
                values = [board.spaces[j] for j in line]
                if self.mark in values and self.opponent_mark not in values:
                    return self.calculate_rearranged_line_index(i)

        # If there are no lines that satisfy both having a current player mark and no opponent mark
        # Find a line that does not have an opponent mark
        for i in range(0, len(rearranged_lines)):
            line = rearranged_lines[i]
            if not self.is_line_full(board, line):
                values = [board.spaces[j] for j in line]
                if self.opponent_mark not in values:
                    return self.calculate_rearranged_line_index(i)

        if self.goal_line_index == -1:
            # it is impossible to win at this point, so just pick a random goal with an open slot
            for i in range(0, len(rearranged_lines)):
                line = rearranged_lines[i]
                if not self.is_line_full(board, line):
                    return self.calculate_rearranged_line_index(i)

    def calculate_rearranged_line_index(self, i: int) -> int:
        if i < 2:
            index = 6 + i
        else:
            index = i - 2
        return index

    def is_line_full(self, board: Board, line: list) -> bool:
        # Check if the line has any open slots
        # Returns True if there are no open spaces in the line
        values = [board.spaces[j] for j in line]
        return values.count('-') == 0

    def get_best_index(self, board: Board, line: list) -> int:
        # Get the best index within the given line
        space_worth = []
        for index in line:
            if board.is_open_space(index):
                space_worth.append(self.get_space_worth(board, index))
            else:
                space_worth.append(-10)
        return space_worth.index(max(space_worth))

    def get_space_worth(self, board: Board, space_index: int) -> int:
        # Calculate the worth of the space so that if the space is part of a row, column, or diagonal
        # that has multiple opportunities for a chance to win, go there
        # this ideally sets up the opportunity to choose a spot that will guarantee a win
        # of course this is only possible if the other player has not blocked one of the directions.
        total = 0
        for line in board.lines:
            if space_index in line:
                values = [board.spaces[j] for j in line]
                total += (values.count(self.mark))
        return total
