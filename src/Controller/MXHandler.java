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

package src.Controller;

import java.awt.Rectangle;
import java.io.File;
import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import src.Model.AudioClip;
import src.Model.GraphicInstance;
import src.Model.GraphicInstanceGroup;
import src.Model.MXData;
import src.Model.Voice;
import src.Util.RectangleExtension;


public class MXHandler extends DefaultHandler {
    public MXData MX;
    
    private Boolean is_maintitle=false;
    private Boolean is_worktitle=false;
    private String crnt_voice=""; //crnt = current
    
    private GraphicInstanceGroup crnt_gInstanceGroup;
    private GraphicInstance crnt_gInstance;
    private AudioClip crnt_audioClip;
    
    public void startDocument() {
        this.MX=new MXData();
    }
    
    public void startElement(String uri, String localName, String qName, Attributes attrs) {
        
        //author saltiamo per ora
        
        if (localName.equals("rest") || localName.equals("chord")){
            this.MX.spine2voice.put(attrs.getValue("event_ref"),this.crnt_voice);
        }
        
        else if (localName.equals("voice")){
            crnt_voice=attrs.getValue("voice_item_ref");
            this.MX.voices.put(crnt_voice,new Voice(crnt_voice));
        }
        
        
        else if (localName.equals("graphic_event")){
            
            int x=new Integer(attrs.getValue("upper_left_x"));
            int y=new Integer(attrs.getValue("upper_left_y"));
            int width=new Integer(attrs.getValue("lower_right_x"))-x;
            int height=new Integer(attrs.getValue("lower_right_y"))-y;
            Rectangle tmpCoord=new Rectangle(x,y,width,height);
            
            crnt_gInstance.getSpine2point().put(attrs.getValue("event_ref"),tmpCoord);
            crnt_gInstance.getTree().insertNewElement(tmpCoord);
            /*crnt_gInstance.tree.insertNewElement(tmpCoord);*/
        }
        
        else if (localName.equals("track_event")){
            Float num=new Float(attrs.getValue("start_time"))*100;
            crnt_audioClip.addSpineTime(attrs.getValue("event_ref"),
                num.intValue());
        }
        
        else if (localName.equals("graphic_instance")){
            crnt_gInstanceGroup.addInstance(new GraphicInstance());
            crnt_gInstance.setRelativeImagePath(attrs.getValue("file_name"));
            //mancano nel nuovo xml spine_start_ref ed end_ref
        }
        
        else if (localName.equals("graphic_instance_group")){
            crnt_gInstanceGroup=this.MX.addGroup(attrs.getValue("description"));
        }
        
        
        else if (localName.equals("track")){
            crnt_audioClip=this.MX.addAudioClip(attrs.getValue("file_name"));
        }
        
        else if (localName.equals("main_title")){
            this.is_maintitle=true;
        }
        else if (localName.equals("work_title")){
            this.is_worktitle=true;
        }
        
        
    }
    
    public void endElement(String uri, String localName, String qName) {
        if (localName.equals("graphic_istance_group")){
//            Collections.sort(crnt_gInstanceGroup.instances);
            this.crnt_gInstanceGroup=null;
            this.crnt_gInstance=null;
        }
        
    }
    
    public void characters(char[] ch, int start, int length) {
        if (is_maintitle) this.MX.movement_title=new String(ch);
        else if (is_worktitle) this.MX.work_title=new String(ch);
    }

    public static MXData parseIt(String path) {
        try {
            MXHandler handler = new MXHandler();
            XMLReader reader = XMLReaderFactory.createXMLReader();
            reader.setContentHandler(handler);
            reader.parse(path);
            
            handler.MX.baseDir=(new File((new File(path)).getAbsolutePath())).getParent();
            
            /*questa parte prende un audio a caso ed individua spineStart e spineEnd
             che ci servono ma non esistono piu' nel nuovo formato MX*/
            
            AudioClip audioClip = null;
            for(AudioClip clip: handler.MX.audioClipDict.values()) {
                audioClip=clip;
                break;
            }
            if (audioClip==null) {
                //we have no audio, at present this is a problem
                return null;
            }
            
            String spineStart, spineEnd;
            int startTime,endTime;
            int num;
            for(GraphicInstanceGroup group: handler.MX.graphic_instance_group.values()) {
                for(GraphicInstance instance: group.getInstances()) {
                    spineStart = null;
                    spineEnd = null;
                    startTime = 999999999;
                    endTime = -1;
                    
                    for(String tmpSpine: instance.getSpine2point().keySet()){
                        try {num = audioClip.getSpine2time().get(tmpSpine);}
                        catch (NullPointerException e) {continue;}
                        if (num<startTime) {
                            startTime=num;
                            spineStart=tmpSpine;
                        }
                        if (num>endTime) {
                            endTime=num;
                            spineEnd=tmpSpine;
                        }
                    }
                    instance.setSpineStart(spineStart);
                    instance.setSpineEnd(spineEnd);
                }
            }
            
            
            return handler.MX;
            
        } catch (org.xml.sax.SAXException e) { 
            System.err.println(e); 
        } catch (java.io.IOException e) { 
            System.err.println(e); 
        }
        return null;
    }
}
