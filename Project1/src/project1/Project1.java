/*
 * Breanna Burd 8/25/2020
Project 1

This project will allow the user to interact with matrices.
 */
package project1;

import java.util.Arrays;
import java.util.Random;

public class Project1 
{

    public static void main(String[] args) 
    {
        Matrix m1 = new Matrix();
        Matrix m2 = new Matrix(1, 3);
        System.out.println("m1: " + m1);
        System.out.println("m2: \n" + m2);
        m2.setEntry(0, 1, 2.5);
        System.out.println("m2: \n" + m2);
        Matrix m3 = new Matrix(3, 2);
        Matrix m4 = new Matrix(3, 2);
        m3.setEntry(0, 0, 2);
        m3.setEntry(0, 1, 5);
        m3.setEntry(1, 0, -3);
        m3.setEntry(1, 1, 8);
        m3.setEntry(2, 0, 7);
        m3.setEntry(2, 1, 5);
        
        m4.setEntry(0, 0, -3);
        m4.setEntry(0, 1, 10);
        m4.setEntry(1, 0, 2);
        m4.setEntry(1, 1, 8);
        m4.setEntry(2, 0, 9);
        m4.setEntry(2, 1, 0);

        System.out.println("m3: \n" + m3);
        System.out.println("m4: \n" + m4);
        
        try
        {
            System.out.println("The sum of m3 and m4:\n" + m3.add(m4));
        } catch(dimensionMismatch e)
        {
            System.out.println(e.getMessage());
        }
        
        //This should fail
        try
        {
            System.out.println("The sum of m3 and m2:\n" + m3.add(m2));
        } catch(dimensionMismatch e)
        {
            System.out.println(e.getMessage());
        }
        
        try
        {
            System.out.println("The difference of m3 and m4:\n" + m3.subtract(m4));
        } catch(dimensionMismatch e)
        {
            System.out.println(e.getMessage());
        }
        //This should fail
        try
        {
            System.out.println("The difference of m3 and m2:\n" + m3.subtract(m2));
        } catch(dimensionMismatch e)
        {
            System.out.println(e.getMessage());
        }
        
        Matrix m5 = new Matrix(2, 3);
        m5.setEntry(0, 0, 1);
        m5.setEntry(0, 1, 2);
        m5.setEntry(0, 2, 3);
        m5.setEntry(1, 0, 3);
        m5.setEntry(1, 1, 2);
        m5.setEntry(1, 2, 1);
        
        Matrix m6 = new Matrix(3, 2);
        m6.setEntry(0, 0, 2);
        m6.setEntry(0, 1, 5);
        m6.setEntry(1, 0, -3);
        m6.setEntry(1, 1, 8);
        m6.setEntry(2, 0, 7);
        m6.setEntry(2, 1, 5);
        
        try
        {
            System.out.println("The product of m5 and m6:\n" + m5.multiply(m6));
        } catch(dimensionMismatch e)
        {
            System.out.println(e.getMessage());
        }
        //This should fail
        try
        {
            System.out.println("The product of m2 and m5:\n" + m2.multiply(m5));
        } catch(dimensionMismatch e)
        {
            System.out.println(e.getMessage());
        }
        
        System.out.println(m3.equals(m4));
        
        System.out.println(m3 + "\n" + m3.transpose());
        
        System.out.println(m5.compareTo(m6));
        
        Matrix[] marray = new Matrix[10];
        //Fill up the array with 20 random matrices
        Random r = new Random();
        for(int i = 0; i < 10; i++)
        {
                //Fill the martices with random double values
                int row = 1 + r.nextInt(8);
                int col = 1 + r.nextInt(8);
                Matrix ma = new Matrix(row, col);
                for(int x = 0; x< row; x++)
                {
                    for(int y = 0; y < col; y++)
                    {
                        //Creates random values between -9 and 9.
                        int value = 9 - r.nextInt(19);
                        ma.setEntry(x, y, value);
                    }
                }
                marray[i] = ma;
        }
        
        //Output the 10 arrays unsorted
        System.out.println("UNSORTED\n");
        for(int i = 0; i < 10; i++)
        {
            System.out.println(marray[i] + "\t SUM = " + marray[i].sum() + "\n");
        }
        System.out.println("DONE UNSORTED");
        
        //Output the 10 arrays according to the natural ordering (ascending)
        Arrays.sort(marray);
        System.out.println("SORTED ASCENDING\n");
        for(int i = 0; i < 10; i++)
        {
            System.out.println(marray[i] + "\t SUM = " + marray[i].sum() + "\n");
        }
        System.out.println("DONE SORTED ASCENDING");
        
        //Output the 10 arrays in descending order
        Arrays.sort(marray, new MatrixOrdering());
        System.out.println("SORTED DESCENDING\n");
        for(int i = 0; i < 10; i++)
        {
            System.out.println(marray[i] + "\t SUM = " + marray[i].sum() + "\n");
        }
        System.out.println("DONE SORTED DESCENDING");

    }
    
}
