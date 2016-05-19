/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.business;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * @author sirDarts
 */
public interface MangaParser
{

    boolean checkManga(String name) throws IOException, SAXException, URISyntaxException;

    List<String> getAllManga() throws IOException, SAXException, URISyntaxException;

    ImageProperties getImageLink(String url) throws IOException, SAXException, URISyntaxException;

    String getLastChapter(String name) throws IOException, SAXException, URISyntaxException;

    String getMangaLocation(String name, String chapter) throws IOException, SAXException, URISyntaxException;

    List<String> getPageLinks(String url) throws IOException, SAXException, URISyntaxException;

    List<Pair<Manga, String>> getLastChapters(Map<String, Manga> mlist) throws IOException, SAXException, URISyntaxException;

    Map<String, String> getAllChapters(Manga m) throws IOException, SAXException, URISyntaxException;

}
