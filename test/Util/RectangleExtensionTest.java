package test.Util;

import static org.junit.Assert.*;
import org.junit.Test;

import src.Util.RectangleExtension;

import java.awt.Rectangle;

public class RectangleExtensionTest {
	int x, y, width, height, ratio;
	Rectangle rectangle;
	
	@Before
	public void setUpRectangleAndFeature() {
		x = 3;
		y = 3; 
		width = 5;
		height = 8;
		ratio = 9;
		
		rectangle = new Rectangle(x, y, width, height);
	}

	/**
	 * Purpose: change rectangle's scale with ratio 
	 * Input: Rectangle = (x = 3, y = 3, width = 5, height = 8), ratio = 9 
	 * Expected: Rectangle = (x = 27, y = 27, width = 45, height = 72) 
	 *
	 * return SUCCESS
	 * 
	 * Rectangle = (x = 3, y = 3, width = 5, height = 8) changed
	 * Rectangle = (x = 27, y = 27, width = 45, height = 72)
	 */
	@Test
	public void rectangleScaleUpTest() {
		Rectangle scaleUpRectangle = RectangleExtension.scale(rectangle, ratio);
		assertTrue((scaleUpRectangle.x == x * ratio) && (scaleUpRectangle.y == y * ratio)
				&& (scaleUpRectangle.width == width * ratio) && (scaleUpRectangle.height == height * ratio));
	}

}
