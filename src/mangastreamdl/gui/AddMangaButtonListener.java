/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static mangastreamdl.gui.MangaWindow.action.*;

/**
 * Observer implementation
 *
 * @author Grigor Riskov - riskogri@fit.cvut.cz
 */
public class AddMangaButtonListener implements ActionListener
{

    MangaWindow mw;

    public AddMangaButtonListener(MangaWindow mw)
    {
        this.mw = mw;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        switch (command)
        {
            case "ADD":
                mw.buttonPressed(OK);
                break;
            case "RES":
                mw.buttonPressed(RESET);
                break;
            default:
                mw.buttonPressed(DEFAULT);
                break;
        }
    }

}
