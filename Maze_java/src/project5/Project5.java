/*
 * Tests the maze class by giving it different mazes to traverse through.

Breanna Burd
 */
package project5;


public class Project5 
{

    public static void main(String[] args) 
    {
        char[][] m1 = {{'1', '1', '1', '1', '1'},
                       {'b', '0', '1', '0', '1'},
                       {'1', '0', '1', '0', 'e'},
                       {'0', '0', '0', '0', '1'},
                       {'1', '1', '1', '1', '1'}
                      };
        Maze maze1 = new Maze(m1);
        maze1.start();
        
        char [][] m2 = {{'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'},
                        {'1', '0', '0', '0', '0', '0', '1', '0', '0', '0', '1'},
                        {'1', '0', '1', '0', '0', '0', '1', '0', '1', '0', '1'},
                        {'e', '0', '1', '0', '0', '0', '0', '0', '1', '0', '1'},
                        {'1', '0', '1', '1', '1', '1', '1', '0', '1', '0', '1'},
                        {'1', '0', '1', '0', '1', '0', '0', '0', '1', '0', '1'},
                        {'1', '0', '0', '0', '1', '0', '1', '0', '0', '0', '1'},
                        {'1', '1', '1', '1', '1', '0', '1', '0', '0', '0', '1'},
                        {'1', '0', '1', 'b', '1', '0', '1', '0', '0', '0', '1'},
                        {'1', '0', '0', '0', '0', '0', '1', '0', '0', '0', '1'},
                        {'1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'}
                       };
        Maze maze2 = new Maze(m2);
        maze2.start();

    }
    
}
