package test.Component;

import static org.junit.Assert.assertNotNull;

import javax.swing.ImageIcon;

import org.junit.Test;

import src.Component.ScrollablePicture;

public class ScrollablePictureTest {
	ImageIcon icon = null;
	int maxUnitIncrement = 0;
	ScrollablePicture scrollablePicture;
	
	/**
	 * Purpose: make ScrollablePicture
	 * Input: 
	 * Expected: not Null
	 * 		return SUCCESS
	 * 		scrollablePicture -> not null
	 */
	@Test
	public void testScrollablePicture() {
		scrollablePicture = new ScrollablePicture(icon, maxUnitIncrement);
		
		icon = new ImageIcon("images/icon.png");
		scrollablePicture = new ScrollablePicture(icon, maxUnitIncrement);

		assertNotNull(scrollablePicture);
	}
	
	
}
