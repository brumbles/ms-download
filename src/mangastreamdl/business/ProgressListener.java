/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.business;

import mangastreamdl.gui.Gui;

import javax.imageio.ImageReader;
import javax.imageio.event.IIOReadProgressListener;

/**
 * @author sirDarts
 */
public class ProgressListener implements IIOReadProgressListener
{

    Gui   gui;
    float sizePercentage;
    float lastPercentage;

    public ProgressListener(Gui gui, float sizePercentage, float lastPercentage)
    {
        this.gui = gui;
        this.sizePercentage = sizePercentage;
        this.lastPercentage = lastPercentage;
    }

    @Override
    public void sequenceStarted(ImageReader source, int minIndex)
    {
    }

    @Override
    public void sequenceComplete(ImageReader source)
    {
    }

    @Override
    public void imageStarted(ImageReader source, int imageIndex)
    {
        //gui.setPgProgressBar(100);
    }

    @Override
    public void imageProgress(ImageReader source, float percentageDone)
    {
        //System.out.println("percentageDone*sizePercentage "+percentageDone*sizePercentage);
        gui.updatePgProgressBar(percentageDone * sizePercentage + lastPercentage);
    }

    @Override
    public void imageComplete(ImageReader source)
    {
    }

    @Override
    public void thumbnailStarted(ImageReader source, int imageIndex, int thumbnailIndex)
    {
    }

    @Override
    public void thumbnailProgress(ImageReader source, float percentageDone)
    {
    }

    @Override
    public void thumbnailComplete(ImageReader source)
    {
    }

    @Override
    public void readAborted(ImageReader source)
    {
    }

}
