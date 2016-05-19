/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.business;

import mangastreamdl.business.mf.MFParser;
import mangastreamdl.business.ms.MSParser;

/**
 * @author Grigor Riskov - grigor@riskov.cz
 */
public class MangaParserFactory
{

    public static MangaParser getParser(Sites s)
    {
        switch (s)
        {
            case MF:
                return new MFParser();
            case MS:
                return new MSParser();
            default:
                return new MSParser();
        }
    }

}
