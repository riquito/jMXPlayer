package test.Util;

import static org.junit.Assert.*;
import org.junit.Test;

import src.Util.PathExtension;

public class PathExtensionTest {

	/**
	 * Purpose: Path Join Test 
	 * Input: "/user/bin", "images/flowers/rose1.jpeg"
	 * Expected: "/user/bin/images/flowers/rose1.jpeg"
	 *
	 * return SUCCESS
	 * 
	 * "/user/bin" + "images/flowers/rose1.jpeg"
	 * "/user/bin/images/flowers/rose1.jpeg"
	 */
	@Test
	public void pathJoinTest() {
		assertEquals(PathExtension.join("/user/bin", "images/flowers/rose1.jpeg"),
				"/user/bin/images/flowers/rose1.jpeg");
	}
}
