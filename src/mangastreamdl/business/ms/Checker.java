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
public class Checker extends DefaultHandler
{

    private String name;
    private boolean found = false;
    private boolean inTH  = false;

    public boolean checkManga(String name) throws IOException, SAXException, URISyntaxException
    {
        this.name = name;
        String uri = "http://mangastream.com/manga";
        Parser parser = new Parser();
        parser.setContentHandler(this);
        parser.setErrorHandler(this);
        InputSource is = new InputSource(Downloader.getInputStreamForURL(uri));
        parser.parse(is);
        return found;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, qName, attributes);
        switch (localName)
        {
            case "strong":
                inTH = true;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        super.endElement(uri, localName, qName);
        if (localName.equals("strong"))
        {
            inTH = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        super.characters(ch, start, length);
        if (inTH)
        {
            String th = new String(ch, 0, length).toLowerCase();
            String manga = name.toLowerCase();
            if (th.equals(manga))
            {
                found = true;
            }
        }
    }

}
