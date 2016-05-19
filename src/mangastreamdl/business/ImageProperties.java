/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mangastreamdl.business;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * @author sirDarts
 */
public class ImageProperties
{

    private int width;
    private int           height  = 0;
    private List<Picture> images  = new ArrayList<>();
    private boolean       changed = false;
    BufferedImage i = null;
    String type;
    private float fullSize = 1;

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void addImage(String ID, int width, int height, int top, int left, int z_index)
    {
        images.add(new Picture(ID, width, height, top, left, z_index));
        if (left == 0)
        {
            this.height += height;
        }
        changed = true;
        fullSize += height * width;
    }

    public void addImageKnownHeight(String ID, int width, int height, int top, int left, int z_index)
    {
        images.add(new Picture(ID, width, height, top, left, z_index));
        changed = true;
        fullSize += height * width;
    }

    public void setData(String ID, Image image)
    {
        for (Picture p : images)
        {
            if (p.ID.equals(ID))
            {
                p.setData(image);
            }
        }
    }

    public void setLocation(String ID, String location)
    {
        for (Picture p : images)
        {
            if (p.ID.equals(ID))
            {
                p.setLocation(location);
            }
        }
        if (type == null)
        {
            type = location.substring(location.length() - 4);
        }
        else if (!type.equalsIgnoreCase(".jpg") || type.equals(""))
        {
            type = location.substring(location.length() - 4);
        }
    }

    public String getLocation(String ID)
    {
        for (Picture p : images)
        {
            if (p.ID.equals(ID))
            {
                return p.getLocation();
            }
        }
        return "";
    }

    public Collection<String> getIDs()
    {
        Set<String> s = new TreeSet<>();
        for (Picture p : images)
        {
            s.add(p.ID);
        }
        return s;
    }

    public String getFileType()
    {
        return type;
    }

    public BufferedImage getFullImage()
    {
        if (i == null || !changed)
        {
            Collections.sort(images);
            if (height == -1)
            { //old way of saving images
                Picture tmp = images.get(0);
                height = tmp.getData().getHeight(null);
                tmp.height = height;
                if (width == 0)
                {
                    width = tmp.getData().getWidth(null);
                    tmp.width = width;
                }
            }
            if (getFileType().equals(".png"))
            {
                i = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            }
            else
            {
                i = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            }
            Graphics g = i.getGraphics();
            for (Picture p : images)
            {
                g.drawImage(p.getData(), p.left, p.top, p.width, p.height, null);
            }
        }
        return i;
    }

    public int getWidth()
    {
        return width;
    }

    public float getSizePercentage(String ID)
    {
        float size = getSize(ID);
        if (size == -1)
        {
            return 1F;
        }
        return size / fullSize;
    }

    private int getSize(String ID)
    {
        for (Picture picture : images)
        {
            if (picture.ID.equals(ID))
            {
                return picture.getSize();
            }
        }
        return -1;
    }

    private class Picture implements Comparable<Picture>
    {

        private String ID;
        private int    width, height, top, left, z_index;
        private Image  data;
        private String location;

        private Picture(int width, int height, int top, int left, int z_index)
        {
            this.height = height;
            this.left = left;
            this.top = top;
            this.width = width;
            this.z_index = z_index;
        }

        private Picture(String ID, int width, int height, int top, int left, int z_index)
        {
            this(width, height, top, left, z_index);
            this.ID = ID;
        }

        public String getLocation()
        {
            return location;
        }

        public Image getData()
        {
            return data;
        }

        public void setData(Image data)
        {
            this.data = data;
        }

        private void setLocation(String location)
        {
            this.location = location;
        }

        @Override
        public int compareTo(Picture o)
        {
            if (z_index > o.z_index)
            {
                return 1;
            }
            else if (z_index < o.z_index)
            {
                return -1;
            }
            return 0;
        }

        private int getSize()
        {
            if (height == -1)
            {
                return -1;
            }
            return height * width;
        }
    }
}
