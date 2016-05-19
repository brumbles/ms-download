/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.business.ms;

import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.regex.Pattern;
import mangastreamdl.business.Downloader;

/**
 * @author Grigor Riskov - grigor@riskov.cz
 */
public class MangaGetter extends DefaultHandler
{

    private String name;
    private String chapter;
    private String location;
    private String loc;
    private String ret;
    private boolean inA   = false;
    private boolean inTD  = false;
    private boolean found = false;
    private boolean first = true;

    public String getMangaLocation(String name, String chapter) throws IOException, SAXException, URISyntaxException
    {
        this.name = name;
        this.chapter = Pattern.quote(chapter);
        String uri = "http://mangastream.com/manga";
        Parser parser = new Parser();
        parser.setContentHandler(this);
        parser.setErrorHandler(this);
        InputSource is = new InputSource(Downloader.getInputStreamForURL(uri));
        parser.parse(is);
        first = false;
        uri = location;
        is = new InputSource(Downloader.getInputStreamForURL(uri));
        parser.parse(is);
        return ret;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, qName, attributes);
        switch (localName)
        {
            case "a":
                loc = attributes.getValue("href");
                inA = true;
                break;
            case "td":
                inTD = true;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        super.endElement(uri, localName, qName);
        switch (localName)
        {
            case "a":
                inA = false;
                break;
            case "td":
                inTD = false;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        super.characters(ch, start, length);
        if (!first && inA && found)
        {
            String s = new String(ch, 0, length);
            if (s.matches("^" + chapter + " .*"))
            {
                ret = loc;
                found = false;
            }
        }
        else if (inTD)
        {
            String th = new String(ch, 0, length).toLowerCase();
            String manga = name.toLowerCase();
            if (th.equals(manga))
            {
                if (first)
                {
                    location = loc;
                    found = true;
                }
            }
        }
    }

}
