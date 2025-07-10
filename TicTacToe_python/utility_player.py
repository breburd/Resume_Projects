# Import libraries
from board import Board
from conditional_player import ConditionalPlayer
from argmax import argmax


# Represents a tic-tac-toe agent evaluating moves with a utility function
# Note: this agent inherits from a conditional player
# Note: it uses it's conditional logic for making decisive moves
class UtilityPlayer(ConditionalPlayer):

    # Gets the next move using an utility function
    # and conditional logic for decisive moves
    def get_next_move(self, board: Board) -> int:
        spaces = self.get_utility_of_spaces(board, self.get_utility_of_lines(board))
        # Set the best move to the line with the best utility and the first open space
        # in that line
        best_space = spaces.index(max(spaces))
        return best_space

    def get_line_utility(self, board: Board, line) -> int:
        agent_marks = 0
        opponent_marks = 0
        for space in line:
            if board.spaces[space] == self.mark:
                agent_marks += 1
            elif board.spaces[space] == self.opponent_mark:
                opponent_marks += 1
        # Use the evaluation function that was provided in the pseudocode
        # Note : I did not use the evaluation from the homework pdf because the test cases seemed to fail.
        # I listened to the live learning recordings and found out afterwards that the test cases could've been
        # changed, but I had already completed this portion of the assignment
        return 3 * agent_marks - opponent_marks

    def get_utility_of_spaces(self, board: Board, utility_of_lines: list) -> list:
        space_utilities = []
        for space in range(0, 9):
            if not board.is_open_space(space):
                space_utilities.append(-99)
            else:
                line_indexes_containing_space = []
                index = 0
                for line in board.lines:
                    if space in line:
                        line_indexes_containing_space.append(index)
                    index += 1
                utility = 0
                for index in line_indexes_containing_space:
                    line_utility = utility_of_lines[index]
                    if line_utility == 6:
                        # This is a winning lcoation
                        utility = 100
                    else:
                        line = board.lines[index]
                        values = [board.spaces[j] for j in line]
                        if values.count(self.opponent_mark) == 2:
                            utility = 10
                        else:
                            utility += utility_of_lines[index]
                space_utilities.append(utility)
        return space_utilities

    def get_utility_of_lines(self, board: Board) -> list:
        line_utilities = []
        for line in board.lines:
            if self.is_line_empty(board, line):
                utility = 0
            elif self.is_line_full(board, line):
                utility = -10
            else:
                utility = self.get_line_utility(board, line)
            line_utilities.append(utility)
        return line_utilities

    def is_line_empty(self, board: Board, line: list) -> bool:
        values = [board.spaces[j] for j in line]
        return values.count('-') == 3

    def is_line_full(self, board: Board, line: list) -> bool:
        values = [board.spaces[j] for j in line]
        return values.count('-') == 0
