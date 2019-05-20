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



package src.View;

import java.awt.Graphics;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import src.Component.HighLights;
import src.Model.MXData;


// Declare the event. It must extend EventObject.
class CanvasResizedEvent extends java.util.EventObject {
    public CanvasResizedEvent(Object source) {
        super(source);
    }
}

// Declare the listener class. It must extend EventListener.
interface CanvasResizedListener extends java.util.EventListener {
    public void on_canvas_resized(int x_offset,int y_offset,double ratio);
}


class MyCanvas extends JPanel {
    private BufferedImage img = null;
    private int imgWidth, imgHeight;
    
    protected int x_offset, y_offset;
    protected double ratio;
    
    public MyCanvas() {
        super();
    }
    
    // Create the listener list
    protected javax.swing.event.EventListenerList listenerList =
        new javax.swing.event.EventListenerList();
    
    // This methods allows classes to register for MyEvents
    public void addMyEventListener(CanvasResizedListener listener) {
        listenerList.add(CanvasResizedListener.class, listener);
    }
    
    // This methods allows classes to unregister for MyEvents
    public void removeMyEventListener(CanvasResizedListener listener) {
        listenerList.remove(CanvasResizedListener.class, listener);
    }
    
    public void paintComponent(Graphics g) {
        int mainWidth=this.getWidth();
        int mainHeight=this.getHeight();
        BufferedImage tmpImg;
        
        int option=0;
        
        //set a background color
        g.setColor(java.awt.Color.BLACK);
        g.fillRect(0, 0, mainWidth,mainHeight);
        
        
        if (img != null) {
            int x,y;
            double tmpRatio;
            
            /*
             * ### spiegazione per il caso larghezza>altezza ###
             * 
             * se l'immagine è più larga che alta, la ridimensioniamo in modo
             * che la sua larghezza sia pari a quella della finestra.
             * se tuttavia la finestra risulta essere troppo bassa per contenere
             * la nuova altezza dell'immagine, allora ridimensioniamo in base
             *  alla larghezza.
             * 
             */
            if (imgWidth>imgHeight) {
                option=0;
                tmpRatio=new Float(mainWidth)/this.imgWidth;
                if (this.imgHeight*tmpRatio>mainHeight) {
                    option=1;
                }
            }
            else {
                option=1;
                tmpRatio=new Float(mainHeight)/this.imgHeight;
                if (this.imgWidth*tmpRatio>mainWidth) {
                    option=0;
                }
            }
            
            if (option==0) {
                tmpRatio=new Float(mainWidth)/this.imgWidth;
                System.out.println("ratio is "+tmpRatio);
                tmpImg=this.scaleImage(img, tmpRatio);
                x=0;
                y=new Double((mainHeight-tmpRatio*this.imgHeight)/2.0).intValue();
            }
            else {
                tmpRatio=new Float(mainHeight)/this.imgHeight;
                System.out.println("ratio is "+tmpRatio);
                y=0;
                x=new Double((mainWidth-tmpRatio*this.imgWidth)/2.0).intValue();
                
            }
            
           
            tmpImg=this.scaleImage(img, tmpRatio);
            g.drawImage(tmpImg, x, y, null);
            
            if (tmpRatio!=this.ratio) {
                this.ratio=tmpRatio;
                this.x_offset=x;
                this.y_offset=y;
                
                System.out.println("painting: x_off, y_off: "+x+" "+y);
                
                Object[] listeners = listenerList.getListenerList();
                // Each listener occupies two elements - the first is the listener class
                // and the second is the listener instance
                for (int i=0; i<listeners.length; i+=2) {
                    if (listeners[i]==CanvasResizedListener.class) {
                        ((CanvasResizedListener)listeners[i+1]).on_canvas_resized(
                                this.x_offset,this.y_offset,this.ratio
                        );
                    }
                }
            }
                
            
        }
    }
    
    private BufferedImage scaleImage(BufferedImage img, double scale) {
        if (scale<0.0) scale=0.0;
        AffineTransformOp op = new AffineTransformOp
          (AffineTransform.getScaleInstance(scale, scale), null);
        return op.filter(img, null);
        
    }
    
    protected void setImage(BufferedImage img){
        this.img=img;
        this.imgWidth=img.getWidth();
        this.imgHeight=img.getHeight();
        this.repaint();
    }
}

/**
 *
 * @author  Riquito
 */
public class PartitureWindow extends javax.swing.JFrame {
    public HighLights marks;
    public MXData.GraphicInstanceGroup group;
    
    private MyCanvas mainCanvas;
    
    private BufferedImage currentImage = null;
    private BufferedImage nextImage = null;
    
    public MXData.GraphicInstanceGroup.GraphicInstance currentGraphicInstance;
    
    /**
     * Creates new form PartitureWindow
     */
    public PartitureWindow(HighLights marks,MXData.GraphicInstanceGroup group) {
        initComponents();
        
        this.marks=marks;
        this.group=group;
        
        this.mainCanvas=new MyCanvas();
        this.jLayeredPane2.add(this.mainCanvas,0);
        
        this.marks.setBackgroundPanel(this.jLayeredPane2);
        
        final HighLights cpMarks=this.marks;
        this.mainCanvas.addMyEventListener(new CanvasResizedListener(){
            public void on_canvas_resized(int x_offset,int y_offset,double ratio) {
                cpMarks.xAdjust=x_offset;
                cpMarks.yAdjust=y_offset;
                cpMarks.scaling=ratio;
            }
        });
    }
    
    public MyCanvas getCanvas(){
        return this.mainCanvas;
    }
    
    public void resetPrefetching() {
        this.nextImage=null;
    }
    
    
    /**
     *legge un'immagine da un filepath
     *@imgPath è una path relativa alla directory Resources
     */
    private BufferedImage readImage(String imgPath){
        BufferedImage img=null;
        
        try {
            img=ImageIO.read(new File(imgPath));
        }
        catch (IOException e) {
            System.err.println("Errore nel caricamento dell'immagine di uno spartito");
            e.printStackTrace();
        }
        return img;
    }
    
    
    
    private void prefetchNextPage(MXData.GraphicInstanceGroup.GraphicInstance gi)     {
    	if (gi!=null) {
            BufferedImage i = readImage(gi.getImagePath());
            this.nextImage = i;
            //System.out.println("Prefetch di "+gi.getImagePath());
    	}
    }	
    
    
    /*
     load the first image in the MXData group
     */
    public void loadFirstPage(){
        this.loadPage(this.group.instances.get(0));
    }
    
    /*
     redraw the image, checking for window dimension
    */
    private void refreshCanvas(){
        this.mainCanvas.setBounds(0,0,this.jLayeredPane2.getWidth(),this.jLayeredPane2.getHeight());
        this.mainCanvas.setImage(this.currentImage);
    }
    
    /*
     load a partiture page
     */
    private void loadPage(MXData.GraphicInstanceGroup.GraphicInstance gInstance){
        this.currentGraphicInstance=gInstance;
        
        if (this.nextImage == null) {
            this.currentImage= this.readImage(gInstance.getImagePath());
    	}
    	else {	
            this.currentImage = this.nextImage;
            this.nextImage = null;
    	}
        
	this.refreshCanvas();
        
        this.marks.hideAllLabel();
        
   	//fare delayed fetch della prossima se esistente
	java.util.Timer delayedPrefetch = new java.util.Timer(); 
        final MXData.GraphicInstanceGroup.GraphicInstance next=gInstance.getNext();
	delayedPrefetch.schedule(new java.util.TimerTask() { public void run() {prefetchNextPage(next);}} ,800);
    }
    
    /*
     load the next image in the MXData group, if any
     */
    public void loadNextPage(){
        if(this.currentGraphicInstance.getNext()!=null) {
            this.loadPage(this.currentGraphicInstance.getNext());
        }
    }
    
    /*
     load the previous image in the MXData group, if any
     */
    public void loadPrevPage(){
        if(this.currentGraphicInstance.getPrev()!=null) {
            this.loadPage(this.currentGraphicInstance.getPrev());
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane2 = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLayeredPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 803, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jLayeredPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        this.refreshCanvas();
    }//GEN-LAST:event_formComponentResized
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane jLayeredPane2;
    // End of variables declaration//GEN-END:variables
    
}
