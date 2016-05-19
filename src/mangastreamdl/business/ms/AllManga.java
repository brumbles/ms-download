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
import java.util.ArrayList;
import java.util.List;
import mangastreamdl.business.Downloader;

/**
 * @author Grigor Riskov - grigor@riskov.cz
 */
public class AllManga extends DefaultHandler
{

    private List<String> list    = new ArrayList<>();
    private boolean      inTH    = false;
    private boolean      inTable = false;

    public List<String> getAllManga() throws IOException, SAXException, URISyntaxException
    {
        String uri = "http://mangastream.com/manga";
        Parser parser = new Parser();
        parser.setContentHandler(this);
        parser.setErrorHandler(this);
        InputSource is = new InputSource(Downloader.getInputStreamForURL(uri));
        parser.parse(is);
        return list;
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
            case "table":
                inTable = true;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        super.endElement(uri, localName, qName);
        switch (localName)
        {
            case "strong":
                inTH = false;
                break;
            case "table":
                inTable = false;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        super.characters(ch, start, length);
        if (inTable && inTH)
        {
            String th = new String(ch, 0, length);
            if (length > 1 && !th.equals("&nbsp;"))
            {
                list.add(th);
            }
        }
    }

}
