package test.Util;

import static org.junit.Assert.*;
import org.junit.Test;
import java.awt.Color;
import src.Util.CustomColor;

public class CustomColorTest {

	/**
	* Purpose: Get Next Color 
	* Input: 
	* Expected:
	*
	*	return SUCCESS 
	*	getNextColor instanceof Color
	*/
	@Test
	public void getNextColorTest() {
		assertTrue(CustomColor.getNextColor() instanceof Color);
	}

}
