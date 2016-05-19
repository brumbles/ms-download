/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static mangastreamdl.gui.Gui.Action.*;

/**
 * Observer implementation
 *
 * @author Grigor Riskov - riskogri@fit.cvut.cz
 */
public class GuiButtonListener implements ActionListener
{

    Gui gui;

    public GuiButtonListener(Gui gui)
    {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        switch (command)
        {
            case "ADD":
                gui.buttonPressed(ADD);
                break;
            case "DEL":
                gui.buttonPressed(DEL);
                break;
            case "SET":
                gui.buttonPressed(SET);
                break;
            case "DL":
                gui.buttonPressed(DL);
                break;
            case "DLALL":
                gui.buttonPressed(DLALL);
                break;
            case "STOP":
                gui.buttonPressed(STOP);
                break;
            case "VER":
                gui.buttonPressed(VER);
                break;
            case "ALL":
                gui.buttonPressed(ALL);
                break;
            case "CHAP":
                gui.buttonPressed(CHAP);
                break;
            case "OPT":
                gui.buttonPressed(OPT);
                break;
            case "ABOUT":
                gui.buttonPressed(ABOUT);
                break;
            case "EDIT":
                gui.buttonPressed(EDIT);
                break;
            default:
                gui.buttonPressed(DEFAULT);
                break;
        }
    }

}
