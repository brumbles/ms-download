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
import mangastreamdl.business.Downloader;

/**
 * @author Grigor Riskov - grigor@riskov.cz
 */
public class LastChapter extends DefaultHandler
{

    private String name;
    private String lastChapter;
    private boolean inA   = false;
    private boolean inTH  = false;
    private boolean found = false;

    public String getLastChapter(String name) throws IOException, SAXException, URISyntaxException
    {
        this.name = name;
        String uri = "http://mangastream.com/manga";
        Parser parser = new Parser();
        parser.setContentHandler(this);
        parser.setErrorHandler(this);
        InputSource is = new InputSource(Downloader.getInputStreamForURL(uri));
        parser.parse(is);
        return lastChapter;
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
            lastChapter = s;
            found = false;
        }
        else if (inTH)
        {
            String th = new String(ch, 0, length);
            if (th.equalsIgnoreCase(name))
            {
                found = true;
            }
        }
    }

}
