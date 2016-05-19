/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.business.mf;

import mangastreamdl.business.Manga;
import mangastreamdl.business.Pair;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Grigor Riskov - grigor@riskov.cz
 */
public class LastChapters extends DefaultHandler
{

    List<Pair<Manga, String>> getLastChapters(Map<String, Manga> mlist) throws IOException, SAXException, URISyntaxException
    {
        List<Pair<Manga, String>> ret = new ArrayList<>(mlist.size());
        LastChapter lc;
        for (Manga m : mlist.values())
        {
            lc = new LastChapter();
            Pair<Manga, String> tmp = new Pair<>(m, lc.getLastChapter(m.getName()));
            ret.add(tmp);
        }
        return ret;
    }

}
