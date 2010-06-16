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
 * HighLights.java
 *
 * This class is meant to organize and move
 *  the various higlights that are shown on an image
 *
 */

package mainFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 *
 * @author Riquito
 */
public class HighLights {
    
    private class SpinedLabel extends JLabel{
        public Voice voice; //stored for possible future uses
        
        public SpinedLabel(Voice voice){
            super();
            this.voice=voice;
            
            this.setOpaque(true);
            this.setBackground(voice.rgba);
            this.setBorder(BorderFactory.createLineBorder(Color.black));
        }
    }
    
    public double scaling=1.0;
    public int xAdjust=0,yAdjust=0;
    
    private int layerIndex=1; //index 0 is the background partiture image
    
    private JLayeredPane bgPanel=null; // backrounds panel
    private Hashtable<String,SpinedLabel> spinedLabels;
    
    
    /** Creates a new instance of HighLights */
    public HighLights(){
        this.spinedLabels=new Hashtable<String,SpinedLabel>();
    }
    
    public void setBackgroundPanel(JLayeredPane panel){
        this.bgPanel=panel;
    }
    
    public void hideAllLabel() {
    	
    	Coord data = new Coord(-100,-100,1,1);
    	Enumeration keys = this.spinedLabels.keys();
    	while ( keys.hasMoreElements() ) {
    	   String key = (String)keys.nextElement();
    	   SpinedLabel singleLabel = this.spinedLabels.get( key );
    	   singleLabel.setBounds(data.x, data.y, (int)data.getWidth(), (int)data.getHeight());
           singleLabel.setPreferredSize(new Dimension((int)data.getWidth(),(int)data.getHeight()));
           //setlayer is needed to show again the label (setvisible, validate, updateUI don't work)
           
           this.bgPanel.setLayer(singleLabel,this.layerIndex);
    	} // end while
    	
    }
    /**
     * Aggiunge un rettangolo trasparente su di una nota
     */
    public void appendLabel(Coord data, Voice voice){
        SpinedLabel singleLabel=null;
        
        if (data==null || voice==null) {
            //errore. capire perché non sono stati trovati
            System.err.println("errore in appendLabel");
            System.err.println("  ->data: "+data+" -> voice"+voice);
            return;
        }
        
        singleLabel=this.spinedLabels.get(voice.name);
        if (singleLabel==null) {
            //first time we listen to this voice
            singleLabel=new SpinedLabel(voice);
            this.spinedLabels.put(voice.name,singleLabel);
            
            this.bgPanel.add(singleLabel,this.layerIndex);
        }
        
        data=data.scale(this.scaling);
        singleLabel.setBounds(data.x+this.xAdjust, data.y+this.yAdjust, (int)data.getWidth(), (int)data.getHeight());
        singleLabel.setPreferredSize(new Dimension((int)data.getWidth(),(int)data.getHeight()));
        //setlayer is needed to show again the label (setvisible, validate, updateUI don't work)
        
        this.bgPanel.setLayer(singleLabel,this.layerIndex);
    }
    
}
