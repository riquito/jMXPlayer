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

package mainFrame;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Collections;


class MXData implements Serializable{
    public String work_title;
    public String movement_title;
    public String author;
    public String baseDir;
    
    public class GraphicInstanceGroup {
        public String description;
        
        public class GraphicInstance implements Comparable {
            public String relativePath;
            public Hashtable<String,Coord> spine2point;
            public RTree tree;
            
            public String spineStart=null,spineEnd=null;
            
            public GraphicInstanceGroup group=null;
            private int index=-1;
            
            public GraphicInstance(){
                this.tree=new RTree(4);
                this.spine2point=new Hashtable<String,Coord>();
            }
            
            public GraphicInstance getNext() {
                try {return this.group.instances.get(this.index+1);}
                catch (ArrayIndexOutOfBoundsException e) {return null;}
            }
            
            public GraphicInstance getPrev() {
                try {return this.group.instances.get(this.index-1);}
                catch (ArrayIndexOutOfBoundsException e) {return null;}
            }
            
            public String getImagePath(){
                return Tools.joinPath(baseDir,this.relativePath.replace('\\','/'));
            }
            
            public int compareTo(Object o) {
            	//escludendo a priori caso uguale...
                return this.index>((GraphicInstance)o).index?1:-1;
            }
        }
        
        public Vector<GraphicInstance> instances;
        
        public GraphicInstanceGroup(String description) {
            this.instances=new Vector<GraphicInstance>();
            this.description=description;
        }
        
        public void setNumInstances(int maxSize){
            this.instances.setSize(maxSize);
        }
        
        public GraphicInstance addInstance(int index){
            GraphicInstance tmp=new GraphicInstance();
            tmp.group=this;
            tmp.index=index-1;
            this.instances.add(tmp);
            Collections.sort(this.instances);
           
            return tmp;
        }
         
    }
    
    public class AudioClip implements Serializable{
        public String relativePath;
        public Hashtable<String,Integer> spine2time;
        public Hashtable<Integer,Vector> time2spine;
        public Tree timeRBTree;
        
        public AudioClip(){//String fileAudioPath){
            //this.fileAudioPath=fileAudioPath;
            this.spine2time=new Hashtable<String,Integer>();
            this.time2spine=new Hashtable<Integer,Vector>();
            this.timeRBTree=new Tree();
        }
        
        public void addSpineTime(String spine, int time){
            this.spine2time.put(spine,time);
            
            if (!this.time2spine.containsKey(time)) {
                this.time2spine.put(time,new Vector<String>());
            }
            this.time2spine.get(time).add(spine);
            this.timeRBTree.put((Comparable)time,null);
        }
        
        public Vector<String> getSpines(int time){
            return this.time2spine.get(time);
        }
        
        public int getTime(String spine){
            return this.spine2time.get(spine);
        }
        
        public String getFileAudioPath(){
            return  Tools.joinPath(baseDir,this.relativePath.replace('\\','/'));
        }
    }
    
    //collezioni di dati fondamentali, accessibili a tutti
    public Hashtable<String,GraphicInstanceGroup> graphic_instance_group;
    public Hashtable<String,AudioClip> audioClipDict;
    public Hashtable<String,String> spine2voice;
    public Hashtable<String,Voice> voices;
    
    public MXData (){
        this.graphic_instance_group=new Hashtable<String,GraphicInstanceGroup>();
        this.audioClipDict=new Hashtable<String,AudioClip>();
        this.spine2voice=new Hashtable<String,String>();
        this.voices=new Hashtable<String,Voice>();
    }
    
    public GraphicInstanceGroup addGroup(String description){
        GraphicInstanceGroup group=new GraphicInstanceGroup(description);
        this.graphic_instance_group.put(description,group);
        return group;
    }
    
    public AudioClip addAudioClip(String relativePath){
        AudioClip clip=new AudioClip();
        clip.relativePath=relativePath;
        this.audioClipDict.put(relativePath,clip);
        return clip;
    }
    
}

