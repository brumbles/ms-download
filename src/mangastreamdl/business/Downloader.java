/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.business;

import mangastreamdl.gui.Gui;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.xml.sax.InputSource;

/**
 * @author Grigor Riskov - riskogri@fit.cvut.cz
 */
public final class Downloader implements Runnable
{


    public static final Logger LOGGER = Logger.getLogger(Downloader.class.getName());

    public static InputStream getInputStreamForURL(String url) throws URISyntaxException, IOException {
        URI uri = new URI(url);
        HttpURLConnection huc = (HttpURLConnection) uri.toURL().openConnection();
        huc.setRequestMethod("GET");
        huc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.99 Safari/537.36 Vivaldi/1.0.288.3");
        InputStream is =  huc.getInputStream();
        if ("gzip".equals(huc.getContentEncoding()))
        {
            is = new GZIPInputStream(is);
        }
        return is;
    }

    private enum State
    {
        URLLIST, LIST, SINGLE
    }

    Manga       manga;
    String      chapter;
    File        f;
    Gui         gui;
    boolean     running;
    MangaParser parser;
    private List<Pair<Manga, String>> list;
    private Map<String, String>       urlmap;
    private File                      dir;
    private State                     state;
    private boolean                   error = false;

    /**
     * Creates a new Downloader.
     *
     * @param gui     The gui to send download status to. Some similarities with the observer pattern.
     * @param manga   The manga to be downloaded.
     * @param chapter Name of the chapter to be downloaded.
     * @param f       Save directory.
     */
    public Downloader(Gui gui, Manga manga, String chapter, File f)
    {
        this.gui = gui;
        this.manga = manga;
        this.chapter = chapter;
        this.f = f;
        this.parser = MangaParserFactory.getParser(manga.getSite());
        running = true;
        state = State.SINGLE;
    }

    public Downloader(Gui gui, List<Pair<Manga, String>> l, File currentdir)
    {
        this.gui = gui;
        this.list = l;
        this.dir = currentdir;
        running = true;
        state = State.LIST;
    }

    public Downloader(Gui gui, Map<String, String> map, File currentdir, Manga m)
    {
        this.gui = gui;
        this.urlmap = map;
        this.dir = currentdir;
        this.manga = m;
        running = true;
        state = State.URLLIST;
    }

    @Override
    public void run()
    {
        switch (state)
        {
            case LIST:
                runList();
                break;
            case SINGLE:
                runSingle();
                break;
            case URLLIST:
                runUrlList();
                break;
        }
        if (!error) {
            gui.done();
        }
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    private void runSingle()
    {
        try
        {
            String url = parser.getMangaLocation(manga.getName(), chapter);
            if (url == null)
            {
                if (parser.checkManga(manga.getName()))
                {
                    JOptionPane.showMessageDialog(gui, "The chapter " + chapter + " of " + manga.getName() +
                            " is not available on mangastream.",
                            "Chapter not available.", JOptionPane.ERROR_MESSAGE);
                    error = true;
                }
                else
                {
                    JOptionPane.showMessageDialog(gui,
                            "The manga " + manga.getName() + " was not found. It was probably dropped by mangastream.",
                            "Manga not found.", JOptionPane.ERROR_MESSAGE);
                    error = true;
                }
                return;
            }
            Download(url);
        }
        catch (URISyntaxException | IOException | SAXException ex)
        {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        try
        {
            if (gui.isZip())
            {
                zip();
            }
        }
        catch (IOException ex)
        {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void runList()
    {
        for (Pair<Manga, String> pair : list)
        {
            if (!running)
            {
                return;
            }
            manga = pair.first;
            chapter = pair.second;
            this.parser = MangaParserFactory.getParser(manga.getSite());
            f = new File(dir, gui.parseFormat(manga, chapter));
            if (!f.exists())
            {
                boolean mkdirs = f.mkdirs();
                if (!mkdirs)
                {
                    gui.showError("Creating the required directory " + f.getAbsolutePath() + " failed");
                    error = true;
                    return;
                }
            }
            runSingle();
        }
    }

    private void runUrlList()
    {
        for (Entry<String, String> entry : urlmap.entrySet())
        {
            try
            {
                String url = entry.getValue();
                manga.setChapter(entry.getKey());
                chapter = manga.getChapter();

                if (!running)
                {
                    return;
                }
                f = new File(dir, gui.parseFormat(manga, manga.getChapter()));
                if (!f.exists())
                {
                    boolean mkdirs = f.mkdirs();
                    if (!mkdirs)
                    {
                        gui.showError("Creating the required directory " + f.getAbsolutePath() + "  failed");
                        error = true;
                        return;
                    }
                }

                parser = MangaParserFactory.getParser(manga.getSite());
                if (url == null)
                {
                    if (parser.checkManga(manga.getName()))
                    {
                        JOptionPane.showMessageDialog(gui, "The chapter " + chapter + " of " + manga.getName() +
                                " is not available on mangastream.",
                                "Chapter not available.", JOptionPane.ERROR_MESSAGE);
                        error = true;
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(gui,
                                "The manga " + manga.getName() +
                                        " was not found. It was probably dropped by mangastream.",
                                "Manga not found.", JOptionPane.ERROR_MESSAGE);
                        error = true;
                    }
                    return;
                }
                Download(url);
            }
            catch (IOException | SAXException | URISyntaxException ex)
            {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            try
            {
                if (gui.isZip())
                {
                    zip();
                }
            }
            catch (IOException ex)
            {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        gui.save();
    }

    private void Download(String url) throws SAXException, URISyntaxException, IOException
    {
        int page = 1;
        List<String> links = parser.getPageLinks(url);
        gui.setChapProgressBar(links.size());
        gui.nextPage(String.valueOf(page), links.size(), manga, chapter);

        for (String string : links)
        {
            if (!running)
            {
                break;
            }
            ImageProperties img = parser.getImageLink(string);
            File out = new File(
                    f.getAbsolutePath() + File.separator + "Page" + (page < 10 ? "0" : "") + page + img.getFileType());
            gui.setPgProgressBar(100);
            float percentage = 0;
            for (String ID : img.getIDs())
            {
                URI uri = new URI(img.getLocation(ID));
                HttpURLConnection huc = (HttpURLConnection) uri.toURL().openConnection();
                huc.setRequestMethod("GET");
                huc.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:19.0) Gecko/20100101 Firefox/19.0");
                try (InputStream is = huc.getInputStream())
                {
                    ImageInputStream iis = ImageIO.createImageInputStream(is);
                    Iterator<ImageReader> it = ImageIO.getImageReaders(iis);
                    ImageReader ir = it.next();
                    float sp = img.getSizePercentage(ID);
                    ir.addIIOReadProgressListener(new ProgressListener(gui, sp, percentage));
                    percentage += sp * 100;
                    ir.setInput(iis);
                    Image image = ir.read(0);
                    //Image image = ImageIO.read(is);
                    img.setData(ID, image);
                }
            }
            BufferedImage image = img.getFullImage();
            ImageOutputStream ios;
            try (FileOutputStream fos = new FileOutputStream(out))
            {
                String type = img.getFileType().substring(1);
                ios = ImageIO.createImageOutputStream(fos);
                Iterator<ImageWriter> it = ImageIO.getImageWritersBySuffix(type);
                if (!it.hasNext())
                {
                    gui.nextPage(String.valueOf(page), links.size(), manga, chapter);
                    page++;
                    gui.updateChapProgressBar(page - 1);
                    break;
                }
                ImageWriter iw = it.next();
                iw.setOutput(ios);
                iw.write(image);
                gui.nextPage(String.valueOf(page), links.size(), manga, chapter);
                page++;
                gui.updateChapProgressBar(page - 1);
            }
            ios.close();
        }
    }

    private void zip() throws IOException
    {
        int BUFFER = 2048;
        BufferedInputStream origin;
        File z = new File(f.getParent() + File.separator + f.getName() + ".zip");
        try (FileOutputStream fos = new FileOutputStream(z); ZipOutputStream zos = new ZipOutputStream(
                new BufferedOutputStream(fos)))
        {
            String files[] = f.list();
            byte data[] = new byte[BUFFER];
            for (String file : files)
            {
                try (FileInputStream fi = new FileInputStream(f + File.separator + file))
                {
                    origin = new BufferedInputStream(fi, BUFFER);
                    ZipEntry ze = new ZipEntry(file);
                    zos.putNextEntry(ze);
                    int count;
                    while ((count = origin.read(data, 0, BUFFER)) != -1)
                    {
                        zos.write(data, 0, count);
                    }
                    origin.close();
                }
            }
        }
        if (gui.isDel())
        {
            String f2[] = f.list();
            for (String aF2 : f2)
            {
                File fd = new File(f + File.separator + aF2);
                System.out.println("Deleting " + fd);
                if (!fd.delete())
                {
                    gui.showError("Failed to delete " + fd.getAbsolutePath());
                }
            }
            boolean delete = f.delete();
            if (!delete)
            {
                gui.showError("Failed to delete " + f.getAbsolutePath());
            }

        }
    }

}
