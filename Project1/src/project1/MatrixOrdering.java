/*
 * This class implements the Comparable interface in order to order the 
matrices by the sum of the absolute values of the entries. 
Bre Burd
 */
package project1;


import java.util.Comparator;

public class MatrixOrdering implements Comparator<Matrix>
{
    @Override
    public int compare(Matrix ma1, Matrix ma2)
    {
        return -1 * ma1.compareTo(ma2);        
    }
}
