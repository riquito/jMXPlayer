package test.Component;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

import src.Component.ImageCanvas;

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

}
