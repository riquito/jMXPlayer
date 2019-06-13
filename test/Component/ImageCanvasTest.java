package test.Component;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import src.Component.ImageCanvas;

public class ImageCanvasTest {
	
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
		ImageCanvas imageCanvas = new ImageCanvas();
		assertNotNull(imageCanvas);
	}
}
