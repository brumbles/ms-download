/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.persistence;

import mangastreamdl.business.Manga;
import mangastreamdl.business.Sites;
import mangastreamdl.gui.Gui;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton
 * Data access object for saving in XML format.
 *
 * @author Grigor Riskov - riskogri@fit.cvut.cz
 */
class XMLDAO implements DAO
{

    private static XMLDAO instance;

    protected static synchronized XMLDAO getInstance()
    {
        if (instance == null)
        {
            instance = new XMLDAO();
        }
        return instance;
    }

    private XMLDAO()
    {
    }

    @Override
    public void load(Gui aThis, File f)
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = dbf.newDocumentBuilder();
            Document load = parser.parse(f);
            String dir = getDirectory(load);
            Set<Manga> manga = getMangas(load);
            aThis.setCurrentdir(new File(dir));
            aThis.setMangalist(manga);
            aThis.setChck(getChck(load));
            aThis.setZip(getZip(load));
            aThis.setDel(getDel(load));
            aThis.setFormat(getFormat(load));
        }
        catch (SAXException | IOException | ParserConfigurationException ex)
        {
            Logger.getLogger(XMLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getDirectory(Document load)
    {
        return load.getDocumentElement().getAttribute("dir");
    }

    private boolean getChck(Document load)
    {
        String s = load.getDocumentElement().getAttribute("chck");
        return s.equals("true");
    }

    private boolean getZip(Document load)
    {
        String s = load.getDocumentElement().getAttribute("zip");
        return s != null && s.equals("true");
    }

    private boolean getDel(Document load)
    {
        String s = load.getDocumentElement().getAttribute("del");
        return s == null || s.equals("true");
    }

    private String getFormat(Document load)
    {
        String s = load.getDocumentElement().getAttribute("format");
        if (s == null)
        {
            return "Chapter%c";
        }
        return s;
    }

    private Set<Manga> getMangas(Document load)
    {
        NodeList list = load.getElementsByTagName("manga");
        Set<Manga> ret = new TreeSet<>();
        for (int i = 0; i < list.getLength(); i++)
        {
            ret.add(getManga(list.item(i)));
        }
        return ret;
    }

    private Manga getManga(Node n)
    {
        NamedNodeMap atmap = n.getAttributes();
        String name = atmap.getNamedItem("name").getTextContent();
        String dir = atmap.getNamedItem("directory").getTextContent();

        String chap;
        Node chp = atmap.getNamedItem("chapter");
        if (chp != null)
        {
            chap = chp.getTextContent();
        }
        else
        {
            chap = null;
        }
        String chapter;
        if (chap != null)
        {
            chapter = chap;
        }
        else
        {
            chapter = "0";
        }

        Sites site = Sites.MS;
        Node st = atmap.getNamedItem("site");
        if (st != null)
        {
            switch (st.getTextContent())
            {
                case "MS":
                    site = Sites.MS;
                    break;
                case "MF":
                    site = Sites.MF;
                    break;
            }
        }

        Manga ret = new Manga(name, dir, chapter);
        ret.setSite(site);
        return ret;
    }

    @Override
    public void save(Gui aThis, File options)
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = factory.newDocumentBuilder();
            Document save = parser.newDocument();
            save.appendChild(CreateRootElement(aThis, save));
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(save);
            StreamResult result = new StreamResult(options);
            transformer.transform(source, result);
        }
        catch (TransformerException | ParserConfigurationException ex)
        {
            Logger.getLogger(XMLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Element CreateRootElement(Gui gui, Document doc)
    {
        Element root = doc.createElement("options");
        root.setAttribute("dir", gui.getCurrentdir().toString());
        root.setAttribute("chck", String.valueOf(gui.isChck()));
        root.setAttribute("zip", String.valueOf(gui.isZip()));
        root.setAttribute("del", String.valueOf(gui.isDel()));
        root.setAttribute("format", gui.getFormat());
        Set<Manga> set = gui.getMangalist();
        for (Manga manga : set)
        {
            root.appendChild(CreateMangaElement(doc, manga));
        }
        return root;
    }

    private Element CreateMangaElement(Document doc, Manga manga)
    {
        Element m = doc.createElement("manga");
        m.setAttribute("name", manga.getName());
        m.setAttribute("directory", manga.getDirectory());
        m.setAttribute("chapter", String.valueOf(manga.getChapter()));
        m.setAttribute("site", manga.getSite().toString());
        return m;
    }

}
