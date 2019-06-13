package test.Component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JToggleButton;

import org.junit.Before;
import org.junit.Test;

import src.Component.ImageCanvas;
import src.Component.ToggleButton;
import src.Controller.CanvasResizedListener;

public class ImageCanvasTest {
	
	ImageCanvas imageCanvas;

	/**
	 * Purpose: make image canvas
	 * Input: 
	 * Expected: not Null
	 * 		return SUCCESS
	 * 		ImageCanvas -> not null
	 * 		
	 *
	 */
	@Test
	public void testImageCanvas() {
		imageCanvas = new ImageCanvas();
		assertNotNull(imageCanvas);
	}
	/**
	 * Purpose: set image
	 * Input: 
	 * Expected: not Null
	 * 		return SUCCESS
	 * 		setImage -> not null
	 * 		
	 *
	 */
	@Test
	public void testsetImage() {
		BufferedImage image=null;
		imageCanvas = new ImageCanvas();
		imageCanvas.setImage(image);
		assertNotNull(imageCanvas);
	}
	
	/**
	 * Purpose: add CanvasResizeEventListener
	 * Input: 
	 * Expected: not Null
	 * 		return SUCCESS
	 * 		imageCanvas -> not null
	 * 		
	 *
	 */
	@Test
	public void testaddCanvasResizeEventListener() {
		CanvasResizedListener listener = null;
		imageCanvas.addCanvasResizeEventListener(listener);
		assertNotNull(imageCanvas);
	}
	
	/**
	 * Purpose: remove CanvasResizeEventListener
	 * Input: 
	 * Expected: not Null
	 * 		return SUCCESS
	 * 		imageCanvas -> not null
	 * 		
	 *
	 */
	@Test
	public void testremoveCanvasResizeEventListener() {
		CanvasResizedListener listener = null;
		imageCanvas = new ImageCanvas();
		imageCanvas.removeCanvasResizeEventListener(listener);
		assertNotNull(imageCanvas);
	}
	
}
