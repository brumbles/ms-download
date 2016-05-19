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
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import mangastreamdl.business.Downloader;
import org.xml.sax.InputSource;

/**
 * @author Grigor Riskov - grigor@riskov.cz
 */
public class AllManga extends DefaultHandler
{

    private List<String> list        = new ArrayList<>();
    private String       nextPage    = "?az";
    private String       tmpPage     = "";
    private boolean      inNav       = false;
    private boolean      disable     = false;
    private boolean      inNext      = false;
    private boolean      inMangaText = false;
    private boolean      inTitle     = false;

    List<String> getAllManga() throws IOException, SAXException, URISyntaxException
    {
        Parser parser = new Parser();
        parser.setContentHandler(this);
        parser.setErrorHandler(this);
        while (nextPage != null)
        {
            String url = "http://www.mangafox.me/directory/" + nextPage.replace(' ', '+');
            InputSource is = new InputSource(Downloader.getInputStreamForURL(url.replace(' ', '+')));
            parser.parse(is);
        }
        return list;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equals("div"))
        {
            String tmpid = attributes.getValue("id");
            String tmpclass = attributes.getValue("class");
            if (tmpid != null && tmpid.equalsIgnoreCase("nav"))
            {
                inNav = true;
            }
            if (tmpclass != null && tmpclass.equalsIgnoreCase("manga_text"))
            {
                inMangaText = true;
            }
        }
        if (inNav)
        {
            switch (localName)
            {
                case "a":
                {
                    String tmp = attributes.getValue("href");
                    if (tmp != null)
                    {
                        tmpPage = tmp;
                    }
                    break;
                }
                case "span":
                {
                    String tmp = attributes.getValue("class");
                    if (tmp != null && tmp.equalsIgnoreCase("next"))
                    {
                        inNext = true;
                        nextPage = tmpPage;
                        //System.out.println(nextPage);
                        if (disable)
                        {
                            nextPage = null;
                        }
                    }
                    if (tmp != null && tmp.equalsIgnoreCase("disable"))
                    {
                        disable = true;
                    }
                    break;
                }
            }
        }
        if (inMangaText)
        {
            if (localName.equals("a"))
            {
                String tmp = attributes.getValue("class");
                if (tmp != null && tmp.equalsIgnoreCase("title"))
                {
                    inTitle = true;
                }
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        super.characters(ch, start, length);
        if (inTitle)
        {
            String title = new String(ch, 0, length);
            if (length > 1 && !title.equals("&nbsp;"))
            {
                list.add(title);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        super.endElement(uri, localName, qName);
        if (localName.equals("div"))
        {
            if (inNav)
            {
                inNav = false;
            }
            if (inMangaText)
            {
                inMangaText = false;
            }
        }
        if (inNav)
        {
            if (localName.equals("span"))
            {
                disable = false;
                if (inNext)
                {
                    inNext = false;
                }
            }
        }
        if (inMangaText)
        {
            if (localName.equals("a"))
            {
                if (inTitle)
                {
                    inTitle = false;
                }
            }
        }
    }

}
