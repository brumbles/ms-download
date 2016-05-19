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
public class PageGetter extends DefaultHandler
{
    private List<String> list  = new ArrayList<>();
    private int          pages = 0;
    private boolean inPage;
    private boolean firstGroup = true;
    private boolean inA        = false;

    public List<String> getPageLinks(String url) throws IOException, SAXException, URISyntaxException
    {
        Parser parser = new Parser();
        parser.setContentHandler(this);
        parser.setErrorHandler(this);
        InputSource is = new InputSource(Downloader.getInputStreamForURL(url));
        parser.parse(is);
        String tmp = url.substring(0, url.lastIndexOf('/') + 1);
        for (int i = 1; i <= pages; i++)
        {
            list.add(tmp + i);
        }
        return list;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equals("div"))
        {
            String tmp = attributes.getValue("class");
            if (tmp != null && tmp.equals("btn-group"))
            {
                if (firstGroup)
                {
                    firstGroup = false;
                }
                else
                {
                    inPage = true;
                }
            }
        }
        else if (inPage && localName.equals("a"))
        {
            //list.add(attributes.getValue("href"));
            inA = true;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        super.endElement(uri, localName, qName);
        if (inPage && localName.equals("div"))
        {
            inPage = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        super.characters(ch, start, length);
        String page = new String(ch, 0, length);
        if (inA)
        {
            if (page.toLowerCase().contains("last page"))
            {
                String tmp = page.substring(page.indexOf('(') + 1, page.lastIndexOf(')'));
                pages = Integer.parseInt(tmp);
            }
        }
    }

}
