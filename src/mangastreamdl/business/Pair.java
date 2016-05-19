/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.business;

/**
 * @author Grigor Riskov - riskogri@fit.cvut.cz
 */
public class Pair<T extends Comparable, S extends Comparable> implements Comparable<Pair>
{

    public T first;
    public S second;
    private static SortingStrategy sstr = SortingStrategy.FIRST;

    public Pair(T first, S second)
    {
        this.first = first;
        this.second = second;
    }

    @Override
    public int compareTo(Pair o)
    {
        switch (sstr)
        {
            case FIRST:
                return first.compareTo(o.first);
            case SECOND:
                return second.compareTo(o.second);
        }
        return -1;
    }

    public static void setFirstSortingStrategy()
    {
        sstr = SortingStrategy.FIRST;
    }

    public static void setSecondSortingStrategy()
    {
        sstr = SortingStrategy.SECOND;
    }

    private static enum SortingStrategy
    {
        FIRST, SECOND
    }

    @Override
    public String toString()
    {
        return first + " - " + second;
    }


}
