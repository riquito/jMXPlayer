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



package src.Model;

import java.awt.Color;

public class Voice{
    public String name;
    public Color rgba;
    public boolean isVisible=false;
    public boolean hasChanged=false;
    
    private static int colorIdx=0;
    
    /*Color.BLUE,
        Color.CYAN,
        Color.WHITE,
        Color.GREEN,
        Color.MAGENTA,
        Color.ORANGE,
        Color.PINK,
        Color.YELLOW,
        Color.RED,
        Color.BLUE.darker(),
        Color.CYAN.darker(),
        Color.WHITE.darker(),
        Color.GREEN.darker(),
        Color.MAGENTA.darker(),
        Color.ORANGE.darker(),
        Color.PINK.darker(),
        Color.YELLOW.darker(),
        Color.RED.darker()
        */
    
    private static Color[] colorList={
        Color.decode("#FF9900"),
        Color.decode("#FFFF00"),
        Color.decode("#FF3300"),
        Color.decode("#CCFF00"),
        Color.decode("#CC9900"),
        Color.decode("#99FF00"),
        Color.decode("#999900"),
        Color.decode("#FF3366"),
        Color.decode("#FF0099"),
        Color.decode("#FF99CC"),
        Color.decode("#9933CC"),
        Color.decode("#33FFCC"),
        Color.decode("#3333CC"),
        Color.decode("#FFCCFF"),
        Color.decode("#CC99FF"),
        Color.decode("#3399FF"),
        Color.decode("#9966FF"),
        Color.decode("#3300CC"),
        Color.decode("#FFFFCC")
    };
    
    public Voice(String voiceName){
        this.name=voiceName;
        
        //this.rgba=new Color(this.colorList[this.colorIdx].getRGB()|((122)<<24));
        
        Color tmp=Voice.colorList[Voice.colorIdx];
        this.rgba=new Color(tmp.getRed(),tmp.getGreen(),tmp.getBlue(),51);
        
        Voice.colorIdx+=1;
        if (Voice.colorIdx==Voice.colorList.length) Voice.colorIdx=0;
    }
    
    public void setVisible(boolean isVisible){
        this.isVisible=isVisible;
        this.hasChanged=true; //XXX check if really has changed?
    }
}

