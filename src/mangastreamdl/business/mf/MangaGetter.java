/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.business.mf;

import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.regex.Pattern;
import mangastreamdl.business.Downloader;
import org.xml.sax.InputSource;

/**
 * @author Grigor Riskov - grigor@riskov.cz
 */
public class MangaGetter extends DefaultHandler
{

    private String  name;
    private String  chapter;
    private String  url;
    private String  tmpurl;
    private String  url0;
    private boolean inTips;
    private boolean first;


    String getMangaLocation(String name, String chapter) throws IOException, SAXException, URISyntaxException
    {
        this.name = name;
        Parser parser = new Parser();
        parser.setContentHandler(this);
        parser.setErrorHandler(this);
        first = true;
        String u = "http://mangafox.me/search.php?name_method=bw&name=" + name.replace(' ', '+') +
                "&type=&author_method=cw&author=&artist_method=cw&artist=&genres%5BAction%5D=0&genres%5BAdult%5D=0&genres%5BAdventure%5D=0&genres%5BComedy%5D=0&genres%5BDoujinshi%5D=0&genres%5BDrama%5D=0&genres%5BEcchi%5D=0&genres%5BFantasy%5D=0&genres%5BGender+Bender%5D=0&genres%5BHarem%5D=0&genres%5BHistorical%5D=0&genres%5BHorror%5D=0&genres%5BJosei%5D=0&genres%5BMartial+Arts%5D=0&genres%5BMature%5D=0&genres%5BMecha%5D=0&genres%5BMystery%5D=0&genres%5BOne+Shot%5D=0&genres%5BPsychological%5D=0&genres%5BRomance%5D=0&genres%5BSchool+Life%5D=0&genres%5BSci-fi%5D=0&genres%5BSeinen%5D=0&genres%5BShoujo%5D=0&genres%5BShoujo+Ai%5D=0&genres%5BShounen%5D=0&genres%5BShounen+Ai%5D=0&genres%5BSlice+of+Life%5D=0&genres%5BSmut%5D=0&genres%5BSports%5D=0&genres%5BSupernatural%5D=0&genres%5BTragedy%5D=0&genres%5BWebtoons%5D=0&genres%5BYaoi%5D=0&genres%5BYuri%5D=0&released_method=eq&released=&rating_method=eq&rating=&is_completed=&advopts=1";
        InputSource is = new InputSource(Downloader.getInputStreamForURL(u));
        parser.parse(is);
        first = false;
        this.chapter = Pattern.quote(chapter);
        parser.parse(url0);

        return url.substring(0, url.lastIndexOf("/") + 1);
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
                    inTips = true;
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        super.endElement(uri, localName, qName);
        if (inTips && localName.equals("a"))
        {
            inTips = false;
        }
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        super.characters(ch, start, length);
        String chars = new String(ch, 0, length).toLowerCase();
        if (first)
        {
            if (length != name.length())
            {
                return;
            }
            if (chars.equalsIgnoreCase(name))
            {
                url0 = tmpurl;
            }
        }
        else
        {
            if (inTips)
            {
                if (chars.matches(".+ " + chapter + "$"))
                {
                    url = tmpurl;
                }
            }
        }
    }

}
