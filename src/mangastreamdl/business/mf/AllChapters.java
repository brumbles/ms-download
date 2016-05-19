/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangastreamdl.business.mf;

import mangastreamdl.business.Manga;
import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.TreeMap;
import mangastreamdl.business.Downloader;
import org.xml.sax.InputSource;

/**
 * @author sirDarts
 */
class AllChapters extends DefaultHandler
{

    private Manga  manga;
    private String tmpurl;
    private String url;
    private Map<String, String> map = new TreeMap<>();
    private boolean intips;
    private boolean first;

    Map<String, String> getAllChapters(Manga m) throws IOException, SAXException, URISyntaxException
    {
        this.manga = m;
        Parser parser = new Parser();
        parser.setContentHandler(this);
        parser.setErrorHandler(this);
        first = true;
        String u = "http://mangafox.me/search.php?name_method=bw&name=" + m.getName().replace(' ', '+') +
                "&type=&author_method=cw&author=&artist_method=cw&artist=&genres%5BAction%5D=0&genres%5BAdult%5D=0&genres%5BAdventure%5D=0&genres%5BComedy%5D=0&genres%5BDoujinshi%5D=0&genres%5BDrama%5D=0&genres%5BEcchi%5D=0&genres%5BFantasy%5D=0&genres%5BGender+Bender%5D=0&genres%5BHarem%5D=0&genres%5BHistorical%5D=0&genres%5BHorror%5D=0&genres%5BJosei%5D=0&genres%5BMartial+Arts%5D=0&genres%5BMature%5D=0&genres%5BMecha%5D=0&genres%5BMystery%5D=0&genres%5BOne+Shot%5D=0&genres%5BPsychological%5D=0&genres%5BRomance%5D=0&genres%5BSchool+Life%5D=0&genres%5BSci-fi%5D=0&genres%5BSeinen%5D=0&genres%5BShoujo%5D=0&genres%5BShoujo+Ai%5D=0&genres%5BShounen%5D=0&genres%5BShounen+Ai%5D=0&genres%5BSlice+of+Life%5D=0&genres%5BSmut%5D=0&genres%5BSports%5D=0&genres%5BSupernatural%5D=0&genres%5BTragedy%5D=0&genres%5BWebtoons%5D=0&genres%5BYaoi%5D=0&genres%5BYuri%5D=0&released_method=eq&released=&rating_method=eq&rating=&is_completed=&advopts=1";
        InputSource is = new InputSource(Downloader.getInputStreamForURL(url.replace(' ', '+')));
        parser.parse(is);
        first = false;
        parser.parse(url);

        return map;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equals("a"))
        {
            tmpurl = attributes.getValue("href");

            if (!first)
            {
                String cs = attributes.getValue("class");
                if (cs != null && cs.equals("tips"))
                {
                    intips = true;
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        super.endElement(uri, localName, qName);
        if (intips && localName.equals("a"))
        {
            intips = false;
        }
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        super.characters(ch, start, length);
        String chars = new String(ch, 0, length).toLowerCase();
        if (first)
        {
            if (length != manga.getName().length())
            {
                return;
            }
            if (chars.equalsIgnoreCase(manga.getName()))
            {
                url = tmpurl;
            }
        }
        else if (intips)
        {
            String[] ss = chars.split(" ");
            String tmp = ss[ss.length - 1];
            map.put(tmp, tmpurl.substring(0, tmpurl.lastIndexOf("/") + 1));
        }
    }

}
