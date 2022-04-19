/*
This class takes a 2D array maze and finds all of the possible solutions and
prints them.

 * Breanna  Burd
 */
package project5;

public class Maze 
{
    private char[][] maze;
    private int solutions;
    
    /*
    Construcotr that takes a maze as a parameter.
    */
    public Maze(char[][] m)
    {
        maze = new char[m.length][m[0].length];
        for(int i = 0; i < m.length; i++)
        {
            for(int j = 0; j < m[0].length; j++)
                maze[i][j] = m[i][j];
        }
        solutions = 0;
    }
    
    /**
     * Finds the starting position, b, in the given maze and then solves the maze
     */
    public void start()
    {
        //Find the starting point 'b'
        for(int i = 0; i < maze.length; i++)
        {
            for(int j = 0; j < maze[0].length; j++)
            {
                if(maze[i][j] == 'b')
                {
                    solve(i, j);
                }
            }
        }
    }
    
    /**
     * Checks all of the possible next steps from the given position (r,c).
     */
    public void solve(int r, int c)
    {        
        //Check every direction for a '0'
        
        //Follow that path until a dead end or 'e' 

            if(checkRight(r, c))
            {
                maze[r][c + 1] = 'p';
                solve(r, c+1);
                maze[r][c+1] = '0';
            }
            if(checkLeft(r, c))
            {
                maze[r][c - 1] = 'p';
                solve(r, c-1);
                maze[r][c-1] = '0';
            }
            if(checkUp(r, c))
            {
                maze[r+1][c] = 'p';
                solve(r+1, c);
                maze[r+1][c] = '0';
            }
            if(checkDown(r, c))
            {
                maze[r-1][c] = 'p';
                solve(r-1, c);
                maze[r-1][c] = '0';
            }
    }
    
    /**
     * Checks the position (r, c+1) and prints out the maze if the ending
     * position, e, is found. 
     */
    public boolean checkRight(int r, int c)
    {
        if(c < maze[0].length-1)
        {
            if(maze[r][c+1] == '0')
                return true;
            if(maze[r][c+1] == 'e')
            {
                solutions++;
                System.out.println("Solution: " + solutions);
                this.print();
            }
        }
        return false;
    }
    
    /**
     * Checks the position (r, c-1) and prints out the maze if the ending
     * position, e, is found. 
     */
    public boolean checkLeft(int r, int c)
    {
        if(c > 0)
        {
            if(maze[r][c-1] == '0')
                return true;
            if(maze[r][c-1] == 'e')
            {
                solutions++;
                System.out.println("Solution: " + solutions);
                this.print();
            }
        }
        return false;
    }
    
    /**
     * Checks the position (r+1, c) and prints out the maze if the ending
     * position, e, is found. 
     */
    public boolean checkUp(int r, int c)
    {
        if(r < maze.length-1)
        {
            if(maze[r+1][c] == '0')
                    return true;
            if(maze[r+1][c] == 'e')
            {
                solutions++;
                System.out.println("Solution: " + solutions);
                this.print();
            }
        }
        return false;
    }
    
    /**
     * Checks the position (r-1, c) and prints out the maze if the ending
     * position, e, is found. 
     */
    public boolean checkDown(int r, int c)
    {
        if(r > 0)
        {
            if(maze[r-1][c] == '0')
                return true;
            if(maze[r-1][c] == 'e')
            {
                solutions++;
                System.out.println("Solution: " + solutions);
                this.print();
            }
        }
        return false;
    }
    
    /**
     * Prints out the maze
     */
    public void print()
    {
        for(int i = 0; i < maze.length; i++)
        {
            for(int j = 0; j < maze[0].length; j++)
                System.out.print(maze[i][j] + " ");
            System.out.println();
        }
    }
    
    
}
