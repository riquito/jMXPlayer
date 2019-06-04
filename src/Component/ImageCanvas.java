package src.Component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import src.Controller.CanvasResizedListener;

public class ImageCanvas extends JPanel {
	private BufferedImage image = null;
	private EventListenerList listenerList;
	
	public ImageCanvas() {
		super();
		listenerList = new EventListenerList();
	}

	public void setImage(BufferedImage image) {
		this.image = image;
		this.repaint();
	}
	
	// This methods allows classes to register for MyEvents
	public void addCanvasResizeEventListener(CanvasResizedListener listener) {
		listenerList.add(CanvasResizedListener.class, listener);
	}

	// This methods allows classes to unregister for MyEvents
	public void removeCanvasResizeEventListener(CanvasResizedListener listener) {
		listenerList.remove(CanvasResizedListener.class, listener);
	}
}