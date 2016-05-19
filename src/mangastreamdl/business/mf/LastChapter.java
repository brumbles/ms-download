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
import mangastreamdl.business.Downloader;
import org.xml.sax.InputSource;

/**
 * @author Grigor Riskov - grigor@riskov.cz
 */
public class LastChapter extends DefaultHandler
{

    private int     column        = 0;
    private String  lastchapter   = "";
    private boolean inlisting     = false;
    private boolean foundcolumn   = false;
    private boolean correctcolumn = false;
    private boolean inTH          = false;
    private boolean inA           = false;

    String getLastChapter(String name) throws IOException, SAXException, URISyntaxException
    {
        Parser parser = new Parser();
        parser.setContentHandler(this);
        parser.setErrorHandler(this);
        String url = "http://mangafox.me/search.php?name_method=bw&name=" + name.replace(' ', '+') +
                "&type=&author_method=cw&author=&artist_method=cw&artist=&genres%5BAction%5D=0&genres%5BAdult%5D=0&genres%5BAdventure%5D=0&genres%5BComedy%5D=0&genres%5BDoujinshi%5D=0&genres%5BDrama%5D=0&genres%5BEcchi%5D=0&genres%5BFantasy%5D=0&genres%5BGender+Bender%5D=0&genres%5BHarem%5D=0&genres%5BHistorical%5D=0&genres%5BHorror%5D=0&genres%5BJosei%5D=0&genres%5BMartial+Arts%5D=0&genres%5BMature%5D=0&genres%5BMecha%5D=0&genres%5BMystery%5D=0&genres%5BOne+Shot%5D=0&genres%5BPsychological%5D=0&genres%5BRomance%5D=0&genres%5BSchool+Life%5D=0&genres%5BSci-fi%5D=0&genres%5BSeinen%5D=0&genres%5BShoujo%5D=0&genres%5BShoujo+Ai%5D=0&genres%5BShounen%5D=0&genres%5BShounen+Ai%5D=0&genres%5BSlice+of+Life%5D=0&genres%5BSmut%5D=0&genres%5BSports%5D=0&genres%5BSupernatural%5D=0&genres%5BTragedy%5D=0&genres%5BWebtoons%5D=0&genres%5BYaoi%5D=0&genres%5BYuri%5D=0&released_method=eq&released=&rating_method=eq&rating=&is_completed=&advopts=1";
        InputSource is = new InputSource(Downloader.getInputStreamForURL(url));
        parser.parse(is);
        return lastchapter;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equals("table"))
        {
            String id = attributes.getValue("id");
            if (id != null && id.equalsIgnoreCase("listing"))
            {
                inlisting = true;
            }
        }
        if (inlisting)
        {
            switch (localName)
            {
                case "th":
                    inTH = true;
                    if (!foundcolumn)
                    {
                        column++;
                    }
                    break;
                case "td":
                    column--;
                    if (column == 0)
                    {
                        correctcolumn = true;
                    }
                    break;
                case "a":
                    inA = true;
                    break;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        super.endElement(uri, localName, qName);
        switch (localName)
        {
            case "table":
                inlisting = false;
                break;
            case "th":
                inTH = false;
                break;
            case "td":
                correctcolumn = false;
                break;
            case "a":
                inA = false;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        super.characters(ch, start, length);
        String tmp = new String(ch, 0, length);
        if (correctcolumn && inA)
        {
            lastchapter = tmp;
        }
        else if (inTH)
        {
            if (tmp.equalsIgnoreCase("Latest Chapter"))
            {
                foundcolumn = true;
            }
        }
    }

}
