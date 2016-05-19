/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Observer implementation
 *
 * @author Grigor Riskov - riskogri@fit.cvut.cz
 */
public class MSChangeListener implements ChangeListener
{

    Gui gui;

    public MSChangeListener(Gui gui)
    {
        this.gui = gui;
    }

    public void stateChanged(ChangeEvent e)
    {
        Object src = e.getSource();
        if (src instanceof JCheckBox)
        {
            gui.setChck(!gui.isChck());
            gui.save();
        }
    }

}
