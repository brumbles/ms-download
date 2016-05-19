/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangastreamdl.business.ms;

import mangastreamdl.business.Manga;
import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.TreeMap;
import mangastreamdl.business.Downloader;

/**
 * @author sirDarts
 */
public class AllChapters extends DefaultHandler
{

    private Manga  manga;
    private String tmpurl;
    private String url;
    private Map<String, String> map = new TreeMap<>();
    private boolean inA;
    private boolean inTD;
    private boolean first;
    private boolean found = false;

    Map<String, String> getAllChapters(Manga m) throws IOException, SAXException, URISyntaxException
    {
        this.manga = m;
        Parser parser = new Parser();
        parser.setContentHandler(this);
        parser.setErrorHandler(this);
        first = true;
        String uri = "http://mangastream.com/manga";
        InputSource is = new InputSource(Downloader.getInputStreamForURL(uri));
        parser.parse(is);
        first = false;
        parser.parse(url);

        return map;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, qName, attributes);
        switch (localName)
        {
            case "a":
                tmpurl = attributes.getValue("href");
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
        if (!first && inA && found && inTD)
        {
            String s = new String(ch, 0, length);
            String[] ss = s.split(" ");
            map.put(ss[0], tmpurl);
        }
        else if (inTD)
        {
            String s = new String(ch, 0, length).toLowerCase();
            String m = manga.getName().toLowerCase();
            if (s.equals(m))
            {
                if (first)
                {
                    url = tmpurl;
                    found = true;
                }
            }
        }
    }

}
