package test.Util;

import static org.junit.Assert.*;
import org.junit.Test;

import src.Util.RectangleExtension;

import java.awt.Rectangle;

public class RectangleExtensionTest {

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
		int x = 3, y = 3, width = 5, height = 8, ratio = 9;

		Rectangle rectangle = new Rectangle(x, y, width, height);
		Rectangle scaleUpRectangle = RectangleExtension.scale(rectangle, ratio);
		assertTrue((scaleUpRectangle.x == x * ratio) && (scaleUpRectangle.y == y * ratio)
				&& (scaleUpRectangle.width == width * ratio) && (scaleUpRectangle.height == height * ratio));
	}

}
