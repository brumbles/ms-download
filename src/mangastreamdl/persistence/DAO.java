/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.persistence;

import mangastreamdl.gui.Gui;

import java.io.File;

/**
 * Strategy implementation
 * <p/>
 * All classes implementing this interface should be singletons.
 *
 * @author sirDarts
 */
public interface DAO
{

    public void load(Gui aThis, File f);

    public void save(Gui aThis, File options);

}
