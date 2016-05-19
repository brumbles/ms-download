/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.gui;

import javax.swing.*;

/**
 * @author Grigor Riskov - grigor@riskov.cz
 */
public class StringSpinnerModel extends AbstractSpinnerModel
{

    private String value;

    public StringSpinnerModel()
    {
        value = "0";
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value.toString();
        fireStateChanged();
    }

    public Object getNextValue()
    {
        if (isNumber(value))
        {
            int i = Integer.parseInt(value);
            value = String.valueOf(++i);
            return value;
        }
        String ret = value.substring(0, value.length() - 1);
        char c = (char) (value.charAt(value.length() - 1) + 1);
        ret += c;
        value = ret;
        return value;
    }

    public Object getPreviousValue()
    {
        if (isNumber(value))
        {
            int i = Integer.parseInt(value);
            value = String.valueOf(--i);
            return value;
        }
        String ret = value.substring(0, value.length() - 2);
        ret += (value.charAt(value.length() - 1) - 1);
        value = ret;
        return value;
    }

    private boolean isNumber(String value)
    {
        for (int i = 0; i < value.length(); i++)
        {
            if (!Character.isDigit(value.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

}
