/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.gui;

import javax.swing.*;

/**
 * @author Grigor Riskov - grigor@riskov.cz
 */
public class StringSpinnerEditor extends JSpinner.DefaultEditor
{

    public StringSpinnerEditor(JSpinner spinner)
    {
        super(spinner);
        this.getTextField().setEditable(true);
    }

}
