/**
 * jMXPlayer, a GUI to IEEE PAR1599 (MX) data
 * Copyright © 2010 Riccardo Attilio Galli <riccardo@sideralis.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * ImagePane.java
 *
 * Created on February 2, 2003, 9:51 PM
 */

package mainFrame;

import java.awt.*;
import java.awt.image.*;

import java.io.*;
import java.util.Iterator;

import javax.imageio.*;
import javax.imageio.stream.*;
import javax.imageio.event.*;

import javax.swing.*;
import javax.swing.event.*;


/**
 *
 * @author  tonny
 */
public class ImagePane extends JPanel implements IIOReadUpdateListener, IIOReadProgressListener {
    /** an image for this component */
    protected BufferedImage image;
    
    /** the scale with for this component. */
    protected double scaleWidth = 1.0;
    
    /** the scaleHeight for this component. */
    protected double scaleHeight = 1.0;
    
    protected Dimension imageSize;
    
    protected ImageReader imageReader;
    
    protected Thread loadThread;
    
    protected EventListenerList listenerList;
    
    protected double percentComplete;
    
    protected double repaintProgress = 25;
    
    
    /** Creates a new instance of ImagePane */
    public ImagePane() {
        listenerList = new EventListenerList();
    }
    
    /** invoked to draw this component
     * @param g the Graphics context in which to paint
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);    // paint background
        
        if (image == null) {
            return;
        }
        
        Graphics2D g2 = (Graphics2D) g;
        
        Insets insets = getInsets();
        
        double areaWidth = getWidth() - insets.left - insets.right;
        double areaHeight = getHeight()  - insets.top - insets.bottom;
        
        double x = insets.left;
        double y = insets.top;
        double w = image.getWidth() * scaleWidth;
        double h = image.getHeight() * scaleHeight;
        
        double tmpX = (areaWidth - w)/2;
        double tmpY = (areaHeight - h)/2;
        if (tmpX > x) {
            x = tmpX;
        }
        
        if (tmpY > y) {
            y =tmpY;
        }
        
        //System.out.println("x:" + x + " y:" + y);
        
        g2.drawImage(image,(int)x,(int)y,(int)w,(int)h,this);
    }
    
    /** set the image for this component to display
     * if the value of image is null or empty string, nothing is displayed.
     * @param image the image to display
     */
    public void setImage(BufferedImage image) {
        this.image = image;
        if (image != null) {
            imageSize.width = image.getWidth();
            imageSize.height = image.getHeight();
        }
    }
    
    /** Return Image 
     * @return Image 
     */
    public BufferedImage getImage() {
        return this.image;
    }
    
    /** set the image rescale
     * @param scaleWidth the scaleWidth for the image.
     * @param scaleHeight the scaleHeight for the image.
     */
    public void setImageRescale(double scaleWidth, double scaleHeight) {
        this.scaleWidth = scaleWidth;
        this.scaleHeight = scaleHeight;
    }
    
    /** Set the repaint progress increment value between 0 to 100.
     *The default value is 25
     *@param increment The repaint progress increment
     *@see #getProgressRepaint
     */
    public void setProgressRepaint(double increment) {
        if (increment < 0 || increment > 100) {
            throw new IllegalArgumentException("value must be between 0 - 100");
        }
        this.repaintProgress = increment;
    }
    
    /** Return the repaint progress increment
     * @return repaint progress increment
     * @see #setProgressRepaint(double)
     */
    public double getProgessRepaint() {
        return this.repaintProgress;
    }
    
    /**
     *@see javax.imageio.ImageReader#addIIOReadProgressListener(IIOReadProgressListener)
     */
    public void addIIOReadProgressListener(IIOReadProgressListener listener) {
        listenerList.add(IIOReadProgressListener.class, listener);
    }
    
    /**
     *@see javax.imageio.ImageReader#removeIIOReadProgressListener(IIOReadProgressListener)
     */
    public void removeIIOReadProgressListener(IIOReadProgressListener listener) {
        listenerList.remove(IIOReadProgressListener.class, listener);
    }
    
    /**
     *@see javax.imageio.ImageReader#addIIOReadUpdateListener(IIOReadUpdateListener)
     */
    public void addIIOReadUpdateListener(IIOReadUpdateListener listener) {
        listenerList.add(IIOReadUpdateListener.class, listener);
    }
    
    /**
     *@see javax.imageio.ImageReader#removeIIOReadUpdateListener(IIOReadUpdateListener)
     */
    public void removeIIOReadUpdateListener(IIOReadUpdateListener listener) {
        listenerList.remove(IIOReadUpdateListener.class, listener);
    }
    
    /**
     *@see javax.imageio.ImageReader#addIIOReadWarningListener(IIOReadWarningListener)
     */
    public void addIIOReadWarningListener(IIOReadWarningListener listener) {
        listenerList.add(IIOReadWarningListener.class, listener);
    }
    
    /**
     *@see javax.imageio.ImageReader#removeIIOReadWarningListener(IIOReadWarningListener)
     */
    public void removeIIOReadWarningListener(IIOReadWarningListener listener) {
        listenerList.remove(IIOReadWarningListener.class, listener);
    }
    
    /** Display the File Image
     * @param file <code>File</code>
     */
    public void view(File file) {
        /*if ( (file==null) || (!ImageUtilities.isFileImage(file)) ) {
            setImage(null);
            repaint();
            return;
        }*/
        startFileImageLoading(file);
    }
    
    private void startFileImageLoading(File file) {
        
        if (imageReader != null) {
            imageReader.abort();
        }
        
        if (loadThread != null) {
            while (loadThread.isAlive())  {
                try {
                    //Thread.currentThread().wait();
                    loadThread.join();
                } catch (InterruptedException e) {}
            }
        }
        
        try {
            image = null;
            imageReader = getImageReader(file);
            Dimension imageDim = new Dimension(imageReader.getWidth(0), imageReader.getHeight(0));
            imageSize = imageDim;
            
            Rectangle rect = SwingUtilities.calculateInnerArea(this,null);
            scaleWidth=rect.width;
            scaleHeight=rect.height;
            
        } catch (IOException e) {}
        
        
        Runnable r = new Runnable() {
            public void run() {
                readImage();
                if (imageReader != null) {
                    imageReader.dispose();
                }
            }
        };
        
        loadThread = new Thread(r);
        loadThread.start();
    }
    
    private void readImage() {
        try {
            imageReader.addIIOReadUpdateListener(this);
            imageReader.addIIOReadProgressListener(this);
            
            Object[] listeners = listenerList.getListenerList();
            for (int i = listeners.length-2; i>=0; i-=2) {
                if (listeners[i]==IIOReadUpdateListener.class) {
                    imageReader.addIIOReadUpdateListener((IIOReadUpdateListener)listeners[i+1]);
                } else if (listeners[i]==IIOReadProgressListener.class) {
                    imageReader.addIIOReadProgressListener((IIOReadProgressListener)listeners[i+1]);
                } else if (listeners[i]==IIOReadWarningListener.class) {
                    imageReader.addIIOReadWarningListener((IIOReadWarningListener)listeners[i+1]);
                }
            }
            
            image = imageReader.read(0);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (imageReader.getInput() instanceof ImageInputStream) {
                try {
                    ((ImageInputStream)imageReader.getInput()).close();
                }   catch (Exception e) {}
            }
            imageReader.removeIIOReadUpdateListener(this);
            imageReader.removeIIOReadProgressListener(this);
            
            Object[] listeners = listenerList.getListenerList();
            for (int i = listeners.length-2; i>=0; i-=2) {
                if (listeners[i]==IIOReadUpdateListener.class) {
                    imageReader.removeIIOReadUpdateListener((IIOReadUpdateListener)listeners[i+1]);
                } else if (listeners[i]==IIOReadProgressListener.class) {
                    imageReader.removeIIOReadProgressListener((IIOReadProgressListener)listeners[i+1]);
                } else if (listeners[i]==IIOReadWarningListener.class) {
                    imageReader.removeIIOReadWarningListener((IIOReadWarningListener)listeners[i+1]);
                }
            }
            
            imageReader.dispose();
            imageReader = null;
        }
    }
    
    /** return the <code>ImageReader</code> use to read the specified <code>file</code>
     * @return the <code>ImageReader</code> or null if no suitable reader
     * @throws IOException If an I/O error occurs
     */
    private ImageReader getImageReader(File file) throws IOException {
        // find suitable reader
        /*String suffix = ShellUtilities.getFileSuffix(file);
        IIOPreferences iioPrefs = IIOPreferences.getInstance();
         */
        
        Iterator<ImageReader> readerIt = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader=null;
        if (readerIt.hasNext()) {
            reader = readerIt.next();
        }
        
        return reader;
    }
    
    /** {@inheritDoc} */
    public void imageComplete(ImageReader source) {
        percentComplete = 100;
        repaint();
    }
    
    /** {@inheritDoc} */
    public void imageProgress(ImageReader source, float percentageDone) {
        if (((percentageDone - percentComplete) >= repaintProgress)  ) {
            //paintImmediately(0,0,getWidth(),getHeight());
            repaint();
            percentComplete = percentageDone;
        }
    }
    
    /** {@inheritDoc} */
    public void imageStarted(ImageReader source, int imageIndex) {
        percentComplete = 0;
    }
    
    /** {@inheritDoc} */
    public void imageUpdate(ImageReader source, BufferedImage theImage, int minX, int minY, int width, int height, int periodX, int periodY, int[] bands) {
        //repaint();
    }
    
    /** {@inheritDoc} */
    public void passComplete(ImageReader source, BufferedImage theImage) {
    }
    
    /** {@inheritDoc} */
    public void passStarted(ImageReader source, BufferedImage theImage, int pass, int minPass, int maxPass, int minX, int minY, int periodX, int periodY, int[] bands) {
        if (pass == 0) {
            this.image = theImage;
            repaint();
        }
    }
    
    /** {@inheritDoc} */
    public void readAborted(ImageReader source) {
    }
    
    /** {@inheritDoc} */
    public void sequenceComplete(ImageReader source) {
    }
    
    /** {@inheritDoc} */
    public void sequenceStarted(ImageReader source, int minIndex) {
    }
    
    /** {@inheritDoc} */
    public void thumbnailComplete(ImageReader source) {
    }
    
    /** {@inheritDoc} */
    public void thumbnailPassComplete(ImageReader source, BufferedImage theThumbnail) {
    }
    
    /** {@inheritDoc} */
    public void thumbnailPassStarted(ImageReader source, BufferedImage theThumbnail, int pass, int minPass, int maxPass, int minX, int minY, int periodX, int periodY, int[] bands) {
    }
    
    /** {@inheritDoc} */
    public void thumbnailProgress(ImageReader source, float percentageDone) {
    }
    
    /** {@inheritDoc} */
    public void thumbnailStarted(ImageReader source, int imageIndex, int thumbnailIndex) {
    }
    
    /** {@inheritDoc} */
    public void thumbnailUpdate(ImageReader source, BufferedImage theThumbnail, int minX, int minY, int width, int height, int periodX, int periodY, int[] bands) {
    }
    
}
