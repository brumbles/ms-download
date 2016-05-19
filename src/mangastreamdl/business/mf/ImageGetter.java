/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.business.mf;

import java.io.BufferedReader;
import mangastreamdl.business.ImageProperties;
import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import mangastreamdl.business.Downloader;
import org.xml.sax.InputSource;

/**
 * @author Grigor Riskov - grigor@riskov.cz
 */
public class ImageGetter extends DefaultHandler
{

    String imgurl;
    private boolean first = true;


    ImageProperties getImageLink(String url) throws IOException, SAXException, URISyntaxException
    {
        Parser parser = new Parser();
        parser.setContentHandler(this);
        parser.setErrorHandler(this);
        InputSource is = new InputSource(Downloader.getInputStreamForURL(url.replace(' ', '+')));
        parser.parse(is);
        ImageProperties ret = new ImageProperties();
        ret.addImage("0", 0, -1, 0, 0, 0);
        ret.setLocation("0", imgurl);
        return ret;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, qName, attributes);
        if (first && localName.equals("img"))
        {
            first = false;
            imgurl = attributes.getValue("src");
        }
    }


}
