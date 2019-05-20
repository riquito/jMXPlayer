package src.Component;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import src.Controller.CanvasResizedListener;

public class MyCanvas extends JPanel {
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
    
    public void setImage(BufferedImage img) {
        this.img=img;
        this.imgWidth=img.getWidth();
        this.imgHeight=img.getHeight();
        this.repaint();
    }
}