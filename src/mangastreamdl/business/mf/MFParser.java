/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.business.mf;

import mangastreamdl.business.ImageProperties;
import mangastreamdl.business.Manga;
import mangastreamdl.business.MangaParser;
import mangastreamdl.business.Pair;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;


/**
 * @author Grigor Riskov - grigor@riskov.cz
 */
public class MFParser implements MangaParser
{

    @Override
    public boolean checkManga(String name) throws IOException, SAXException, URISyntaxException
    {
        Checker mpc = new Checker();
        return mpc.checkManga(name);
    }

    @Override
    public List<String> getAllManga() throws IOException, SAXException, URISyntaxException
    {
        AllManga mpam = new AllManga();
        return mpam.getAllManga();
    }

    @Override
    public ImageProperties getImageLink(String url) throws IOException, SAXException, URISyntaxException
    {
        ImageGetter mpig = new ImageGetter();
        return mpig.getImageLink(url);
    }

    @Override
    public String getLastChapter(String name) throws IOException, SAXException, URISyntaxException
    {
        LastChapter mplc = new LastChapter();
        return mplc.getLastChapter(name);
    }

    @Override
    public String getMangaLocation(String name, String chapter) throws IOException, SAXException, URISyntaxException
    {
        MangaGetter mpmg = new MangaGetter();
        return mpmg.getMangaLocation(name, chapter);
    }

    @Override
    public List<String> getPageLinks(String url) throws IOException, SAXException, URISyntaxException
    {
        PageGetter mppg = new PageGetter();
        return mppg.getPageLinks(url);
    }

    @Override
    public List<Pair<Manga, String>> getLastChapters(Map<String, Manga> mlist) throws IOException, SAXException, URISyntaxException
    {
        LastChapters mplc = new LastChapters();
        return mplc.getLastChapters(mlist);
    }

    @Override
    public Map<String, String> getAllChapters(Manga m) throws IOException, SAXException, URISyntaxException
    {
        AllChapters mpac = new AllChapters();
        return mpac.getAllChapters(m);
    }


}
