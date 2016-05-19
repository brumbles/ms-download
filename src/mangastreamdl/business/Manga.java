/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mangastreamdl.business;

/**
 * @author Grigor Riskov - riskogri@fit.cvut.cz
 */
public class Manga implements Comparable<Manga>
{

    private String name;
    private String directory;
    private String chapter;
    private Sites site = Sites.MS;

    public Manga(String name, String directory, String chapter)
    {
        this.name = name;
        this.directory = directory;
        this.chapter = chapter;
    }

    public String getDirectory()
    {
        return directory;
    }

    public void setDirectory(String directory)
    {
        this.directory = directory;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public int compareTo(Manga o)
    {
        return name.compareTo(o.getName());
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Manga && this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 61 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    public String getChapter()
    {
        return chapter;
    }

    public void setChapter(String chapter)
    {
        this.chapter = chapter;
    }

    public Sites getSite()
    {
        return site;
    }

    public void setSite(Sites site)
    {
        this.site = site;
    }

}
