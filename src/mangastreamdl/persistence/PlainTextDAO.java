/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.persistence;

import mangastreamdl.gui.Gui;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton
 *
 * @author Grigor Riskov - riskogri@fit.cvut.cz
 */
public class PlainTextDAO implements DAO
{

    private static PlainTextDAO instance;

    protected static synchronized PlainTextDAO getInstance()
    {
        if (instance == null)
        {
            instance = new PlainTextDAO();
        }
        return instance;
    }

    private PlainTextDAO()
    {
    }

    public void load(Gui aThis, File f)
    {
        try
        {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            while (br.ready())
            {
                line = br.readLine();
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(PlainTextDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save(Gui aThis, File options)
    {
        try
        {
            FileWriter fw = new FileWriter(options);
            BufferedWriter bw = new BufferedWriter(fw);
        }
        catch (IOException ex)
        {
            Logger.getLogger(PlainTextDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
