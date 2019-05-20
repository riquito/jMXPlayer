package src.Model;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

import src.Util.Tools;

public class AudioClip implements Serializable {
	private String relativePath;
    private String basePath;
    private Hashtable<String,Integer> spine2time;
    private Hashtable<Integer,Vector> time2spine;
    private Tree timeRBTree;
    
    public AudioClip() {//String fileAudioPath){
        //this.fileAudioPath=fileAudioPath;
        this.setSpine2time(new Hashtable<String, Integer>());
        this.time2spine = new Hashtable<Integer, Vector>();
        this.setTimeRBTree(new Tree());
    }
    
    public void addSpineTime(String spine, int time){
        this.getSpine2time().put(spine,time);
        
        if (!this.time2spine.containsKey(time)) {
            this.time2spine.put(time, new Vector<String>());
        }
        
        this.time2spine.get(time).add(spine);
        this.getTimeRBTree().put((Comparable)time,null);
    }
    
    public Vector<String> getSpines(int time){
        return this.time2spine.get(time);
    }
    
    public int getTime(String spine){
        return this.getSpine2time().get(spine);
    }
    
    public String getFileAudioPath(){
        return Tools.joinPath(basePath, this.getRelativePath().replace('\\','/'));
    }
    
    public Hashtable<Integer,Vector> getTime2Spine() {
    	return time2spine;
    }

	public Hashtable<String,Integer> getSpine2time() {
		return spine2time;
	}
	
	public void setSpine2time(Hashtable<String,Integer> spine2time) {
		this.spine2time = spine2time;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public Tree getTimeRBTree() {
		return timeRBTree;
	}

	public void setTimeRBTree(Tree timeRBTree) {
		this.timeRBTree = timeRBTree;
	}
}