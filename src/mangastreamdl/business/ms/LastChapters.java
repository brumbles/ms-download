/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.business.ms;

import mangastreamdl.business.Manga;
import mangastreamdl.business.Pair;
import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mangastreamdl.business.Downloader;

/**
 * @author Grigor Riskov - grigor@riskov.cz
 */
class LastChapters extends DefaultHandler
{

    private Map<String, Manga>        mlist;
    private List<Pair<Manga, String>> lastChapters;
    private Manga                     current;
    private boolean inA   = false;
    private boolean inTH  = false;
    private boolean found = false;

    List<Pair<Manga, String>> getLastChapters(Map<String, Manga> mlist)
    throws IOException, SAXException, URISyntaxException
    {
        if (mlist.size() <= 0)
        {
            return new ArrayList<>(0);
        }
        this.mlist = mlist;
        this.lastChapters = new ArrayList<>();
        String uri = "http://mangastream.com/manga";
        Parser parser = new Parser();
        parser.setContentHandler(this);
        parser.setErrorHandler(this);
        InputSource is = new InputSource(Downloader.getInputStreamForURL(uri));
        parser.parse(is);
        return lastChapters;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, qName, attributes);
        switch (localName)
        {
            case "a":
                inA = true;
                break;
            case "strong":
                inTH = true;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        super.endElement(uri, localName, qName);
        if (localName.equals("a"))
        {
            inA = false;
        }
        if (localName.equals("strong"))
        {
            inTH = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        super.characters(ch, start, length);
        if (inA && found)
        {
            String s = new String(ch, 0, length);
            lastChapters.add(new Pair<>(current, parseChapter(s)));
            found = false;
        }
        else if (inTH)
        {
            String th = new String(ch, 0, length);
            current = mlist.get(th);
            if (current != null)
            {
                found = true;
            }
        }
    }

    private String parseChapter(String s)
    {
        String[] ss = s.split(" ");
        return ss[0];
    }

}
