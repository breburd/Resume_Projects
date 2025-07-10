import unittest
from parameterized import parameterized
from goal_player import GoalPlayer
from board import Board


class GoalPlayerTests(unittest.TestCase):

    @parameterized.expand([
        ["---------", 0],
        ["X--------", 2],
        ["X-X------", 1],
        ["XOOX-O---", 8],
        ['X----X-X-', 2]
    ])
    def test_get_next_move(self, state, expected_move):
        board = Board(state)
        player = GoalPlayer(2)
        result = player.get_next_move(board)
        self.assertEqual(expected_move, result)

    @parameterized.expand([
        ["---------", None],
        ["X--------", None],
        ["X-X------", 1],
        ["XOOX-----", 6],
        ['X--OO-X--', 5]
    ])
    def test_check_for_win(self, state, expected_move):
        board = Board(state)
        player = GoalPlayer(2)
        result = player.check_for_win(board)
        self.assertEqual(expected_move, result)

    @parameterized.expand([
        ['XO---X-X-', 7],
        ['---X-O---', 5],
        ['XXXXXX---', 2],
        ['XXXXXXX-X', 2],
    ])
    def test_get_new_goal(self, state, expected_move):
        board = Board(state)
        player = GoalPlayer(2)
        result = player.get_new_goal(board)
        self.assertEqual(expected_move, result)

    @parameterized.expand([
        [0, 6],
        [1, 7],
        [2, 0],
        [3, 1],
        [4, 2],
        [5, 3],
        [6, 4],
        [7, 5],
    ])
    def test_calculate_rearranged_line_index(self, index, expected_index):
        player = GoalPlayer(2)
        result = player.calculate_rearranged_line_index(index)
        self.assertEqual(expected_index, result)

    @parameterized.expand([
        ['XXX------', (0, 1, 2), True],
        ['---------', (0, 1, 2), False],
        ['X---X---X', (0, 4,8), True],
        ['XXXXXXXX-', (6, 7, 8), False],
    ])
    def test_is_line_full(self, state, line, expected_index):
        board = Board(state)
        player = GoalPlayer(2)
        result = player.is_line_full(board, line)
        self.assertEqual(expected_index, result)

    @parameterized.expand([
        ['XXX------', (0, 1, 2), 0],
        ['---------', (0, 4, 8), 0],
        ['-O-X-O-X-', (2, 4, 6), 0],
    ])
    def test_get_best_index(self, state, line, expected_index):
        board = Board(state)
        player = GoalPlayer(2)
        result = player.get_best_index(board, line)
        self.assertEqual(expected_index, result)

    @parameterized.expand([
        ['-O-X-O---', 2, 2],
        ['XXXXXX---', 6, 0],
        ['O----O-O-', 4, 3],
        ['OOOO-OOOO', 4, 8],
    ])
    def test_get_space_worth(self, state, space_index, expected_index):
        board = Board(state)
        player = GoalPlayer(2)
        result = player.get_space_worth(board, space_index)
        self.assertEqual(expected_index, result)


if __name__ == '__main__':
    unittest.main()
