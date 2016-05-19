/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.gui;

import mangastreamdl.business.Manga;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Observer implementation
 *
 * @author Grigor Riskov - riskogri@fit.cvut.cz
 */
public class MangaChooserListener implements ItemListener
{

    private Gui gui;

    public MangaChooserListener(Gui gui)
    {
        this.gui = gui;
    }

    @Override
    public void itemStateChanged(ItemEvent e)
    {
        Object o = e.getItem();
        if (o instanceof Manga)
        {
            Manga manga = (Manga) o;
            gui.setChapter(manga.getChapter());
            switch (manga.getSite())
            {
                case MF:
                case MS:
                    gui.setDLALLEnabled(true);
                    break;
                default:
                    gui.setDLALLEnabled(false);
            }
        }
    }

}
