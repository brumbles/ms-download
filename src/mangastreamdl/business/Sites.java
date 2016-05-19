/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.business;

/**
 * @author sirDarts
 */
public enum Sites
{

    MS("MS"), MF("MF");

    private final String name;

    private Sites(String s)
    {
        name = s;
    }

    @Override
    public String toString()
    {
        return name;
    }

}
