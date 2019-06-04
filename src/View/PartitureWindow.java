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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import src.Component.HighLights;
import src.Controller.CanvasResizedListener;
import src.Model.GraphicInstance;
import src.Model.GraphicInstanceGroup;
import src.Component.ImageCanvas;

/**
 *
 * @author  Riquito
 */
public class PartitureWindow extends javax.swing.JFrame {
    private HighLights marks;
    private GraphicInstanceGroup graphicGroup;
    private ImageCanvas mainCanvas;
    private BufferedImage currentImage = null;
    private BufferedImage nextImage = null;
    private GraphicInstance currentGraphicInstance;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane jLayeredPane2;
    // End of variables declaration//GEN-END:variables
    
    
    /**
     * Creates new form PartitureWindow
     */
    public PartitureWindow(HighLights marks, GraphicInstanceGroup group) {
        initComponents();
        
        this.marks = marks;
        this.graphicGroup = group;
        
        this.mainCanvas = new ImageCanvas();
        this.jLayeredPane2.add(this.mainCanvas,0);
        
        this.marks.setBackgroundPanel(this.jLayeredPane2);
        
        final HighLights cpMarks = this.marks;
        this.mainCanvas.addCanvasResizeEventListener(new CanvasResizedListener(){
            public void on_canvas_resized(int x_offset, int y_offset, double ratio) {
                cpMarks.setXAdjust(x_offset);
                cpMarks.setYAdjust(y_offset);
                cpMarks.setScaling(ratio);
            }
        });
    }
    
    public ImageCanvas getCanvas(){
        return this.mainCanvas;
    }
    
    public void resetPrefetching() {
        this.nextImage = null;
    }
    
    public GraphicInstanceGroup getGroup() {
		return graphicGroup;
	}
    
    public HighLights getMarks() {
    	return marks;
    }
    
    /**
     *legge un'immagine da un filepath
     *@imgPath è una path relativa alla directory Resources
     */
    private BufferedImage readImage(String imgPath) {
        BufferedImage image = null;
        
        try {
            image = ImageIO.read(new File(imgPath));
        }
        catch (IOException e) {
            System.err.println("Errore nel caricamento dell'immagine di uno spartito");
            e.printStackTrace();
        }
        return image;
    }
    
    
    
    private void prefetchNextPage(GraphicInstance instance) {
    	if (instance != null) {
            BufferedImage image = readImage(instance.getImagePath());
            this.nextImage = image;
            //System.out.println("Prefetch di "+gi.getImagePath());
    	}
    }
    
    
    /*
     load the first image in the MXData group
     */
    public void loadFirstPage(){
        this.loadPage(this.graphicGroup.getInstances().get(0));
    }
    
    /*
     redraw the image, checking for window dimension
    */
    private void refreshCanvas(){
        this.mainCanvas.setBounds(0, 0, this.jLayeredPane2.getWidth(), this.jLayeredPane2.getHeight());
        this.mainCanvas.setImage(this.currentImage);
    }
    
    /*
     load a partiture page
     */
    private void loadPage(GraphicInstance instance) {
        this.setCurrentGraphicInstance(instance);
        
        if (this.nextImage == null) {
            this.currentImage = this.readImage(instance.getImagePath());
    	} else {	
            this.currentImage = this.nextImage;
            this.nextImage = null;
    	}

		this.refreshCanvas();
	    
	    this.marks.hideAllLabel();
	    
	   	//fare delayed fetch della prossima se esistente
		java.util.Timer delayedPrefetch = new java.util.Timer(); 
    
		final GraphicInstance next = graphicGroup.getNextInstance();
		
		delayedPrefetch.schedule(new java.util.TimerTask() { 
			public void run() {
				prefetchNextPage(next);
			}
		} ,800);
    }
    
    /*
     load the next image in the MXData group, if any
     */
    public void loadNextPage() {
        if (this.graphicGroup.getNextInstance() != null) {
            this.loadPage(this.graphicGroup.getNextInstance());
        }
    }
    
    /*
     load the previous image in the MXData group, if any
     */
    public void loadPrevPage() {
        if (this.graphicGroup.getPreviousInstance() != null) {
            this.loadPage(this.graphicGroup.getPreviousInstance());
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

	public GraphicInstance getCurrentGraphicInstance() {
		return currentGraphicInstance;
	}

	public void setCurrentGraphicInstance(GraphicInstance currentGraphicInstance) {
		this.currentGraphicInstance = currentGraphicInstance;
	}
}
