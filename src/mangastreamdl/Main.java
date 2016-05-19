/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl;

import mangastreamdl.gui.Gui;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author sirDarts
 */
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        /*
        MangaParser MP = MangaParserFactory.getParser(Sites.MS);
        try {
            String page = "Last Page (21)";
            String tmp = page.substring(page.indexOf('(')+1, page.lastIndexOf(')'));
            System.out.println(tmp);
            System.out.println(MP.getPageLinks("http://mangastream.com/read/fairy_tail/340/1973/1"));
        } catch (IOException | SAXException ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        */


        Gui g;
        File f = new File("options.ini");
        if (f.exists())
        {
            g = new Gui(f);
        }
        else
        {
            g = new Gui();
        }
        JFrame jf = g;
        try
        {
            FileOutputStream foe = new FileOutputStream("error.log");
            FileOutputStream foo = new FileOutputStream("out.log");
            PrintStream err = new PrintStream(foe);
            PrintStream out = new PrintStream(foo);
            System.setErr(err);
            System.setOut(out);
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        jf.setVisible(true);
    }

}
