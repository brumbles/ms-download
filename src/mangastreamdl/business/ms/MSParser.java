/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.business.ms;

import mangastreamdl.business.ImageProperties;
import mangastreamdl.business.Manga;
import mangastreamdl.business.MangaParser;
import mangastreamdl.business.Pair;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Facade implementation
 *
 * @author Grigor Riskov - riskogri@fit.cvut.cz
 */
public class MSParser implements MangaParser
{

    public static final Logger LOGGER = Logger.getLogger(MSParser.class.getName());

    @Override
    public boolean checkManga(String name) throws IOException, SAXException, URISyntaxException
    {
        Checker mpc = new Checker();
        boolean ret = mpc.checkManga(name);
        return ret;
    }

    @Override
    public String getMangaLocation(String name, String chapter) throws IOException, SAXException, URISyntaxException
    {
        MangaGetter mpmg = new MangaGetter();
        String ret = mpmg.getMangaLocation(name, chapter);
        return ret;
    }

    @Override
    public List<String> getPageLinks(String url) throws IOException, SAXException, URISyntaxException
    {
        PageGetter mppg = new PageGetter();
        List<String> ret = mppg.getPageLinks(url);
        return ret;
    }

    @Override
    public ImageProperties getImageLink(String url) throws IOException, SAXException, URISyntaxException
    {
        ImageGetter mpig = new ImageGetter();
        ImageProperties ret = mpig.getImageLink(url);
        return ret;
    }

    @Override
    public String getLastChapter(String name) throws IOException, SAXException, URISyntaxException
    {
        LastChapter mplc = new LastChapter();
        String ret = mplc.getLastChapter(name);
        return ret;
    }

    @Override
    public List<String> getAllManga() throws IOException, SAXException, URISyntaxException
    {
        AllManga mpam = new AllManga();
        List<String> ret = mpam.getAllManga();
        return ret;
    }

    @Override
    public List<Pair<Manga, String>> getLastChapters(Map<String, Manga> mlist) throws IOException, SAXException, URISyntaxException
    {
        LastChapters mplc = new LastChapters();
        List<Pair<Manga, String>> ret = mplc.getLastChapters(mlist);
        return ret;
    }

    @Override
    public Map<String, String> getAllChapters(Manga m) throws IOException, SAXException, URISyntaxException
    {
        AllChapters mpac = new AllChapters();
        Map<String, String> ret = mpac.getAllChapters(m);
        return ret;
    }

}
