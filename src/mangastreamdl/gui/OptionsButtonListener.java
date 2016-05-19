/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static mangastreamdl.gui.Options.Action.*;

/**
 * Observer implementation
 *
 * @author Grigor Riskov - riskogri@fit.cvut.cz
 */
public class OptionsButtonListener implements ActionListener
{

    private Options options;

    public OptionsButtonListener(Options options)
    {
        this.options = options;
    }

    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        switch (command)
        {
            case "OK":
                options.buttonPressed(OK);
                break;
            case "CANCEL":
                options.buttonPressed(CANCEL);
                break;
            case "APPLY":
                options.buttonPressed(APPLY);
                break;
            case "HELP":
                options.buttonPressed(HELP);
                break;
            default:
                options.buttonPressed(DEFAULT);
                break;
        }
    }

}
