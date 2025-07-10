/*
 * Breanna Burd
   This contains all of the methods used by the user with the matrices.
 */
package project1;


public class Matrix implements Comparable<Matrix>
{
    private int n;  //The number of rows
    private int m;  //The number of columns
    private double[][] mat;
    
    /**
     * Default constructor that creates a 1-by-1 Matrix and initializes each
     * entry to 0.
     */
    public Matrix()
    {
        n = 1;
        m = 1;
        mat = new double[n][m];
        initialize(n, m);
    }
    
    /**
     * Default constructor that creates an n-by-m Matrix.
     * 
     * @param a the number of rows in the Matrix
     * @param b the number of columns in the Matrix
     */
    public Matrix(int a, int b)
    {
        n = a;
        m = b;
        mat = new double[n][m];
        initialize(n, m);
    }
    
    /**
     * Initializes each entry of the Matrix to 0.
     * 
     * @param a the number of rows
     * @param b the number of columns
     */
    public void initialize(int a, int b)
    {
        for(int i = 0; i < a; i++)
        {
            for(int j = 0; j < b; j++)
            {
                mat[i][j] = 0;
            }
        }
    }
    
    /**
     * Takes a position of an entry and replaces the previous entry with the 
     * double type given number.
     * 
     * @param x the row number
     * @param y the column number
     * @param z the new entry value
     */
    public void setEntry(int x, int y, double z)
    {
        mat[x][y] = z;
    }
    
    /**
     * This adds two matrices. The matrices must have the same n-by-m 
     * dimensions. Otherwise, it will throw a dimensionMismatch exception.
     * 
     * @param ma The matrix being added to this Matrix
     */
    public Matrix add(Matrix ma) throws dimensionMismatch
    {
        if(n != ma.n || m != ma.m)
        {
            throw new dimensionMismatch("These matrices cannot be added " +
                    "because the dimensions must be the same!");
        }
        Matrix matrix = new Matrix(n, m);
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < m; j++)
            {
                matrix.mat[i][j] = mat[i][j] + ma.mat[i][j];
            }
        }
        return matrix;
    }
    
    /**
     * This subtracts two matrices. The matrices must have the same n-by-m 
     * dimensions. Otherwise, it will throw a dimensionMismatch exception.
     * 
     * @param ma The matrix being subtracted from this Matrix
     */
    public Matrix subtract(Matrix ma) throws dimensionMismatch
    {
        if(n != ma.n || m != ma.m)
        {
            throw new dimensionMismatch("These matrices cannot be subtracted " +
                    "because the dimensions must be the same!");
        }
        Matrix matrix = new Matrix(n, m);
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < m; j++)
            {
                matrix.mat[i][j] = mat[i][j] - ma.mat[i][j];
            }
        }
        return matrix;
    }
    
    /**
     * This multiplies two matrices. The user can only multiply matrices that
     * have opposite dimensions, meaning that the 
     * number of columns of this matrix is equal to the number of rows of the
     * second matrix (An n-by-m and an m-by-p multiplication). The resultant 
     * matrix is an n-by-p, meaning the number of rows of this matrix by the
     * number of columns of the second matrix. If these matrices cannot be
     * multiplied, it will throw a dimensionMismatch exception.
     * 
     * The multiplication is performed by taking the sum of the products of
     * the ith row of the first matrix times the jth column of the second 
     * matrix.
     * 
     * @param ma the matrix being multiplied by this matrix
     */
    public Matrix multiply(Matrix ma) throws dimensionMismatch
    {
        if(m != ma.n)
        {
            throw new dimensionMismatch("These matrices cannot be multiplied " +
                    "because they must have the form n-by-m * m-by-p, meaning"
                    + " the number of columns of the first must match the "
                    + "number of rows of the second!");
        }
        Matrix product = new Matrix(n, ma.m);
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < ma.m; j++)
            {
                for(int k = 0; k < m; k++)
                {
                    product.mat[i][j] += (mat[i][k] * ma.mat[k][j]);
                }
            }
        }
        return product;

        
    }
    
    /**
     * Returns true if the matrices are equal, meaning the value at each 
     * position is equal. Otherwise returns false.
     */
    public boolean equals(Matrix ma)
    {
        if(n != ma.n || m != ma.m)
            return false;
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < m; j++)
            {
                if(mat[i][j] != ma.mat[i][j])
                    return false;
            }
        }
        return true;
    }
    
    /**
     * Creates a new Matrix by flipping the number of rows and number of 
     * columns and for each (i, j) in the new matrix was the (j, i) position
     * of the original matrix.
     */
    public Matrix transpose()
    {
        Matrix ma = new Matrix(m, n);
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < m; j++)
            {
                ma.mat[j][i] = mat[i][j];
            }
        }
        return ma;
    }
    
    /**
     * Prints out the matrix as a string.
     */
    @Override
    public String toString()
    {
        String output = "";
        for(int i = 0; i < this.n; i++)
        {
            for(int j = 0; j < this.m; j++)
            {
                output += "\t" + Math.round((mat[i][j]* Math.pow(10, 2)) / Math.pow(10, 2));
            }
            output += "\n";
        }
        return output;
    }
    
    /**
     * Returns 1 if the sum of all of the absolute values of the entries of this
     * matrix is greater than the absolute values of the entries of the other 
     * matrix. Returns -1 if the sums of the absolute values of the entries of 
     * this matrix is less than the absolute values of the entries of the other 
     * matrix. Returns 0 if they are equal.
     * 
     * @param ma2 the matrix being compared to this matrix
     */
    public int compareTo(Matrix ma2)
    {
        int x = 0, y = 0;
        
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < m; j++)
            {
                x += Math.abs(mat[i][j]);
            }
        }
        
        for(int i = 0; i < ma2.n; i++)
        {
            for(int j = 0; j < ma2.m; j++)
            {
                y += Math.abs(ma2.mat[i][j]);
            }
        }
        
        if(x > y)
            return 1;
        if(x < y)
            return -1;
        return 0;
    }
    
    /**
     * This outputs the sum of all of the absolute values of the entries in the 
     * Matrix. This method is purely used to confirm that the compareTo() and
     * compare() methods work properly. I have kept this method for your help 
     * as well in confirming they are indeed sorted.
     * 
     */
    public int sum()
    {
        int x = 0;
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < m; j++)
            {
                x += Math.abs(mat[i][j]);
            }
        }
        return x;
    }
}
