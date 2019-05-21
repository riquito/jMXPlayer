package src.Model;

import java.awt.Rectangle;
import java.util.Hashtable;

import src.Util.Tools;

public class GraphicInstance {
	private String relativeImagePath;
	private Hashtable<String, Rectangle> spine2point;
	private RTree tree;
	private String basePath;
	private String spineStart = null;
	private String spineEnd = null;

	public GraphicInstance() {
		this.setTree(new RTree(4));
		this.setSpine2point(new Hashtable<String, Rectangle>());
	}
	
	public void setBasePath(String imagePath) {
		this.basePath = imagePath;
	}
	
	public void setRelativeImagePath(String relativeImagePath) {
		this.relativeImagePath = relativeImagePath;
	}

	public String getImagePath(){
		return Tools.joinPath(basePath, this.relativeImagePath.replace('\\','/'));
	}

	public RTree getTree() {
		return tree;
	}

	public void setTree(RTree tree) {
		this.tree = tree;
	}

	public Hashtable<String, Rectangle> getSpine2point() {
		return spine2point;
	}

	public void setSpine2point(Hashtable<String, Rectangle> spine2point) {
		this.spine2point = spine2point;
	}

	public String getSpineStart() {
		return spineStart;
	}

	public void setSpineStart(String spineStart) {
		this.spineStart = spineStart;
	}

	public String getSpineEnd() {
		return spineEnd;
	}

	public void setSpineEnd(String spineEnd) {
		this.spineEnd = spineEnd;
	}
}