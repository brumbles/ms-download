/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.gui;

/**
 * @author sirDarts
 */
public interface MangaWindow
{

    public enum action
    {
        OK, RESET, DEFAULT
    }

    public void buttonPressed(action action);

}
