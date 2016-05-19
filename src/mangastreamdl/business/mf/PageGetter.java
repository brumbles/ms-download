/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.business.mf;

import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mangastreamdl.business.Downloader;
import org.xml.sax.InputSource;

/**
 * @author Grigor Riskov - grigor@riskov.cz
 */
public class PageGetter extends DefaultHandler
{

    List<String> links = new ArrayList<>();
    String url;
    boolean inScript = false;

    List<String> getPageLinks(String url) throws SAXException, IOException, URISyntaxException
    {
        Parser parser = new Parser();
        parser.setContentHandler(this);
        parser.setErrorHandler(this);
        this.url = url;
        InputSource is = new InputSource(Downloader.getInputStreamForURL(url.replace(' ', '+')));
        parser.parse(is);
        return links;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equals("script"))
        {
            inScript = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        super.endElement(uri, localName, qName);
        if (inScript && localName.equals("script"))
        {
            inScript = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        super.characters(ch, start, length);
        if (inScript)
        {
            String chars = new String(ch, 0, length).toLowerCase();
            StringReader sr = new StringReader(chars);
            BufferedReader br = new BufferedReader(sr);
            try
            {
                while (br.ready())
                {
                    String line = br.readLine();
                    if (line == null)
                    {
                        break;
                    }
                    if (line.contains("total_pages"))
                    {
                        String pgs = line.substring(line.indexOf('=') + 1, line.length() - 1);
                        int pages = Integer.parseInt(pgs);
                        for (int i = 1; i <= pages; i++)
                        {
                            links.add(url + i + ".html");
                        }
                    }
                }
            }
            catch (IOException ex)
            {
                Logger.getLogger(PageGetter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


}
